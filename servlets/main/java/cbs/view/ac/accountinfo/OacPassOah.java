package cbs.view.ac.accountinfo;

import cbs.common.SystemService;
import cbs.common.enums.ACEnum;
import cbs.repository.account.maininfo.model.Actoac;
import cbs.repository.account.maininfo.model.Actoah;
import cbs.repository.account.maininfo.model.Actobf;
import cbs.repository.code.model.Actani;
import cbs.repository.code.model.Actglc;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.SimpleDateFormat;

/**
 * Created by IntelliJ IDEA.
 * User: haiyu
 * Date: 2010-11-30
 * Time: 13:52:34
 * To change this template use File | Settings | File Templates.
 */
public class OacPassOah {
    private static final Log logger = LogFactory.getLog(OacPassOah.class);

    public Actani oacPassani(Actoac oac) {
        Actani ani = new Actani();
        ani.setSysidt(ACEnum.SYSIDT_AC.getStatus());
        ani.setOrgidt(oac.getOrgidt());
        ani.setCusidt(oac.getCusidt());
        ani.setApcode(oac.getApcode());
        ani.setCurcde(oac.getCurcde());
        ani.setDepnum(oac.getDepnum());
        ani.setAmdtlr(oac.getCretlr());
        ani.setUpddat(oac.getCredat());
        return ani;
    }
    public Actobf oacPassobf(Actoac oac) {
        Actobf obf = new Actobf();
        obf.setSysidt(ACEnum.SYSIDT_AC.getStatus());
        obf.setOrgidt(oac.getOrgidt());
        obf.setCusidt(oac.getCusidt());
        obf.setApcode(oac.getApcode());
        obf.setCurcde(oac.getCurcde());
        obf.setActsts(ACEnum.ACTSTS_VALID.getStatus());
        obf.setFrzsts(ACEnum.FRZSTS_NORMAL.getStatus());
        obf.setRegsts(ACEnum.REGSTS_NORMAL.getStatus());
//        obf.setRecsts();  ?
        if (oac.getOveexp() != null) {
            SimpleDateFormat dtFormat = new SimpleDateFormat("yyyyMMdd");
            String dt = dtFormat.format(oac.getOveexp());
            obf.setOveexp(dt);
        }
        obf.setCqeflg(oac.getCqeflg());
        Actglc glc = new Actglc();
        obf.setGlcbal(glc.getGLCBAL(oac.getApcode()));
        return obf;
        

    }
    public Actoah oacPassOah(Actoac oac) {
        Actoah oah = new Actoah();
        oah.setOrgidt(oac.getOrgidt());
        oah.setCusidt(oac.getCusidt());
        oah.setApcode(oac.getApcode());
        oah.setCurcde(oac.getCurcde());
        oah.setOacflg(oac.getOacflg());
        oah.setActnam(oac.getActnam());
        oah.setDinrat(oac.getDinrat());
        oah.setCinrat(oac.getCinrat());
        oah.setDratsf(oac.getDratsf());
        oah.setCratsf(oac.getCratsf());
        oah.setIntflg(oac.getIntflg());
        oah.setIntcyc(oac.getIntcyc());
        oah.setInttra(oac.getInttra());
        oah.setStmcyc(oac.getStmcyc());
        oah.setStmcdt(oac.getStmcdt());
        oah.setStmfmt(oac.getStmfmt());
        oah.setStmadd(oac.getStmadd());
        oah.setStmzip(oac.getStmzip());
        oah.setStmsht(oac.getStmsht());
        oah.setStmdep(oac.getStmdep());
        oah.setLegcyc(oac.getLegcyc());
        oah.setLegcdt(oac.getLegcdt());
        oah.setLegfmt(oac.getLegfmt());
        oah.setLegadd(oac.getLegadd());
        oah.setLegzip(oac.getLegzip());
        oah.setLegsht(oac.getLegsht());
        oah.setLegdep(oac.getLegdep());
        oah.setActtyp(oac.getActtyp());
        oah.setDepnum(oac.getDepnum());
        oah.setOpndat(oac.getOpndat());
        oah.setClsdat(oac.getClsdat());
        oah.setCqeflg(oac.getCqeflg());
        oah.setBallim(oac.getBallim());
        oah.setOvelim(oac.getOvelim());
        oah.setOveexp(oac.getOveexp());
        oah.setCretlr(oac.getCretlr());
        oah.setCredat(oac.getCredat());
        oah.setCretim(SystemService.getBizTime8());
        oah.setRecsts(oac.getRecsts());
        return oah;
    }
}
