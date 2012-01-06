package cbs.batch.ac.acbint;

/**
 * ��Ϣ֪ͨ����ʽ
 */
public class InterestVchTemplate {
    private StringBuilder builder;
    private String titleName;      // ����
    private String orgidt;            // ������
    private String date;               // ����
    private String vchset;           // ��Ʊ�׺�
    private String borStr = "(��)";           // ��
    private String lenStr = "(��)";            // ��
    private String actno;             // �˺�
    private String amt;                //  ����
    private String navAmt;         // �����
    private String fromActno;     // �Է��˺�
    private String newLineCh = "\r\n";

    public InterestVchTemplate() {
        builder = new StringBuilder();
    }


    private void generateReportUnit() {
        builder.append("                                   " + titleName).append(newLineCh);
        builder.append("     ����:" + date).append("  ����:" + orgidt).append("  ��Ʊ�׺�:" + vchset).append(newLineCh);
        builder.append("  ���������������������������ש����������������������������ש�������������������������").append(newLineCh);
        builder.append("  ��                        ��            �˺�            ��            ���        ��").append(newLineCh);
        builder.append("  �ǩ������������������������贈���������������������������贈������������������������").append(newLineCh);
        builder.append("  ��           " + borStr + "         �� " + appendSpaceToStr(27,actno) + "�� " + appendSpaceToStr(23,amt) + "��").append(newLineCh);
        builder.append("  ��           " + lenStr + "         �� " + appendSpaceToStr(27,fromActno) + "�� " +appendSpaceToStr(23, navAmt) + "��").append(newLineCh);
        builder.append("  ��                        ��                            ��                        ��").append(newLineCh);
        builder.append("  ��                        ��                            ��                        ��").append(newLineCh);
        builder.append("  ��                        ��                            ��                        ��").append(newLineCh);
        builder.append("  ���������������������������ߩ����������������������������ߩ�������������������������");
        builder.append(newLineCh);
        builder.append("  ������������������������������������������������������������������������������������");
        builder.append(newLineCh);
    }

    // �ַ�����ӿո�
    private String appendSpaceToStr(int spaceCnt, String content) {
        if (content == null) {
            content = "null";
        }
        int spaceAddCnt = spaceCnt - content.toCharArray().length;
        StringBuilder builder = new StringBuilder();
        if (spaceAddCnt > 0) {
            for (int i = 0; i < spaceAddCnt; i++) {
                builder.append(" ");
            }
        }
        return builder.append(content).toString();
    }

    // ������ӿո�
    private String appendSpaceToChStr(int spaceCnt, String content) {
        if (content == null) {
            content = "null";
        }
        int spaceAddCnt = spaceCnt - content.getBytes().length;
        StringBuilder builder = new StringBuilder();
        if (spaceAddCnt > 0) {
            for (int i = 0; i < spaceAddCnt; i++) {
                builder.append(" ");
            }
        }
        return builder.append(content).toString();
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setVchset(String vchset) {
        this.vchset = vchset;
    }

    public void setActno(String actno) {
        this.actno = actno;
    }

    public void setOrgidt(String orgidt) {
        this.orgidt = orgidt;
    }

    public void setBorStr(String borStr) {
        this.borStr = borStr;
    }

    public void setLenStr(String lenStr) {
        this.lenStr = lenStr;
    }

    public void setNavAmt(String navAmt) {
        this.navAmt = navAmt;
    }

    public void setFromActno(String fromActno) {
        this.fromActno = fromActno;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    @Override
    public String toString() {
        generateReportUnit();
        return builder.toString();
    }
}
