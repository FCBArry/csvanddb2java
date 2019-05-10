package cn.fcbarry.db2dao.base;

/**
 *
 * Dao层数据库执行情况观察对象
 *
 * @author 科兴第一盖伦
 * @version 2019/5/10
 */
public class DBWatcher
{
    private long end = 0;

    private long first = 0;

    private long second = 0;

    public DBWatcher() {
        first = System.currentTimeMillis();
    }

    public void keeyRecords(String procName, DBParamWrapper params)
    {
        long spendTime = end - first;
        if (spendTime > 100)
        {
            if (params != null)
                procName = DBHelper.pstmtFormat(procName, params);

            System.out.println("speed too much sql time:" +procName);
        }
    }


    public void watchGetConnector() {
        second = System.currentTimeMillis();
    }

    public void watchSqlExec() {
        end = System.currentTimeMillis();
    }
}
