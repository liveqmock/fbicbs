package cbs.batch.ac.acbint;

import cbs.common.SystemService;

import java.math.BigDecimal;

/**
 * ��Ϣת�˽跽ƾ֤
 * To change this template use File | Settings | File Templates.
 */
public class TranDebitVoucher {

    private String orgnam;   // ��������
    private String bebglc;    // �跽��Ŀ852
    private String corglc;     // ������Ŀ
    private int txncnt;         // ���ױ���
    private BigDecimal sumint;   // ��Ϣ�ܽ��
    private String date;
    private String corglcnam;   // ������Ŀ����
    private int index;

    public String generateRptUnit() {
        StringBuilder builder = new StringBuilder();
        String newLineCh = "\r\n";
        builder.append(newLineCh).append(newLineCh);
        builder.append("                                       " + orgnam + "ת�˽跽ƾ֤").append(newLineCh);
        builder.append("               ���: " + index + "                                                         ����: " + date).append(newLineCh);
        builder.append("               ��Ŀ(��): " + bebglc + "��Ϣ֧��                                   �Է���Ŀ(��): " + corglc + corglcnam).append(newLineCh);
        builder.append("              ���������������������������ש��������������������������������ש���������������������������������").append(newLineCh);
        builder.append("              ��       �������˺�       ��                ժҪ            ��              ���              ��").append(newLineCh);
        builder.append("              �ǩ������������������������贈�������������������������������贈��������������������������������").append(newLineCh);
        builder.append("              ��                        �� "+addLeftSpace("��"+corglc+"��Ŀ��Ϣ "+txncnt+" ��",30)+" ��     "+ addLeftSpace(SystemService.formatStrAmt(String.format("%.2f", sumint)),25)+"  ��").append(newLineCh);
        builder.append("              �ǩ������������������������贈�������������������������������贈��������������������������������").append(newLineCh);
        builder.append("              ��                        ��                                ��                                ��").append(newLineCh);
        builder.append("              �ǩ������������������������贈�������������������������������贈��������������������������������").append(newLineCh);
        builder.append("              ��                        ��                                ��                                ��").append(newLineCh);
        builder.append("              �ǩ������������������������贈�������������������������������贈��������������������������������").append(newLineCh);
        builder.append("              ��                        ��                                ��                                ��").append(newLineCh);
        builder.append("              �ǩ������������������������ߩ��������������������������������贈��������������������������������").append(newLineCh);
        builder.append("              ��             ��                          ��               ��                                ��").append(newLineCh);
        builder.append("              �ǩ����������������������������������������������������������ߩ���������������������������������").append(newLineCh);
        builder.append("                                         ���                     ����                             ����                           ").append(newLineCh);
        builder.append(newLineCh).append(newLineCh);
        builder.append("-------------------------------------------------------------------------------------------------------------------------------").append(newLineCh);
        return builder.toString();
    }

    /**
       * �Ҷ���  ����һ���ַ����� �������������ܿ��
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
