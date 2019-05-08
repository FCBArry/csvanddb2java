package cn.fcbarry.db2bean.orm;

import it.biobytes.ammentos.Ammentos;
import it.biobytes.ammentos.PersistenceException;
import it.biobytes.ammentos.query.Query;

import java.util.List;

/**
 *
 * Ammentos
 *
 * @author 科兴第一盖伦
 * @version 2019/5/6
 */
public class Ammentos2Bean
{
    public static<T> List<T> listAll(Class<T> classname)
    {
        try
        {
            return Ammentos.load(classname, new Query());
        }
        catch (PersistenceException e)
        {
            System.out.println("获取数据失败: " + e.toString());
            return null;
        }
    }
}
