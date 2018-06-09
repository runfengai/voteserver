package com.jarry.vote.model.response;

import java.util.Date;
import java.util.List;

/**
 * Description:
 * User: Jarry
 * Date: 2018-06-02
 * Time: 22:22
 */
public class UserVoteInfo {
    private String userId;
    private String subjectId;
    private Long optionId;
    private Date createDate;
//    private UserInfo userInfo;

    private String userName;
    private String school;//学校
    private int type;//0普通用户


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Long getOptionId() {
        return optionId;
    }

    public void setOptionId(Long optionId) {
        this.optionId = optionId;
    }

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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
