package cn.fcbarry.db2dao.tool.util;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 *
 * gen tool
 *
 * @author 科兴第一盖伦
 * @version 2019/5/10
 * @author from fei.wang(https://github.com/wang35666)
 */
@Getter
@Setter
public class ToolContext
{
    private String currentSelectedTable;

    private String currentSelectedDB;

    private Map<String, FieldInfo> fieldMap;

    private Properties properties;

    private DaoDBUtil daoDBUtil;

    private List<String> selectedTable;

    private boolean restart;

    private String propertiesPath;
    
    public ToolContext()
    {
        selectedTable = new ArrayList<>();
    }
}
