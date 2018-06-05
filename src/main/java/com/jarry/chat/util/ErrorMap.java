package com.jarry.chat.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 * User: Jarry
 * Date: 2018-03-08
 * Time: 22:50
 */
public class ErrorMap {
    static Map<String, String> errorMap = new HashMap<>();

    static {
        errorMap.put(Constant.CODE_ERROR, Constant.MSG_ERROR);
        errorMap.put(Constant.CODE_REGISTER_USER_NAME_NULL, "手机号为空");
        errorMap.put(Constant.CODE_LOGIN_ERROR_PWD_NULL, "密码为空");
        errorMap.put(Constant.CODE_USER_INFO_NULL, "用户信息入参为空");
        errorMap.put(Constant.CODE_USER_NOT_EXIST, "用户不存在");
        errorMap.put(Constant.CODE_REGISTER_USER_NAME_NULL, "用户为空");
        errorMap.put(Constant.CODE_VOTE_SUBJECT_NULL, "题目为空");
        errorMap.put(Constant.CODE_VOTE_OPTION_NULL, "选项为空");
        errorMap.put(Constant.CODE_VOTE_OPTION_CONTAINS_NULL, "选项有空值");
        errorMap.put(Constant.CODE_VOTE_EXPIRY_DATE_NULL, "失效日期为空");
        errorMap.put(Constant.CODE_VOTE_CREATE_ERROR, "创建投票失败");
        errorMap.put(Constant.CODE_VOTE_DELETE_NULL, "暂无投票信息");
        errorMap.put(Constant.CODE_VOTE_USERID_NULL, "用户信息为空");
        errorMap.put(Constant.CODE_VOTE_INFO_NULL, "投票信息为空");
        errorMap.put(Constant.CODE_VOTE_SINGLE_ONLY, "只能单选");
        errorMap.put(Constant.CODE_VOTE_NOT_SELECT, "请先选择选项");
        errorMap.put(Constant.CODE_VOTE_OPT_NOT_EXIT, "没有指定选项");
        errorMap.put(Constant.CODE_VOTE_ALEARDY, "该用户已投过票");
        errorMap.put(Constant.CODE_VOTE_ABORT, "已超过投票期限");
    }

    public static String getErrorStr(String code) {
        return errorMap.get(code);
    }
}
