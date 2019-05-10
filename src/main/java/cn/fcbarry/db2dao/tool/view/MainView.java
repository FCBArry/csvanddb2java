package cn.fcbarry.db2dao.tool.view;

import cn.fcbarry.db2dao.tool.util.FieldInfo;
import cn.fcbarry.db2dao.tool.util.ToolContext;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 *
 * gen tool
 *
 * @author 科兴第一盖伦
 * @version 2019/5/10
 * @author from fei.wang(https://github.com/wang35666)
 */
public class MainView
{
    private Table table;

    private Tree tree;

    private boolean SELECT_ALL = false;

    private Shell shell;

    private Display display;

    private ToolContext ctx;

    public static final String[] titles =
    { "Name", "Java Type", "Sql Type", "Max Size", "Comment" };

    public MainView(ToolContext context)
    {
        this.ctx = context;
    }

    public void show()
    {
        display = Display.getDefault();
        shell = new Shell();
        shell.setSize(1000, 600);
        shell.setText("db2dao Tool");
        shell.setLayout(new FillLayout(SWT.HORIZONTAL));

        Monitor primary = display.getPrimaryMonitor();
        Rectangle bounds = primary.getBounds();
        Rectangle rect = shell.getBounds();

        int x = bounds.x + (bounds.width - rect.width) / 2;
        int y = bounds.y + (bounds.height - rect.height) / 2;
        shell.setLocation(x, y);

        Composite composite = new Composite(shell, SWT.NONE);
        composite.setLayout(getGridLayout());

        SashForm sashForm = new SashForm(composite, SWT.BORDER);
        sashForm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

        Composite compConnList = new Composite(sashForm, SWT.BORDER);
        compConnList.setLayout(getGridLayout());

        Composite composite_1 = new Composite(compConnList, SWT.NONE);
        composite_1.setLayout(new FillLayout(SWT.HORIZONTAL));
        composite_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

        CLabel lblNewLabel = new CLabel(composite_1, SWT.NONE);
        lblNewLabel.setText("Connections");

        Composite composite_2 = new Composite(compConnList, SWT.NONE);
        composite_2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        composite_2.setLayout(getGridLayout());

        tree = new Tree(composite_2, SWT.BORDER | SWT.MULTI);
        tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

        List<String> databases = ctx.getDaoDBUtil().getDatabases();
        for (int i = 0; i < databases.size(); i++)
        {
            TreeItem dbItem = new TreeItem(tree, SWT.NONE);
            dbItem.setData("type", "DB");
            dbItem.setData("db_name", databases.get(i));
            dbItem.setText(databases.get(i));
        }

        tree.addListener(SWT.Selection, new SelectTreeNodeListener());

        Composite compContentShow = new Composite(sashForm, SWT.BORDER);
        compContentShow.setLayout(getGridLayout());

        ToolBar codeBar = new ToolBar(compContentShow, SWT.FLAT | SWT.RIGHT);
        codeBar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

        ToolItem tltmSelectall = new ToolItem(codeBar, SWT.NONE);
        tltmSelectall.addSelectionListener(new TltmListener());
        tltmSelectall.setText("全选");

        ToolItem codeRedis = new ToolItem(codeBar, SWT.NONE);
        codeRedis.addSelectionListener(new CodeRedisListener());
        codeRedis.setText("生成");

        Composite compTable = new Composite(compContentShow, SWT.NONE);
        compTable.setLayout(new FillLayout(SWT.HORIZONTAL));
        compTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

        table = new Table(compTable, SWT.CHECK | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
        table.setHeaderVisible(true);
        table.setLinesVisible(true);

        for (int i = 0; i < titles.length; i++)
        {
            TableColumn column = new TableColumn(table, SWT.NONE);
            column.setText(titles[i]);
        }

        table.addListener(SWT.Selection, new Listener()
        {
            @Override
            public void handleEvent(Event e)
            {
                String clickItemName = ((TableItem) e.item).getText();
                boolean tmp = ((TableItem) e.item).getChecked();
                ((TableItem) e.item).setChecked(tmp);
                ctx.getFieldMap().get(clickItemName).setFlag(tmp);
            }
        });

        sashForm.setWeights(new int[]
        { 1, 4 });

        shell.open();
        shell.layout();
        while (!shell.isDisposed())
        {
            if (!display.readAndDispatch())
                display.sleep();
        }
    }

    private GridLayout getGridLayout()
    {
        GridLayout gridLayout = new GridLayout(1, false);
        gridLayout.verticalSpacing = 0;
        gridLayout.marginWidth = 0;
        gridLayout.marginHeight = 0;
        gridLayout.horizontalSpacing = 0;
        return gridLayout;
    }

    public class SelectTreeNodeListener implements Listener
    {
        @Override
        public void handleEvent(Event event)
        {
            TreeItem[] selection = tree.getSelection();
            if (selection.length < 0)
                return;

            if (selection[0].getData("type").equals("DB") && selection[0].getItemCount() <= 0)
            {
                List<String> tables = ctx.getDaoDBUtil().getTables((String) selection[0].getData("db_name"));
                for (int j = 0; j < tables.size(); j++)
                {
                    TreeItem treeItem = new TreeItem(selection[0], SWT.NONE);
                    treeItem.setText(tables.get(j));
                    treeItem.setData("type", "TABLE");
                }

                ctx.setCurrentSelectedDB((String) selection[0].getData("db_name"));
            }
            else if (selection[0].getData("type").equals("TABLE"))
            {
                ctx.setCurrentSelectedTable(selection[0].getText());

                table.removeAll();

                SELECT_ALL = true;

                ctx.setFieldMap(
                        ctx.getDaoDBUtil().getTableFieldList(selection[0].getParentItem().getText(),
                                selection[0].getText()));

                Collection<FieldInfo> c = ctx.getFieldMap().values();
                Iterator<FieldInfo> it = c.iterator();
                while (it.hasNext())
                {
                    FieldInfo field = (FieldInfo) it.next();
                    TableItem t_item = new TableItem(table, SWT.NONE);
                    t_item.setText(0, field.getName());
                    t_item.setText(1, field.getJavaType());
                    t_item.setText(2, field.getSqlType());
                    t_item.setText(3, field.getLen() + "");
                    t_item.setText(4, field.getComment());
                    t_item.setChecked(true);
                }

                for (int i = 0; i < titles.length; i++)
                {
                    table.getColumn(i).pack();
                }
            }
        }
    };

    public class TltmListener extends SelectionAdapter
    {
        @Override
        public void widgetSelected(SelectionEvent arg0)
        {
            if (ctx.getCurrentSelectedTable() == null || table == null)
                return;

            SELECT_ALL = !SELECT_ALL;

            for (TableItem item : table.getItems())
            {
                String clickItemName = item.getText();
                item.setChecked(SELECT_ALL);
                ctx.getFieldMap().get(clickItemName).setFlag(SELECT_ALL);
            }
        }
    }

    public class CodeRedisListener extends SelectionAdapter
    {
        @Override
        public void widgetSelected(SelectionEvent arg0)
        {
            TreeItem[] items = tree.getSelection();
            ctx.getSelectedTable().clear();
            for (TreeItem treeItem : items)
            {
                if (!treeItem.getData("type").equals("TABLE"))
                    continue;

                ctx.getSelectedTable().add(treeItem.getText());

            }

            GenDaoDialog genRedisDialog = new GenDaoDialog(ctx, shell, display);
            genRedisDialog.show();
        }
    };
}
