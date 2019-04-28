/**
 *
 * csv生成类测试
 *
 * @author 科兴第一盖伦
 * @version 2019/4/28
 */
public class CSVGenTest
{
    public static void main(String[] args)
    {
        String confPath = "E:\\github\\csv2javabean\\src\\test\\resources\\project.conf";
        CSVGen csvGen = new CSVGen();
        csvGen.genBeans(confPath);
    }
}
