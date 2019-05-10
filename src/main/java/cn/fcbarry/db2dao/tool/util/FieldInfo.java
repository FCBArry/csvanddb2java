package cn.fcbarry.db2dao.tool.util;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * gen tool
 *
 * @author 科兴第一盖伦
 * @version 2019/5/10
 * @author from fei.wang(https://github.com/wang35666)
 */
@Getter
@Setter
public class FieldInfo
{
    /**
     * 字段名称
     */
    private String name;

    /**
     * 字段Java类型
     */
    private String javaType;

    /**
     * 字段数据库类型
     */
    private String sqlType;

    /**
     * 字段值长度
     */
    private int len;

    /**
     * 字段注释
     */
    private String comment;

    /**
     * 是否是自增长
     */
    private boolean isAotuIncreamte = false;

    /**
     * 是否是主键
     */
    private boolean isPrimaryKey = false;

    /**
     * 标志是否加更改说明
     */
    private boolean flag = false;

    public String getLowerName()
    {
        int offset1 = name.offsetByCodePoints(0, 1);
        return name.substring(0, offset1).toLowerCase() +
                name.substring(offset1);
    }

    public String getUpperName()
    {
        int offset1 = name.offsetByCodePoints(0, 1);
        return name.substring(0, offset1).toUpperCase() +
                name.substring(offset1);
    }
    
    public String getParamsType()
    {
        if (sqlType.equals("INT"))
            return "INTEGER";
        if (sqlType.equals("DATETIME"))
            return "TIMESTAMP";
        
        return sqlType;
    }
    
    public String getUpperJavaTypeName()
    {
        if (javaType.equals("int"))
            return "Int";
        if (javaType.equals("float"))
            return "Float";
        if (javaType.equals("long"))
            return "Long";
        if (javaType.equals("boolean"))
            return "Boolean";
        if (javaType.equals("Date"))
            return "Timestamp";
        if (javaType.equals("java.math.BigDecimal"))
            return "BigDecimal";
        
        return javaType;
    }
}
