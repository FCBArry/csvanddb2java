package cn.fcbarry;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * 项目配置类
 *
 * @author 科兴第一盖伦
 * @version 2019/4/26
 */
public class Conf
{
    private Properties properties;

    public Conf()
    {

    }

    public void init(String conf)
    {
        InputStream in = null;
        properties = new Properties();

        try
        {
            in = new FileInputStream(conf);
            properties.load(in);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public Properties getProperties()
    {
        return properties;
    }
}
