package cbs.view.ac.verifi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: Lichao.W
 * Date: 2014/10/28
 * Time: 8:49
 * To change this template use File | Settings | File Templates.
 */
public class JinDieBean {
    private static final Logger logger = LoggerFactory.getLogger(JinDieBean.class);

    private String jdnum; //����˺�
    private String jdinf; //ƾ֤��
    private String jddat; //����
    private String jdcmt; //�������
    private String jddmt; //�跽���

    public String getJdnum() {
        return jdnum;
    }

    public void setJdnum(String jdnum) {
        this.jdnum = jdnum;
    }

    public String getJdinf() {
        return jdinf;
    }

    public void setJdinf(String jdinf) {
        this.jdinf = jdinf;
    }

    public String getJddat() {
        return jddat;
    }

    public void setJddat(String jddat) {
        this.jddat = jddat;
    }

    public String getJdcmt() {
        return jdcmt;
    }

    public void setJdcmt(String jdcmt) {
        this.jdcmt = jdcmt;
    }

    public String getJddmt() {
        return jddmt;
    }

    public void setJddmt(String jddmt) {
        this.jddmt = jddmt;
    }
}
