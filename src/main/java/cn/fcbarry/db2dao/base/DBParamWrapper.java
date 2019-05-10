package cn.fcbarry.db2dao.base;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * sql脚本参数的包装类
 *
 * @author 科兴第一盖伦
 * @version 2019/5/10
 */
public class DBParamWrapper
{
    private List<Object> dbParamList = null;

    public DBParamWrapper()
    {
        dbParamList = new ArrayList<Object>(30);
    }

    public List<Object> getParams()
    {
        return dbParamList;
    }

    public void put(int type, Object dbData)
    {
        dbParamList.add(dbData);
    }
}
