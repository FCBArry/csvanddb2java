import cn.fcbarry.db2dao.DaoMgr;

/**
 *
 * dao test
 *
 * @author 科兴第一盖伦
 * @version 2019/5/9
 */
public class DaoTest
{
    public static void main(String[] args)
    {
        System.out.println(DaoMgr.getSecretInfoDao().listAll().size());
    }
}
