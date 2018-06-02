package com.jarry.chat.service.impl;

import com.jarry.chat.mapper.UserInfoMapper;
import com.jarry.chat.mapper.VoteInfoMapper;
import com.jarry.chat.model.MessageData;
import com.jarry.chat.model.request.SectionSourceParam;
import com.jarry.chat.model.request.VoteParam;
import com.jarry.chat.model.response.UserInfo;
import com.jarry.chat.model.response.VoteInfo;
import com.jarry.chat.model.response.VoteOptionsInfo;
import com.jarry.chat.service.VoteService;
import com.jarry.chat.util.Constant;
import com.jarry.chat.util.DateUtils;
import com.jarry.chat.util.ErrorMap;
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
    public MessageData voteList() {
        List<VoteInfo> list = voteInfoMapper.getVoteList(null);
//        map.put("list", list);
        List<Map<String, Object>> resList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            VoteInfo item = list.get(i);
            Map<String, Object> map = new HashMap<>();
            map.put("subject", item.getSubject());
            map.put("subjectId", item.getSubjectId());
            map.put("type", item.getType());
            map.put("expiryDate", item.getExpiryDate());
            map.put("createDate", item.getCreateDate());
            //查询选项
            List<VoteOptionsInfo> voteOptionsList = voteInfoMapper.getVoteOptionsList(item.getSubjectId());
            map.put("optionList", voteOptionsList);
            resList.add(map);
        }
        return MessageData.createSuccessMsg("查询成功", resList);
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
        //开始插入数据
        int res = voteInfoMapper.vote(userId, subjectId, optionIds, new Date());
        if (res > 0) {
            //插入完成后，手动将对应的数量更新
            voteInfoMapper.updateOptionCountPlus1(subjectId, optionIds);
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
            return new MessageData("删除成功", Constant.CODE_SUCCESS);
        } else
            return new MessageData(ErrorMap.getErrorStr(Constant.CODE_VOTE_DELETE_NULL), Constant.CODE_VOTE_DELETE_NULL);
    }

    //直接查询每个选项有多人选
    @Override
    public MessageData detail(String subjectId) {
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
            //查询选项
            List<VoteOptionsInfo> voteOptionsList = voteInfoMapper.getVoteOptionsList(voteInfo.getSubjectId());
//            for (VoteOptionsInfo voteOptionsInfo : voteOptionsList) {
//
//            }
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
