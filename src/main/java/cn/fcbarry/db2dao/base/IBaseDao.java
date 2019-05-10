package cn.fcbarry.db2dao.base;

import java.util.List;

/**
 *
 * IBaseDao接口
 *
 * @author 科兴第一盖伦
 * @version 2019/5/10
 */
public interface IBaseDao<T>
{
    boolean add(T t);

    boolean addOrUpdate(T t);

    int[] addOrUpdateBatch(List<T> t);

    boolean delete(T t);

    int[] deleteBatch(List<T> t);

    boolean deleteByKey(Object... ids);

    T getByKey(Object... ids);

    List<T> listAll();

    T query(String sql, DBParamWrapper paramWrapper);

    List<T> queryList(String sql);

    List<T> queryList(String sql, DBParamWrapper paramWrapper);

    boolean update(String sql, DBParamWrapper paramWrapper);

    boolean update(T t);
}
