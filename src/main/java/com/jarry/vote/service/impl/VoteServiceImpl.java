package com.jarry.vote.service.impl;

import com.jarry.vote.mapper.UserInfoMapper;
import com.jarry.vote.mapper.VoteInfoMapper;
import com.jarry.vote.model.MessageData;
import com.jarry.vote.model.request.SectionSourceParam;
import com.jarry.vote.model.request.VoteParam;
import com.jarry.vote.model.response.*;
import com.jarry.vote.service.VoteService;
import com.jarry.vote.util.Constant;
import com.jarry.vote.util.DateUtils;
import com.jarry.vote.util.ErrorMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Description: Ctrl+Alt+V 快速生成代码
 * User: Jarry
 * Date: 2018-06-02
 * Time: 15:28
 */
@Service
public class VoteServiceImpl implements VoteService {
    Logger logger = Logger.getLogger(VoteServiceImpl.class);

    @Autowired
    VoteInfoMapper voteInfoMapper;
    @Autowired
    UserInfoMapper userInfoMapper;

    @Override
    public MessageData voteList(String userId) {
        List<VoteInfo> list = voteInfoMapper.getVoteList(null);
//        map.put("list", list);
        List<Map<String, Object>> resList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            VoteInfo item = list.get(i);
            //查询下是否投过票，待优化
            List<UserVoteInfo> userVote = voteInfoMapper.isUserVote(item.getSubjectId(), userId);
            Map<String, Object> map = new HashMap<>();
            map.put("subject", item.getSubject());
            map.put("subjectId", item.getSubjectId());
            map.put("type", item.getType());
            map.put("expiryDate", item.getExpiryDate());
            map.put("createDate", item.getCreateDate());
            map.put("sumeUser", item.getSumUser());
            map.put("sumVote", item.getSumVote());
            logger.info("userVote----->" + userVote);
            map.put("hasVoted", (userVote != null && userVote.size() > 0) ? true : false);
//            map.put("voteId", (userVote != null && userVote.size() > 0) ?  userVote.get(0).getOptionId() : -1);
            List<VoteOptionsInfo> voteOptionsList = voteInfoMapper.getVoteOptionsList(item.getSubjectId());
            for (VoteOptionsInfo voteOptionsInfo : voteOptionsList) {
                voteOptionsInfo.setPercent(item.getSumVote() == 0 ? "0%" : voteOptionsInfo.getVoteCount() * 100 / item.getSumVote() + "%");
                //判断用户是否已经选择
                voteOptionsInfo.setHasVoted(hasVoted(voteOptionsInfo.getId(), userVote));
            }
            map.put("optionList", voteOptionsList);
            resList.add(map);
        }
        return MessageData.createSuccessMsg("查询成功", resList);
    }

    //是否已投票
    private boolean hasVoted(Long voteId, List<UserVoteInfo> userVote) {
        if (userVote != null && userVote.size() > 0) {
            for (UserVoteInfo userVoteInfo : userVote) {
                if (userVoteInfo.getOptionId() == voteId)
                    return true;
            }
            return false;
        } else return false;
    }

    //投票
    @Override
    public MessageData vote(String userId, String subjectId, List<Long> optionIds) {
        List<UserInfo> users = userInfoMapper.getUserInfoById(userId);
        if (users == null || users.size() == 0)
            return new MessageData(ErrorMap.getErrorStr(Constant.CODE_USER_NOT_EXIST), Constant.CODE_USER_NOT_EXIST);
        List<VoteInfo> voteList = voteInfoMapper.getVoteList(subjectId);
        if (voteList == null || voteList.size() == 0)
            return new MessageData(ErrorMap.getErrorStr(Constant.CODE_VOTE_INFO_NULL), Constant.CODE_VOTE_INFO_NULL);
        VoteInfo voteInfo = voteList.get(0);
        //判断
        Date expiryDate = voteInfo.getExpiryDate();
        if (expiryDate == null)
            return new MessageData(ErrorMap.getErrorStr(Constant.CODE_VOTE_EXPRIORY_ERR), Constant.CODE_VOTE_ABORT);

        if (expiryDate.before(new Date()))
            return new MessageData(ErrorMap.getErrorStr(Constant.CODE_VOTE_ABORT), Constant.CODE_VOTE_ABORT);

        int type = voteInfo.getType();
        if (type == VoteInfo.TYPE_SINGLE && optionIds.size() > 1) {//单选校验
            return new MessageData(ErrorMap.getErrorStr(Constant.CODE_VOTE_SINGLE_ONLY), Constant.CODE_VOTE_SINGLE_ONLY);
        }
        List<VoteOptionsInfo> voteOptionsList = voteInfoMapper.getVoteOptionsList(subjectId);
        //所有选项
        for (Long item : optionIds) {
            if (!checkIn(item, voteOptionsList))
                return new MessageData(ErrorMap.getErrorStr(Constant.CODE_VOTE_OPT_NOT_EXIT), Constant.CODE_VOTE_OPT_NOT_EXIT);
        }
        //最后还得校验一次用户是否已经投过票
        List<UserVoteInfo> userVoteOptions = voteInfoMapper.isUserVote(subjectId, userId);
        if (userVoteOptions != null && userVoteOptions.size() > 0) {
            return new MessageData(ErrorMap.getErrorStr(Constant.CODE_VOTE_ALEARDY), Constant.CODE_VOTE_ALEARDY);
        }
        //校验投票时间


        //开始插入数据
        int res = voteInfoMapper.vote(userId, subjectId, optionIds, new Date());
        if (res > 0) {
            //插入完成后，手动将对应的数量更新/比例
            voteInfoMapper.updateOptionCountPlus1(subjectId, optionIds);
            //同时更新总数(人数+1，票数+n)
            voteInfoMapper.updateVoteCount(subjectId, optionIds.size());
            return MessageData.createSuccessMsg("投票成功", null);
        }
        return new MessageData("投票失败", Constant.CODE_ERROR);
    }

    private boolean checkIn(Long target, List<VoteOptionsInfo> voteOptionsList) {
        for (VoteOptionsInfo item : voteOptionsList) {
            if (item.getId() == target) return true;
        }
        return false;
    }

    @Override
    public MessageData create(VoteParam voteParam) {
        String subjectId = UUID.randomUUID().toString().replaceAll("-", "");
        logger.info("VOTE---->subjectId=" + subjectId);
        voteParam.setSubjectId(subjectId);
        //转化下日期格式
        Date date = DateUtils.parse(voteParam.getExpiryDate());
        VoteInfo voteInfo = new VoteInfo();
        voteInfo.setSubject(voteParam.getSubject());
        voteInfo.setExpiryDate(date);
        voteInfo.setSubjectId(subjectId);
        voteInfo.setCreateDate(new Date());
        voteInfo.setType(voteParam.getType());
        voteInfo.setSumUser(0);
        voteInfo.setSumVote(0);
        int createSubRe = voteInfoMapper.createSub(voteInfo);
        if (createSubRe > 0) {
            //继续创建选项

            List<VoteOptionsInfo> voteInfos = new ArrayList<>();
            //选项
            for (int i = 0; i < voteParam.getOption().size(); i++) {
                VoteOptionsInfo voteOptionsInfo = new VoteOptionsInfo();
                voteOptionsInfo.setCreateDate(new Date());
                voteOptionsInfo.setOptionStr(voteParam.getOption().get(i));
                voteOptionsInfo.setOptionIndex(i);
                voteOptionsInfo.setSubjectId(subjectId);
                voteOptionsInfo.setVoteCount(0);
                voteInfos.add(voteOptionsInfo);
            }
            int res = voteInfoMapper.createOptions(voteInfos);
            if (res > 0) {
                Map<String, Object> resMap = new HashMap<>();
                resMap.put("subjectId", subjectId);
                return new MessageData("创建成功", Constant.CODE_SUCCESS, resMap);
            }
        }
        return new MessageData(ErrorMap.getErrorStr(Constant.CODE_VOTE_CREATE_ERROR), Constant.CODE_VOTE_CREATE_ERROR, null);
    }

    @Override
    public MessageData delete(String subjectId) {
        //先查，查完再删
        List<VoteInfo> list = voteInfoMapper.getVoteList(subjectId);
        if (list != null && list.size() > 0) {//有数据，直接删除
            //删除
            int voteDelRes = voteInfoMapper.deleteVote(subjectId);
            logger.info("vote del---->voteDelRes=" + voteDelRes);
            voteInfoMapper.deleteOptions(subjectId);
            voteInfoMapper.deleteVoteUser(subjectId);
            return new MessageData("删除成功", Constant.CODE_SUCCESS);
        } else
            return new MessageData(ErrorMap.getErrorStr(Constant.CODE_VOTE_DELETE_NULL), Constant.CODE_VOTE_DELETE_NULL);
    }

    //直接查询每个选项有多人选
    @Override
    public MessageData detail(String subjectId, String userId) {
        //先查，查完再删
        List<VoteInfo> list = voteInfoMapper.getVoteList(subjectId);
        if (list != null && list.size() > 0) {//有数据
            VoteInfo voteInfo = list.get(0);
            List<Map<String, Object>> resList = new ArrayList<>();

            Map<String, Object> map = new HashMap<>();
            map.put("subject", voteInfo.getSubject());
            map.put("subjectId", voteInfo.getSubjectId());
            map.put("type", voteInfo.getType());
            map.put("expiryDate", voteInfo.getExpiryDate());
            map.put("createDate", voteInfo.getCreateDate());
            map.put("sumeUser", voteInfo.getSumUser());
            map.put("sumVote", voteInfo.getSumVote());
            //查询下是否投过票，待优化
            List<UserVoteInfo> userVote = voteInfoMapper.isUserVote(subjectId, userId);
            map.put("hasVoted", (userVote != null && userVote.size() > 0) ? true : false);
            //查询选项
            List<VoteOptionsInfo> voteOptionsList = voteInfoMapper.getVoteOptionsList(voteInfo.getSubjectId());
            for (VoteOptionsInfo voteOptionsInfo : voteOptionsList) {
                //防止有0的情况
                voteOptionsInfo.setPercent(voteInfo.getSumVote() == 0 ? "0%" : voteOptionsInfo.getVoteCount() * 100 / voteInfo.getSumVote() + "%");
                //======添加投票人信息=================
                List<UserVoteInfo> userVoteOptions = voteInfoMapper.getUserVoteOptionsDetailsByOpt(subjectId, voteOptionsInfo.getId());
                voteOptionsInfo.setUserVotes(userVoteOptions);
                //判断用户是否已经选择
                voteOptionsInfo.setHasVoted(hasVoted(voteOptionsInfo.getId(), userVote));
                //=====================================
            }

            map.put("optionList", voteOptionsList);
            resList.add(map);
            return new MessageData(Constant.MSG_SUCCESS, Constant.CODE_SUCCESS, map);
        } else
            return new MessageData(ErrorMap.getErrorStr(Constant.CODE_VOTE_DELETE_NULL), Constant.CODE_VOTE_DELETE_NULL);

    }

    @Override
    public MessageData update(VoteParam voteParam) {
        return null;
    }

    @Override
    public MessageData checkSource(SectionSourceParam sectionSourceParam) {
        return null;
    }
}
