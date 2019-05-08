package officialexample;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * 官方例子：http://opencsv.sourceforge.net/#reading
 *
 * @author 科兴第一盖伦
 * @version 2019/4/29
 */
@Getter
@Setter
public class Visitors
{
    @CsvBindByName
    private String firstName;

    @CsvBindByName
    private String lastName;

    @CsvBindByName
    private int visitsToWebsite;
}
