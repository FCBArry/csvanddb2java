package cn.fcbarry.db2dao.base.bonePool;

import cn.fcbarry.Conf;
import cn.fcbarry.db2dao.base.IPool;
import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * pool - BoneCP
 *
 * @author 科兴第一盖伦
 * @version 2019/5/10
 */
public class BoneCPDBPool implements IPool
{
    private BoneCP boneCP;

    private BoneCPConfig boneCPConfig;

    public BoneCPDBPool()
    {
        boneCPConfig = new BoneCPConfig();
    }

    public BoneCPDBPool(boolean isStartNow)
    {
        boneCPConfig = new BoneCPConfig();

        if (isStartNow)
            startup();
    }

    public void init(Conf conf)
    {
        Properties properties = conf.getProperties();
        boneCPConfig.setJdbcUrl(properties.getProperty("game"));
        boneCPConfig.setUsername(properties.getProperty("username"));
        boneCPConfig.setPassword(properties.getProperty("password"));

        // 其他配置

        try
        {
            boneCP = new BoneCP(boneCPConfig);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public boolean startup()
    {
        Conf conf = new Conf();
        conf.init("src/main/resources/db.conf");

        init(conf);
        return true;
    }

    @Override
    public Connection getConnection()
    {
        try
        {
            if (boneCP != null)
                return boneCP.getConnection();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public void shutdown()
    {
        if (boneCP != null)
            boneCP.close();
    }

    public boolean validConnection()
    {
        return boneCP != null;
    }
}
