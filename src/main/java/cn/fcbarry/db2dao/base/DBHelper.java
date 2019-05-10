package cn.fcbarry.db2dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 *
 * db工具类
 *
 * @author 科兴第一盖伦
 * @version 2019/5/10
 */
public class DBHelper
{
    private IPool pool;

    public DBHelper(IPool pool)
    {
        this.pool = pool;
    }

    private void closeConn(Connection conn)
    {
        try
        {
            if (conn != null && !conn.isClosed())
            {
                conn.close();
                conn = null;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private void closeConn(Connection conn, Statement stmt)
    {
        try
        {
            if (stmt != null)
            {
                if (stmt instanceof PreparedStatement)
                {
                    ((PreparedStatement) stmt).clearParameters();
                }

                stmt.close();
                stmt = null;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            closeConn(conn);
        }
    }

    private void closeConn(Connection conn, Statement stmt, ResultSet rs)
    {
        try
        {
            if (rs != null)
            {
                rs.close();
                rs = null;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            closeConn(conn, stmt);
        }
    }

    public int execNoneQuery(String sql)
    {
        return execNoneQuery(sql, null);
    }

    public int execNoneQuery(String sql, DBParamWrapper params)
    {
        int result = 0;
        DBWatcher watcher = new DBWatcher();
        Connection conn = openConn();
        if (conn == null)
            return result;

        watcher.watchGetConnector();
        PreparedStatement pstmt = null;
        try
        {
            pstmt = conn.prepareStatement(sql);
            prepareCommand(pstmt, params);
            result = pstmt.executeUpdate();
            watcher.watchSqlExec();
        }
        catch (SQLException e)
        {
            String strSQL = pstmtFormat(sql, params);
            System.out.println("SMS-Alarm sqlExec:" + strSQL);
            System.out.println("execNoneQuery error:" + e);
        }
        finally
        {
            closeConn(conn, pstmt);
            watcher.keeyRecords(sql, params);
        }

        return result;
    }

    public int executeCountQuery(String sql)
    {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try
        {
            conn = openConn();
            if (null == conn)
                return 0;

            pstmt = conn.prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next())
                return rs.getInt(1);

            return 0;
        }
        catch (Exception e)
        {
            System.out.println("SMS-Alarm:sqlExec:{}" + sql);
            System.out.println("execNoneQuery error:" + e);
        }
        finally
        {
            closeConn(conn, pstmt);
        }

        return 0;
    }

    public <T> T executeQuery(String sql, DataExecutor<T> executor)
    {
        return executeQuery(sql, null, executor);
    }

    public <T> T executeQuery(String sql, DataReader<T> reader)
    {
        return executeQuery(sql, null, reader);
    }

    public <T> T executeQuery(String sql, DBParamWrapper params, DataExecutor<T> executor, Object... objects)
    {
        DBWatcher watcher = new DBWatcher();
        Connection conn = openConn();
        if (null == conn)
            return null;

        watcher.watchGetConnector();
        PreparedStatement pstmt = null;
        T resultData = null;
        try
        {
            pstmt = conn.prepareStatement(sql);
            prepareCommand(pstmt, params);
            resultData = executor.execute(pstmt, objects);
            watcher.watchSqlExec();
        }
        catch (Exception e)
        {
            System.out.println("SMS-Alarm:sqlExec:{}" + sql);
            System.out.println("execNoneQuery error:" + e);
        }
        finally
        {
            closeConn(conn, pstmt);
            watcher.keeyRecords(sql, params);
        }

        return resultData;
    }

    public <T> T executeQuery(String sql, DBParamWrapper params, DataReader<T> reader, Object... objects)
    {
        DBWatcher watcher = new DBWatcher();
        Connection conn = openConn();
        if (null == conn)
            return null;

        watcher.watchGetConnector();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        T resultData = null;

        try
        {
            pstmt = conn.prepareStatement(sql);
            prepareCommand(pstmt, params);
            rs = pstmt.executeQuery();
            resultData = reader.readData(rs, objects);
            watcher.watchSqlExec();
        }
        catch (Exception e)
        {
            System.out.println("SMS-Alarm:sqlExec:{}" + sql);
            System.out.println("execNoneQuery error:" + e);
        }
        finally
        {
            closeConn(conn, pstmt, rs);
            watcher.keeyRecords(sql, params);
        }

        return resultData;
    }

    private Connection openConn()
    {
        try
        {
            return pool.getConnection();
        }
        catch (Exception e)
        {
            System.out.println("SMS-Alarm:openConn");
            System.out.println("openConn error:" + e);
            return null;
        }
    }

    public PreparedStatement prepareCommand(PreparedStatement pstmt, DBParamWrapper paramWrapper) throws SQLException
    {
        if (pstmt == null || paramWrapper == null)
            return null;

        List<Object> dbParamList = paramWrapper.getParams();
        if (dbParamList == null)
            return null;

        for (int i = 0; i < dbParamList.size(); i++)
        {
            pstmt.setObject(i + 1, dbParamList.get(i));
        }

        return pstmt;
    }

    public int sqlBatch(List<String> sqlComm)
    {
        int result = 0;
        Connection conn = openConn();
        if (conn == null)
            return result;

        int[] results = null;
        Statement stmt = null;
        String strSQL = "";
        try
        {
            stmt = conn.createStatement();
            for (int i = 0; i < sqlComm.size(); i++)
            {
                stmt.addBatch(sqlComm.get(i));
                strSQL = strSQL + ", i:" + sqlComm.get(i);
            }

            results = stmt.executeBatch();
        }
        catch (SQLException e)
        {
            System.out.println("SMS-Alarm:sqlExec:{}" + strSQL);
            System.out.println("execNoneQuery error:" + e);
        }
        finally
        {
            closeConn(conn, stmt);
        }

        if (results != null)
        {
            for (int i = 0; i < results.length; i++)
            {
                result += results[i];
            }
        }

        return result;
    }

    public <T, V> T sqlBatch(String sql, List<V> entities, DataExecutor<T> executor)
    {
        DBWatcher watcher = new DBWatcher();
        Connection conn = openConn();
        if (conn == null)
            return null;

        watcher.watchGetConnector();
        PreparedStatement pstmt = null;
        try
        {
            pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            T result = executor.execute(pstmt, entities);
            watcher.watchSqlExec();
            return result;
        }
        catch (Exception e)
        {
            System.out.println("SMS-Alarm:sqlExec:{}" + sql);
            System.out.println("execNoneQuery error:" + e);
        }
        finally
        {
            closeConn(conn, pstmt);
            watcher.keeyRecords(sql, null);
        }

        return null;
    }

    public static String pstmtFormat(String sql, DBParamWrapper paramWrapper)
    {
        if (sql == null || paramWrapper == null)
            return "";

        List<Object> dbParamList = paramWrapper.getParams();
        if (dbParamList == null)
            return sql;

        String resSQL = String.copyValueOf(sql.toCharArray());
        for (Object aDbParamList : dbParamList)
        {
            String strValue = "";
            Object dbData = aDbParamList;
            if (null == dbData)
                strValue = "'null'";
            else
                strValue = String.format("'%s'", dbData.toString());

            resSQL = resSQL.replaceFirst("\\?", strValue);
        }

        return resSQL;
    }
}
