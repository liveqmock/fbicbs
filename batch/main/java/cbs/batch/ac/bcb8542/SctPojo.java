package cbs.batch.ac.bcb8542;

import cbs.repository.code.model.Actsct;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 2011-3-10
 * Time: 15:32:42
 * To change this template use File | Settings | File Templates.
 */
public class SctPojo {
  private Date crndat;
  private Date nwkday;
  private String ipymak;
  private Date ipydat;
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
