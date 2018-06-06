package com.jarry.chat.mapper;

import com.jarry.chat.model.response.UserVote;
import com.jarry.chat.model.response.UserVoteInfo;
import com.jarry.chat.model.response.VoteInfo;
import com.jarry.chat.model.response.VoteOptionsInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Description:
 * User: Jarry
 * Date: 2018-01-28
 * Time: 22:04
 */
@Mapper
public interface VoteInfoMapper {
    int createSub(@Param("voteInfo") VoteInfo voteInfo);

    int createOptions(@Param("voteInfo") List<VoteOptionsInfo> voteInfos);

    List<VoteInfo> getVoteList(@Param("subjectId") String subjectId);

    List<VoteInfo> getVoteListByUserId(@Param("userId") String userId);

    List<VoteInfo> getVoteDetail(@Param("subjectId") String subjectId);

    List<VoteOptionsInfo> getVoteOptionsList(@Param("subjectId") String subjectId);

    //获取投票
    List<UserVoteInfo> getUserVoteOptions(@Param("subjectId") String subjectId, @Param("optionId") Long optionId);

    List<UserVoteInfo> isUserVote(@Param("subjectId") String subjectId, @Param("userId") String userId);

    //连表查询，详情
    List<UserVoteInfo> getUserVoteOptionsDetailsByOpt(@Param("subjectId") String subjectId, @Param("optionId") Long optionId);

    int deleteVote(@Param("subjectId") String subjectId);

    int deleteOptions(@Param("subjectId") String subjectId);

    int deleteVoteUser(@Param("subjectId") String subjectId);

    int vote(@Param("userId") String userId, @Param("subjectId") String subjectId, @Param("optionIds") List<Long> optionIds, @Param("createDate") Date createDate);

    int updateOptionCountPlus1(@Param("subjectId") String subjectId, @Param("optionId") List<Long> optionId);

    int updateVoteCount(@Param("subjectId") String subjectId, @Param("addVoteCount") int addVoteCount);

//    List<UserInfo> login(@Param("userName") String userName, @Param("password") String password);
//
//    List<UserInfo> checkAccountByUserName(@Param("userName") String userName);
//
//    int register(@Param("userInfo") UserInfo userInfo);
//
//    List<UserInfo> getUserInfoById(@Param("userId") String userId);
//
//    List<UserInfo> getUserInfoByPhone(@Param("phone") String phone);
}
