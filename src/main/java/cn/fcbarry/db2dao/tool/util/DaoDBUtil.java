package cn.fcbarry.db2dao.tool.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

/**
 *
 * gen tool
 *
 * @author 科兴第一盖伦
 * @version 2019/5/10
 * @author from fei.wang(https://github.com/wang35666)
 */
public class DaoDBUtil
{
    private Properties prop;

    public DaoDBUtil(Properties prop)
    {
        this.prop = prop;
    }

    public Connection getConn() throws ClassNotFoundException, SQLException
    {
        Class.forName(prop.getProperty("driverName"));
        Connection conn = DriverManager.getConnection(prop.getProperty("jdbcUrl"),
                prop.getProperty("username"), prop.getProperty("password"));
        return conn;
    }

    public Connection getConn(String dbName) throws ClassNotFoundException, SQLException
    {
        Class.forName(prop.getProperty("driverName"));
        Connection conn = DriverManager.getConnection(prop.getProperty("jdbcUrl")
                + "/" + dbName, prop.getProperty("username"), prop.getProperty("password"));
        return conn;
    }

    public void close(ResultSet rs, Statement stmt, Connection conn)
    {
        if (rs != null)
        {
            try
            {
                rs.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

        if (stmt != null)
        {
            try
            {
                stmt.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

        if (conn != null)
        {
            try
            {
                conn.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    public List<String> getDatabases()
    {
        Connection conn = null;
        DatabaseMetaData metaData = null;
        ResultSet rs = null;
        List<String> databases = new ArrayList<String>();
        try
        {
            conn = getConn();
            metaData = conn.getMetaData();
            rs = metaData.getCatalogs();
            while (rs.next())
            {
                String database = rs.getString("TABLE_CAT");
                System.err.println(database);
                databases.add(database);
            }
        }
        catch (SQLException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        finally
        {
            close(rs, null, conn);
        }

        return databases;
    }

    public List<String> getTables(String dbName)
    {
        Connection conn = null;
        DatabaseMetaData metaData = null;
        ResultSet rs = null;
        List<String> tables = new ArrayList<String>();
        try
        {
            conn = getConn(dbName);
            metaData = conn.getMetaData();
            rs = metaData.getTables(null, null, null, new String[]
            { "TABLE" });

            while (rs.next())
            {
                String tableName = rs.getString("TABLE_NAME");
                tables.add(tableName);
            }
        }
        catch (SQLException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        finally
        {
            close(rs, null, conn);
        }

        return tables;
    }

    public Map<String, FieldInfo> getTableFieldList(String dbName, String tableName)
    {
        Map<String, FieldInfo> fieldMap = new LinkedHashMap<String, FieldInfo>();
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        ResultSetMetaData rsMetaData = null;
        try
        {
            conn = getConn("information_schema");
            String sql = "SELECT * FROM COLUMNS WHERE TABLE_SCHEMA='" + dbName + "' AND TABLE_NAME='" + tableName + "'";
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();

            while (rs.next())
            {
                if (!rs.getString("column_comment").contains("无用"))
                {
                    FieldInfo field = new FieldInfo();
                    field.setName(rs.getString("column_name"));
                    field.setComment(rs.getString("column_comment"));
                    fieldMap.put(rs.getString("column_name"), field);
                }
            }

            conn = getConn(dbName);
            sql = "select * from " + tableName + " where 1<0";
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            rsMetaData = rs.getMetaData();
            pstm.getParameterMetaData();

            int cols = rsMetaData.getColumnCount();
            for (int i = 1; i <= cols; i++)
            {
                String name = rsMetaData.getColumnName(i);

                String javaType = rsMetaData.getColumnClassName(i);
                String SqlType = rsMetaData.getColumnTypeName(i);

                if (SqlType.contains("UNSIGNED"))
                {
                    SqlType = SqlType.replace("UNSIGNED", "").trim();
                }

                if (javaType == "[B")
                {
                    javaType = "byte[]";
                }
                else if (javaType == "java.lang.Boolean")
                {
                    javaType = "boolean";
                }
                else if (javaType == "java.lang.Integer")
                {
                    javaType = "int";
                }
                else if (javaType == "java.lang.Long")
                {
                    javaType = "long";
                }
                else if (javaType == "java.lang.Float")
                {
                    javaType = "float";
                }
                else if (javaType == "java.lang.String")
                {
                    javaType = "String";
                }
                else if (javaType == "java.sql.Timestamp")
                {
                    javaType = "Date";
                }
                else if (javaType == "java.math.BigInteger")
                {
                    javaType = "long";
                }

                if (fieldMap.get(name) != null)
                {
                    fieldMap.get(name).setJavaType(javaType);

                    fieldMap.get(name).setSqlType(SqlType);

                    fieldMap.get(name).setLen(
                            (rsMetaData.getColumnDisplaySize(i)));
                }

                ResultSet pkRSet = conn.getMetaData().getPrimaryKeys(null, dbName, tableName);
                while (pkRSet.next())
                {
                    FieldInfo key = fieldMap.get(pkRSet.getObject(4));
                    if (key != null)
                    {
                        key.setPrimaryKey(true);
                    }
                }
            }

            Set<Entry<String, FieldInfo>> set = fieldMap.entrySet();
            for (Entry<String, FieldInfo> entry : set)
            {
                FieldInfo fieldInfo = entry.getValue();
                if (!fieldInfo.isPrimaryKey())
                {
                    continue;
                }

                String clmName = fieldInfo.getName();
                ResultSet clmSet = conn.getMetaData().getColumns(null, dbName,
                        tableName, clmName);
                while (clmSet.next())
                {
                    String flag = (String) clmSet.getObject("IS_AUTOINCREMENT");
                    if (flag.equals("YES"))
                    {
                        fieldInfo.setAotuIncreamte(true);
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            close(rs, pstm, conn);
        }

        return fieldMap;
    }
}
