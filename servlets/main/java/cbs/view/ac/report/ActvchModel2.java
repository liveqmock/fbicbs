package cbs.view.ac.report;

import cbs.repository.account.maininfo.model.ActvchForGlDRpt;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: haiyu
 * Date: 2010-12-15
 * Time: 15:40:14
 * To change this template use File | Settings | File Templates.
 */
public class ActvchModel2 {
    private String curnmc; //货币名称
    private String glcnam; //科目名称
    private List<ActvchForGlDRpt> vchRptLst;  //科目日结单报表list

    public String getCurnmc() {
        return curnmc;
    }

    public void setCurnmc(String curnmc) {
        this.curnmc = curnmc;
    }

    public String getGlcnam() {
        return glcnam;
    }

    public void setGlcnam(String glcnam) {
        this.glcnam = glcnam;
    }

    public List<ActvchForGlDRpt> getVchRptLst() {
        return vchRptLst;
    }

    public void setVchRptLst(List<ActvchForGlDRpt> vchRptLst) {
        this.vchRptLst = vchRptLst;
    }
}