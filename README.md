# csv2javabean
csv to javabean

------------------------------------
project:maven project

------------------------------------
java:jdk8

------------------------------------
velocity:http://velocity.apache.org/

------------------------------------
opencsv:http://opencsv.sourceforge.net/

------------------------------------
author:https://github.com/FCBArry

------------------------------------
注意事项：

1.在conf下配置好你的路径

2.csv默认前3行是，注释，类型，字段名必须要有

3.若某一列以[D]开头，则不会生成

4.csv命名规范t_s_xxx_xxx:xxx为英文单词

5.生成目录在jartools下

6.注意csv的编码格式不能带有BOM（如果按照csv头部注释，类型，字段名的顺序则不用关心）

------------------------------------
计划：

1.生成bean 20190428 done

2.读取数据的基本功能 20190428 done

3.支持mysql读取

4.支持list类型 20190429 done 暂时支持2种List<Integer>，List<String>以逗号分割a1,a2,a3......

5.支持自定义类型：http://opencsv.sourceforge.net/#annotations_2

6.去除读文件时的BOM格式问题，解决csv头部注释，类型，字段名顺序问题

（1）如果按照csv头部注释，类型，字段名的顺序，withSkipLines(2)则不会有问题

（2）可通过CsvToBeanFilter来过滤
/*
CsvToBeanFilter filter = new CsvToBeanFilter()
{
    @Override
    public boolean allowLine(String[] strings)
    {
        return false;
    }
};
*/

（3）......

7.skipLines的设计不合理
