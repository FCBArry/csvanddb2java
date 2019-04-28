import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;
import java.util.List;

/**
 *
 * csv to javabean
 *
 * @author 科兴第一盖伦
 * @version 2019/4/28
 */
public class CSV2JavaBean
{
    public static<T> List<T> getBeanList(Class<T> tClass)
    {
        List<T> beans = null;
        try
        {
            Iterator iterator = new CsvToBeanBuilder(new FileReader("src\\test\\resources\\csv\\t_s_fire.csv"))
                    .withType(tClass).build().iterator();

            int index = 0;
            while (iterator.hasNext())
            {
                index++;
                if (index <= 3)
                    continue;

                T bean = (T) iterator.next();
                beans.add(bean);
                iterator.remove();
            }

            System.out.println(beans.size());
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        return beans;
    }
}
