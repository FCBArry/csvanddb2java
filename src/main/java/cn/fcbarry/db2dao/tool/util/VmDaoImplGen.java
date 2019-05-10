package cn.fcbarry.db2dao.tool.util;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.log.NullLogChute;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Properties;

/**
 *
 * gen tool
 *
 * @author 科兴第一盖伦
 * @version 2019/5/10
 * @author from fei.wang(https://github.com/wang35666)
 */
public class VmDaoImplGen
{
    private Properties properties;
    private VelocityEngine vmEngine;
    private Template template;
    private String tableName;
    private boolean genGetByUserID;

    public VmDaoImplGen(Properties properties)
    {
        this.properties = properties;

        vmEngine = new VelocityEngine();
        vmEngine.addProperty("input.encoding", "UTF-8");
        vmEngine.addProperty("output.encoding", "UTF-8");
        vmEngine.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM, new NullLogChute()); // 防止log导致空指针异常
        vmEngine.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH, properties.get("tmplPath"));
        vmEngine.init();
    }

    public void genCode(String className, List<FieldInfo> fields)
    {
        try
        {
            template = vmEngine.getTemplate("daoimpl.vm", "UTF-8");
            String filePath = properties.getProperty("rootPath") + "\\dao\\impl\\" + className + "InfoDaoImpl.java";
            FileOutputStream fos = new FileOutputStream(filePath);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));

            VelocityContext ctx = new VelocityContext();
            ctx.put("fields", fields);
            ctx.put("className", className);
            ctx.put("insertSql", DaoCreateCodeUtil.createInsertSql(tableName, fields));
            ctx.put("updateSql", DaoCreateCodeUtil.createUpdateSql(tableName, fields));
            ctx.put("deleteSql", DaoCreateCodeUtil.createDeleteSql(tableName, fields));
            ctx.put("addOrUpdateSql", DaoCreateCodeUtil.createaddOrUpdateSql(tableName, fields));
            ctx.put("selectSql", DaoCreateCodeUtil.createGetByIDSql(tableName, fields));
            ctx.put("tableName", tableName);
            ctx.put("genGetByUserID", genGetByUserID);
            ctx.put("lowClassName", firstChar2Lower(className));
            template.merge(ctx, writer);

            writer.flush();
            writer.close();
            fos.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private Object firstChar2Lower(String name)
    {
        int offset1 = name.offsetByCodePoints(0, 1);
        return name.substring(0, offset1).toLowerCase() + name.substring(offset1);
    }

    public void setTableName(String tableName)
    {
        this.tableName = tableName;
    }

    public void setGenGetByUserID(boolean genGetByUserID)
    {
        this.genGetByUserID = genGetByUserID;
    }

    public static void main(String[] args)
    {
        Properties prop = new Properties();
        InputStream in = null;
        try
        {
            in = new FileInputStream("src/main/resources/db.conf");
            prop.load(in);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            try
            {
                if (null != in)
                    in.close();
            }
            catch (IOException e1)
            {
                e1.printStackTrace();
            }
        }
    }
}
