package officialexample;

import com.opencsv.bean.CsvToBeanBuilder;
import java.io.FileReader;
import java.util.List;

/**
 *
 * 官方例子：http://opencsv.sourceforge.net/#reading
 *
 * @author 科兴第一盖伦
 * @version 2019/4/29
 */
public class OfficialTest
{
    @SuppressWarnings("unchecked")
    public static void main(String[] args)
    {
        List<Visitors> beans = null;
        try
        {
            FileReader fileReader=  new FileReader("src/test/java/officialexample/t_s_visitors.csv");
            beans = new CsvToBeanBuilder(fileReader)
                    .withType(Visitors.class).withSkipLines(2)
                    .build().parse();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        for (Visitors visitors : beans)
            System.out.println(visitors.getFirstName() + "-"
                    + visitors.getLastName() + "-" + visitors.getVisitsToWebsite());
    }
}