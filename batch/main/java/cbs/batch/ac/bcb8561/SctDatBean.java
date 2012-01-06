package cbs.batch.ac.bcb8561;

import cbs.repository.code.model.Actsct;

import java.util.Date;

public class SctDatBean {

    private int sct_lwkdds;
    private int sct_nxtdds;
    private int work_days;

    public int getSct_nxtdds() {
        return sct_nxtdds;
    }

    public void setSct_nxtdds(int sct_nxtdds) {
        this.sct_nxtdds = sct_nxtdds;
    }

    public int getWork_days() {
        return work_days;
    }

    public void setWork_days(int work_days) {
        this.work_days = work_days;
    }

    public int getSct_lwkdds() {
        return sct_lwkdds;
    }

    public void setSct_lwkdds(int sct_lwkdds) {
        this.sct_lwkdds = sct_lwkdds;
    }
}