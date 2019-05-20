package cn.fcbarry;

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
 * 模板生成类
 *
 * @author 科兴第一盖伦
 * @version 2019/4/26
 */
public class VelocityGen
{
    private Properties properties;

    private VelocityEngine velocityEngine;

    public static void main(String[] args)
    {

    }

    public VelocityGen(Properties properties)
    {
        this.properties = properties;

        velocityEngine = new VelocityEngine();
        velocityEngine.addProperty("input.encoding", "UTF-8");
        velocityEngine.addProperty("output.encoding", "UTF-8");
        velocityEngine.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM, new NullLogChute());

        String path = (String) properties.get("project_dir") + properties.get("conf_dir");
        velocityEngine.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH, path);
        velocityEngine.init();
    }

    public void genBean(int type, String className, String sourceDomain, List<FieldInfo> fields)
    {
        try
        {
            // TODO 改进拿到名字的方式 by arry
            String vmName = null;
            String beanDirName = null;
            if (type == 1)
            {
                vmName = "csv_javabean.vm";
                beanDirName = "csv_bean_dir";
            }
            else if (type == 2)
            {
                vmName = "db_javabean.vm";
                beanDirName = "db_bean_dir";
            }

            Template template = velocityEngine.getTemplate(vmName, "UTF-8");
            String filePath = (String) properties.get("project_dir") + properties.get(beanDirName);
            filePath += className + "Bean.java";

            FileOutputStream fos = new FileOutputStream(filePath);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));

            VelocityContext ctx = new VelocityContext();
            ctx.put("className", className);
            ctx.put("sourceDomain", sourceDomain);
            ctx.put("fields", fields);
            template.merge(ctx, writer);

            System.out.println("##################已生成：" + filePath);

            writer.flush();
            writer.close();
            fos.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void genMgr(String className, String key)
    {
        try
        {
            Template template = velocityEngine.getTemplate("csv_javabeanmgr.vm", "UTF-8");
            String filePath = (String)properties.get("project_dir") + properties.get("mgr_dir");
            filePath += className + "Mgr.java";

            FileOutputStream fos = new FileOutputStream(filePath);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));

            VelocityContext ctx = new VelocityContext();
            ctx.put("className", className);
            ctx.put("primaryKey", key);
            template.merge(ctx, writer);

            System.out.println("##################已生成：" + filePath);

            writer.flush();
            writer.close();
            fos.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
