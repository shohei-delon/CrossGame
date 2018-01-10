package com.delon.user.crossgameapplication;

/**
 * Created by User on 2016/07/04.
 */

public class RankingItem {

    private int resultId;
    private String resultDate;
    private int resultCount;

    public RankingItem(int resultId, String resultDate, int resultCount){
        this.resultId = resultId;
        this.resultDate = resultDate;
        this.resultCount = resultCount;
    }

    public void setResultId(int resultId) {
        this.resultId = resultId;
    }

    public int getResultId() {
        return resultId;
    }

    public void setResultDate(String resultDate) {
        this.resultDate = resultDate;
    }

    public String getResultDate() {
        return resultDate;
    }

    public void setResultCount(int resultCount) {
        this.resultCount = resultCount;
    }

    public int getResultCount() {
        return resultCount;
    }

}
