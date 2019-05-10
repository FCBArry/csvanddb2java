package cn.fcbarry.db2dao;

import cn.fcbarry.db2dao.base.DBHelper;
import cn.fcbarry.db2dao.base.hikariPool.HikariCPPool;
import cn.fcbarry.db2dao.gen.dao.ISecretInfoDao;
import cn.fcbarry.db2dao.gen.dao.impl.SecretInfoDaoImpl;

/**
 *
 * dao mgr
 *
 * @author 科兴第一盖伦
 * @version 2019/5/10
 */
public class DaoMgr
{
    private static final DBHelper dBHelper = new DBHelper(new HikariCPPool(true));

    private static final ISecretInfoDao secretInfoDao = new SecretInfoDaoImpl(dBHelper);

    public static ISecretInfoDao getSecretInfoDao()
    {
        return secretInfoDao;
    }
}
