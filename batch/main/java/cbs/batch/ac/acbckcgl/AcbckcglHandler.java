package cbs.batch.ac.acbckcgl;

import cbs.batch.ac.acbckcgl.dao.AcbckcglMapper;
import cbs.batch.common.AbstractACBatchJobLogic;
import cbs.batch.common.BatchParameterData;
import cbs.batch.common.BatchResultFileHandler;
import cbs.common.enums.ACEnum;
import cbs.repository.account.maininfo.model.Actcgl;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by IntelliJ IDEA.
 * User: zhangxiaobo
 * Date: 2011-3-3
 * Time: 9:31:50
 * To change this template use File | Settings | File Templates.
 */
//@Service("AcbckcglHandler")
@Service
public class AcbckcglHandler extends AbstractACBatchJobLogic {
    private static final Logger logger = LoggerFactory.getLogger(AcbckcglHandler.class);
    @Inject
    private AcbckcglMapper mapper;
    private BatchResultFileHandler fileHandler;
    private String validRecsts = ACEnum.RECSTS_VALID.getStatus();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private List<CglGlc> cglList;
    private String orgnam;
    private String fOrgidt;
    private String oldOrgidt = "";

    @Override
    protected void processBusiness(BatchParameterData parameterData) {
        logger.info("Acbckcgl 科目检查 BEGIN");
        try {
            initProps();
            if (cglList != null && !cglList.isEmpty()) {
                this.fileHandler.setFileTitle("---------------  对应科目及9990科目错误清单    ---------------");
                this.fileHandler.setSpaces("        ");
                String tempGlcode = null;
                long tempDlsbal = 0;
                long tempClsbal = 0;
                long tempDramnt = 0;
                long tempCramnt = 0;
                long tempDrbala = 0;
                long tempCrbala = 0;
                for (CglGlc cgl : cglList) {
                    if (!"9990".equalsIgnoreCase(cgl.getGlcode())) {
                        tempGlcode = cgl.getGlcode();
                        Actcgl actcgl = mapper.qryUniqueCgl(cgl.getOrgidt(), tempGlcode, cgl.getCurcde(), cgl.getRectyp(), cgl.getGldate());
                        if (actcgl != null) {
                            tempDlsbal = actcgl.getDlsbal();
                            tempClsbal = actcgl.getClsbal();
                            tempDramnt = actcgl.getDramnt();
                            tempCramnt = actcgl.getCramnt();
                            tempDrbala = actcgl.getDrbala();
                            tempCrbala = actcgl.getCrbala();
                        }
                    }
                    if (!"9990".equalsIgnoreCase(cgl.getGlcode())
                            && (cgl.getDlsbal() != tempClsbal * (-1)
                            || cgl.getClsbal() != tempDlsbal * (-1)
                            || cgl.getDramnt() != tempCramnt * (-1)
                            || cgl.getCramnt() != tempDramnt * (-1)
                            || cgl.getDrbala() != tempCrbala * (-1)
                            || cgl.getCrbala() != tempDrbala * (-1))
                            || "9990".equalsIgnoreCase(cgl.getGlcode())
                            && (cgl.getDrbala() != 0 || cgl.getCrbala() != 0)) {
                        if (!this.oldOrgidt.equals(cgl.getOrgidt())) {
                            if (!"".equals(this.oldOrgidt)) {
                                this.fileHandler.appendNewLineAffterTitle("-------------------------------------------------");
                            }
                            this.orgnam = mapper.qryOrgnam(cgl.getOrgidt());
                            this.fOrgidt = cgl.getOrgidt();
                            this.oldOrgidt = cgl.getOrgidt();
                            this.fileHandler.appendNewLineAffterTitle(String.format("       机构名称:   %1$s", orgnam));
                            this.fileHandler.appendNewLineAffterTitle(String.format("       机构号:   %1$s       日期:%2$s", fOrgidt, sdf.format(new Date())));
                            this.fileHandler.appendToOtherLineContent("-------------------------------------------------------------------------");
                            this.fileHandler.setFileHeadByArray(new String[]{"科目", "货币", "记录类型", " 日期  ", "上期借方余额",
                                    "上期贷方余额", "本期借方发生额", "本期贷方发生额", "本期借方余额", "本期贷方余额"});
                            this.fileHandler.setFileBodyByArray(new String[]{cgl.getGlcode(), cgl.getCurcde(), cgl.getRectyp(),
                                    sdf.format(cgl.getGldate()), String.valueOf(cgl.getDlsbal()), String.valueOf(cgl.getClsbal()),
                                    String.valueOf(cgl.getDramnt()), String.valueOf(cgl.getCramnt()), String.valueOf(cgl.getDrbala()),
                                    String.valueOf(cgl.getCrbala())});
                        }
                    }
                }

            } else {
                this.fileHandler.appendNewLineAffterTitle("该清单为空!");
            }
            this.fileHandler.writeToFile();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        logger.info("Acbckcgl 科目检查 END");
    }

    protected void initBatch(final BatchParameterData batchParam) {
        this.fileHandler = new BatchResultFileHandler("ck_cglerr.txt");
    }

    private void initProps() {
        this.cglList = mapper.qryCgls("8", "7%", validRecsts, validRecsts, ACEnum.RECTYP_Y.getStatus(),
                "9990", validRecsts, ACEnum.RECTYP_D.getStatus());
    }
}
