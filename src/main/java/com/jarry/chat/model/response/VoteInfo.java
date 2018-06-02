package com.jarry.chat.model.response;

import java.io.Serializable;
import java.util.Date;

/**
 * Description:
 * User: Jarry
 * Date: 2018-06-02
 * Time: 15:17
 */
public class VoteInfo implements Serializable {
    private String subject;
    private String subjectId;
    public static final int TYPE_SINGLE = 0;
    public static final int TYPE_MULTI = 1;
    private int type;
    private Date expiryDate;
    private Date createDate;
    private Date updateDate;

    public Date getUpdateDate() {
        return updateDate;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

}
