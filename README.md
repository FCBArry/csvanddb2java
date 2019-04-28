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

2.csv默认前3行是，字段名，类型，注释必须要有

3.若某一列以[D]开头，则不会生成

4.csv命名规范t_s_xxx_xxx:xxx为英文单词

5.生成目录在jartools下

------------------------------------
计划：

1.生成bean 20190428 done

2.读取数据的基本功能 20190428 done

2.支持mysql读取

3.支持list类型

4.支持自定义类型
