package com.jarry.chat.model.response;

import java.io.Serializable;
import java.util.Date;

/**
 * Description:
 * User: Jarry
 * Date: 2018-06-02
 * Time: 15:17
 */
public class VoteOptionsInfo implements Serializable {
    private Long id;
    private String subjectId;
    private String optionStr;
    private int optionIndex;
    private Date createDate;

    //统计信息
    private int voteCount;
    private float percent;

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getOptionStr() {
        return optionStr;
    }

    public void setOptionStr(String optionStr) {
        this.optionStr = optionStr;
    }

    public int getOptionIndex() {
        return optionIndex;
    }

    public void setOptionIndex(int optionIndex) {
        this.optionIndex = optionIndex;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
