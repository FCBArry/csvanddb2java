import cn.fcbarry.CSVGen;

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
        String confPath = "src/main/resources/csv_gen.conf";
        CSVGen csvGen = new CSVGen();
        csvGen.genBeans(confPath, 1);
    }
}
