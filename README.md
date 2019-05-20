# csvanddb2java
csv and db to javabean and dao

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
概述：

1.CSVGen类可用于生成javabean（csv版本和db版本）

2.CSV2JavaBean类可用于读取csv数据到javabean

3.Ammentos2Bean类可用于读取db数据到javabean

3.打包生成目录在csvgentool下，目前只做了一个csvgen的jar包，生成jar包可在pom里面配置

4.测试用例在test包下

5.资源放在resources目录下

------------------------------------
注意事项：

1.在conf下配置好你的路径

2.csv默认前3行是，注释，类型，字段名必须要有

3.若某一列以[D]开头，则不会生成javabean属性字段

4.csv命名规范t_s_xxx_xxx:xxx为英文单词

5.注意csv的编码格式不能带有BOM（如果按照csv头部注释，类型，字段名的顺序则不用关心）

6.手动引入包resources/lib

7.csv头部注释不能\n，解决方案目前有2种：
（1）去掉\n
（2）使用filter方式忽略注释这一行

------------------------------------
版本：

1.0 生成bean

2.0 读取数据的基本功能

3.0 支持mysql读取

4.0 支持list类型 暂时支持2种List<Integer>，List<String>以逗号分割a1,a2,a3......

5.0 支持自定义类型：http://opencsv.sourceforge.net/#annotations_2

6.0 去除读文件时的BOM格式问题，解决csv头部注释，类型，字段名顺序问题

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

7.0 skipLines的设计不合理（吐槽）

8.0 增加db to javabean

9.0 增加db pool练习HikariCP

10.0 增加db2dao包，实现数据库到实体的转换，图形化工具，源码由我老大哥王飞实现（https://github.com/wang35666）

11.0 修改一些vm格式；增加mgr的生成；csv头部变成4行，多增加K,V行（不需要的可以去掉）

------------------------------------
TODO：

1.改进拿到vm文件名字的方式 by arry

2.改进拿到vm中主键id的设置方式 by arry

3.整理db2dao vm的格式