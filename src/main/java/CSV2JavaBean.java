import com.opencsv.bean.CsvToBeanBuilder;

import java.io.File;
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
        return getBeanList("src/main/resources/project.conf", tClass);
    }

    public static<T> List<T> getBeanList(String confPath, Class<T> tClass)
    {
        List<T> beans = null;
        try
        {
            Conf conf = new Conf();
            conf.init(confPath);
            String csvPath = (String) conf.getProperties().get("project_dir") + conf.getProperties().get("csv_dir");
            File file = Utils.getFileByClassName(csvPath, tClass.getSimpleName());

            beans = new CsvToBeanBuilder(new FileReader(file)).withType(tClass)
                    .withSkipLines(0).withSkipLines(1).withSkipLines(2)
                    .build().parse();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        return beans;
    }
}
