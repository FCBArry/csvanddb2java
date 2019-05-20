package cn.fcbarry.csv2bean;

import cn.fcbarry.Conf;
import cn.fcbarry.Utils;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

/**
 *
 * csv to bean
 *
 * @author 科兴第一盖伦
 * @version 2019/4/28
 */
public class CSV2JavaBean
{
    public static<T> List<T> getBeanList(Class tClass)
    {
        return getBeanList("src/main/resources/csv_gen.conf", tClass);
    }

    @SuppressWarnings("unchecked")
    public static<T> List<T> getBeanList(String confPath, Class tClass)
    {
        List<T> beans = null;
        try
        {
            Conf conf = new Conf();
            conf.init(confPath);
            String csvPath = (String) conf.getProperties().get("project_dir") + conf.getProperties().get("csv_dir");
            File file = Utils.getFileByClassName(csvPath, tClass.getSimpleName());
            if (file != null)
            {
                beans = new CsvToBeanBuilder(new FileReader(file))
                        .withType(tClass).withSkipLines(3)
                        .build().parse();
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        return beans;
    }
}
