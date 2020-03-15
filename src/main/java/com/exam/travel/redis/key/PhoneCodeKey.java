package com.exam.travel.redis.key;

import com.exam.travel.redis.BasePrefix;

/**
 * 手机验证码 存在redis里面
 * @Author w1586
 * @Date 2020/3/15 1:06
 * @Cersion 1.0
 */
public class PhoneCodeKey extends BasePrefix {

    public PhoneCodeKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public PhoneCodeKey(String prefix) {
        super(prefix);
    }

    /**
     *  短信验证码默认是五分钟有效
      */
    public static PhoneCodeKey phoneCode = new PhoneCodeKey(300,"phoneCode");

}
