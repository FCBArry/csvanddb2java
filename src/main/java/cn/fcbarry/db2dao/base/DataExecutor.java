package cn.fcbarry.db2dao.base;

import java.sql.PreparedStatement;

/**
 *
 * db执行statement接口
 *
 * @author 科兴第一盖伦
 * @version 2019/5/9
 */
public interface DataExecutor<T>
{
    T execute(PreparedStatement statement, Object... objects) throws Exception;
}
