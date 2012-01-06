package cbs.repository.account.maininfo.model;

import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: haiyu
 * Date: 2011-1-26
 * Time: 15:08:49
 * To change this template use File | Settings | File Templates.
 */
public class ActoacChd extends Actoac {
    private String ballimD;

    private String ovelimD;

    private String oldacn;

    private String strBokbal;

    private String strAvabal;

    public String getBallimD() {
        return ballimD;
    }

    public void setBallimD(String ballimD) {
        this.ballimD = (ballimD == ""?"0":ballimD);
    }

    public String getOvelimD() {
        return ovelimD;
    }

    public void setOvelimD(String ovelimD) {
        this.ovelimD = (ovelimD == ""?"0":ovelimD);
    }

    public String getStrBokbal() {
        return strBokbal;
    }

    public void setStrBokbal(String strBokbal) {
        this.strBokbal = strBokbal;
    }

    public String getOldacn() {
        return oldacn;
    }

    public void setOldacn(String oldacn) {
        this.oldacn = oldacn;
    }

    public String getStrAvabal() {
        return strAvabal;
    }

    public void setStrAvabal(String strAvabal) {
        this.strAvabal = strAvabal;
    }
}
