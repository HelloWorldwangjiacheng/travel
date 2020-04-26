package com.exam.travel.redis.key;

import com.exam.travel.redis.BasePrefix;

/**
 * @Author w1586
 * @Date 2020/3/3 19:39
 * @Cersion 1.0
 */

public class UserKey extends BasePrefix {

    public static final int TOKEN_EXPIRE = 3600;

    public UserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static UserKey token = new UserKey(TOKEN_EXPIRE,"token");

    /**
     * 永久有效
     */
    public static UserKey getById = new UserKey(0,"userId");

}
