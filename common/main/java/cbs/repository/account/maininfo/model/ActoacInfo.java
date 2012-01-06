package cbs.repository.account.maininfo.model;

/**
 * Created by IntelliJ IDEA.
 * User: haiyu
 * Date: 2010-11-29
 * Time: 13:33:03
 * To change this template use File | Settings | File Templates.
 */
public class ActoacInfo extends Actoac {
    //’À∫≈
    private String oldacn;
    //’À√Ê”‡∂Ó
    private long bokbal;
    //’À√Ê”‡∂Óformat
    private String strBokbal;
    //’Àªß◊¥Ã¨
    private String actsts;

    public String getOldacn() {
        return oldacn;
    }

    public void setOldacn(String oldacn) {
        this.oldacn = oldacn;
    }

    public long getBokbal() {
        return bokbal;
    }

    public void setBokbal(long bokbal) {
        this.bokbal = bokbal;
    }

    public String getActsts() {
        return actsts;
    }

    public void setActsts(String actsts) {
        this.actsts = actsts;
    }

    public String getStrBokbal() {
        return strBokbal;
    }
    
    public void setStrBokbal(String strBokbal) {
        this.strBokbal = strBokbal;
    }

}
