/**
 *
 * 字段对象类
 *
 * @author 科兴第一盖伦
 * @version 2019/4/28
 */
public class FieldInfo
{
    /**
     * 字段名称
     */
    private String name;

    /**
     * 字段类型
     */
    private String javaType;

    /**
     * 字段注释
     */
    private String comment;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getJavaType()
    {
        return javaType;
    }

    public void setJavaType(String javaType)
    {
        this.javaType = javaType;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public String getLowerName()
    {
        int offset = name.offsetByCodePoints(0, 1);
        return name.substring(0, offset).toLowerCase() + name.substring(offset);
    }

    public String getUpperName()
    {
        int offset = name.offsetByCodePoints(0, 1);
        return name.substring(0, offset).toUpperCase() + name.substring(offset);
    }
}
