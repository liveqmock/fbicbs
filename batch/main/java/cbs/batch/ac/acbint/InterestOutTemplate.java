package cbs.batch.ac.acbint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * ��Ϣ�嵥��ʽ
 */
public class InterestOutTemplate {
    private StringBuilder builder;
    private String titleName;      // ����
    private String orgidt;            // ������
    private String date;               // ����
    private String actnam;           // ��λ����
    private String actno;             // �˺�
    private String startDate;       // ��Ϣ����
    private String endDate;        // ��Ϣ����
    private String days;               // ����
    private String craccm;        // ����
    private String rate;                // ����
    private String interest;         //  ��Ϣ
    private String sxInterest;       //  ��Ϣ2
    private String fints;               // ��Ϣ
    private String vchset;            //  ��Ʊ�׺�
    private String fromActno;     // �Է���Ŀ
    private String newLineCh = "\r\n";

    public InterestOutTemplate() {
        builder = new StringBuilder();
    }

    private void generateReportUnit() {
        days = String.valueOf(getBetweenDays(startDate, endDate));
        builder.append(newLineCh);
        builder.append("                                   " + titleName).append(newLineCh);
        builder.append("   ����:" + orgidt + appendSpaceToStr(85 - orgidt.getBytes().length, "����: " + date)).append(newLineCh);
        builder.append("   �˺�:" + this.actno + appendSpaceToStr(79 - actno.getBytes().length, "��λ����: " + actnam)).append(newLineCh);
        builder.append("  �������������������ש����������������ש��������ש��������������������ש��������ש�������������������").append(newLineCh);
        builder.append("  ��    ��Ϣ����    ��    ��Ϣ����    ��  ����  ��        ����        �� ������ ��        ��Ϣ      ��").append(newLineCh);
        builder.append("  �ǩ����������������贈���������������贈�������贈�������������������贈�������贈������������������").append(newLineCh);
        builder.append("  ��   " + startDate + "   ��   " + endDate + "   ��" + appendSpaceToStr(8, days) + "��" + appendSpaceToStr(20, craccm) + "�� "
                + appendSpaceToStr(7, rate) + "��" + appendSpaceToStr(18, interest) + "��").append(newLineCh);
        builder.append("  �ǩ����������������ߩ����������������ߩ��������ߩ��������������������贈�������ߩ�������������������").append(newLineCh);
        builder.append("  ��      ���д����Ϣ���������㵥λ" + appendSpaceToStr(26, actno) + "�˻���  ����Ŀ������:" + appendSpaceToStr(17, actno) + "��").append(newLineCh);
        builder.append("  ��                                                                  ���Է���Ŀ���裩:" + appendSpaceToStr(13, fromActno) + "��").append(newLineCh);
        builder.append("  ��                                                                  ��                            ��").append(newLineCh);
        builder.append("  ��                                        ��ǩ�£�                  ��   ����          ����       ��").append(newLineCh);
        builder.append("  ���������������������������������������������������������������������ߩ�����������������������������");
        //builder.append("��Ϣ2(ShengXiInts): " + sxInterest + "��Ʊ�׺�: " + vchset + " ��Ϣ: " + fints);
        builder.append(newLineCh);
        builder.append("  ����������������������������������������������������������������������������������������������������");
        builder.append(newLineCh);
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

    public void setOrgidt(String orgidt) {
        this.orgidt = orgidt;
    }

    public void setFromActno(String fromActno) {
        this.fromActno = fromActno;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setActnam(String actnam) {
        this.actnam = actnam;
    }

    public void setActno(String actno) {
        this.actno = actno;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public void setCraccm(String craccm) {
        this.craccm = craccm;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public void setFints(String fints) {
        this.fints = fints;
    }

    public void setSxInterest(String sxInterest) {
        this.sxInterest = sxInterest;
    }

    public void setVchset(String vchset) {
        this.vchset = vchset;
    }

    public static int getBetweenDays(String t1, String t2) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        int betweenDays = 0;
        try {
            Date d1 = format.parse(t1);
            Date d2 = format.parse(t2);

            Calendar c1 = Calendar.getInstance();
            Calendar c2 = Calendar.getInstance();
            c1.setTime(d1);
            c2.setTime(d2);
            if (c1.after(c2)) {
                c1 = c2;
                c2.setTime(d1);
            }
            int betweenYears = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
            betweenDays = c2.get(Calendar.DAY_OF_YEAR) - c1.get(Calendar.DAY_OF_YEAR);
            for (int i = 0; i < betweenYears; i++) {
                c1.set(Calendar.YEAR, (c1.get(Calendar.YEAR) + 1));
                betweenDays += c1.getMaximum(Calendar.DAY_OF_YEAR);
            }
        } catch (ParseException e) {
            throw new RuntimeException("��Ϣ֪ͨ�м�������ʱ����ת�������쳣��");
        }
        return betweenDays;
    }

    @Override
    public String toString() {
        generateReportUnit();
        return builder.toString();
    }
}
