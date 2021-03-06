package cbs.batch.report.rpactvch;

import cbs.batch.common.AbstractACBatchJobLogic;
import cbs.batch.common.BatchParameterData;
import cbs.batch.common.service.BatchSystemService;
import cbs.common.utils.PropertyManager;
import cbs.common.utils.ReportHelper;
import cbs.repository.account.maininfo.dao.ActvthMapper;
import cbs.repository.account.maininfo.model.ActvchForGlDRpt;
import cbs.repository.code.dao.ActorgMapper;
import cbs.repository.code.model.Actorg;
import cbs.repository.platform.dao.PtenudetailMapper;
import cbs.repository.platform.model.Ptenudetail;
import cbs.repository.platform.model.PtenudetailExample;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.*;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;

/**
 * 科目日结单.
 * User: Administrator （hanghaiyu）
 * Date: 2011-3-17
 * Time: 15:02:33
 * 版本修改：2012-11-09   zhanrui  增加输出格式参数配置
 */
@Service
public class RPACTVCHHandler extends AbstractACBatchJobLogic {
    private static final Log logger = LogFactory.getLog(RPACTVCHHandler.class);
    private String nowDate = "";
    private String reportdate = "";


    //每页所含表格数
    private static int TABLE_NUM_PER_PAGE = 3;
    //每个表格顶端边距行数
    private static int TABLE_MARGIN_TOP = 2;
    //每个表格底部边距行数
    private static int TABLE_MARGIN_BOTTOM = 0;

    @Inject
    private BatchSystemService systemService;

    @Inject
    private ActorgMapper orgmap;

    @Inject
    private ActvthMapper vchmap;

    @Inject
    private PtenudetailMapper enudetail;

    @Override
    protected void processBusiness(BatchParameterData parameterData) {

        try {
            nowDate = systemService.getBizDate();
            reportdate = systemService.getBizDate10();

            //zhanrui  根据枚举配置打印参数，无枚举配置时使用默认值
            try {
                TABLE_NUM_PER_PAGE = getEnu_RPTFMTPARAM("KMRJD_TABLE_NUM");
                TABLE_MARGIN_TOP = getEnu_RPTFMTPARAM("KMRJD_MARGIN_TOP");
                TABLE_MARGIN_BOTTOM = getEnu_RPTFMTPARAM("KMRJD_MARGIN_BOTTOM");
            } catch (Exception e) {
                logger.error("枚举表中报表打印参数定义错误, 使用默认设置", e);
            }

            ExcelActvch1();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void initBatch(final BatchParameterData batchParam) {

    }

    public int getEnu_RPTFMTPARAM(String enumitem){
        PtenudetailExample example = new PtenudetailExample();
        example.createCriteria().andEnutypeEqualTo("RPTFMTPARAM").andEnuitemvalueEqualTo(enumitem);
        List<Ptenudetail>  enudetails = enudetail.selectByExample(example);
        if (enudetails.size() == 1) {
            return Integer.parseInt(enudetails.get(0).getEnuitemexpand());
        }else{
            throw new RuntimeException("参数定义错误");
        }
    }

    public String ExcelActvch1() throws IOException {
        DecimalFormat df0 = new DecimalFormat("###,##0.00");
        DecimalFormat df01 = new DecimalFormat("###,###");
        String strPath = PropertyManager.getProperty("REPORT_ROOTPATH") + nowDate + "\\";
        String orgidt = "";
        String orgnam = "";
        String title1 = "                                 \u001BW1科目日结单\u001BW0";
        String titleOrg = "";
        String titleCurDate = "";
        String titleGlc = "";
        String titleCur = "";
        String title2Cell1 = "          ";
        String title3 = "┃   项目   ┃ 借方笔数 ┃     借方金额         ┃ 贷方笔数 ┃     贷方金额         ┃件";
        String title4 = "┃现金      ┃";
        String title5 = "┃转账      ┃";
        String title6 = "┃其他      ┃";
        String title7 = "┃合计      ┃";
        String nodtTitle = "         0┃                  0.00┃         0┃                  0.00┃";
        String footer = title2Cell1 + "复核：              记账：                    制表：                         ";
        String topplan = "┏━━━━━┳━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━┓附";
        String middleplan = "┃━━━━━╋━━━━━╋━━━━━━━━━━━╋━━━━━╋━━━━━━━━━━━┃";
        String bottomplan = "┗━━━━━┻━━━━━┻━━━━━━━━━━━┻━━━━━┻━━━━━━━━━━━┛张";

        List<Actorg> orgLst = orgmap.selectForActvch();
        if (orgLst.size() == 0) {
            System.out.print("科目日结单报表无数据生成。");
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
                    System.out.print("路径创建失败！");
                    logger.error("路径创建失败！");
                }
            }
            File file = new File(strPath + "日结单.txt");
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(fw);
            //获取数据
            int rowCnt = 0;
            List<ActvchForGlDRpt> glcurLst = vchmap.selectForGlCur(orgidt);
            int glCurCnt = 0;
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


                for (int i = 0; i < TABLE_MARGIN_TOP; i++) {
                    bw.newLine();
                }

                bw.write(title1);
                bw.newLine();
                bw.write(titleOrg + titleCurDate);
                bw.newLine();
                bw.write(titleGlc + titleCur);
                bw.newLine();
                bw.write(topplan);
                bw.newLine();
                bw.write(title3);
                bw.newLine();
                rowCnt += 8;
                //获取数据
                List<ActvchForGlDRpt> gldrptLst = vchmap.selectForGlDRpt(orgidt, curcde, glcode);
                short totalvchatt = 0;
                for (ActvchForGlDRpt gld : gldrptLst) {
                    totalvchatt += gld.getVchatt();  //附件数
                }
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

                    if (tempi == 0 && rvslbl.equals("1")) {
                        bw.write(middleplan + totalvchatt);
                        bw.newLine();
                        bw.write(title4 + dcunt + damt + ccunt + camt + "张");
                    } else if (tempi == 0 && rvslbl.equals("2")) {
                        bw.write(middleplan + totalvchatt);
                        bw.newLine();
                        bw.write(title4 + nodtTitle + "张");
                        bw.newLine();
                        bw.write(middleplan);
                        bw.newLine();
                        bw.write(title5 + dcunt + damt + ccunt + camt + "印");
                    } else
                        bw.write(title5 + dcunt + damt + ccunt + camt + "印");
                    bw.newLine();
                    bw.write(middleplan);
                    bw.newLine();
                    totalDcunt += intDcun;
                    totalDamt += doubleDamt;
                    totalCcunt += intCcunt;
                    totalCamt += doubleCamt;
                    tempi++;
                }
                bw.write(title6 + nodtTitle + "鉴");
                bw.newLine();
                bw.write(middleplan);
                bw.newLine();
                String strTotalDcunt = ReportHelper.getRightSpaceStr(df01.format(totalDcunt), 10) + "┃";
                String strTotalDamt = ReportHelper.getRightSpaceStr(df0.format(totalDamt), 22) + "┃";
                String strTotalCcunt = ReportHelper.getRightSpaceStr(df01.format(totalCcunt), 10) + "┃";
                String strTotalCamt = ReportHelper.getRightSpaceStr(df0.format(totalCamt), 22) + "┃";
                bw.write(title7 + strTotalDcunt + strTotalDamt + strTotalCcunt + strTotalCamt);
                bw.newLine();
                bw.write(bottomplan);
                bw.newLine();
                bw.write(footer);
                bw.newLine();

                //换页符添加
                glCurCnt++;
                if (glCurCnt % TABLE_NUM_PER_PAGE == 0) {
                    bw.write("\f");
                }else{
                    for (int i = 0; i < TABLE_MARGIN_BOTTOM; i++) {
                        bw.newLine();
                    }
                }
            }
            bw.flush();
            bw.close();

        }
        System.out.print("科目日结单报表生成成功。");
        logger.info("科目日结单报表生成成功。");
        return null;
    }

}
