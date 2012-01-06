package cbs.batch.ac.bcb8543;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 2011-3-12
 * Time: 11:09:24
 * To change this template use File | Settings | File Templates.
 */
public class SctPojoBean {
    private Date crndat;
    private Date nwkday;
    private String ipymak;
    private Date ipydat;
    private Short nxtdds;
    private Short bmmdds;
    private Short bssdds;
    private Short byydds;
    private int ipydats;
    private int nwkdays;
    private int crndats;

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

    public int getIpydats() {
        return ipydats;
    }

    public void setIpydats(int ipydats) {
        this.ipydats = ipydats;
    }

    public int getNwkdays() {
        return nwkdays;
    }

    public void setNwkdays(int nwkdays) {
        this.nwkdays = nwkdays;
    }

    public int getCrndats() {
        return crndats;
    }

    public void setCrndats(int crndats) {
        this.crndats = crndats;
    }
}
