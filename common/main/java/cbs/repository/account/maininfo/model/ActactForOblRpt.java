package cbs.repository.account.maininfo.model;

/**
 * Created by IntelliJ IDEA.
 * User: haiyu
 * Date: 2010-12-10
 * Time: 17:22:45
 * To change this template use File | Settings | File Templates.
 */
public class ActactForOblRpt extends Actact {
    private String oldacn;
    private String glcode; //������code
    private String apcode; //������code
    private String glcnam; //��������
    private String apcnam; //��������
    private String curnmc; //������

    public String getOldacn() {
        return oldacn;
    }

    public void setOldacn(String oldacn) {
        this.oldacn = oldacn;
    }

    public String getGlcode() {
        return glcode;
    }

    public void setGlcode(String glcode) {
        this.glcode = glcode;
    }

    public String getApcode() {
        return apcode;
    }

    public void setApcode(String apcode) {
        this.apcode = apcode;
    }

    public String getGlcnam() {
        return glcnam;
    }

    public void setGlcnam(String glcnam) {
        this.glcnam = glcnam;
    }

    public String getApcnam() {
        return apcnam;
    }

    public void setApcnam(String apcnam) {
        this.apcnam = apcnam;
    }

    public String getCurnmc() {
        return curnmc;
    }

    public void setCurnmc(String curnmc) {
        this.curnmc = curnmc;
    }
}
