package cn.fcbarry.db2dao.tool.view;

import cn.fcbarry.db2dao.tool.util.DaoCommonUtil;
import cn.fcbarry.db2dao.tool.util.FieldInfo;
import cn.fcbarry.db2dao.tool.util.ToolContext;
import cn.fcbarry.db2dao.tool.util.VmDaoGen;
import cn.fcbarry.db2dao.tool.util.VmEntityGen;
import cn.fcbarry.db2dao.tool.util.VmDaoImplGen;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * gen tool
 *
 * @author 科兴第一盖伦
 * @version 2019/5/10
 * @author from fei.wang(https://github.com/wang35666)
 */
public class GenDaoDialog
{
    private Shell shell;

    private Display display;

    private Shell codeDialog;

    private Table _Table;

    private ToolContext ctx;

    private TableItem itemDaoImpl;

    private TableItem genGetUserMethod;

    public GenDaoDialog(ToolContext ctx, Shell shell, Display display)
    {
        this.ctx = ctx;
        this.shell = shell;
        this.display = display;
    }

    public void show()
    {
        codeDialog = new Shell(shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
        Point pt = display.getCursorLocation();
        codeDialog.setLocation(pt);
        codeDialog.setLayout(new FillLayout(SWT.VERTICAL));
        codeDialog.setText("提示：");

        Label _Label = new Label(codeDialog, SWT.BORDER);
        _Label.setText("请选择需要拓展的方法：");
        _Table = new Table(codeDialog, SWT.CHECK | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);

        itemDaoImpl = new TableItem(_Table, SWT.NONE);
        itemDaoImpl.setText(0, "生成Dao");
        itemDaoImpl.setChecked(true);

        genGetUserMethod = new TableItem(_Table, SWT.NONE);
        genGetUserMethod.setText(0, "getEntityByUserID");
        genGetUserMethod.setChecked(true);

        final Composite _Composite = new Composite(codeDialog, SWT.BOTTOM);
        _Composite.setLayout(new FillLayout());

        final Button _SubmitButton = new Button(_Composite, SWT.BOTTOM);
        _SubmitButton.setText("生成");

        _SubmitButton.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent arg0)
            {
                try
                {
                    List<String> tables = ctx.getSelectedTable();
                    for (String table : tables)
                    {
                        Map<String, FieldInfo> fieldMap = ctx.getDaoDBUtil().getTableFieldList(ctx.getCurrentSelectedDB(), table);
                        genCode(table, fieldMap);
                    }

                    codeDialog.dispose();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

        codeDialog.setSize(600, 500);
        codeDialog.open();
    }

    private void genCode(String table, Map<String, FieldInfo> fieldMap)
    {
        String entityName = DaoCommonUtil.generateEntityNameBySourceTable(table);

        VmEntityGen vmEntityGen = new VmEntityGen(ctx.getProperties());
        vmEntityGen.genCode(entityName, new ArrayList<>(fieldMap.values()));

        VmDaoGen vmDaoInterfaceGen = new VmDaoGen(ctx.getProperties());
        vmDaoInterfaceGen.setGenGetByUserID(genGetUserMethod.getChecked());
        vmDaoInterfaceGen.genCode(entityName, new ArrayList<>(fieldMap.values()));

        if (itemDaoImpl.getChecked())
        {
            VmDaoImplGen vmSqlDaoGen = new VmDaoImplGen(ctx.getProperties());
            vmSqlDaoGen.setTableName(table);
            vmSqlDaoGen.setGenGetByUserID(genGetUserMethod.getChecked());
            vmSqlDaoGen.genCode(entityName, new ArrayList<>(fieldMap.values()));
        }
    }
}
