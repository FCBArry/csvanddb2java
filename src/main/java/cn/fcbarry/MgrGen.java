package cn.fcbarry;

import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * mgr生成类
 *
 * @author 科兴第一盖伦
 * @version 2019/5/20
 */
public class MgrGen
{
    public static void main(String[] args)
    {
        // default path
        String confPath = "src/main/resources/csv_gen.conf";
        if (args.length > 0)
            confPath = args[0];

        MgrGen mgrGen = new MgrGen();
        mgrGen.genMgrs(confPath);
    }

    public void genMgrs(String confPath)
    {
        Conf conf = new Conf();
        conf.init(confPath);
        VelocityGen velocityGen = new VelocityGen(conf.getProperties());

        String csvPath = (String) conf.getProperties().get("project_dir") + conf.getProperties().get("csv_dir");
        List<File> files = Utils.getTotalFiles(csvPath);
        for (File file : files)
        {
            String fileName = file.getName();
            String className = Utils.genClassName(fileName);
            String key = readKey(file);
            velocityGen.genMgr(className, key);
        }
    }

    private String readKey(File file)
    {
        try
        {
            // 1.FileInputStream
            // InputStreamReader ir = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
            // CSVReader csvReader = new CSVReader(ir);

            // 2.FileReader
            CSVReader csvReader = new CSVReader(new FileReader(file));
            List<String[]> dataList = csvReader.readAll();
            if (dataList.size() < 4)
                return null;

            String key = dataList.get(3)[0];
            int columns = dataList.get(0).length;
            Map<Integer, FieldInfo> map = new HashMap<>();
            for (int i = 0; i < columns; i++)
            {
                String ka = dataList.get(2)[i];
                if (ka == "K")
                    key = dataList.get(3)[i];
            }

            return key;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
