package cn.fcbarry.db2dao.base;

import java.sql.Connection;

/**
 *
 * pool interface
 *
 * @author 科兴第一盖伦
 * @version 2019/5/9
 */
public interface IPool
{
    boolean startup();

    Connection getConnection();

    void shutdown();

    boolean validConnection();
}
