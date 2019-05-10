package cn.fcbarry.db2dao.tool;

import cn.fcbarry.db2dao.tool.util.DaoCommonUtil;
import cn.fcbarry.db2dao.tool.util.DaoDBUtil;
import cn.fcbarry.db2dao.tool.util.ToolContext;
import cn.fcbarry.db2dao.tool.view.MainView;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * gen tool
 *
 * @author 科兴第一盖伦
 * @version 2019/5/10
 * @author from fei.wang(https://github.com/wang35666)
 */
public class CodeTool
{
    public static void main(String[] args)
    {
        ToolContext context = new ToolContext();
        context.setPropertiesPath("src/main/resources/db.conf");
        context.setRestart(true);
        while (context.isRestart())
        {
            Properties prop = new Properties();
            InputStream in = null;
            try
            {
                in = new FileInputStream(context.getPropertiesPath());
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

                System.exit(1);
            }

            DaoCommonUtil.reload(prop);
            context.setRestart(false);
            context.setProperties(prop);
            context.setDaoDBUtil(new DaoDBUtil(prop));
            MainView mainView = new MainView(context);
            mainView.show();
        }
    }
}
