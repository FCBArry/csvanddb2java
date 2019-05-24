# -*- coding: utf-8 -*
# python 3.7
# 生成csv

import os.path
import os
import csv
import os.path
from io import StringIO
from configparser import ConfigParser

import xlrd
import time

# 生成csv
def excel2csv():
    try:
        config = StringIO()
        config.write('[dummysection]\n')
        with open('./config.ini', encoding="utf-8") as configfile:
            def with_BOM(s):
                return True if s == u'\ufeff' else False

            if not with_BOM(configfile.read(1)):
                configfile.seek(0)
            config.write(configfile.read())
            config.seek(0)
            cp = ConfigParser()
            cp.read_file(config)

            csvdir = cp.get('dummysection', 'csvdir')
            srcdir = cp.get('dummysection', 'srcdir')
            for i in cp.items('dummysection'):
                print(i[0], i[1])
            config.close()
            cp.clear()

            print("excel2csv start")
            if not os.path.exists(srcdir):
                os.makedirs(srcdir)

            for dirpath, dirnames, filenames in os.walk(srcdir):
                for filename in filenames:
                    pathfile = os.path.join(dirpath, filename)
                    if filename.rfind(".xlsx") != -1:
                        process_excel(pathfile, csvdir)

            print("excel2csv end success")
    except Exception as e:
        print("excel2csv fail")
        raise Exception(e)

# 生成单个csv
def process_excel(filepath, outpath):
    tablename = os.path.splitext(os.path.basename(filepath))[0]
    sheets = xlrd.open_workbook(filepath)
    table = sheets.sheets()[0]

    try:
        if not check_data(table, tablename):
            print("check %s failed" % tablename)
            os.system("pause")
            return
    except Exception as e:
        raise Exception(e)

    # 这里文件名可能会变动t_s_xxx
    csvfilepath = outpath + os.sep + 't_s_' + tablename + '.csv'
    csvfile = open(csvfilepath, 'w', encoding="utf-8", newline='')
    csv_write = csv.writer(csvfile, dialect='excel')

    attrubiteNameDic = table.row_values(0)
    attrubiteTypeDic = table.row_values(1)
    keyOrAttruDic = table.row_values(2)
    attrubiteDesDic = table.row_values(3)
    for i in range(len(attrubiteDesDic)):
        attrubiteDesDic[i] = str(attrubiteDesDic[i]).replace('\n', ' ', -1)
    csv_write.writerow(attrubiteDesDic)
    csv_write.writerow(attrubiteTypeDic)
    csv_write.writerow(keyOrAttruDic)
    csv_write.writerow(attrubiteNameDic)

    n = table.nrows
    for i in range(4, n):
        rowData = table.row_values(i)
        for j in range(len(rowData)):
            if (is_int_type(attrubiteTypeDic[j])):
                rowData[j] = int(rowData[j])
        csv_write.writerow(rowData)

    csvfile.close()
    print('excel 2 csv %s success' % tablename)

# 检测数据
def check_data(datas, tablename):
    heads = datas.row_values(0)
    types = datas.row_values(1)
    headSize = len(heads)
    n = datas.nrows

    # 检查数据类型是否正确
    for index in range(headSize):
        if not is_support_type(types[index]):
            print('table:%s col:%d headColName:%s dataType:%s can not support!'
                  % (tablename, (index + 1), heads[index], types[index]))
            return False

    # 校验数据列数是否正确
    for row in range(n):
        row_item = datas.row_values(row)
        if headSize != len(row_item):
            print('table:%s row:%s headSize:%s colSize:%s id:%s error!'
                  % (tablename, row + 1, headSize, len(row_item), row_item[0]))
            return False

    # 校验表头及类型是否为空
    for row in range(0, 1):
        row_item = datas.row_values(row)
        for col in range(len(row_item)):
            col_value = row_item[col]
            if col_value == None or col_value == 'nil' or str(col_value).strip() == '':
                print('table:%s row:%d col:%d headColName:%s dataType:%s id:%s can not null!'
                      % (tablename, (row + 1), (col + 1), heads[col], types[col], row_item[0]))
                return False

    # 校验数据类型是否正确
    for row in range(4, n):
        row_item = datas.row_values(row)
        for col in range(len(row_item)):
            col_value = row_item[col]
            data_type = types[col]
            if col_value == None or col_value == 'nil' or str(col_value).strip() == '':
                if is_str_type(data_type):
                    row_item[col] = ''
                elif is_number_type(data_type) or is_date_type(data_type):
                    print('table:%s row:%d colName:%s dataType:%s id:%s can not null! '
                          % (tablename, (row + 1), heads[col], data_type, row_item[0]))
                    return False

            elif is_number_type(data_type) and (not is_valid_number(col_value)):
                print(col_value)
                print('table:%s row:%d colName:%s dataType:%s value:%s id:%s error !'
                      % (tablename, (row + 1), heads[col], data_type, col_value, row_item[0]))
                return False

            elif is_date_type(data_type) and (not is_valid_date(col_value)):
                print(col_value, is_date_type(data_type), is_valid_date(col_value))
                print('table:%s row:%d colName:%s dataType:%s value:%s id:%s error !'
                      % (tablename, (row + 1), heads[col], data_type, col_value, row_item[0]))
                return False

            elif is_int_type(data_type) and (not is_valid_int(col_value)):
                print(col_value)
                print('table:%s row:%d colName:%s dataType:%s value:%s id:%s error !'
                      % (tablename, (row + 1), heads[col], data_type, col_value, row_item[0]))
                return False

    # 校验重复key
    repeatkeys = {}
    for row in range(4, n):
        row_item = datas.row_values(row)
        if row_item[0] in repeatkeys:
            print('table:%s row:%d has multiple key:%s' % (tablename, row, row_item[0]))
            return False
        else:
            repeatkeys[row_item[0]] = row_item

    # # 校验数据长度
    # typelens = {}
    # for col, type_value in types.items():
    #     if type_value.startswith('datetime'):
    #         continue
    #     typelens[col] = int(type_value[type_value.index("(") + 1:type_value.index(')')].split(',')[0])

    return True

# 类型校验
def is_support_type(dataType):
    return is_str_type(dataType) or is_number_type(dataType)

def is_str_type(dataType):
    return dataType.startswith('char') or dataType.startswith(
        'varchar') or dataType.startswith('string') or dataType.startswith('vector')

def is_number_type(dataType):
    return dataType.startswith('int') or dataType.startswith('smallint') or dataType.startswith(
        'tinyint') or dataType.startswith('bigint') or dataType.startswith('float')

# 数据校验
def is_date_type(dataType):
    return dataType.startswith('datetime')

def is_int_type(dataType):
    return dataType.startswith('int') or dataType.startswith('smallint') or dataType.startswith(
        'tinyint') or dataType.startswith('bigint')

def is_valid_int(value):
    try:
        int(value)
        return True
    except ValueError:
        return False

def is_valid_number(value):
    try:
        float(value)
        return True
    except ValueError:
        return False

def is_valid_date(value):
    if value == '0000-00-00 00:00:00':
        return True
    try:
        time.strptime(value, "%Y-%m-%d %H:%M:%S")
        return True
    except BaseException as e:
        print(e)
        return False

def format_value(value, vtype, book):
    if value == None:
        return value

    if vtype == 2:
        if (str(value).endswith(".0")):
            value = int(value)
    elif vtype == 3:
        datetuple = xlrd.xldate_as_tuple(value, book.datemode)
        # time only     no date component
        if datetuple[0] == 0 and datetuple[1] == 0 and datetuple[2] == 0:
            value = "%02d:%02d:%02d" % datetuple[3:]
        # date only, no time
        elif datetuple[3] == 0 and datetuple[4] == 0 and datetuple[5] == 0:
            value = "%04d/%02d/%02d" % datetuple[:3]
        else:  # full date
            value = "%04d/%02d/%02d %02d:%02d:%02d" % datetuple

    return value

# execute
excel2csv()
input("Press <enter>")