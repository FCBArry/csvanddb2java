package cn.fcbarry.db2dao.base;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.pool2.ObjectPool;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * BaseDao抽象类
 *
 * @author 科兴第一盖伦
 * @version 2019/5/10
 */
@Getter
@Setter
public abstract class BaseDao<T>
{
    protected DBHelper dbHelper;

    protected ObjectPool<T> pool;

    public BaseDao(DBHelper helper)
    {
        setDbHelper(helper);
    }

    public T query(String sql)
    {
        return query(sql, null);
    }

    public T query(String sql, DBParamWrapper paramWrapper)
    {
        T result = getDbHelper().executeQuery(sql, paramWrapper,
                new DataReader<T>()
                {
                    @Override
                    public T readData(ResultSet rs, Object... objects) throws Exception
                    {
                        if (rs.last())
                        {
                            return BaseDao.this.rsToEntity(rs);
                        }

                        return null;
                    }
                });

        return result;
    }

    public List<T> queryList(String sql)
    {
        return queryList(sql, null);
    }

    public List<T> queryList(String sql, DBParamWrapper paramWrapper)
    {
        List<T> entitis = getDbHelper().executeQuery(sql, paramWrapper,
                new DataReader<List<T>>()
                {
                    @Override
                    public List<T> readData(ResultSet rs, Object... objects) throws Exception
                    {
                        return BaseDao.this.rsToEntityList(rs);
                    }
                });

        return entitis;
    }

    @SuppressWarnings("unchecked")
    public <K> Map<K, T> queryMap(String sql, DBParamWrapper paramWrapper, Object... key)
    {
        Map<K, T> resultMap = getDbHelper().executeQuery(sql, paramWrapper,
                new DataReader<Map<K, T>>()
                {
                    @Override
                    public Map<K, T> readData(ResultSet rs, Object... objects) throws Exception
                    {
                        Map<K, T> resultMap = new HashMap<>();
                        while (rs.next())
                        {
                            if (objects.length > 1)
                            {
                                String hashKey = "";
                                for (Object string : objects)
                                {
                                    hashKey += rs.getObject((String) string) + "_";
                                }

                                hashKey = hashKey.substring(0, hashKey.length() - 1);
                                resultMap.put((K) hashKey, rsToEntity(rs));
                            }
                            else if (objects.length == 1)
                            {
                                resultMap.put((K) rs.getObject((String) objects[0]), rsToEntity(rs));
                            }
                        }

                        return resultMap;
                    }
                }, key);

        return resultMap;
    }

    protected abstract T rsToEntity(ResultSet rs) throws Exception;

    protected List<T> rsToEntityList(ResultSet rs)
    {
        List<T> entitis = new ArrayList<T>();
        if (rs != null)
        {
            try
            {
                while (rs.next())
                {
                    T entity = rsToEntity(rs);
                    entitis.add(entity);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        return entitis;
    }

    public boolean update(String sql, DBParamWrapper paramWrapper)
    {
        return getDbHelper().execNoneQuery(sql, paramWrapper) > -1;
    }
}
