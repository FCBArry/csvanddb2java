import cn.fcbarry.csv2bean.CSV2JavaBean;
import cn.fcbarry.javabean.csv.FireBean;

import java.util.List;

/**
 *
 * csv to bean test
 *
 * @author 科兴第一盖伦
 * @version 2019/4/28
 */
public class CSV2JavaBeanTest
{
    public static void main(String[] args)
    {
        List<FireBean> fireBeans = CSV2JavaBean.getBeanList("src/main/resources/csv_gen.conf", FireBean.class);
        System.out.println(fireBeans.size());
        for (FireBean fireBean : fireBeans)
            System.out.println(fireBean.getId() + "-" + fireBean.getBuff());
    }
}
