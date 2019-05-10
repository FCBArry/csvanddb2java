package cn.fcbarry.db2dao.base.hikariPool;

import cn.fcbarry.Conf;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import cn.fcbarry.db2dao.base.IPool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * pool - HikariCP
 *
 * @author 科兴第一盖伦
 * @version 2019/5/5
 */
public class HikariCPPool implements IPool
{
    private HikariConfig hikariConfig;

    private HikariDataSource hikariDataSource;

    public HikariCPPool()
    {
        hikariConfig = new HikariConfig();
    }

    public HikariCPPool(boolean isStartNow)
    {
        hikariConfig = new HikariConfig();

        if (isStartNow)
            startup();
    }

    public void init(Conf conf)
    {
        Properties properties = conf.getProperties();
        hikariConfig.setJdbcUrl(properties.getProperty("game"));
        hikariConfig.setUsername(properties.getProperty("username"));
        hikariConfig.setPassword(properties.getProperty("password"));
        hikariConfig.setMaximumPoolSize(Integer.parseInt(properties.getProperty("maximumPoolSize")));
        hikariConfig.setConnectionTimeout(Long.parseLong(properties.getProperty("connectionTimeout")));
        hikariConfig.setAutoCommit(Boolean.parseBoolean(properties.getProperty("autoCommit")));
        hikariConfig.setIdleTimeout(Long.parseLong(properties.getProperty("idleTimeout")));
        hikariConfig.setMaxLifetime(Long.parseLong(properties.getProperty("maxLifetime")));
        hikariConfig.setReadOnly(Boolean.parseBoolean(properties.getProperty("readOnly")));

        hikariDataSource = new HikariDataSource(hikariConfig);
    }

    @Override
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
        if (hikariDataSource != null)
        {
            try
            {
                return hikariDataSource.getConnection();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public void shutdown()
    {
        if (hikariDataSource != null)
            hikariDataSource.close();
    }

    @Override
    public boolean validConnection()
    {
        return hikariDataSource != null;
    }
}
