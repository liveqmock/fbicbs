package cbs.batch.ac.bcb8564;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 2011-4-2
 * Time: 15:32:44
 * To change this template use File | Settings | File Templates.
 */
public class SctDat {
    private Date crndat;
    private Date nwkday;
    private Date mDate;
    private Date sDate;
    private Date yDate;

    public Date getCrndat() {
        return crndat;
    }

    public void setCrndat(Date crndat) {
        this.crndat = crndat;
    }

    public Date getNwkday() {
        return nwkday;
    }

    public void setNwkday(Date nwkday) {
        this.nwkday = nwkday;
    }

    public Date getmDate() {
        return mDate;
    }

    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }

    public Date getsDate() {
        return sDate;
    }

    public void setsDate(Date sDate) {
        this.sDate = sDate;
    }

    public Date getyDate() {
        return yDate;
    }

    public void setyDate(Date yDate) {
        this.yDate = yDate;
    }
}
