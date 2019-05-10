package cn.fcbarry.db2dao.tool.util;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.log.NullLogChute;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
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
public class VmDaoGen
{
    private Properties properties;

    private VelocityEngine vmEngine;
    private Template template;
    private boolean genGetByUserID;

    public VmDaoGen(Properties properties)
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
            template = vmEngine.getTemplate("daointerface.vm", "UTF-8");
            String filePath = properties.getProperty("rootPath") + "\\dao\\I" + className + "InfoDao.java";
            FileOutputStream fos = new FileOutputStream(filePath);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));

            VelocityContext ctx = new VelocityContext();
            ctx.put("fields", fields);
            ctx.put("className", className);
            ctx.put("genGetByUserID", genGetByUserID);
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
    
    public void setGenGetByUserID(boolean genGetByUserID)
    {
        this.genGetByUserID = genGetByUserID;
    }
}
