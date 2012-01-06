package cbs.batch.ac.bcb8531;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 2011-3-1
 * Time: 10:57:11
 * To change this template use File | Settings | File Templates.
 */
public class SctBean {
    private Date crndat;
    private Short nxtdds;
    private Short bmmdds;
    private Short bssdds;
    private Short byydds;
    private String ipymak;
    private Date ipydat;
    private Date nwkday;
    private Date workMDate;
    private Date workSDate;
    private Date workYDate;

    public Date getCrndat() {
        return crndat;
    }

    public void setCrndat(Date crndat) {
        this.crndat = crndat;
    }

    public Short getNxtdds() {
        return nxtdds;
    }

    public void setNxtdds(Short nxtdds) {
        this.nxtdds = nxtdds;
    }

    public Short getBmmdds() {
        return bmmdds;
    }

    public void setBmmdds(Short bmmdds) {
        this.bmmdds = bmmdds;
    }

    public Short getBssdds() {
        return bssdds;
    }

    public void setBssdds(Short bssdds) {
        this.bssdds = bssdds;
    }

    public Short getByydds() {
        return byydds;
    }

    public void setByydds(Short byydds) {
        this.byydds = byydds;
    }

    public String getIpymak() {
        return ipymak;
    }

    public void setIpymak(String ipymak) {
        this.ipymak = ipymak;
    }

    public Date getIpydat() {
        return ipydat;
    }

    public void setIpydat(Date ipydat) {
        this.ipydat = ipydat;
    }

    public Date getNwkday() {
        return nwkday;
    }

    public void setNwkday(Date nwkday) {
        this.nwkday = nwkday;
    }

    public Date getWorkMDate() {
        return workMDate;
    }

    public void setWorkMDate(Date workMDate) {
        this.workMDate = workMDate;
    }

    public Date getWorkSDate() {
        return workSDate;
    }

    public void setWorkSDate(Date workSDate) {
        this.workSDate = workSDate;
    }

    public Date getWorkYDate() {
        return workYDate;
    }

    public void setWorkYDate(Date workYDate) {
        this.workYDate = workYDate;
    }
}
