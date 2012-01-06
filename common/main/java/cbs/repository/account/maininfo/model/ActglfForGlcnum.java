package cbs.repository.account.maininfo.model;

/**
 * Created by IntelliJ IDEA.
 * User: haiyu
 * Date: 2010-12-21
 * Time: 13:56:40
 * To change this template use File | Settings | File Templates.
 */
public class ActglfForGlcnum extends Actglf {
    private String glcnam;  //机构名称
    private String curnmc;  //币别名

    public String getGlcnam() {
        return glcnam;
    }

    public void setGlcnam(String glcnam) {
        this.glcnam = glcnam;
    }

    public String getCurnmc() {
        return curnmc;
    }

    public void setCurnmc(String curnmc) {
        this.curnmc = curnmc;
    }
}
