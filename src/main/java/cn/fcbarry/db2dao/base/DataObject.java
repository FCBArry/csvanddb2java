package cn.fcbarry.db2dao.base;

/**
 *
 * DataObject
 *
 * @author 科兴第一盖伦
 * @version 2019/5/9
 */
public class DataObject
{
    private short op = 0;

    public final short getOp()
    {
        return this.op;
    }

    public final void setOp(short option)
    {
        if ((this.op == DataOption.INSERT) && (option == DataOption.UPDATE))
            return;

        this.op = option;
    }
}
