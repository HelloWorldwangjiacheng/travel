package com.exam.travel.utils;

import java.util.Random;

/**
 * @Author w1586
 * @Date 2020/3/17 2:05
 * @Cersion 1.0
 */

public class RandomUtil {
    /**
     * 会生成一个大于1000的随机数，
     * 用code=(int)(Math.random()*9999)+10000;得到的code会有除0的情况
     */
    public static int getPhoneCode(Integer num){

        int i = new Random().nextInt(num) + 1000;

        return i;
    }
}
