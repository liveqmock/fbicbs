package cbs.repository.account.maininfo.model;

import cbs.repository.account.billinfo.model.Actlgc;

import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: haiyu
 * Date: 2010-12-16
 * Time: 9:46:36
 * To change this template use File | Settings | File Templates.
 */
public class ActstmForActnum extends Actlgc {
    private String orgnam;  //������
    private String glcnam;  //��Ŀ��
    private String oldacn;  //�˺�
    private String actnam;  //�˻�����
    private String depnum;  //���ź�
    private String depnam;  //������
    private String curnmc;  //��������
    private long legbal;   //�ϴε�һ���ҷֻ������
    private BigDecimal irtval; //�˺�����

    public String getOrgnam() {
        return orgnam;
    }

    public void setOrgnam(String orgnam) {
        this.orgnam = orgnam;
    }

    public String getGlcnam() {
        return glcnam;
    }

    public void setGlcnam(String glcnam) {
        this.glcnam = glcnam;
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

    public String getDepnum() {
        return depnum;
    }

    public void setDepnum(String depnum) {
        this.depnum = depnum;
    }

    public String getDepnam() {
        return depnam;
    }

    public void setDepnam(String depnam) {
        this.depnam = depnam;
    }

    public String getCurnmc() {
        return curnmc;
    }

    public void setCurnmc(String curnmc) {
        this.curnmc = curnmc;
    }

    public long getLegbal() {
        return legbal;
    }

    public void setLegbal(long legbal) {
        this.legbal = legbal;
    }

    public BigDecimal getIrtval() {
        return irtval;
    }

    public void setIrtval(BigDecimal irtval) {
        this.irtval = irtval;
    }
}
