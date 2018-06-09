package com.jarry.vote.model.request;

import java.util.Date;
import java.util.List;

/**
 * Description:
 * User: Jarry
 * Date: 2018-06-02
 * Time: 15:17
 */
public class VoteParam {
    private String voteId;
    private String subjectId;
    private String subject;
    private int type;
    private List<String> option;
    private String expiryDate;//失效日期
    private String createDate;//失效日期

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public String getVoteId() {
        return voteId;
    }

    public void setVoteId(String voteId) {
        this.voteId = voteId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<String> getOption() {
        return option;
    }

    public void setOption(List<String> option) {
        this.option = option;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
}
