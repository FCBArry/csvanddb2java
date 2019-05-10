package cn.fcbarry.db2dao.base;

import cn.fcbarry.db2dao.base.bonePool.BoneCPDBPool;
import cn.fcbarry.db2dao.base.hikariPool.HikariCPPool;

/**
 *
 * dbPool工厂类
 *
 * @author 科兴第一盖伦
 * @version 2019/5/9
 */
public class DBDataSourceFactory
{
    public static IPool createDBPool(int poolType)
    {
        IPool pool = null;
        switch (poolType)
        {
            case 1:
                pool = new HikariCPPool();
                break;

            case 2:
                pool = new BoneCPDBPool();
                break;

            default:
                System.out.println("no type:" + poolType);
                break;
        }

        return pool;
    }
}
