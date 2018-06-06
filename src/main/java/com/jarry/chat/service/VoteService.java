package com.jarry.chat.service;

import com.jarry.chat.model.MessageData;
import com.jarry.chat.model.request.SectionSourceParam;
import com.jarry.chat.model.request.VoteParam;

import java.util.List;

/**
 * Description:
 * User: Jarry
 * Date: 2018-06-02
 * Time: 15:14
 */
public interface VoteService {

    //列表
    MessageData voteList(String userId);

    //投票
    MessageData vote(String userId, String subjectId, List<Long> optionIndexs);

    //创建
    MessageData create(VoteParam voteParam);

    MessageData delete(String subjectId);

    MessageData detail(String subjectId,String userId);

    //修改
    MessageData update(VoteParam voteParam);

    //查看某个投票的某个选项的来源
    MessageData checkSource(SectionSourceParam sectionSourceParam);
}
