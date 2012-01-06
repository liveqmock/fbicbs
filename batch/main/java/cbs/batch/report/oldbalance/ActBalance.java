package cbs.batch.report.oldbalance;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 11-4-22
 * Time: ÉÏÎç10:01
 * To change this template use File | Settings | File Templates.
 */
public class ActBalance {
    private String orgidt;
    private String oldacn;
    private String actnam;
    private Double bokbal;

    public String getOrgidt() {
        return orgidt;
    }

    public void setOrgidt(String orgidt) {
        this.orgidt = orgidt;
    }

    public String getOldacn() {
        return oldacn;
    }

    public void setOldacn(String oldacn) {
        this.oldacn = oldacn;
    }

    public String getActnam() {
        return actnam;
    }

    public void setActnam(String actnam) {
        this.actnam = actnam;
    }

    public Double getBokbal() {
        return bokbal;
    }

    public void setBokbal(Double bokbal) {
        this.bokbal = bokbal;
    }
}
