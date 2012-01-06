package cbs.batch.ac.acbint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 计息清单格式
 */
public class InterestOutTemplate {
    private StringBuilder builder;
    private String titleName;      // 标题
    private String orgidt;            // 机构号
    private String date;               // 日期
    private String actnam;           // 单位名称
    private String actno;             // 账号
    private String startDate;       // 起息日期
    private String endDate;        // 结息日期
    private String days;               // 天数
    private String craccm;        // 积数
    private String rate;                // 利率
    private String interest;         //  利息
    private String sxInterest;       //  利息2
    private String fints;               // 罚息
    private String vchset;            //  传票套号
    private String fromActno;     // 对方科目
    private String newLineCh = "\r\n";

    public InterestOutTemplate() {
        builder = new StringBuilder();
    }

    private void generateReportUnit() {
        days = String.valueOf(getBetweenDays(startDate, endDate));
        builder.append(newLineCh);
        builder.append("                                   " + titleName).append(newLineCh);
        builder.append("   机构:" + orgidt + appendSpaceToStr(85 - orgidt.getBytes().length, "日期: " + date)).append(newLineCh);
        builder.append("   账号:" + this.actno + appendSpaceToStr(79 - actno.getBytes().length, "单位名称: " + actnam)).append(newLineCh);
        builder.append("  ┏━━━━━━━━┳━━━━━━━━┳━━━━┳━━━━━━━━━━┳━━━━┳━━━━━━━━━┓").append(newLineCh);
        builder.append("  ┃    起息日期    ┃    结息日期    ┃  天数  ┃        积数        ┃ 年利率 ┃        利息      ┃").append(newLineCh);
        builder.append("  ┣━━━━━━━━╋━━━━━━━━╋━━━━╋━━━━━━━━━━╋━━━━╋━━━━━━━━━┫").append(newLineCh);
        builder.append("  ┃   " + startDate + "   ┃   " + endDate + "   ┃" + appendSpaceToStr(8, days) + "┃" + appendSpaceToStr(20, craccm) + "┃ "
                + appendSpaceToStr(7, rate) + "┃" + appendSpaceToStr(18, interest) + "┃").append(newLineCh);
        builder.append("  ┣━━━━━━━━┻━━━━━━━━┻━━━━┻━━━━━━━━━━╋━━━━┻━━━━━━━━━┫").append(newLineCh);
        builder.append("  ┃      上列存款利息，已照收你单位" + appendSpaceToStr(26, actno) + "账户。  ┃科目（贷）:" + appendSpaceToStr(17, actno) + "┃").append(newLineCh);
        builder.append("  ┃                                                                  ┃对方科目（借）:" + appendSpaceToStr(13, fromActno) + "┃").append(newLineCh);
        builder.append("  ┃                                                                  ┃                            ┃").append(newLineCh);
        builder.append("  ┃                                        （签章）                  ┃   复核          记账       ┃").append(newLineCh);
        builder.append("  ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━┛");
        //builder.append("利息2(ShengXiInts): " + sxInterest + "传票套号: " + vchset + " 罚息: " + fints);
        builder.append(newLineCh);
        builder.append("  ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        builder.append(newLineCh);
        builder.append(newLineCh);
    }

    // 字符串添加空格
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

    // 汉字添加空格
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
            throw new RuntimeException("利息通知中计算天数时日期转换发生异常！");
        }
        return betweenDays;
    }

    @Override
    public String toString() {
        generateReportUnit();
        return builder.toString();
    }
}
