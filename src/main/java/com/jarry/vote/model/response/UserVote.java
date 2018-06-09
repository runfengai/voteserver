package com.jarry.vote.model.response;

import java.util.Date;
import java.util.List;

/**
 * Description:
 * User: Jarry
 * Date: 2018-06-02
 * Time: 22:22
 */
public class UserVote {
    private String userId;
    private String subjectId;
    private List<Long> optionId;
    private Date createDate;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public List<Long> getOptionId() {
        return optionId;
    }

    public void setOptionId(List<Long> optionId) {
        this.optionId = optionId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
