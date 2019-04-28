import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * 工具类
 *
 * @author 科兴第一盖伦
 * @version 2019/4/28
 */
public class Utils
{
    public static String genClassName(String fileName)
    {
        String[] sub = fileName.split("_");
        String className = "";
        if (sub.length < 2)
            return className;

        for (int i = 2; i < sub.length; i++)
            className += sub[i].substring(0, 1).toUpperCase() + sub[i].substring(1);

        return className.replace(".csv", "");
    }

    public static List<File> getTotalFiles(String csvPath)
    {
        List<File> files = new ArrayList<>();
        getTotalFiles(csvPath, files);
        return files;
    }

    private static void getTotalFiles(String filePath, List<File> totalFileList)
    {
        File root = new File(filePath);
        if (!root.exists())
            return;

        File[] files = root.listFiles();
        if (files == null)
            return;

        for (File file : files)
        {
            if (file.isDirectory())
                getTotalFiles(file.getAbsolutePath(), totalFileList);
            else if (file.isFile())
                totalFileList.add(file);
        }
    }

    public static File getFileByClassName(String csvPath, String className)
    {
        List<File> files = getTotalFiles(csvPath);
        for (File file : files)
        {
            String genClassName = genClassName(file.getName());
            genClassName = genClassName.replace(".csv", "");
            genClassName += "Bean";
            if (genClassName.equals(className))
                return file;
        }

        return null;
    }
}
