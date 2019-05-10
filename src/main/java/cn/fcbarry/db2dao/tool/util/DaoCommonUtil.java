package cn.fcbarry.db2dao.tool.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 *
 * gen tool
 *
 * @author 科兴第一盖伦
 * @version 2019/5/10
 * @author from fei.wang(https://github.com/wang35666)
 */
public class DaoCommonUtil
{
    public static Map<String, String> entitySuffix = new HashMap<>();

    public static Map<String, String> entitySubPath = new HashMap<>();

    public static String rootPath = "";

    public static String entityPath = "";

    public static String interfacePath = "";

    public static String implPath = "";

    public static String protoPath = "";

    public static String default_orm_entityPackage = "cn.fcbarry.db2dao.gen.entity";

    public static void reload(Properties prop)
    {
        entitySuffix.put("t_s", "Bean");
        entitySuffix.put("t_u", "Info");
        entitySuffix.put("t_p", "Data");

        rootPath = prop.getProperty("rootPath");
        entityPath = prop.getProperty("entityPath");
        interfacePath = prop.getProperty("interfacePath");
        implPath = prop.getProperty("implPath");
        protoPath = prop.getProperty("protoPath");
        default_orm_entityPackage = prop.getProperty("orm_entityPackage");

        String suffix = prop.getProperty("entitySuffix");
        String[] _suffixs = suffix.split(",");
        for (String string : _suffixs)
        {
            String[] _suffix = string.split(":");
            entitySuffix.put(_suffix[0], _suffix[1]);
            entitySubPath.put(_suffix[0], _suffix[2]);
        }
    }

    public static String toLowerName(String entityName)
    {
        return entityName.substring(0, 1).toLowerCase() + entityName.substring(1);
    }

    public static String toUpperName(String entityName)
    {
        return entityName.substring(0, 1).toUpperCase() + entityName.substring(1);
    }

    public static String generateEntityNameBySourceTable(String tableName)
    {
        String[] sub = tableName.split("_");
        String _tableName = "";
        if (sub.length < 2)
        {
            return tableName;
        }

        for (int i = 2; i < sub.length; i++)
        {
            _tableName += toUpperName(sub[i]);
        }

        return _tableName;
    }
}
