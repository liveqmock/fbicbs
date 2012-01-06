package cbs.view.ac.report;

import cbs.common.IbatisManager;
import cbs.common.SystemService;
import cbs.common.enums.ACEnum;
import cbs.common.utils.MessageUtil;
import cbs.common.utils.PropertyManager;
import cbs.common.utils.ReportHelper;
import cbs.repository.account.billinfo.dao.ActlgcMapper;
import cbs.repository.account.billinfo.dao.ActnsmMapper;
import cbs.repository.account.maininfo.dao.ActactMapper;
import cbs.repository.account.maininfo.dao.ActcglMapper;
import cbs.repository.account.maininfo.dao.ActglfMapper;
import cbs.repository.account.maininfo.dao.ActvthMapper;
import cbs.repository.account.maininfo.model.*;
import cbs.repository.code.dao.ActccyMapper;
import cbs.repository.code.dao.ActglcMapper;
import cbs.repository.code.dao.ActorgMapper;
import cbs.repository.code.model.Actccy;
import cbs.repository.code.model.Actglc;
import cbs.repository.code.model.Actorg;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: haiyu
 * Date: 2010-12-8
 * Time: 13:29:04
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean(name = "report")
@RequestScoped
public class Report {
    @ManagedProperty("#{ibatisManager}")
    IbatisManager ibatisManager;
    private static final Log logger = LogFactory.getLog(Report.class);
    private final String nowDate = SystemService.getBizDate();
    private final String reportdate = SystemService.getBizDate10();
    /**
     * 损益表报表生成*/
    public void ExcelPlc() {
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        try {
            DecimalFormat df0 = new DecimalFormat("###,##0.00");
            String strPath = PropertyManager.getProperty("REPORT_ROOTPATH") + nowDate + "\\010\\";
            String plitStr = "|";
            String title0 = "                                                             损益表";
            String title1 = "货币:人民币                                      " + reportdate + "            " +
                    "                                      单位：元";
            String titleCont0 = "                         支出项目                              " + plitStr +
                    "                            收入项目 ";
            String titleCont1 = "    代号                 名称                      金额        " + plitStr +
                    "    代号                 名称                      金额";
            int contLen0 = 6;
            int contLen1 = 5;
            int contLen2 = 32;
            int contLen3 = 20;
            String leftSpace = ReportHelper.getRightSpaceStr(plitStr, contLen0 + contLen1 + contLen2 + contLen3 + plitStr.length());
            String plan = "------------------------------------------------------------------------------" +
                    "-------------------------------------------------";
            //创建路径
            File fileDirec = new File(strPath);
            if (!fileDirec.isDirectory() || !fileDirec.exists()) {
                if (!fileDirec.mkdirs()) {
                    MessageUtil.addInfo("路径创建失败！");
                    logger.error("路径创建失败！");
                }
            }
            //创建报表文件
            File file = new File(strPath+"损益表.txt");
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw=new BufferedWriter(fw);
            bw.write(title0);
            bw.newLine();
            bw.write(title1);
            bw.newLine();
            bw.write(plan);
            bw.newLine();
            bw.write(titleCont0);
            bw.newLine();
            bw.write(plan);
            bw.newLine();
            bw.write(titleCont1);
            bw.newLine();
            bw.write(plan);
            bw.newLine();
            List<String> strLst = new ArrayList();
            ActglcMapper glcmap = session.getMapper(ActglcMapper.class);
            List<Actglc> glcLst = glcmap.selectGrpActForPlc(ACEnum.CURCDE_001.getStatus(),ACEnum.PLCTYP_OUT.getStatus());//支出项目
            Iterator it0 = glcLst.iterator();
            while (it0.hasNext()) {
                Actglc glc = (Actglc)it0.next();
                String glcode = glc.getGlcode();
                String glcnam = glc.getGlcnam();
                String strT = ReportHelper.getLeftSpaceStr(glcode, contLen0 + contLen1) + ReportHelper.getLeftSpaceStr(glcnam, contLen2 + contLen3)
                        + plitStr;
                strLst.add(strT);
                ActactMapper actmap = session.getMapper(ActactMapper.class);
                List<ActactForPlcRpt> plcLst = actmap.selectForPlc(ACEnum.CURCDE_001.getStatus(),glcode);
                Iterator it1 = plcLst.iterator();
                while (it1.hasNext()) {
                    ActactForPlcRpt plc = (ActactForPlcRpt)it1.next();
                    String plcode = ReportHelper.getRightSpaceStr(ReportHelper.getLeftSpaceStr(plc.getActplc(), contLen1), contLen0 + contLen1);
                    String plcnam = ReportHelper.getLeftSpaceStr(plc.getPlcnam(), contLen2);
                    double dbTotalBokbal = (double)plc.getTotalBokbal()/100;
                    String totalBokbal = ReportHelper.getRightSpaceStr(df0.format(dbTotalBokbal), contLen3);
                    strLst.add(plcode + plcnam + totalBokbal + plitStr);
                }
            }
            List<Actglc> glc1Lst = glcmap.selectGrpActForPlc(ACEnum.CURCDE_001.getStatus(),ACEnum.PLCTYP_IN.getStatus());//收入项目
            Iterator it2 = glc1Lst.iterator();
            int lstCnt = strLst.size();
            int i = 1;
            while (it2.hasNext()) {
                Actglc glc = (Actglc)it2.next();
                String glcode = glc.getGlcode();
                String glcnam = glc.getGlcnam();
                String strT = ReportHelper.getLeftSpaceStr(glcode, contLen0 + contLen1) + ReportHelper.getLeftSpaceStr(glcnam, contLen2 + contLen3);
                if (lstCnt >= i) {
                    String tmpStr = strLst.get(i-1);
                    strLst.remove(i-1);
                    strLst.add(i-1, tmpStr + strT);
                }
                else {
                    strLst.add(leftSpace + strT);
                }
                i++;
                ActactMapper actmap = session.getMapper(ActactMapper.class);
                List<ActactForPlcRpt> plcLst = actmap.selectForPlc(ACEnum.CURCDE_001.getStatus(),glcode);
                Iterator it1 = plcLst.iterator();
                while (it1.hasNext()) {
                    ActactForPlcRpt plc = (ActactForPlcRpt)it1.next();
                    String plcode = ReportHelper.getRightSpaceStr(ReportHelper.getLeftSpaceStr(plc.getActplc(), contLen1), contLen0 + contLen1);
                    String plcnam = ReportHelper.getLeftSpaceStr(plc.getPlcnam(), contLen2);
                    double dbTotalBokbal = (double)plc.getTotalBokbal()/100;
                    String totalBokbal = ReportHelper.getRightSpaceStr(df0.format(dbTotalBokbal), contLen3);
                    String strT0 = plcode + plcnam + totalBokbal;
                    if (lstCnt >= i) {
                        String tmpStr = strLst.get(i-1);
                        strLst.remove(i-1);
                        strLst.add(i-1,tmpStr + strT0);
                    } else {
                        strLst.add(leftSpace + strT0);
                    }
                    i++;
                }
            }
            lstCnt = strLst.size();
            for(i=0;i<lstCnt;i++) {
                bw.write(strLst.get(i));
                bw.newLine();
            }
            bw.flush();
            bw.close();
            MessageUtil.addInfoWithClientID(null, "损益表数据生成成功！");
        } catch (Exception ex) {
            ex.getMessage();
            ex.printStackTrace();
            logger.error("损益表报表抽出数据错误。");
            MessageUtil.addInfoWithClientID(null, "损益表数据抽出数据错误！");
        } finally {
            session.close();
        }
    }
    /*
    * 余额表2报表数据生成*/
    public String ExcelObf2() {
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        try {
            DecimalFormat df0 = new DecimalFormat("###,##0.00");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String strPath = PropertyManager.getProperty("REPORT_ROOTPATH") + nowDate + "\\";
            String orgidt = "";
            String orgnam = "";
            int pageRowCnt = 50;   //25行数据(+分行标志行+表头+页脚 总共60行/页)
            int pageLine = 10;     //每隔10行加横线
            String title0 = "                                       余额表";
            String titleCont0 = "       账号       ";
            String titleCont1 = "              名称              ";
            String titleCont2 = "      余额     ";
            String titleCont3 = "  最后交易日期";
            String titleCont4 = "   借方发生额  ";
            String titleCont5 = "   贷方发生额  ";
            String plan = "-----------------------------------------------------------------------------------------------------------";
            int title01Len = 50;   //表头内容（机构）字节长度
            int title02Len = 27;   //表头内容（日期）字节长度
            ActorgMapper orgmap = session.getMapper(ActorgMapper.class);
            List<Actorg> orgLst = orgmap.selectForActact();
            Iterator it = orgLst.iterator();
            while (it.hasNext()) {
                int pageCount = 1;   //页数
                Actorg org = (Actorg) it.next();
                orgidt = org.getOrgidt();
                orgnam = org.getOrgnam();
                strPath = strPath + orgidt + "\\";
                //创建路径
                File fileDirec = new File(strPath);
                if (!fileDirec.isDirectory() || !fileDirec.exists()) {
                    if (!fileDirec.mkdirs()) {
                        MessageUtil.addInfo("路径创建失败！");
                        logger.error("路径创建失败！");
                    }
                }
                File file = new File(strPath+"余额表.txt");
                FileWriter fw = new FileWriter(file);
                BufferedWriter bw=new BufferedWriter(fw);
                bw.write(title0);
                bw.newLine();
                bw.write(ReportHelper.getLeftSpaceStr("机构：" + orgnam, title01Len) + ReportHelper.getLeftSpaceStr("日期：" + reportdate, title02Len) + "页码：" + pageCount);
                bw.newLine();
                bw.write(plan);
                bw.newLine();
                bw.write(titleCont0 + titleCont1 + titleCont2 + titleCont3 + titleCont4 + titleCont5);
                bw.newLine();
                bw.write(plan);
                bw.newLine();
                int j = 0; //行数计算
                //获得币种分组
                ActactMapper actmap = session.getMapper(ActactMapper.class);
                List<ActactForOblRpt> CurLst = actmap.selectGrpCurcde(orgidt);
                Iterator itCur = CurLst.iterator();
                while (itCur.hasNext()) {
                    ActactForOblRpt grpCur = (ActactForOblRpt) itCur.next();
                    String curcde = grpCur.getCurcde();
                    String curnmc = grpCur.getCurnmc();
                    bw.write(ReportHelper.getLeftSpaceStr("币别：" + curcde, 18) + curnmc);
                    bw.newLine();
                    j++;
                    //加横线
                    if (j !=0 && (j % pageLine == 0)) {
                        bw.write(plan);
                        bw.newLine();
                    }
                    //换页
                    if ((j!=0) && (j%pageRowCnt==0)) {
                        //换页
                        pageCount++;
                        bw.write("\f");
                        //建立表头
                        bw.write(title0);
                        bw.newLine();
                        bw.write(titleCont0 + titleCont1 + titleCont2 + titleCont3 + titleCont4 + titleCont5);
                        bw.newLine();
                        bw.write(plan);
                        bw.newLine();
                        bw.write(ReportHelper.getLeftSpaceStr("机构：" + orgnam, title01Len) + ReportHelper.getLeftSpaceStr("日期：" + reportdate, title02Len) + "页码：" + pageCount);
                        bw.newLine();
                        bw.write(plan);
                        bw.newLine();

                    }
                    //获得科目分组
                    List<ActactForOblRpt> glLst = actmap.selectGrpGlcode(orgidt,curcde);
                    Iterator itGl = glLst.iterator();
                    while (itGl.hasNext()) {
                        ActactForOblRpt grpGl = (ActactForOblRpt) itGl.next();
                        String glcode = grpGl.getGlcode();
                        String glcnam = grpGl.getGlcnam();
                        double bokbalGl = (double)grpGl.getBokbal()/100;
                        String bokbalGlStr = ReportHelper.getRightSpaceStr(df0.format(bokbalGl), 15);
                        bw.write(ReportHelper.getLeftSpaceStr("总账码：" + glcode, 18) + ReportHelper.getLeftSpaceStr(glcnam, 32) + bokbalGlStr);
                        bw.newLine();
                        j++;
                        //加横线
                        if (j !=0 && (j % pageLine == 0)) {
                            bw.write(plan);
                            bw.newLine();
                        }
                        //换页
                        if ((j!=0) && (j%pageRowCnt==0)) {
                            //换页
                            pageCount++;
                            bw.write("\f");
                            //建立表头
                            bw.write(title0);
                            bw.newLine();
                            bw.write(ReportHelper.getLeftSpaceStr("机构：" + orgnam, title01Len) + ReportHelper.getLeftSpaceStr("日期：" + reportdate, title02Len) + "页码：" + pageCount);
                            bw.newLine();
                            bw.write(plan);
                            bw.newLine();
                            bw.write(titleCont0 + titleCont1 + titleCont2 + titleCont3 + titleCont4 + titleCont5);
                            bw.newLine();
                            bw.write(plan);
                            bw.newLine();
                        }
                        //获得核算码分组
                        List<ActactForOblRpt> apLst = actmap.selectGrpApcode(orgidt,curcde,glcode);
                        Iterator itAp = apLst.iterator();
                        while (itAp.hasNext()) {
                            ActactForOblRpt grpAp = (ActactForOblRpt)itAp.next();
                            String apcode = grpAp.getApcode();
                            String apcnam = grpAp.getApcnam();
                            double bokbalAp = (double)grpAp.getBokbal()/100;
                            String bokbalApStr = ReportHelper.getRightSpaceStr(df0.format(bokbalAp), 15);
                            bw.write(ReportHelper.getLeftSpaceStr("核算码：" + apcode, 18) + ReportHelper.getLeftSpaceStr(apcnam, 32) + bokbalApStr);
                            bw.newLine();
                            j++;
                            //加横线
                            if (j !=0 && (j % pageLine == 0)) {
                                bw.write(plan);
                                bw.newLine();
                            }
                            //换页
                            if ((j!=0) && (j%pageRowCnt==0)) {
                                //换页
                                pageCount++;
                                bw.write("\f");
                                //建立表头
                                bw.write(title0);
                                bw.newLine();
                                bw.write(ReportHelper.getLeftSpaceStr("机构：" + orgnam, title01Len) + ReportHelper.getLeftSpaceStr("日期：" + reportdate, title02Len) + "页码：" + pageCount);
                                bw.newLine();
                                bw.write(plan);
                                bw.newLine();
                                bw.write(titleCont0 + titleCont1 + titleCont2 + titleCont3 + titleCont4 + titleCont5);
                                bw.newLine();
                                bw.write(plan);
                                bw.newLine();
                            }
                             //获得账号信息
                            List<ActactForOblRpt> actLst = actmap.selectForObfRpt(orgidt,curcde,apcode);
                            Iterator itAct = actLst.iterator();
                            while (itAct.hasNext()) {
                                ActactForOblRpt actRpt = (ActactForOblRpt) itAct.next();
                                String oldacn = actRpt.getOldacn();
                                String actnam = actRpt.getActnam();
                                double bokbal = (double)actRpt.getBokbal()/100;
                                String lentdt = df.format(actRpt.getLentdt());
                                double ddramt = (double)(actRpt.getDdramt() * -1)/100;
                                double dcramt = (double)actRpt.getDcramt()/100;
                                oldacn = ReportHelper.getLeftSpaceStr(oldacn, 18);
                                actnam = ReportHelper.getLeftSpaceStr(actnam, 32);
                                String bokbalStr = ReportHelper.getRightSpaceStr(df0.format(bokbal), 15);
                                String ddramtStr = ReportHelper.getRightSpaceStr(df0.format(ddramt), 15);
                                String dcramtStr = ReportHelper.getRightSpaceStr(df0.format(dcramt), 15);
                                bw.write(oldacn + actnam + bokbalStr + "  " + lentdt + ddramtStr + dcramtStr);
                                bw.newLine();
                                j++;
                                //加横线
                                if (j !=0 && (j % pageLine == 0)) {
                                    bw.write(plan);
                                    bw.newLine();
                                }
                                //换页
                                if ((j!=0) && (j%pageRowCnt==0)) {
                                    //换页
                                    pageCount++;
                                    bw.write("\f");
                                    //建立表头
                                    bw.write(title0);
                                    bw.newLine();
                                    bw.write(ReportHelper.getLeftSpaceStr("机构：" + orgnam, title01Len) + ReportHelper.getLeftSpaceStr("日期：" + reportdate, title02Len) + "页码：" + pageCount);
                                    bw.newLine();
                                    bw.write(plan);
                                    bw.newLine();
                                    bw.write(titleCont0 + titleCont1 + titleCont2 + titleCont3 + titleCont4 + titleCont5);
                                    bw.newLine();
                                    bw.write(plan);
                                    bw.newLine();
                                }
                            }
                        }
                    }
                }
                bw.flush();
                bw.close();
            }
             MessageUtil.addInfoWithClientID(null, "余额表数据生成成功！");
        } catch (Exception ex) {
            ex.getMessage();
            ex.printStackTrace();
            logger.error("抽出数据错误。");
            MessageUtil.addInfoWithClientID(null, "余额表数据抽出数据错误！");
        } finally {
            session.close();
            return null;
        }
    }

    /**
     * 日结单2*/
    public String ExcelActvch2() {
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        try {
            DecimalFormat df0 = new DecimalFormat("###,##0.00");
            DecimalFormat df01 = new DecimalFormat("###,###");
            String strPath = PropertyManager.getProperty("REPORT_ROOTPATH") + nowDate + "\\";
            String orgidt = "";
            String orgnam = "";
            String title1 = "                                 科目日结单";
            String titleOrg = "";
            String titleCurDate = "";
            String titleGlc = "";
            String titleCur = "";
            String title2Cell1 = "          ";
            String title2Cell2 = "                  借方                  ";
            String title2Cell3 = "                  贷方                  ";
            String title3Cell2 = "       笔数       ";
            String title3Cell3 = "         金额         ";
            String title4 = "现金      ";
            String title5 = "转账      ";
            String title6 = "其它      ";
            String title7 = "合计      ";
            String footer = title2Cell1 + "复合：                                  制表：                                  ";
            String plan = "-------------------------------------------------------------------------------------------";
            ActorgMapper orgmap = session.getMapper(ActorgMapper.class);
            List<Actorg> orgLst = orgmap.selectForActvch();
            if (orgLst.size() == 0) {
                MessageUtil.addInfo("科目日结单报表无数据生成。");
                logger.info("科目日结单报表无数据生成。");
                return null;
            }
            Iterator it = orgLst.iterator();
            while (it.hasNext()) {
                Actorg org = (Actorg) it.next();
                orgidt = org.getOrgidt();
                orgnam = org.getOrgnam();
                strPath += orgidt + "\\";
                //创建路径
                File fileDirec = new File(strPath);
                if (!fileDirec.isDirectory() || !fileDirec.exists()) {
                    if (!fileDirec.mkdirs()) {
                        MessageUtil.addInfo("路径创建失败！");
                        logger.error("路径创建失败！");
                    }
                }
                File file = new File(strPath+"日结单.txt");
                FileWriter fw = new FileWriter(file);
                BufferedWriter bw=new BufferedWriter(fw);
                PrintWriter pw = new PrintWriter(fw);
                //获取数据
                int rowCnt = 0;
                ActvthMapper vchmap = session.getMapper(ActvthMapper.class);
                List<ActvchForGlDRpt> glcurLst = vchmap.selectForGlCur(orgidt);
                int glCurCnt = 0;
                Iterator it1 = glcurLst.iterator();
                while (it1.hasNext()) {
                    ActvchForGlDRpt glcur = (ActvchForGlDRpt)it1.next();
                    String glcode = glcur.getGlcode();
                    String curcde = glcur.getCurcde();
                    String glcnam = glcur.getGlcnam();
                    String curnmc = glcur.getCurnmc();
                    titleOrg = ReportHelper.getLeftSpaceStr("机构：" + orgnam, 48);
                    titleCurDate = ReportHelper.getLeftSpaceStr("日期：" + reportdate, 20);
                    titleGlc = ReportHelper.getLeftSpaceStr("科目：" + glcode + " " + glcnam, 48);
                    titleCur = ReportHelper.getLeftSpaceStr("币别：" + curnmc, 20);
                    bw.newLine();
                    bw.newLine();
                    bw.write(title1);
                    bw.newLine();
                    bw.write(titleOrg+titleCurDate);
                    bw.newLine();
                    bw.write(titleGlc+titleCur);
                    bw.newLine();
                    bw.write(plan);
                    bw.newLine();
                    bw.write(title2Cell1 + title2Cell2 + title2Cell3);
                    bw.newLine();
                    bw.write(title2Cell1 + title3Cell2 + title3Cell3 + title3Cell2 + title3Cell3);
                    bw.newLine();
                    rowCnt += 8;
                    //获取数据
                    List<ActvchForGlDRpt> gldrptLst = vchmap.selectForGlDRpt(orgidt,curcde,glcode);
                    Iterator it2 = gldrptLst.iterator();
                    int totalDcunt = 0;
                    double totalDamt = 0;
                    int totalCcunt = 0;
                    double totalCamt = 0;
                    int tempi = 0;
                    while (it2.hasNext()) {
                        ActvchForGlDRpt gldrpt = (ActvchForGlDRpt)it2.next();
                        String rvslbl = gldrpt.getRvslbl();
                        int intDcun = Integer.parseInt(gldrpt.getDcunt());
                        double doubleDamt = Double.parseDouble(gldrpt.getDamt()) * -1/100;
                        int intCcunt = Integer.parseInt(gldrpt.getCcunt());
                        double doubleCamt = Double.parseDouble(gldrpt.getCamt())/100;
                        String dcunt = ReportHelper.getRightSpaceStr(df01.format(intDcun), 18);   //借方笔数
                        String damt = ReportHelper.getRightSpaceStr(df0.format(doubleDamt), 22);     //借方金额
                        String ccunt = ReportHelper.getRightSpaceStr(df01.format(intCcunt), 18);   //贷方笔数
                        String camt = ReportHelper.getRightSpaceStr(df0.format(doubleCamt), 22);     //贷方金额
                        if (tempi == 0 && rvslbl.equals("1")) {
                            bw.write(plan);
                            bw.newLine();
                            bw.write(title4 + dcunt + damt + ccunt + camt);
                        } else if (tempi == 0 && rvslbl.equals("2")) {
                            bw.write(plan);
                            bw.newLine();
                            bw.write(title4);
                            bw.newLine();
                            bw.write(plan);
                            bw.newLine();
                            bw.write(title5 + dcunt + damt + ccunt + camt);
                        } else
                            bw.write(title5 + dcunt + damt + ccunt + camt);

                        bw.newLine();
                        bw.write(plan);
                        bw.newLine();
                        totalDcunt += intDcun;
                        totalDamt += doubleDamt;
                        totalCcunt += intCcunt;
                        totalCamt += doubleCamt;
                        tempi++;
                    }
                    bw.write(title6);
                    bw.newLine();
                    bw.write(plan);
                    bw.newLine();
                    String strTotalDcunt = ReportHelper.getRightSpaceStr(String.valueOf(totalDcunt), 18);
                    String strTotalDamt = ReportHelper.getRightSpaceStr(String.valueOf(totalDamt), 22);
                    String strTotalCcunt = ReportHelper.getRightSpaceStr(String.valueOf(totalCcunt), 18);
                    String strTotalCamt = ReportHelper.getRightSpaceStr(String.valueOf(totalCamt), 22);
                    bw.write(title7 + strTotalDcunt + strTotalDamt + strTotalCcunt + strTotalCamt);
                    bw.newLine();
                    bw.write(plan);
                    bw.newLine();
                    bw.write(footer);
                    bw.newLine();
                    //换页符添加
                    glCurCnt++;
                    if (glCurCnt % 3 == 0) {
                        bw.write("\f");
                    }
                }
                bw.flush();
                bw.close();

            }
             MessageUtil.addInfo("科目日结单报表生成成功！");
        } catch (Exception ex) {
            MessageUtil.addError("科目日结单报表生成错误！");
            ex.getMessage();
            ex.printStackTrace();
            logger.error("科目日结单抽出数据错误。");
        } finally {
            session.close();
            return null;
        }


    }

    /**
     * 分户账3
     * 文本打印
     * remark:
     *   传10号    摘要10     借方发生额15   贷方发生额15       余额15     日期10
     *                                                                余额与日期中间+两个空格*/
    public String ExcelActstm3() {
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        try {
            DecimalFormat df0 = new DecimalFormat("###,##0.00");
            String strPath = PropertyManager.getProperty("REPORT_ROOTPATH") + nowDate + "\\";
            String orgidt = "";
            String orgnam = "";
            String title0 = "                                  分户账";
            String plan = "-----------------------------------------------------------------------------";
            String contTitle = "  传票号    摘要       借方发生额     贷方发生额         余额       日期     ";
            String contCQY =   "          承前页                                  ";
            //页脚
            String curPageBal = "          本页余额：";
            String curDate = "出帐日期：";
            int title0Cnt1 = 45; //表头内容第一列最大字节数
            //内容列最大宽度（字节数)
            int contCnt1 = 10;
            int contCnt2 = 10;
            int contCnt3 = 15;
            int contCnt4 = 10;
            int footerCnt1 = 15;
            int footerCnt2 = 20;

            ActorgMapper orgmap = session.getMapper(ActorgMapper.class);
            List<Actorg> orgLst = orgmap.selectForActlgc();
            Iterator it = orgLst.iterator();
            while (it.hasNext()) {
                Actorg org = (Actorg) it.next();
                orgidt = org.getOrgidt();
                orgnam = org.getOrgnam();
                strPath += orgidt + "\\";
                //创建路径
                File fileDirec = new File(strPath);
                if (!fileDirec.isDirectory() || !fileDirec.exists()) {
                    if (!fileDirec.mkdirs()) {
                        MessageUtil.addInfo("路径创建失败！");
                        logger.error("路径创建失败！");
                    }
                }
                File file = new File(strPath+"分户账.txt");
                FileWriter fw = new FileWriter(file);
                BufferedWriter bw=new BufferedWriter(fw);
                int pageCount = 0;
                int pageRowCnt = 50;   //25行数据(+分行标志行+表头+页脚 总共61行/页)
                int pageLine = 10;     //每隔10行加横线
                int seq = 1; //账户序号
                ActlgcMapper stmmap = session.getMapper(ActlgcMapper.class);      //20110324 替换为actlgc表
                List<ActstmForActnum> stmnumLst = stmmap.selectForActnum(orgidt);   //取得账户基本信息--账户别
                Iterator it1 = stmnumLst.iterator();
                while (it1.hasNext()) {
                    ActstmForActnum stmnum = (ActstmForActnum)it1.next();
                    String curnmc = stmnum.getCurnmc();
                    String actnam = stmnum.getActnam();
                    String glcnam = stmnum.getGlcnam();
                    String depnam = stmnum.getDepnam();
                    String actacn = stmnum.getOldacn();
                    String sysidt = stmnum.getSysidt();
                    String cusidt = stmnum.getCusidt();
                    String apcode = stmnum.getApcode();
                    String curcde = stmnum.getCurcde();
                    double legbal = (double)stmnum.getLegbal()/100;
                    bw.write(title0);
                    bw.newLine();
                    bw.write("序号：" + seq);
                    bw.newLine();
                    bw.write(ReportHelper.getLeftSpaceStr("货币：" + curnmc, title0Cnt1)+"科目：" + glcnam);
                    bw.newLine();
                    bw.write(ReportHelper.getLeftSpaceStr("机构：" + orgnam, title0Cnt1) + "部门：" + depnam);
                    bw.newLine();
                    bw.write(ReportHelper.getLeftSpaceStr("户名：" + actnam, title0Cnt1) + "账号：" + actacn);
                    bw.newLine();
                    bw.write(plan);
                    bw.newLine();
                    bw.write(contTitle);
                    bw.newLine();
                    bw.write(plan);
                    bw.newLine();
                    bw.write(contCQY + ReportHelper.getRightSpaceStr(df0.format(legbal), contCnt3));
                    bw.newLine();
                    bw.write(plan);
                    bw.newLine();
                    //分户账内容
                    List<ActstmForStmCotent> stmcont = stmmap.selectForStmContent(sysidt,orgidt,cusidt,apcode,curcde);
                    Iterator it2 = stmcont.iterator();
                    int j = 0;       //分户账条数
                    while (it2.hasNext()) {
                        ActstmForStmCotent stmCont2 = (ActstmForStmCotent) it2.next();
                        String cpcode = ReportHelper.getLeftSpaceStr(stmCont2.getCpcode(), contCnt1);
                        String furinf = ReportHelper.getLeftSpaceStr(stmCont2.getFurinf(), contCnt2);
                        double dtxnamt = (double) (stmCont2.getDtxnamt() * -1)/100;
                        double ctxnamt = (double)stmCont2.getCtxnamt()/100;
                        double actbal = (double)stmCont2.getActbal()/100;
                        String spaDtxnamt = ReportHelper.getRightSpaceStr(df0.format(dtxnamt), contCnt3);
                        String spaCtxnamt = ReportHelper.getRightSpaceStr(df0.format(ctxnamt), contCnt3);
                        String spaActbal = ReportHelper.getRightSpaceStr(df0.format(actbal), contCnt3);
                        Date datedat = stmCont2.getValdat();
                        SimpleDateFormat dateformt = new SimpleDateFormat("yyyy-MM-dd");
                        String valdat = ReportHelper.getLeftSpaceStr(dateformt.format(datedat), contCnt4);
                        bw.write(cpcode + furinf + spaDtxnamt + spaCtxnamt + spaActbal + " " + valdat);
                        bw.newLine();
                        if ((j+1) == stmcont.size()) {
                            //添加页脚
                            bw.write(plan);
                            bw.newLine();
                            bw.write(curPageBal + ReportHelper.getLeftSpaceStr(df0.format(actbal), footerCnt1) + curDate +
                                    ReportHelper.getLeftSpaceStr(reportdate, footerCnt2) + "第" + (pageCount+1) + "页");
                            bw.newLine();
                        } else if ((j!=0) && ((j+1)%pageRowCnt==0)) {
                            //添加页脚
                            bw.write(plan);
                            bw.newLine();
                            bw.write(curPageBal + ReportHelper.getLeftSpaceStr(df0.format(actbal), footerCnt1) + curDate +
                                    ReportHelper.getLeftSpaceStr(reportdate, footerCnt2) + "第" + (pageCount+1) + "页");
                            bw.newLine();
                            pageCount++; //页码
                            //换页符
                            bw.write("\f");
                            //建立表头
                            bw.write(title0);
                            bw.newLine();
                            bw.write("序号：" + seq);
                            bw.newLine();
                            bw.write(ReportHelper.getLeftSpaceStr("货币：" + curnmc, title0Cnt1)+"科目：" + glcnam);
                            bw.newLine();
                            bw.write(ReportHelper.getLeftSpaceStr("机构：" + orgnam, title0Cnt1) + "部门：" + depnam);
                            bw.newLine();
                            bw.write(ReportHelper.getLeftSpaceStr("户名：" + actnam, title0Cnt1) + "账号：" + actacn);
                            bw.newLine();
                            bw.write(plan);
                            bw.newLine();
                            bw.write(contTitle);
                            bw.newLine();
                            bw.write(plan);
                            bw.newLine();
                            bw.write(contCQY + ReportHelper.getRightSpaceStr(df0.format(actbal), contCnt3));
                            bw.newLine();
                            bw.write(plan);
                            bw.newLine();
                        } else if (j !=0 && ((j+1) % pageLine == 0)) {
                            bw.write(plan);
                            bw.newLine();
                        }
                        j++;
                    }

                    //序号+1
                    seq++;
                    pageCount++; //页码
                    //换页符
                    bw.write("\f");
                }
                bw.flush();
                bw.close();
            }
            MessageUtil.addInfo("分户账报表生成成功！");
        } catch (Exception ex) {
            MessageUtil.addError("分户账报表生成错误！");
            ex.getMessage();
            ex.printStackTrace();
            logger.error("分户账抽出数据错误。");
        } finally {
            session.close();
        }
        return null;
    }

    /**
     * 对账单*/
    public void ExcelActnsm() {
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        try {
            DecimalFormat df0 = new DecimalFormat("###,##0.00");
            String strPath = PropertyManager.getProperty("REPORT_ROOTPATH") + nowDate + "\\";
            String orgidt = "";
            String orgnam = "";
            String title0 = "                                  对账单";
            String plan = "-----------------------------------------------------------------------------";
            String contTitle = "  传票号    摘要       借方发生额     贷方发生额         余额       日期     ";
            String contCQY = "          承前页                                  ";
            //页脚
            String curPageBal = "          本页余额：";
            String curDate = "出帐日期：";
            int title0Cnt1 = 45; //表头内容第一列最大字节数
            //内容列最大宽度（字节数)
            int contCnt1 = 10;
            int contCnt2 = 10;
            int contCnt3 = 15;
            int contCnt4 = 10;
            int footerCnt1 = 15;
            int footerCnt2 = 20;
            ActorgMapper orgmap = session.getMapper(ActorgMapper.class);
            List<Actorg> orgLst = orgmap.selectForActlgc();
            Iterator it = orgLst.iterator();
            while (it.hasNext()) {
                Actorg org = (Actorg) it.next();
                orgidt = org.getOrgidt();
                orgnam = org.getOrgnam();
                strPath += orgidt + "\\";
                //创建路径
                File fileDirec = new File(strPath);
                if (!fileDirec.isDirectory() || !fileDirec.exists()) {
                    if (!fileDirec.mkdirs()) {
                        MessageUtil.addError("路径创建失败！");
                        logger.error("路径创建失败！");
                    }
                }
                File file = new File(strPath + "对账单.txt");
                FileWriter fw = new FileWriter(file);
                BufferedWriter bw = new BufferedWriter(fw);
                int pageCount = 0;
                int pageRowCnt = 50;   //25行数据(+分行标志行+表头+页脚 总共61行/页)
                int pageLine = 10;     //每隔10行加横线
                int seq = 1; //账户序号
                ActnsmMapper nsmmap = session.getMapper(ActnsmMapper.class);
                List<ActstmForActnum> stmnumLst = nsmmap.selectForActnum(orgidt);   //取得账户基本信息--账户别
                Iterator it1 = stmnumLst.iterator();
                while (it1.hasNext()) {
                    ActstmForActnum stmnum = (ActstmForActnum) it1.next();
                    String curnmc = stmnum.getCurnmc();
                    String actnam = stmnum.getActnam();
                    String glcnam = stmnum.getGlcnam();
                    String depnam = stmnum.getDepnam();
                    String actacn = stmnum.getOldacn();
                    String sysidt = stmnum.getSysidt();
                    String cusidt = stmnum.getCusidt();
                    String apcode = stmnum.getApcode();
                    String curcde = stmnum.getCurcde();
                    double legbal = (double) stmnum.getLegbal() / 100;
                    bw.write(title0);
                    bw.newLine();
                    bw.write("序号：" + seq);
                    bw.newLine();
                    bw.write(ReportHelper.getLeftSpaceStr("货币：" + curnmc, title0Cnt1) + "科目：" + glcnam);
                    bw.newLine();
                    bw.write(ReportHelper.getLeftSpaceStr("机构：" + orgnam, title0Cnt1) + "部门：" + depnam);
                    bw.newLine();
                    bw.write(ReportHelper.getLeftSpaceStr("户名：" + actnam, title0Cnt1) + "账号：" + actacn);
                    bw.newLine();
                    bw.write(plan);
                    bw.newLine();
                    bw.write(contTitle);
                    bw.newLine();
                    bw.write(plan);
                    bw.newLine();
                    bw.write(contCQY + ReportHelper.getRightSpaceStr(df0.format(legbal), contCnt3));
                    bw.newLine();
                    bw.write(plan);
                    bw.newLine();
                    //分户账内容
                    List<ActstmForStmCotent> stmcont = nsmmap.selectForStmContent(sysidt, orgidt, cusidt, apcode, curcde);
                    Iterator it2 = stmcont.iterator();
                    int j = 0;       //分户账条数
                    while (it2.hasNext()) {
                        ActstmForStmCotent stmCont2 = (ActstmForStmCotent) it2.next();
                        String cpcode = ReportHelper.getLeftSpaceStr(stmCont2.getCpcode(), contCnt1);
                        String furinf = ReportHelper.getLeftSpaceStr(stmCont2.getFurinf(), contCnt2);
                        double dtxnamt = (double) (stmCont2.getDtxnamt() * -1) / 100;
                        double ctxnamt = (double) stmCont2.getCtxnamt() / 100;
                        double actbal = (double) stmCont2.getActbal() / 100;
                        String spaDtxnamt = ReportHelper.getRightSpaceStr(df0.format(dtxnamt), contCnt3);
                        String spaCtxnamt = ReportHelper.getRightSpaceStr(df0.format(ctxnamt), contCnt3);
                        String spaActbal = ReportHelper.getRightSpaceStr(df0.format(actbal), contCnt3);
                        Date datedat = stmCont2.getValdat();
                        SimpleDateFormat dateformt = new SimpleDateFormat("yyyy-MM-dd");
                        String valdat = ReportHelper.getLeftSpaceStr(dateformt.format(datedat), contCnt4);
                        bw.write(cpcode + furinf + spaDtxnamt + spaCtxnamt + spaActbal + " " + valdat);
                        bw.newLine();

                        if ((j + 1) == stmcont.size()) {
                            //添加页脚
                            bw.write(plan);
                            bw.newLine();
                            bw.write(curPageBal + ReportHelper.getLeftSpaceStr(df0.format(actbal), footerCnt1) + curDate +
                                    ReportHelper.getLeftSpaceStr(reportdate, footerCnt2) + "第" + (pageCount + 1) + "页");
                            bw.newLine();
                        } else if ((j != 0) && ((j + 1) % pageRowCnt == 0)) {
                            //添加页脚
                            bw.write(plan);
                            bw.newLine();
                            bw.write(curPageBal + ReportHelper.getLeftSpaceStr(df0.format(actbal), footerCnt1) + curDate +
                                    ReportHelper.getLeftSpaceStr(reportdate, footerCnt2) + "第" + (pageCount + 1) + "页");
                            bw.newLine();
                            pageCount++; //页码
                            //换页符
                            bw.write("\f");
                            //建立表头
                            bw.write(title0);
                            bw.newLine();
                            bw.write("序号：" + seq);
                            bw.newLine();
                            bw.write(ReportHelper.getLeftSpaceStr("货币：" + curnmc, title0Cnt1) + "科目：" + glcnam);
                            bw.newLine();
                            bw.write(ReportHelper.getLeftSpaceStr("机构：" + orgnam, title0Cnt1) + "部门：" + depnam);
                            bw.newLine();
                            bw.write(ReportHelper.getLeftSpaceStr("户名：" + actnam, title0Cnt1) + "账号：" + actacn);
                            bw.newLine();
                            bw.write(plan);
                            bw.newLine();
                            bw.write(contTitle);
                            bw.newLine();
                            bw.write(plan);
                            bw.newLine();
                            bw.write(contCQY + ReportHelper.getRightSpaceStr(df0.format(actbal), contCnt3));
                            bw.newLine();
                            bw.write(plan);
                            bw.newLine();
                        } else if (j !=0 && ((j+1) % pageLine == 0)) {
                            bw.write(plan);
                            bw.newLine();
                        }
                        j++;
                    }

                    //序号+1
                    seq++;
                    pageCount++; //页码
                    //换页符
                    bw.write("\f");
                }
                bw.flush();
                bw.close();
            }
            MessageUtil.addError("对账单报表生成成功！");
        } catch (Exception ex) {
            MessageUtil.addError("对账单报表生成错误！");
            ex.getMessage();
            ex.printStackTrace();
            logger.error("对账单抽出数据错误。");
        } finally {
            session.close();
        }
    }

    /**
     * 总账2*/
    public String ExcelActglf2() {
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        try {
            DecimalFormat df0 = new DecimalFormat("###,##0.00");
            SimpleDateFormat dfgldate = new SimpleDateFormat("d");  //取得日期day
            String yearmonth = SystemService.getBizDateYM();
            String strPath = PropertyManager.getProperty("REPORT_ROOTPATH") + nowDate + "\\";
            String orgidt = "";
            String orgnam = "";
            String title0 = "                                 总账";
            String plan = "--------------------------------------------------------------------------------";
            int pageLine = 10;   //每隔10行+横线
            int title01Len = 25;
            int title02Len = 20;
            int titleCont2Len = 15;
            String titleCont0 = "  日期    "; //10
            String titleCont1 = "                      发生额            ";
            String titleCont2 = "             余额             ";
            String titleCont11 = "      借方     ";
            String titleCont12 = "      贷方     ";
            String ContSpace = "                              ";
            String footerCont = "          会计：         复合：         制表：         打印日期："+reportdate;
            ActorgMapper orgmap = session.getMapper(ActorgMapper.class);
            List<Actorg> orgLst = orgmap.selectForActglf();
            Iterator it = orgLst.iterator();
            while (it.hasNext()) {
                Actorg org = (Actorg) it.next();
                orgidt = org.getOrgidt();
                orgnam = org.getOrgnam();
                strPath += orgidt + "\\";
                //创建路径
                File fileDirec = new File(strPath);
                if (!fileDirec.isDirectory() || !fileDirec.exists()) {
                    if (!fileDirec.mkdirs()) {
                        MessageUtil.addInfo("路径创建失败！");
                        logger.error("路径创建失败！");
                    }
                }
                File file = new File(strPath+"总账.txt");
                FileWriter fw = new FileWriter(file);
                BufferedWriter bw=new BufferedWriter(fw);
                int pageCount = 1;
               ActglfMapper glfmap = session.getMapper(ActglfMapper.class);
                List<ActglfForGlcnum> glcnumLst = glfmap.selectForGlcnum(orgidt);
                Iterator it1 =glcnumLst.iterator();
                while (it1.hasNext()) {
                    ActglfForGlcnum glcnum = (ActglfForGlcnum)it1.next();
                    String curcde = glcnum.getCurcde();
                    String curnmc = glcnum.getCurnmc();
                    String glcode = glcnum.getGlcode();
                    String glcnam = glcnum.getGlcnam();
                    //创建表头
                    bw.write(title0);
                    bw.newLine();
                    bw.write(ReportHelper.getLeftSpaceStr("机构：" + orgnam, title01Len) + ReportHelper.getLeftSpaceStr("日期：" + yearmonth, title02Len) + "页数："+pageCount);
                    bw.newLine();
                    bw.write(ReportHelper.getLeftSpaceStr("币别：" + curnmc, title01Len) + ReportHelper.getLeftSpaceStr("科目号：" + glcode, title02Len) + "科目名称："+glcnam);
                    bw.newLine();
                    bw.write(plan);
                    bw.newLine();
                    bw.write(titleCont1 + titleCont2);
                    bw.newLine();
                    bw.write(plan);
                    bw.newLine();
                    bw.write(titleCont0 + titleCont11 + titleCont12 + titleCont11 + titleCont12);
                    bw.newLine();
                    bw.write(plan);
                    bw.newLine();
                    List<Actglf> glfLst = glfmap.selectForGlcCont(orgidt,glcode,curcde);
                    //月累计
                    double dramntTotal = 0;
                    double cramntTotal = 0;
                    double drbalaTotal = 0;
                    double crbalaTotal = 0;
                    int glfLstsize = 0;
                    double drbala = 0;
                    double crbala = 0;
                    int j = 0;
                    Iterator itGlf = glfLst.iterator();
                    while(itGlf.hasNext()) {
                        Actglf glf = (Actglf) itGlf.next();
                        String glDay = dfgldate.format(glf.getGldate());
                        double dramnt = (double) (glf.getDramnt() * -1)/100;
                        double cramnt = (double)glf.getCramnt()/100;
                        drbala = (double) (glf.getDrbala() * -1)/100;
                        crbala = (double)glf.getCrbala()/100;
                        String dramntStr = ReportHelper.getRightSpaceStr(df0.format(dramnt), titleCont2Len);
                        String cramntStr = ReportHelper.getRightSpaceStr(df0.format(cramnt), titleCont2Len);
                        String drbalaStr = ReportHelper.getRightSpaceStr(df0.format(drbala), titleCont2Len);
                        String crbalaStr = ReportHelper.getRightSpaceStr(df0.format(crbala), titleCont2Len);
                        bw.write("    "+ ReportHelper.getLeftSpaceStr(glDay, 6) + dramntStr + cramntStr + drbalaStr + crbalaStr);
                        bw.newLine();
                        j++;
                        //加横线
                        if (j !=0 && (j % pageLine == 0)) {
                            bw.write(plan);
                            bw.newLine();
                        }
                        dramntTotal += dramnt;
                        cramntTotal += cramnt;
                    }

                    //月累计
                    String dramntTotalStr = ReportHelper.getRightSpaceStr(df0.format(dramntTotal), titleCont2Len);
                    String cramntTotalStr = ReportHelper.getRightSpaceStr(df0.format(cramntTotal), titleCont2Len);
                    String drbalaStr = ReportHelper.getRightSpaceStr(df0.format(drbala), titleCont2Len);
                    String crbalaStr = ReportHelper.getRightSpaceStr(df0.format(crbala), titleCont2Len);
                    bw.write(plan);
                    bw.newLine();
                    bw.write("  月累计  " + dramntTotalStr + cramntTotalStr + drbalaStr + crbalaStr);
                    bw.newLine();
                    bw.write(plan);
                    bw.newLine();
                    bw.write(footerCont);
                    //换页
                    pageCount++;
                    bw.write("\f");
                }
                bw.flush();
                bw.close();
            }
            MessageUtil.addInfo("总账报表生成成功！");
        } catch (Exception ex) {
            MessageUtil.addError("总账报表生成错误！");
            ex.getMessage();
            ex.printStackTrace();
            logger.error("总账抽出数据错误。");
        } finally {
            session.close();
        }
        return null;
    }

    /**
     * 日计表2*/
    public String ExcelActcgl2() {
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        try {
            DecimalFormat df0 = new DecimalFormat("###,##0.00");
            String strPath = PropertyManager.getProperty("REPORT_ROOTPATH") + nowDate + "\\";
            String orgidt = "";
            String orgnam = "";
            String title0 = "                                                                             " +
                    "           日计表";
            String plan = "-------------------------------------------------------------------------------" +
                    "------------------------------------------------------------------------------------" +
                    "-------------";
            int title0Len = 40;
            int title1Len = 68;
            int cont0Len = 6;
            int cont1Len = 34;
            int cont2Len = 20;
            int cont3Len = 8;
            String titleCont0 = " 科目 ";
            String titleCont1 = "        科目名称                  ";
            String titleCont2 = "    上日借方余额    ";
            String titleCont3 = "      上日贷方余额  ";
            String titleCont4 = "借方笔数";
            String titleCont5 = "         借方发生额 ";
            String titleCont6 = "贷方笔数";
            String titleCont7 = "         贷方发生额 ";
            String titleCont8 = "     本日借方余额   ";
            String titleCont9 = "    本日贷方余额    ";
            ActorgMapper orgmap = session.getMapper(ActorgMapper.class);
            List<Actorg> orgLst = orgmap.selectForActcgl();
            Iterator it = orgLst.iterator();
            while (it.hasNext()) {
                Actorg org = (Actorg) it.next();
                orgidt = org.getOrgidt();
                orgnam = org.getOrgnam();
                strPath += orgidt + "\\";
                //创建路径
                File fileDirec = new File(strPath);
                if (!fileDirec.isDirectory() || !fileDirec.exists()) {
                    if (!fileDirec.mkdirs()) {
                        MessageUtil.addInfo("路径创建失败！");
                        logger.error("路径创建失败！");
                    }
                }
                File file = new File(strPath+"日计表.txt");
                FileWriter fw = new FileWriter(file);
                BufferedWriter bw=new BufferedWriter(fw);
                //获得币别种类
                ActccyMapper ccymap = session.getMapper(ActccyMapper.class);
                List<Actccy> ccyLst = ccymap.selectForActcgl(orgidt,ACEnum.RECTYP_D.getStatus());
                Iterator itCcy = ccyLst.iterator();
                int ccyCnt = 0;
                while (itCcy.hasNext()) {
                    Actccy ccy = (Actccy)itCcy.next();
                    String curcde = ccy.getCurcde();
                    String curnmc = ccy.getCurnmc();
                    //换页
                    if (ccyCnt !=0 && (ccyCnt%2==0)) {
                        bw.write("\f");
                    } else if (ccyCnt !=0) {
                        bw.newLine();
                        bw.newLine();
                    }
                    bw.write(title0);
                    bw.newLine();
                    bw.write(ReportHelper.getLeftSpaceStr("币别：" + curnmc, title0Len) + ReportHelper.getLeftSpaceStr("日期：" + reportdate, title1Len) +
                    "单位：元");
                    bw.newLine();
                    bw.write(plan);
                    bw.newLine();
                    //表头
                    bw.write(titleCont0 + titleCont1 + titleCont2 + titleCont3 + titleCont4 + titleCont5 + titleCont6 +
                    titleCont7 + titleCont8 + titleCont9);
                    bw.newLine();
                    bw.write(plan);
                    bw.newLine();
                    //获得数据
                    ActcglMapper cglmap = session.getMapper(ActcglMapper.class);
                    List<ActcglForglc> cgLst = cglmap.selectForglcnam(ACEnum.RECTYP_D.getStatus(),orgidt,curcde);
                    Iterator itCgl = cgLst.iterator();
                    double dlsbalTotal = 0;
                    double clsbalTotal = 0;
                    int drcuntTotal = 0;
                    double dramntTotal = 0;
                    int crcuntTotal = 0;
                    double cramntTotal = 0;
                    double drbalaTotal = 0;
                    double crbalaTotal = 0;
                    while (itCgl.hasNext()) {
                        ActcglForglc cfg = (ActcglForglc)itCgl.next();
                        String glcode = ReportHelper.getLeftSpaceStr(cfg.getGlcode(), cont0Len);
                        String glcnam = ReportHelper.getLeftSpaceStr(cfg.getGlcnam(), cont1Len);
                        double dlsbal = (double) (cfg.getDlsbal() * -1)/100;
                        double clsbal = (double) cfg.getClsbal()/100;
                        int drcunt = cfg.getDrcunt();
                        double dramnt = (double) (cfg.getDramnt() * -1)/100;
                        int crcunt = cfg.getCrcunt();
                        double cramnt = (double) cfg.getCramnt()/100;
                        double drbala = (double) (cfg.getDrbala() * -1)/100;
                        double crbala = (double) cfg.getCrbala()/100;
                        String dlsbalStr = ReportHelper.getRightSpaceStr(df0.format(dlsbal), cont2Len);
                        String clsbalStr = ReportHelper.getRightSpaceStr(df0.format(clsbal), cont2Len);
                        String drcuntStr = ReportHelper.getRightSpaceStr(String.valueOf(drcunt), cont3Len);
                        String dramntStr = ReportHelper.getRightSpaceStr(df0.format(dramnt), cont2Len);
                        String crcuntStr = ReportHelper.getRightSpaceStr(String.valueOf(crcunt), cont3Len);
                        String cramntStr = ReportHelper.getRightSpaceStr(df0.format(cramnt), cont2Len);
                        String drbalaStr = ReportHelper.getRightSpaceStr(df0.format(drbala), cont2Len);
                        String crbalaStr = ReportHelper.getRightSpaceStr(df0.format(crbala), cont2Len);
                        //插入内容
                        bw.write(glcode + glcnam + dlsbalStr + clsbalStr + drcuntStr + dramntStr + crcuntStr + cramntStr +
                                drbalaStr + crbalaStr);
                        bw.newLine();
                        //合计
                        dlsbalTotal += dlsbal;
                        clsbalTotal += clsbal;
                        drcuntTotal += drcunt;
                        dramntTotal += dramnt;
                        crcuntTotal += crcunt;
                        cramntTotal += cramnt;
                        drbalaTotal += drbala;
                        crbalaTotal += crbala;
                    }
                    //合计
                    String totalStr = "      " + ReportHelper.getLeftSpaceStr("合计", cont1Len);
                    String dlsbalTotalStr = ReportHelper.getRightSpaceStr(df0.format(dlsbalTotal), cont2Len);
                    String clsbalTotalStr = ReportHelper.getRightSpaceStr(df0.format(clsbalTotal), cont2Len);
                    String drcuntTotalStr = ReportHelper.getRightSpaceStr(String.valueOf(drcuntTotal), cont3Len);
                    String dramntTotalStr = ReportHelper.getRightSpaceStr(df0.format(dramntTotal), cont2Len);
                    String crcuntTotalStr = ReportHelper.getRightSpaceStr(String.valueOf(crcuntTotal), cont3Len);
                    String cramntTotalStr = ReportHelper.getRightSpaceStr(df0.format(cramntTotal), cont2Len);
                    String drbalaTotalStr = ReportHelper.getRightSpaceStr(df0.format(drbalaTotal), cont2Len);
                    String crbalaTotalStr = ReportHelper.getRightSpaceStr(df0.format(crbalaTotal), cont2Len);
                    bw.write(plan);
                    bw.newLine();
                    bw.write(totalStr + dlsbalTotalStr + clsbalTotalStr + drcuntTotalStr + dramntTotalStr + crcuntTotalStr
                            + cramntTotalStr + drbalaTotalStr + crbalaTotalStr);
                    bw.newLine();
                    //币别计数
                    ccyCnt++;

                }
                bw.flush();
                bw.close();
            }
            MessageUtil.addInfo("日计表报表生成成功！");
        } catch (Exception ex) {
            ex.getMessage();
            ex.printStackTrace();
            logger.error("日计表抽出数据错误。");
        } finally {
            session.close();
        }
        return null;
    }

    /**
     * 月计表2*/
    public String ExcelMonth2() {
        SqlSession session = ibatisManager.getSessionFactory().openSession();
        try {
            DecimalFormat df0 = new DecimalFormat("###,##0.00");
            String strPath = PropertyManager.getProperty("REPORT_ROOTPATH") + nowDate + "\\";
            String orgidt = "";
            String orgnam = "";
            String title0 = "                                                                             " +
                    "           月计表";
            String plan = "-------------------------------------------------------------------------------" +
                    "------------------------------------------------------------------------------------" +
                    "-------------";
            int title0Len = 40;
            int title1Len = 68;
            int cont0Len = 6;
            int cont1Len = 34;
            int cont2Len = 20;
            int cont3Len = 8;
            String titleCont0 = " 科目 ";
            String titleCont1 = "        科目名称                  ";
            String titleCont2 = "    上月借方余额    ";
            String titleCont3 = "      上月贷方余额  ";
            String titleCont4 = "借方笔数";
            String titleCont5 = "         借方发生额 ";
            String titleCont6 = "贷方笔数";
            String titleCont7 = "         贷方发生额 ";
            String titleCont8 = "     本月借方余额   ";
            String titleCont9 = "    本月贷方余额    ";
            ActorgMapper orgmap = session.getMapper(ActorgMapper.class);
            List<Actorg> orgLst = orgmap.selectForActcgl();
            Iterator it = orgLst.iterator();
            while (it.hasNext()) {
                Actorg org = (Actorg) it.next();
                orgidt = org.getOrgidt();
                orgnam = org.getOrgnam();
                strPath += orgidt + "\\";
                //创建路径
                File fileDirec = new File(strPath);
                if (!fileDirec.isDirectory() || !fileDirec.exists()) {
                    if (!fileDirec.mkdirs()) {
                        MessageUtil.addInfo("路径创建失败！");
                        logger.error("路径创建失败！");
                    }
                }
                File file = new File(strPath+"月计表.txt");
                FileWriter fw = new FileWriter(file);
                BufferedWriter bw=new BufferedWriter(fw);
                //获得币别种类
                ActccyMapper ccymap = session.getMapper(ActccyMapper.class);
                List<Actccy> ccyLst = ccymap.selectForActcgl(orgidt,ACEnum.RECTYP_M.getStatus());
                Iterator itCcy = ccyLst.iterator();
                int ccyCnt = 0;
                while (itCcy.hasNext()) {
                    Actccy ccy = (Actccy)itCcy.next();
                    String curcde = ccy.getCurcde();
                    String curnmc = ccy.getCurnmc();
                    //换页
                    if (ccyCnt !=0 && (ccyCnt%2==0)) {
                        bw.write("\f");
                    } else if (ccyCnt !=0) {
                        bw.newLine();
                        bw.newLine();
                    }
                    bw.write(title0);
                    bw.newLine();
                    bw.write(ReportHelper.getLeftSpaceStr("币别：" + curnmc, title0Len) + ReportHelper.getLeftSpaceStr("日期：" + reportdate, title1Len) +
                    "单位：元");
                    bw.newLine();
                    bw.write(plan);
                    bw.newLine();
                    //表头
                    bw.write(titleCont0 + titleCont1 + titleCont2 + titleCont3 + titleCont4 + titleCont5 + titleCont6 +
                    titleCont7 + titleCont8 + titleCont9);
                    bw.newLine();
                    bw.write(plan);
                    bw.newLine();
                    //获得数据
                    ActcglMapper cglmap = session.getMapper(ActcglMapper.class);
                    List<ActcglForglc> cgLst = cglmap.selectForglcnam(ACEnum.RECTYP_M.getStatus(),orgidt,curcde);
                    Iterator itCgl = cgLst.iterator();
                    double dlsbalTotal = 0;
                    double clsbalTotal = 0;
                    int drcuntTotal = 0;
                    double dramntTotal = 0;
                    int crcuntTotal = 0;
                    double cramntTotal = 0;
                    double drbalaTotal = 0;
                    double crbalaTotal = 0;
                    while (itCgl.hasNext()) {
                        ActcglForglc cfg = (ActcglForglc)itCgl.next();
                        String glcode = ReportHelper.getLeftSpaceStr(cfg.getGlcode(), cont0Len);
                        String glcnam = ReportHelper.getLeftSpaceStr(cfg.getGlcnam(), cont1Len);
                        double dlsbal = (double) (cfg.getDlsbal() * -1)/100;
                        double clsbal = (double)cfg.getClsbal()/100;
                        int drcunt = cfg.getDrcunt();
                        double dramnt = (double) (cfg.getDramnt() * -1)/100;
                        int crcunt = cfg.getCrcunt();
                        double cramnt = (double)cfg.getCramnt()/100;
                        double drbala = (double) (cfg.getDrbala() * -1)/100;
                        double crbala = (double)cfg.getCrbala()/100;
                        String dlsbalStr = ReportHelper.getRightSpaceStr(df0.format(dlsbal), cont2Len);
                        String clsbalStr = ReportHelper.getRightSpaceStr(df0.format(clsbal), cont2Len);
                        String drcuntStr = ReportHelper.getRightSpaceStr(String.valueOf(drcunt), cont3Len);
                        String dramntStr = ReportHelper.getRightSpaceStr(df0.format(dramnt), cont2Len);
                        String crcuntStr = ReportHelper.getRightSpaceStr(String.valueOf(crcunt), cont3Len);
                        String cramntStr = ReportHelper.getRightSpaceStr(df0.format(cramnt), cont2Len);
                        String drbalaStr = ReportHelper.getRightSpaceStr(df0.format(drbala), cont2Len);
                        String crbalaStr = ReportHelper.getRightSpaceStr(df0.format(crbala), cont2Len);
                        //插入内容
                        bw.write(glcode + glcnam + dlsbalStr + clsbalStr + drcuntStr + dramntStr + crcuntStr + cramntStr +
                                drbalaStr + crbalaStr);
                        bw.newLine();
                        //合计
                        dlsbalTotal += dlsbal;
                        clsbalTotal += clsbal;
                        drcuntTotal += drcunt;
                        dramntTotal += dramnt;
                        crcuntTotal += crcunt;
                        cramntTotal += cramnt;
                        drbalaTotal += drbala;
                        crbalaTotal += crbala;
                    }
                    //合计
                    String totalStr = "      " + ReportHelper.getLeftSpaceStr("合计", cont1Len);
                    String dlsbalTotalStr = ReportHelper.getRightSpaceStr(df0.format(dlsbalTotal), cont2Len);
                    String clsbalTotalStr = ReportHelper.getRightSpaceStr(df0.format(clsbalTotal), cont2Len);
                    String drcuntTotalStr = ReportHelper.getRightSpaceStr(String.valueOf(drcuntTotal), cont2Len);
                    String dramntTotalStr = ReportHelper.getRightSpaceStr(df0.format(dramntTotal), cont2Len);
                    String crcuntTotalStr = ReportHelper.getRightSpaceStr(String.valueOf(crcuntTotal), cont2Len);
                    String cramntTotalStr = ReportHelper.getRightSpaceStr(df0.format(cramntTotal), cont2Len);
                    String drbalaTotalStr = ReportHelper.getRightSpaceStr(df0.format(drbalaTotal), cont2Len);
                    String crbalaTotalStr = ReportHelper.getRightSpaceStr(df0.format(crbalaTotal), cont2Len);
                    bw.write(plan);
                    bw.newLine();
                    bw.write(totalStr + dlsbalTotalStr + clsbalTotalStr + drcuntTotalStr + dramntTotalStr + crcuntTotalStr
                            + cramntTotalStr + drbalaTotalStr + crbalaTotalStr);
                    bw.newLine();
                    //币别计数
                    ccyCnt++;

                }
                bw.flush();
                bw.close();
            }
            MessageUtil.addInfo("月计表报表生成成功！");
        } catch (Exception ex) {
            ex.getMessage();
            ex.printStackTrace();
            logger.error("月计表抽出数据错误。");
        } finally {
            session.close();
        }
        return null;
    }

    public IbatisManager getIbatisManager() {
        return ibatisManager;
    }

    public void setIbatisManager(IbatisManager ibatisManager) {
        this.ibatisManager = ibatisManager;
    }
}