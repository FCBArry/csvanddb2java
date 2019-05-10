package cn.fcbarry.db2dao.base;

import java.sql.ResultSet;

/**
 *
 * db查询结果集数据读取接口
 *
 * @author 科兴第一盖伦
 * @version 2019/5/9
 */
public interface DataReader<T>
{
    T readData(ResultSet rs, Object... objects) throws Exception;
}
