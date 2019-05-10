import cn.fcbarry.Conf;
import cn.fcbarry.db2bean.orm.Ammentos2Bean;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import it.biobytes.ammentos.Ammentos;
import cn.fcbarry.javabean.db.FireBean;

import java.util.List;
import java.util.Properties;

/**
 *
 * Ammentos test
 *
 * @author 科兴第一盖伦
 * @version 2019/5/6
 */
public class Ammentos2BeanTest
{
    public static void main(String[] args)
    {
        Conf conf = new Conf();
        conf.init("src/main/resources/db.conf");
        Properties properties = conf.getProperties();

        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUrl(properties.getProperty("ammentos"));
        dataSource.setUser(properties.getProperty("username"));
        dataSource.setPassword(properties.getProperty("password"));
        Ammentos.setDataSource(dataSource);

        List<FireBean> fireBeans = Ammentos2Bean.listAll(FireBean.class);
        System.out.println("size: " + fireBeans.size());
    }
}
