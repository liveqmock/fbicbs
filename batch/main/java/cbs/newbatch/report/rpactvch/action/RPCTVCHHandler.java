package cbs.newbatch.report.rpactvch.action;

import cbs.batch.common.service.BatchSystemService;
import cbs.common.utils.ReportHelper;
import cbs.repository.account.maininfo.dao.ActvthMapper;
import cbs.repository.account.maininfo.model.ActvchForGlDRpt;
import cbs.repository.code.dao.ActorgMapper;
import cbs.repository.code.model.Actorg;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Lichao.W
 * Date: 2014/10/24
 * Time: 12:56
 * To change this template use File | Settings | File Templates.
 */
public class RPCTVCHHandler {
    private static final Log logger = LogFactory.getLog(RPCTVCHHandler.class);
    private String reportdate = "";
    private ActorgMapper orgmap;
    private ActvthMapper vchmap;
    private BatchSystemService systemService;

    @Test
    public void getData() {
        reportdate = systemService.getBizDate10();
        DecimalFormat df0 = new DecimalFormat("###,##0.00");
        DecimalFormat df01 = new DecimalFormat("###,###");
        String orgidt = "";
        String orgnam = "";
        String titleOrg = "";
        String titleCurDate = "";
        String titleGlc = "";
        String titleCur = "";
        List<Actorg> orgLst = orgmap.selectForActvch();
        Iterator it = orgLst.iterator();
        while (it.hasNext()) {
            Actorg org = (Actorg) it.next();
            orgidt = org.getOrgidt();

        }
        List<ActvchForGlDRpt> glcurLst = vchmap.selectForGlCur(orgidt);
        Iterator it1 = glcurLst.iterator();
        while (it1.hasNext()) {
            ActvchForGlDRpt glcur = (ActvchForGlDRpt) it1.next();
            String glcode = glcur.getGlcode();
            String curcde = glcur.getCurcde();
            String glcnam = glcur.getGlcnam();
            String curnmc = glcur.getCurnmc();

            titleOrg = ReportHelper.getLeftSpaceStr("机构：" + orgnam, 48);
            titleCurDate = ReportHelper.getLeftSpaceStr("日期：" + reportdate, 20);
            titleGlc = ReportHelper.getLeftSpaceStr("科目：" + glcode + " " + glcnam, 48);
            titleCur = ReportHelper.getLeftSpaceStr("币别：" + curnmc, 20);
            System.out.println(titleOrg+titleCurDate+titleGlc+titleCur);

            List<ActvchForGlDRpt> gldrptLst = vchmap.selectForGlDRpt(orgidt, curcde, glcode);
            Iterator it2 = gldrptLst.iterator();
            int totalDcunt = 0;
            double totalDamt = 0;
            int totalCcunt = 0;
            double totalCamt = 0;
            int tempi = 0;
            while (it2.hasNext()) {
                ActvchForGlDRpt gldrpt = (ActvchForGlDRpt) it2.next();
                String rvslbl = gldrpt.getRvslbl();
                int intDcun = Integer.parseInt(gldrpt.getDcunt());
                double doubleDamt = Double.parseDouble(gldrpt.getDamt()) * -1 / 100;
                int intCcunt = Integer.parseInt(gldrpt.getCcunt());
                double doubleCamt = Double.parseDouble(gldrpt.getCamt()) / 100;
                String dcunt = ReportHelper.getRightSpaceStr(df01.format(intDcun), 10) + "┃";   //借方笔数
                String damt = ReportHelper.getRightSpaceStr(df0.format(doubleDamt), 22) + "┃";     //借方金额
                String ccunt = ReportHelper.getRightSpaceStr(df01.format(intCcunt), 10) + "┃";   //贷方笔数
                String camt = ReportHelper.getRightSpaceStr(df0.format(doubleCamt), 22) + "┃";     //贷方金额
                System.out.println(rvslbl+"借方笔数"+dcunt+"借方金额"+damt+"贷方笔数"+ccunt+"贷方金额:"+camt);
            }
        }
    }
}
