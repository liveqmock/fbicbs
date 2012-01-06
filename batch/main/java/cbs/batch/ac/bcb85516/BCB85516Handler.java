package cbs.batch.ac.bcb85516;

import cbs.batch.ac.bcb85516.dao.BCB85516Mapper;
import cbs.batch.common.AbstractACBatchJobLogic;
import cbs.batch.common.BatchParameterData;
import cbs.common.enums.ACEnum;
import cbs.repository.account.billinfo.model.Actlbl;
import cbs.repository.account.billinfo.model.Actsbl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: haiyu
 * Date: 2011-3-2
 * Time: 16:58:54
 * To change this template use File | Settings | File Templates.
 */
@Service
public class BCB85516Handler extends AbstractACBatchJobLogic {
    private static final Logger logger = LoggerFactory.getLogger(BCB85516Handler.class);

    @Inject
    private BCB85516Mapper bcb85516Map;

    private List<Actsbl> sblList;
    private List<Actlbl> lblList;

    @Override
    protected void processBusiness(BatchParameterData parameterData) {
        try {
            initBatch(); //获取Actsbl Actlbl数据
            processBatch();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void processBatch() {
        for (Actsbl sbl:this.sblList) {
            if (sbl.getStmfmt().equals("C") || sbl.getStmfmt().equals("S")) {
                this.bcb85516Map.updateActnsmRecsts(ACEnum.SYSIDT_AC.getStatus(),sbl.getOrgidt(),sbl.getCusidt(),sbl.getApcode(),
                        sbl.getCurcde());
            }
            if (sbl.getStmfmt().equals("L")) {
               this.bcb85516Map.updateActlsmRecsts(ACEnum.SYSIDT_AC.getStatus(),sbl.getOrgidt(),sbl.getCusidt(),sbl.getApcode(),
                        sbl.getCurcde());          
            }
            if (sbl.getRecsts().equals("C")) {
                this.bcb85516Map.updateActsbl(ACEnum.SYSIDT_AC.getStatus(),sbl.getOrgidt(),sbl.getCusidt(),sbl.getApcode(),
                        sbl.getCurcde());
                this.bcb85516Map.updateActsblRecsts(ACEnum.SYSIDT_AC.getStatus(),sbl.getOrgidt(),sbl.getCusidt(),sbl.getApcode(),
                        sbl.getCurcde(),sbl.getSecccy());
            }
        }

        for (Actlbl lbl:this.lblList) {
            if (lbl.getLegfmt().equals("C") || lbl.getLegfmt().equals("I")) {
                this.bcb85516Map.updateActlgcRecsts(ACEnum.SYSIDT_AC.getStatus(),lbl.getOrgidt(),lbl.getCusidt(),lbl.getApcode(),
                        lbl.getCurcde());
            }
            if (lbl.getLegfmt().equals("F")) {
                this.bcb85516Map.updateActlgfRecsts(ACEnum.SYSIDT_AC.getStatus(),lbl.getOrgidt(),lbl.getCusidt(),lbl.getApcode(),
                        lbl.getCurcde());
            }
            if (lbl.getRecsts().equals("C")) {
                this.bcb85516Map.updateActlbl(ACEnum.SYSIDT_AC.getStatus(),lbl.getOrgidt(),lbl.getCusidt(),lbl.getApcode(),
                        lbl.getCurcde());
                this.bcb85516Map.updateActlblRecsts(ACEnum.SYSIDT_AC.getStatus(),lbl.getOrgidt(),lbl.getCusidt(),lbl.getApcode(),
                        lbl.getCurcde(),lbl.getSecccy());
            }
        }
    }

    private void initBatch() {
        this.sblList = this.bcb85516Map.selectActsbl();
        this.lblList = this.bcb85516Map.selectActlbl();
    }

    protected void initBatch(final BatchParameterData batchParam) {

    }

    public void setBcb85516Map(BCB85516Mapper bcb85516Map) {
        this.bcb85516Map = bcb85516Map;
    }
}
