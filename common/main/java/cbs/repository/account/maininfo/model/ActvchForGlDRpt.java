package cbs.repository.account.maininfo.model;

/**
 * Created by IntelliJ IDEA.
 * User: haiyu
 * Date: 2010-12-13
 * Time: 16:04:34
 * To change this template use File | Settings | File Templates.
 */
public class ActvchForGlDRpt extends Actvth {
    private String rvslbl;  //转账or现金etc
    private String damt;    //借方金额
    private String camt;    //贷方金额
    private String dcunt;   //借方笔数
    private String ccunt;   //贷方笔数
    private String glcnam;  //科目名称
    private String glcode;  //科目号
    private String curnmc;  //币种名

    public String getRvslbl() {
        return rvslbl;
    }

    public void setRvslbl(String rvslbl) {
        this.rvslbl = rvslbl;
    }

    public String getDamt() {
        return damt;
    }

    public void setDamt(String damt) {
        this.damt = damt;
    }

    public String getCamt() {
        return camt;
    }

    public void setCamt(String camt) {
        this.camt = camt;
    }

    public String getDcunt() {
        return dcunt;
    }

    public void setDcunt(String dcunt) {
        this.dcunt = dcunt;
    }

    public String getCcunt() {
        return ccunt;
    }

    public void setCcunt(String ccunt) {
        this.ccunt = ccunt;
    }

    public String getGlcnam() {
        return glcnam;
    }

    public void setGlcnam(String glcnam) {
        this.glcnam = glcnam;
    }

    public String getGlcode() {
        return glcode;
    }

    public void setGlcode(String glcode) {
        this.glcode = glcode;
    }

    public String getCurnmc() {
        return curnmc;
    }

    public void setCurnmc(String curnmc) {
        this.curnmc = curnmc;
    }
}
