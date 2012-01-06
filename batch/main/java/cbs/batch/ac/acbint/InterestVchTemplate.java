package cbs.batch.ac.acbint;

/**
 * 计息通知单格式
 */
public class InterestVchTemplate {
    private StringBuilder builder;
    private String titleName;      // 标题
    private String orgidt;            // 机构号
    private String date;               // 日期
    private String vchset;           // 传票套号
    private String borStr = "(借)";           // 借
    private String lenStr = "(贷)";            // 贷
    private String actno;             // 账号
    private String amt;                //  借金额
    private String navAmt;         // 贷金额
    private String fromActno;     // 对方账号
    private String newLineCh = "\r\n";

    public InterestVchTemplate() {
        builder = new StringBuilder();
    }


    private void generateReportUnit() {
        builder.append("                                   " + titleName).append(newLineCh);
        builder.append("     日期:" + date).append("  机构:" + orgidt).append("  传票套号:" + vchset).append(newLineCh);
        builder.append("  ┏━━━━━━━━━━━━┳━━━━━━━━━━━━━━┳━━━━━━━━━━━━┓").append(newLineCh);
        builder.append("  ┃                        ┃            账号            ┃            金额        ┃").append(newLineCh);
        builder.append("  ┣━━━━━━━━━━━━╋━━━━━━━━━━━━━━╋━━━━━━━━━━━━┫").append(newLineCh);
        builder.append("  ┃           " + borStr + "         ┃ " + appendSpaceToStr(27,actno) + "┃ " + appendSpaceToStr(23,amt) + "┃").append(newLineCh);
        builder.append("  ┃           " + lenStr + "         ┃ " + appendSpaceToStr(27,fromActno) + "┃ " +appendSpaceToStr(23, navAmt) + "┃").append(newLineCh);
        builder.append("  ┃                        ┃                            ┃                        ┃").append(newLineCh);
        builder.append("  ┃                        ┃                            ┃                        ┃").append(newLineCh);
        builder.append("  ┃                        ┃                            ┃                        ┃").append(newLineCh);
        builder.append("  ┗━━━━━━━━━━━━┻━━━━━━━━━━━━━━┻━━━━━━━━━━━━┛");
        builder.append(newLineCh);
        builder.append("  ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
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
