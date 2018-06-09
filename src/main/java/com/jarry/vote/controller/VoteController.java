package com.jarry.vote.controller;

import com.jarry.vote.model.MessageData;
import com.jarry.vote.model.request.VoteParam;
import com.jarry.vote.model.response.UserVote;
import com.jarry.vote.service.VoteService;
import com.jarry.vote.util.Constant;
import com.jarry.vote.util.DateUtils;
import com.jarry.vote.util.ErrorMap;
import com.mysql.jdbc.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Description:
 * User: Jarry
 * Date: 2018-06-02
 * Time: 15:13
 */
@RestController
@RequestMapping("/vote")
public class VoteController {
    private static final Logger logger = LoggerFactory.getLogger(VoteController.class);
    @Autowired
    VoteService voteService;


    @RequestMapping(value = "/create", produces = "application/json;charset=utf-8")
    MessageData create(@RequestBody VoteParam voteParam) {
        logger.info("create------->", voteParam);
        if (voteParam == null) return new MessageData(Constant.MSG_PARAM_NULL, Constant.CODE_PARAM_NULL);
        else if (StringUtils.isNullOrEmpty(voteParam.getSubject())) {
            return new MessageData(ErrorMap.getErrorStr(Constant.CODE_VOTE_SUBJECT_NULL), Constant.CODE_VOTE_SUBJECT_NULL);
        } else if (voteParam.getOption() == null || voteParam.getOption().size() == 0) {
            return new MessageData(ErrorMap.getErrorStr(Constant.CODE_VOTE_OPTION_NULL), Constant.CODE_VOTE_OPTION_NULL);
        } else if (org.springframework.util.StringUtils.isEmpty(voteParam.getExpiryDate()) || DateUtils.parse(voteParam.getExpiryDate()) == null) {
            return new MessageData(ErrorMap.getErrorStr(Constant.CODE_VOTE_EXPIRY_DATE_NULL), Constant.CODE_VOTE_EXPIRY_DATE_NULL);
        }
        for (String opt : voteParam.getOption()) {
            if (StringUtils.isNullOrEmpty(opt))
                return new MessageData(ErrorMap.getErrorStr(Constant.CODE_VOTE_OPTION_CONTAINS_NULL), Constant.CODE_VOTE_OPTION_CONTAINS_NULL);
        }
        return voteService.create(voteParam);
    }

    @RequestMapping(value = "/list")
    MessageData getList(String userId) {
        logger.info("getList------->", userId);
        return voteService.voteList(userId);
    }

    @RequestMapping(value = "/delete")
    MessageData delete(String subjectId) {
        logger.info("delete------->", subjectId);
        if (StringUtils.isNullOrEmpty(subjectId))
            return new MessageData(Constant.MSG_PARAM_NULL, Constant.CODE_PARAM_NULL);
        return voteService.delete(subjectId);
    }

    @RequestMapping(value = "/vote", produces = "application/json;charset=utf-8")
    MessageData vote(@RequestBody UserVote userVote) {
        logger.info("vote------->", userVote);
        if (userVote == null)
            return new MessageData(Constant.MSG_PARAM_NULL, Constant.CODE_PARAM_NULL);
        String userId = userVote.getUserId();
        String subjectId = userVote.getSubjectId();
        List<Long> optionId = userVote.getOptionId();
        if (StringUtils.isNullOrEmpty(userId))
            return new MessageData(ErrorMap.getErrorStr(Constant.CODE_USER_NOT_EXIST), Constant.CODE_USER_NOT_EXIST);
        else if (StringUtils.isNullOrEmpty(subjectId))
            return new MessageData(ErrorMap.getErrorStr(Constant.CODE_VOTE_INFO_NULL), Constant.CODE_VOTE_INFO_NULL);
        else if (optionId == null || optionId.size() == 0) {
            return new MessageData(ErrorMap.getErrorStr(Constant.CODE_VOTE_NOT_SELECT), Constant.CODE_VOTE_NOT_SELECT);
        }
        return voteService.vote(userId, subjectId, optionId);
    }

    @RequestMapping(value = "/detail")
    MessageData detail(String subjectId, String userId) {
        logger.info("detail------->", "subjectId=" + subjectId + "  userId=" + userId);
        if (StringUtils.isNullOrEmpty(subjectId))
            return new MessageData(ErrorMap.getErrorStr(Constant.CODE_VOTE_INFO_NULL), Constant.CODE_VOTE_INFO_NULL);
        if (StringUtils.isNullOrEmpty(userId))
            return new MessageData(ErrorMap.getErrorStr(Constant.CODE_USER_NOT_EXIST), Constant.CODE_USER_NOT_EXIST);

        return voteService.detail(subjectId, userId);
    }
}
