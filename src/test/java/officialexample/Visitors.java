package officialexample;

import com.opencsv.bean.CsvBindByName;

/**
 *
 * 官方例子：http://opencsv.sourceforge.net/#reading
 *
 * @author 科兴第一盖伦
 * @version 2019/4/29
 */
public class Visitors
{
    @CsvBindByName
    private String firstName;

    @CsvBindByName
    private String lastName;

    @CsvBindByName
    private int visitsToWebsite;

    public String getFirstNames()
    {
        return firstName;
    }

    public void setFirstNames(String firstNames)
    {
        this.firstName = firstNames;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public int getVisitsToWebsite()
    {
        return visitsToWebsite;
    }

    public void setVisitsToWebsite(int visitsToWebsite)
    {
        this.visitsToWebsite = visitsToWebsite;
    }
}
