package cbs.batch.ac.acbint;

import java.util.Date;

public class OrgAndSct {

    private String orgnam;
    private String supbak;
    private Date crndat;
    private Date Ipydat;
    private Date nwkday;
    private Date befIpydat;
    private Date aftIpydat;

    public String getOrgnam() {
        return orgnam;
    }

    public void setOrgnam(String orgnam) {
        this.orgnam = orgnam;
    }

    public String getSupbak() {
        return supbak;
    }

    public void setSupbak(String supbak) {
        this.supbak = supbak;
    }

    public Date getCrndat() {
        return crndat;
    }

    public void setCrndat(Date crndat) {
        this.crndat = crndat;
    }

    public Date getIpydat() {
        return Ipydat;
    }

    public void setIpydat(Date ipydat) {
        Ipydat = ipydat;
    }

    public Date getNwkday() {
        return nwkday;
    }

    public void setNwkday(Date nwkday) {
        this.nwkday = nwkday;
    }

    public Date getBefIpydat() {
        return befIpydat;
    }

    public void setBefIpydat(Date befIpydat) {
        this.befIpydat = befIpydat;
    }

    public Date getAftIpydat() {
        return aftIpydat;
    }

    public void setAftIpydat(Date aftIpydat) {
        this.aftIpydat = aftIpydat;
    }
}
