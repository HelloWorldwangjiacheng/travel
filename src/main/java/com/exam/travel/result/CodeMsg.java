package com.exam.travel.result;

import lombok.Data;

/**
 * @Author w1586
 * @Date 2020/3/2 21:00
 * @Cersion 1.0
 */
@Data
public class CodeMsg {

    private int code;
    private String msg;

    public CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public CodeMsg(){}

    @Override
    public String toString() {
        return "CodeMsg [code=" + code + ", msg=" + msg + "]";
    }

    public CodeMsg fillArgs(Object...args){
        int code = this.code;
        String message = String.format(this.msg, args);
        return new CodeMsg(code, message);
    }

    /**
     * 通用异常，
     */
    public static CodeMsg SUCCESS = new CodeMsg(0,"success");
    public static CodeMsg SERVER_ERROR = new CodeMsg(500100,"服务端异常, 服务冒烟了，你要不稍后再试试");
    public static CodeMsg BIND_ERROR = new CodeMsg(500101,"参数校验异常：%s");
    public static CodeMsg REQUEST_ILLEGAL = new CodeMsg(500102, "请求非法");
    public static CodeMsg ACCESS_LIMIT_REACHED= new CodeMsg(500104, "访问太频繁！");

    /**
     * 登录注册模块 5002XX
     */
    public static CodeMsg SESSION_ERROR = new CodeMsg(500201,"Session不存在或者已经失效");
    public static CodeMsg PASSWORD_EMPTY = new CodeMsg(500202,"密码不能为空");
    public static CodeMsg MOBILE_EMPTY = new CodeMsg(500203,"手机号不能为空");
    public static CodeMsg MOBILE_ERROR = new CodeMsg(500204,"手机号格式错误");
    public static CodeMsg MOBILE_NOT_EXIST = new CodeMsg(500205,"手机号不存在");
    public static CodeMsg PASSWORD_ERROR = new CodeMsg(500206,"密码错误");
    public static CodeMsg NO_LOGIN = new CodeMsg(500207,"当前操作需要登录,请登录后重试");
    public static CodeMsg GET_PHONE_CODE_ERROR = new CodeMsg(500208,"执行getPhoneCode方法错误");
    public static CodeMsg SEND_PHONE_CODE_SERVER_ERROR = new CodeMsg(500209,"发送短信失败,服务端问题");
    public static CodeMsg SEND_PHONE_CODE_CLIENT_ERROR = new CodeMsg(500210,"发送短信失败,客户端问题");
    public static CodeMsg HAVE_MANY_USER_ERROR = new CodeMsg(500211,"一个账号出现多个用户，数据库有脏数据");
    public static CodeMsg USER_IS_NOT_EXISTED = new CodeMsg(500212,"用户不存在,找不到对应的用户");
    public static CodeMsg LOGIN_DTO_MESSAGE_IS_NOT_EXIST = new CodeMsg(500213,"传输信息不存在，loginDTO为null");
    public static CodeMsg REGISTER_ACCOUNT_ID_READY_EXIST = new CodeMsg(500214,"该账号已经存在，不能注册");
    public static CodeMsg PHONE_CODE_ERROR = new CodeMsg(500215,"该手机号短信验证码错误");
    public static CodeMsg PHONE_CODE_IS_NOT_EXIST = new CodeMsg(500216,"该手机号短信验证码不存在");
    /**
     * 显示模块 5003XX
     */
    public static CodeMsg QUESTION_NOT_FOUND = new CodeMsg(500301,"你找到的问题不在了，要不要换一个试试？");


    /**
     * 提问模块 5004XX
     */


    /**
     * 评论和评论模块 5005XX
     */
    public static CodeMsg COMMENT_TYPE_PARAM_WRONG = new CodeMsg(500501,"评论类型错误或不存在");
    public static CodeMsg TARGET_PARAM_NOT_FOUND = new CodeMsg(500502,"未选中任何问题或评论进行回复");
    public static CodeMsg COMMENT_NOT_FOUND = new CodeMsg(500503,"回复的评论不存在了，要不换个试试");
    public static CodeMsg COMMENT_IS_EMPTY = new CodeMsg(500504,"输入内容不能为空");
    public static CodeMsg READ_NOTIFICATION_FAIL = new CodeMsg(500505,"兄弟你这是读别人的信息呢？");
    public static CodeMsg NOTIFICATION_NOT_FOUND = new CodeMsg(500506,"消息莫非是不翼而飞了？");

    /**
     * 商品模块 5006XX
     */



    /**
     * 订单模块，5007XX
     */
    public static CodeMsg ORDER_NOT_EXIST = new CodeMsg(500400, "订单不存在");

    /**
     * 秒杀模块，5008XX
     */
    public static CodeMsg MIAO_SHA_OVER = new CodeMsg(500500,"商品秒杀完毕");
    public static CodeMsg REPEAT_MIAOSHA = new CodeMsg(500501,"商品不能重复秒杀");
    public static CodeMsg MIAOSHA_FAIL = new CodeMsg(500502, "秒杀失败");

    /**
     * 富文本、mysql，redis数据库模块 5009XX
     */
    public static CodeMsg FILE_UPLOAD_FAIL = new CodeMsg(500901, "图片上传失败");
    public static CodeMsg REDIS_SET_ERROR = new CodeMsg(500902, "redis存储set失败");
}
