package cbs.batch.ac.bcb8521;

import cbs.batch.ac.bcb8521.dao.BCB8521Mapper;
import cbs.batch.common.AbstractACBatchJobLogic;
import cbs.batch.common.BatchParameterData;
import cbs.common.enums.ACEnum;
import cbs.common.utils.DataFormater;
import cbs.common.utils.PropertyManager;
import cbs.common.utils.ReportHelper;
import cbs.repository.account.maininfo.dao.ActvchMapper;
import cbs.repository.account.maininfo.model.Actvch;
import cbs.repository.code.model.Actccy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: haiyu
 * Date: 2011-2-22
 * Time: 15:23:37
 * To change this template use File | Settings | File Templates.
 */
@Service
public class BCB8521Handler extends AbstractACBatchJobLogic {
    private static final Logger logger = LoggerFactory.getLogger(BCB8521Handler.class);
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    @Inject
    private ActvchMapper vchMapper;
    @Inject
    private BCB8521Mapper bcb8521Map;

    private List<Actvch> actvchList;
    private Actvch vch;
    private long vch_amt = 0;
    private int wkCount = 0;
    private String wkCurcde = "   ";
    private String wkOrgidt = "   ";
    private String wkOrgid3 = "   ";
    private String wkTlrnum = "    ";
    private Short wkVchset = 0;
    private int returnValue = 0;
    private int errFlag = 0;

    public static void main(String argv[]) {
        BCB8521Handler handler = new BCB8521Handler();
        BatchParameterData parameterData = new BatchParameterData();
        handler.processBusiness(parameterData);
    }

    @Override
    protected void processBusiness(BatchParameterData parameterData) {

        try {
            initBatch();
            initActvchList();
            processVchList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void initBatch(final BatchParameterData batchParam) {

    }

    private void initBatch() throws ParseException, IOException {

    }

    private int processVchList() throws ParseException, IOException {
        String rptTitle = ReportHelper.setRightSpace(ReportHelper.setLeftSpace("传票不平衡清单", 29), 29);
        String rptHead = ReportHelper.setLeftSpace("货 币", 9) + ReportHelper.setLeftSpace("柜员号", 8) + ReportHelper.setLeftSpace("传票套号", 7) +
                ReportHelper.setRightSpace(ReportHelper.setLeftSpace(" 不平金额 ", 10), 8);
        char oldchar = ' ';
        char newchar = '-';
        String rptLine = ReportHelper.setLeftSpace("", 72).replace(oldchar, newchar);
        String strPath = PropertyManager.getProperty("BATCH_RESULT_FILE_PATH");
//        String strPath = "d:\\cbs\\batch\\tmp\\";
        //创建路径
        File fileDirec = new File(strPath);
        if (!fileDirec.isDirectory() || !fileDirec.exists()) {
            if (!fileDirec.mkdirs()) {
                logger.error("路径创建失败！");
            }
        }
        //创建报表文件
        File file = new File(strPath+"list8521");
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw=new BufferedWriter(fw);
        bw.write(rptTitle);
        bw.newLine();
        bw.write(rptHead);
        bw.newLine();
        bw.write(rptLine);
        bw.newLine();
        if (actvchList.size() < 1) {
            //转换amt为15为整数 todo
//            bw.write();
//            bw.newLine();
            returnValue = 8;
            this.errFlag = 1;
        } else {
            for (Actvch vch:this.actvchList) {
                if (!vch.getCurcde().equals(this.wkCurcde) || !vch.getOrgidt().equals(this.wkOrgidt)
                        || !vch.getTlrnum().equals(this.wkTlrnum) || !vch.getVchset().equals(this.wkVchset)
                        || !vch.getOrgid3().equals(this.wkOrgid3)) {
                    if (this.vch_amt != 0) {
                        //转换amt为15为整数 todo
                        Short ccyDecpos = 0;
                        if (!this.wkCurcde.trim().equals("")) {
                            Actccy ccy = this.bcb8521Map.selectActccyByPK(this.wkCurcde);
                            ccyDecpos = ccy.getDecpos();
                        }
                        String amtFormat = DataFormater.formatNum(ccyDecpos, this.vch_amt);
                        String rptCurcde = ReportHelper.setLeftSpace(this.wkCurcde,10);
                        String rptTlrnum = ReportHelper.setLeftSpace(this.wkTlrnum,10);
                        String rptVchset = ReportHelper.setLeftSpace(ReportHelper.getRightSpaceStr(String.valueOf(this.wkVchset),4),10);
                        String rptTxnamt = ReportHelper.setLeftSpace(ReportHelper.getRightSpaceStr(amtFormat,21),10);
                        bw.write(rptCurcde + rptTlrnum + rptVchset + rptTxnamt);
                        bw.newLine();
                        returnValue = 8;
                        this.errFlag = 1;
                    }
                    this.vch_amt = 0;
                }
                this.wkCurcde = vch.getCurcde();
                this.wkOrgidt = vch.getOrgidt();
                this.wkTlrnum = vch.getTlrnum();
                this.wkVchset = vch.getVchset();
                this.wkOrgid3 = vch.getOrgid3();
                this.wkCount += 1;
                String vchSecccy = vch.getSecccy();
                short vchCrnyer = vch.getCrnyer();
                //
                if (vchSecccy == null || (!isNumber(vchSecccy) && !vchSecccy.trim().equals(""))) {
                    logger.info("SECCCY IS WRONG " + vch.getTlrnum() + String.valueOf(vch.getVchset()) + String.valueOf(vch.getSetseq()));
                    returnValue = 8;
                    this.errFlag = 1;
                }
//                todo vchCrnyer 不为 numeric ？
                long vchTxnamt = vch.getTxnamt();
                if (vch.getRvslbl().equals(ACEnum.RVSLBL_TRUE.getStatus())) {
                    vchTxnamt = 0 - vchTxnamt;
                }
                this.vch_amt += vchTxnamt;
            }
        }
        bw.flush();
        bw.close();
        if (this.errFlag == 0) {
            this.returnValue = 0;
        } else {
            this.returnValue = 8;
        }
        return returnValue;
    }

    private String curConvert(long vchamt) {
        DecimalFormat df0 = new DecimalFormat("0.00");
        double amt = (double)vchamt/100;
        return df0.format(amt);
    }

    private boolean isNumber(String str) {
        boolean isNum = true;
        try {
            Integer.parseInt(str);
        } catch(Exception e) {
            isNum = false;
        }
        return isNum;
    }

    private void initActvchList() {
        this.actvchList = this.vchMapper.selectByRecsts(ACEnum.RECSTS_VALID.getStatus());
    }

    public void setVchMapper(ActvchMapper vchMapper) {
        this.vchMapper = vchMapper;
    }

    public void setBcb8521Map(BCB8521Mapper bcb8521Map) {
        this.bcb8521Map = bcb8521Map;
    }
}
