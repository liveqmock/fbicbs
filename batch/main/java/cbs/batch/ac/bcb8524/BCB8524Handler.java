package cbs.batch.ac.bcb8524;

import cbs.batch.ac.bcb8524.dao.BCB8524Mapper;
import cbs.batch.common.AbstractACBatchJobLogic;
import cbs.batch.common.BatchParameterData;
import cbs.batch.common.BatchResultFileHandler;
import cbs.batch.common.service.BatchSystemService;
import cbs.common.enums.ACEnum;
import cbs.repository.code.model.Actccy;

import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 2011-2-24
 * Time: 10:35:01
 * 核对ACTACT
 */
@Service
public class BCB8524Handler extends AbstractACBatchJobLogic {

    private static final Logger logger = LoggerFactory.getLogger(BCB8524Handler.class);
    @Inject
    private BCB8524Mapper mapper;
    @Inject
    private BatchSystemService systemService;
    private String sysidt;
    private String validRecsts = ACEnum.RECSTS_VALID.getStatus();
    private List<ActArgsBean> actArgsList;
    private BatchResultFileHandler fileHandler1;
    private BatchResultFileHandler fileHandler2;
    private DecimalFormat df = new DecimalFormat("###,###,###,##0.00");

    @Override
    protected void processBusiness(BatchParameterData parameterData) {
        this.actArgsList = qryActArgs(this.sysidt, this.validRecsts);
        if (!this.actArgsList.isEmpty()) {
            this.fileHandler1.setFileTitle("---------------------------BCB8524 核对ACTACT  ACT-SUM-BOKBAL-------------------------");
            this.fileHandler1.setFileHeadByArray(new String[]{"ORGIDT", "CURCDE", "BALANC"});
            this.fileHandler2.setFileTitle("--------------BCB8524 核对ACTACT  ACT-SUM-DDRAMT和ACT-SUM-DCRAMT----------");
            this.fileHandler2.setSpaces("     ");
            this.fileHandler2.setFileHeadByArray(new String[]{"ORGIDT", "CURCDE", "SUM-DDRAMT", "SUM-DCRAMT"
                    , "SUM-MDRAMT", "SUM-MCRAMT", "SUM-YDRAMT", "SUM-YCRAMT", "SUM-BALAMT"});
            int bokbalCheckNum = 0;
            int balAmtbalCheckNum = 0;
            for (ActArgsBean bean : actArgsList) {
                if (bean.getSumBokbal().compareTo(new BigDecimal(0)) != 0) {
                    logger.info("ACT-SUM-BOKBAL != 0");
                    Actccy ccy = mapper.selectCcyByCurcde(bean.getCurcde());
                    String sumBokbal = df.format(bean.getSumBokbal().divide(new BigDecimal(100)));
                    this.fileHandler1.setFileBodyByArray(new String[]{bean.getOrgidt(), bean.getCurcde(), sumBokbal});
                    bokbalCheckNum++;
                }
                BigDecimal wkBalAmtbal = bean.getSumDdramt().add(bean.getSumDcramt());
                if (wkBalAmtbal.compareTo(new BigDecimal(0)) != 0) {
                    logger.info("ACT-SUM-DDRAMT != ACT-SUM-DCRAMT");
                    Actccy ccy = mapper.selectCcyByCurcde(bean.getCurcde());
                    this.fileHandler2.setFileBodyByArray(new String[]{bean.getOrgidt(), bean.getCurcde(), String.valueOf(bean.getSumDdramt())
                            , String.valueOf(bean.getSumDcramt()), String.valueOf(bean.getSumMdramt()), String.valueOf(bean.getSumMcramt()),
                            String.valueOf(bean.getSumYdramt()), String.valueOf(bean.getSumYcramt()), String.valueOf(bean.getSumBokbal())});
                    balAmtbalCheckNum++;
                }

            }
            if (bokbalCheckNum == 0) {
                logger.info("ACT-SUM-BOKBAL == 0");
                this.fileHandler1.appendToFoot("ACT-SUM-BOKBAL == 0");
            } else {
                this.fileHandler1.appendToFoot("检查到" + bokbalCheckNum + "条记录ACT-SUM-BOKBAL != 0");
            }
            if (balAmtbalCheckNum == 0) {
                logger.info("ACT-SUM-DDRAMT == ACT-SUM-DCRAMT");
                this.fileHandler2.appendToFoot("ACT-SUM-DDRAMT == ACT-SUM-DCRAMT");
            } else {
                this.fileHandler2.appendToFoot("检查到" + balAmtbalCheckNum + "条记录ACT-SUM-DDRAMT != ACT-SUM-DCRAMT");
            }
        } else {
            this.fileHandler1.setFileTitle("------BCB8524  ACTACT-没有需核对记录.");
            this.fileHandler2.setFileTitle("------BCB8524  ACTACT-没有需核对记录.");
        }
        this.fileHandler1.writeToFile();
        this.fileHandler2.writeToFile();
    }
    // 生成两个检查结果文件

    protected void initBatch(final BatchParameterData batchParam) {
        this.fileHandler1 = new BatchResultFileHandler("list8524.txt");
        this.fileHandler2 = new BatchResultFileHandler("list8524a.txt");
        this.sysidt = systemService.getSysidtAC();
    }

    private List<ActArgsBean> qryActArgs(String sysidt, String recsts) {
        return mapper.selectActArgs(sysidt, recsts);
    }

    public BCB8524Mapper getMapper() {
        return mapper;
    }

    public void setMapper(BCB8524Mapper mapper) {
        this.mapper = mapper;
    }
}
