/**
 * @author 科兴第一盖伦
 * @version 2019/4/26
 */
public class ConfTest
{
    public static void main(String[] args)
    {
        Conf conf = new Conf();
        conf.init("src/main/resources/project.conf");
        System.out.println(conf.getProperties().size());
    }
}
