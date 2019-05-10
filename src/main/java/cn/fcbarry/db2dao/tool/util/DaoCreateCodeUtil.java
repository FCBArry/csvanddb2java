package cn.fcbarry.db2dao.tool.util;

import java.util.List;

/**
 *
 * gen tool
 *
 * @author 科兴第一盖伦
 * @version 2019/5/10
 * @author from fei.wang(https://github.com/wang35666)
 */
public class DaoCreateCodeUtil
{
    public static String createInsertSql(String tableName, List<FieldInfo> fields)
    {
        StringBuffer sql = new StringBuffer();
        sql.append("\"insert into " + tableName);
        sql.append("(");
        for (int i = 0; i < fields.size(); i++)
        {
            if (!fields.get(i).isAotuIncreamte())
            {
                sql.append("`").append(fields.get(i).getName()).append("`, ");
            }
        }

        sql.delete(sql.length() - 2, sql.length());
        sql.append(") values(");

        for (int i = 0; i < fields.size(); i++)
        {
            if (!fields.get(i).isAotuIncreamte())
            {
                sql.append("?, ");
            }
        }

        sql.delete(sql.length() - 2, sql.length());
        sql.append(");\"");
        return sql.toString();
    }

    public static String createUpdateSql(String tableName, List<FieldInfo> fields)
    {
        StringBuffer sql = new StringBuffer();
        sql.append("\"update " + tableName + " set ");
        for (int i = 0; i < fields.size(); i++)
        {
            if (fields.get(i).isPrimaryKey())
            {
                continue;
            }
            sql.append("`").append(fields.get(i).getName()).append("`=?, ");
        }

        sql.delete(sql.length() - 2, sql.length());
        sql.append(" where ");

        for (int i = 0; i < fields.size(); i++)
        {
            if (!fields.get(i).isPrimaryKey())
            {
                continue;
            }

            sql.append("`").append(fields.get(i).getName()).append("`=?");
            sql.append(" and ");
        }

        if (sql.lastIndexOf(" and ") > 0)
        {
            sql.delete(sql.length() - 5, sql.length());
        }

        sql.append(";\"");
        return sql.toString();
    }

    public static String createaddOrUpdateSql(String tableName, List<FieldInfo> fields)
    {
        StringBuffer sql = new StringBuffer();
        sql.append("\"insert into " + tableName);
        sql.append("(");
        for (int i = 0; i < fields.size(); i++)
        {
            // if (!fields.get(i).isAotuIncreamte())
            // {
            sql.append("`").append(fields.get(i).getName()).append("`, ");
            // }

        }

        sql.delete(sql.length() - 2, sql.length());
        sql.append(") values(");

        for (int i = 0; i < fields.size(); i++)
        {
            // if (!fields.get(i).isAotuIncreamte())
            // {
            sql.append("?, ");
            // }
        }

        sql.delete(sql.length() - 2, sql.length());
        sql.append(") on DUPLICATE KEY update ");

        for (int i = 0; i < fields.size(); i++)
        {
            if (!fields.get(i).isPrimaryKey())
            {
                sql.append("`" + fields.get(i).getName() + "`=?,");
            }
        }

        return sql.substring(0, sql.length() - 1) + ";\"";
    }

    public static String createDeleteSql(String tableName, List<FieldInfo> fields)
    {
        StringBuffer sql = new StringBuffer();
        sql.append("\"delete from " + tableName);
        sql.append(" where ");
        for (int i = 0; i < fields.size(); i++)
        {
            if (!fields.get(i).isPrimaryKey())
            {
                continue;
            }

            sql.append("`").append(fields.get(i).getName()).append("`=?");
            sql.append(" and ");
        }

        if (sql.lastIndexOf(" and ") > 0)
        {
            sql.delete(sql.length() - 5, sql.length());
        }

        sql.append(";\"");
        return sql.toString();
    }

    public static String createGetByIDSql(String tableName, List<FieldInfo> fields)
    {
        StringBuffer sql = new StringBuffer();
        sql.append("\"select * from " + tableName);
        sql.append(" where ");
        for (int i = 0; i < fields.size(); i++)
        {
            if (!fields.get(i).isPrimaryKey())
            {
                continue;
            }

            sql.append("`").append(fields.get(i).getName()).append("`=?");
            sql.append(" and ");
        }

        if (sql.lastIndexOf(" and ") > 0)
        {
            sql.delete(sql.length() - 5, sql.length());
        }

        sql.append(";\"");
        return sql.toString();
    }
}
