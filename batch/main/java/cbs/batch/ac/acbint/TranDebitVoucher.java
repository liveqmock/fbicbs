package cbs.batch.ac.acbint;

import cbs.common.SystemService;

import java.math.BigDecimal;

/**
 * 计息转账借方凭证
 * To change this template use File | Settings | File Templates.
 */
public class TranDebitVoucher {

    private String orgnam;   // 机构名称
    private String bebglc;    // 借方科目852
    private String corglc;     // 贷方科目
    private int txncnt;         // 交易笔数
    private BigDecimal sumint;   // 利息总金额
    private String date;
    private String corglcnam;   // 贷方科目名称
    private int index;

    public String generateRptUnit() {
        StringBuilder builder = new StringBuilder();
        String newLineCh = "\r\n";
        builder.append(newLineCh).append(newLineCh);
        builder.append("                                       " + orgnam + "转账借方凭证").append(newLineCh);
        builder.append("               序号: " + index + "                                                         日期: " + date).append(newLineCh);
        builder.append("               科目(借): " + bebglc + "利息支出                                   对方科目(贷): " + corglc + corglcnam).append(newLineCh);
        builder.append("              ┏━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━┓").append(newLineCh);
        builder.append("              ┃       户名及账号       ┃                摘要            ┃              金额              ┃").append(newLineCh);
        builder.append("              ┣━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━┫").append(newLineCh);
        builder.append("              ┃                        ┃ "+addLeftSpace("付"+corglc+"科目利息 "+txncnt+" 笔",30)+" ┃     "+ addLeftSpace(SystemService.formatStrAmt(String.format("%.2f", sumint)),25)+"  ┃").append(newLineCh);
        builder.append("              ┣━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━┫").append(newLineCh);
        builder.append("              ┃                        ┃                                ┃                                ┃").append(newLineCh);
        builder.append("              ┣━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━┫").append(newLineCh);
        builder.append("              ┃                        ┃                                ┃                                ┃").append(newLineCh);
        builder.append("              ┣━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━┫").append(newLineCh);
        builder.append("              ┃                        ┃                                ┃                                ┃").append(newLineCh);
        builder.append("              ┣━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━┫").append(newLineCh);
        builder.append("              ┃             合                          计               ┃                                ┃").append(newLineCh);
        builder.append("              ┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━┛").append(newLineCh);
        builder.append("                                         会计                     复核                             记账                           ").append(newLineCh);
        builder.append(newLineCh).append(newLineCh);
        builder.append("-------------------------------------------------------------------------------------------------------------------------------").append(newLineCh);
        return builder.toString();
    }

    /**
       * 右对齐  参数一：字符内容 参数二：数据总宽度
       *
       * @return
       */
      private String addLeftSpace(String content, int width) {
          StringBuilder strBuilder = new StringBuilder();
          if (content == null) return null;
          for (int i = 0; i < (width - content.getBytes().length); i++) {
              strBuilder.append(" ");
          }
          return strBuilder.append(content).toString();
      }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCorglcnam() {
        return corglcnam;
    }

    public void setCorglcnam(String corglcnam) {
        this.corglcnam = corglcnam;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getOrgnam() {
        return orgnam;
    }

    public void setOrgnam(String orgnam) {
        this.orgnam = orgnam;
    }

    public String getBebglc() {
        return bebglc;
    }

    public void setBebglc(String bebglc) {
        this.bebglc = bebglc;
    }

    public String getCorglc() {
        return corglc;
    }

    public void setCorglc(String corglc) {
        this.corglc = corglc;
    }

    public int getTxncnt() {
        return txncnt;
    }

    public void setTxncnt(int txncnt) {
        this.txncnt = txncnt;
    }

    public BigDecimal getSumint() {
        return sumint;
    }

    public void setSumint(BigDecimal sumint) {
        if(sumint.doubleValue() < 0) {
            this.sumint = sumint.divide(new BigDecimal(100.0)).multiply(new BigDecimal(-1.0));
        } else {
        this.sumint = sumint.divide(new BigDecimal(100.0));
        }
    }
}
