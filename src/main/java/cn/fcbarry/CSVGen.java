package cn.fcbarry;

import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * csv生成类
 *
 * @author 科兴第一盖伦
 * @version 2019/4/28
 */
public class CSVGen
{
    public static void main(String[] args)
    {
        // default path
        String confPath = "src/main/resources/csv_gen.conf";
        int type = 1;
        if (args.length > 1)
        {
            confPath = args[0];
            type = Integer.parseInt(args[1]);
        }

        CSVGen csvGen = new CSVGen();
        csvGen.genBeans(confPath, type);
    }

    public void genBeans(String confPath, int type)
    {
        Conf conf = new Conf();
        conf.init(confPath);
        VelocityGen velocityGen = new VelocityGen(conf.getProperties());

        String csvPath = (String) conf.getProperties().get("project_dir") + conf.getProperties().get("csv_dir");
        List<File> files = Utils.getTotalFiles(csvPath);
        for (File file : files)
        {
            List<FieldInfo> fieldInfoList = readHead(file);
            if (fieldInfoList == null)
                return;

            String fileName = file.getName();
            String className = Utils.genClassName(fileName);
            String sourceDomain = fileName.replace(".csv", "");
            velocityGen.genBean(type, className, sourceDomain, fieldInfoList);
        }
    }

    private List<FieldInfo> readHead(File file)
    {
        try
        {
            // 1.FileInputStream
            // InputStreamReader ir = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
            // CSVReader csvReader = new CSVReader(ir);

            // 2.FileReader
            CSVReader csvReader = new CSVReader(new FileReader(file));
            List<String[]> dataList = csvReader.readAll();
            if (dataList.size() < 3)
                return null;

            int columns = dataList.get(0).length;
            Map<Integer, FieldInfo> map = new HashMap<>();
            for (int i = 0; i < columns; i++)
            {
                FieldInfo fieldInfo = new FieldInfo();

                // 字段名
                String[] data = dataList.get(3);
                if (data[i].startsWith("\uFEFF"))
                    data[i] = data[i].substring(1);
                String name = data[i].substring(0, 1).toLowerCase() + data[i].substring(1);;
                fieldInfo.setName(name);

                // 类型
                data = dataList.get(1);
                String jType = toJavaType(data[i]);
                if (jType == null)
                    return null;
                fieldInfo.setJavaType(jType);

                // 注释
                data = dataList.get(0);
                String regEx = "[`~!@#$%^&*()+=|{}':;',※и\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？\\s+]";
                Matcher m = Pattern.compile(regEx).matcher(data[i]);
                String comment =  m.replaceAll("").trim();
                fieldInfo.setComment(comment);

                // 主键
                if (i == 0)
                    fieldInfo.setPrimaryKey(true);

                map.put(i, fieldInfo);
            }

            // 过滤
            List<FieldInfo> fieldInfoList = new ArrayList<>();
            for (FieldInfo fieldInfo : map.values())
            {
                if (fieldInfo.getName().startsWith("[D]"))
                    continue;

                fieldInfoList.add(fieldInfo);
            }

            return fieldInfoList;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 这里可以做自己的处理
     */
    private String toJavaType(String data)
    {
        if (data.startsWith("int")
                || data.startsWith("smallint")
                || data.startsWith("tinyint"))
            return "int";
        else if (data.startsWith("varchar"))
            return "String";
        else if (data.startsWith("List<Integer>"))
            return "List<Integer>";
        else if (data.startsWith("List<String>"))
            return "List<String>";

        return null;
    }
}
