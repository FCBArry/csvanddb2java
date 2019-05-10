import cn.fcbarry.db2dao.base.bonePool.BoneCPDBPool;

/**
 *
 * pool test
 *
 * @author 科兴第一盖伦
 * @version 2019/5/10
 */
public class BoneCPPoolTest
{
    public static void main(String[] args)
    {
        BoneCPDBPool boneCPDBPool = new BoneCPDBPool();
        boneCPDBPool.startup();
    }
}
