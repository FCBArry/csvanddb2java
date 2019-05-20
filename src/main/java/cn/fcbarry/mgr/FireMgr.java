package cn.fcbarry.mgr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.fcbarry.javabean.csv.FireBean;
import cn.fcbarry.csv2bean.CSV2JavaBean;

public class FireMgr
{
    private static volatile Map<Integer, FireBean> map;

    public static boolean init()
    {
        return reload();
    }

    public static boolean reload()
    {
        try
        {
            Map<Integer, FireBean> newMap = new HashMap<>();
            List<FireBean> list = CSV2JavaBean.getBeanList(FireBean.class);

            for (FireBean bean : list)
            {
                newMap.put(bean.getId(), bean);
            }

            map = newMap;
        }
        catch (Exception e)
        {
            return false;
        }

        return true;
    }

    public static FireBean getFireBean(int id)
    {
        return map.get(id);
    }
}
