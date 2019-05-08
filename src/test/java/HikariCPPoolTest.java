import cn.fcbarry.db2bean.pool.hikaricp.HikariCPPool;

/**
 *
 * pool test
 *
 * @author 科兴第一盖伦
 * @version 2019/5/5
 */
public class HikariCPPoolTest
{
    public static void main(String[] args)
    {
        HikariCPPool hikariCPPool = new HikariCPPool();
        hikariCPPool.startup();
    }
}
