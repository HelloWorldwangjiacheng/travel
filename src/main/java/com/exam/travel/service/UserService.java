package com.exam.travel.service;

import com.exam.travel.dto.LoginDTO;
import com.exam.travel.dto.PhoneTextDTO;
import com.exam.travel.exception.GlobalException;
import com.exam.travel.mapper.UserMapper;
import com.exam.travel.model.User;
import com.exam.travel.model.UserExample;
import com.exam.travel.redis.RedisService;
import com.exam.travel.redis.key.PhoneCodeKey;
import com.exam.travel.redis.key.UserKey;
import com.exam.travel.result.CodeMsg;
import com.exam.travel.result.Result;
import com.exam.travel.utils.SendSmsUtil;
import com.exam.travel.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author w1586
 * @Date 2020/3/15 12:37
 * @Cersion 1.0
 */
@Service
public class UserService{

    public static final String COOKIE_NAME_TOKEN = "token";

    private static final Integer PHONE_NUMBER_LENGTH = 11;

    private static final String VALID_PHONE_NUMBER_FIRST = "1";

    @Autowired
    UserMapper userMapper;

    @Autowired
    RedisService redisService;

    public String login(HttpServletResponse response,
                        HttpServletRequest request,
                        LoginDTO loginDTO)
    {
        System.out.println(loginDTO.toString());
        if (loginDTO == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }

        @NotNull String accountId = loginDTO.getAccountId();
        @NotNull String submitPassword = loginDTO.getUserPassword();

        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(accountId);
        List<User> users = userMapper.selectByExample(userExample);

        if (users.size()<=0){
            return "0";
        }else if (users.size() == 1){

            User user = users.get(0);
            //验证密码
            String dbPass = user.getUserPassword();
            if (!dbPass.equals(submitPassword)){
                throw new GlobalException(CodeMsg.PASSWORD_ERROR);
            }

            // 生成cookie
            String token = UUIDUtil.uuid();
            addCookie(response, token, user);
            request.getSession().setAttribute("user",user);
            return token;
        }else{
            return "1";
        }

    }

    @Transactional(rollbackFor = Exception.class)
    public String register(LoginDTO loginDTO){

        @NotNull String accountId = loginDTO.getAccountId();
        String phoneCode = loginDTO.getPhoneCode();

        String s = redisService.get(PhoneCodeKey.phoneCode,
                "" + accountId,
                String.class);
        if (s == null || s.length()==0){
            return CodeMsg.PHONE_CODE_IS_NOT_EXIST.getMsg();
//            throw new GlobalException(CodeMsg.PHONE_CODE_IS_NOT_EXIST);
        }
        if (!phoneCode.equals(s)){
            return CodeMsg.PHONE_CODE_ERROR.getMsg();
//            throw new GlobalException(CodeMsg.PHONE_CODE_ERROR);
        }
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(accountId);
        List<User> users = userMapper.selectByExample(userExample);
        if (users.size()!=0){
            return CodeMsg.REGISTER_ACCOUNT_ID_READY_EXIST.getMsg();
//            throw new GlobalException(CodeMsg.REGISTER_ACCOUNT_ID_READY_EXIST);
        }
        String userPassword = loginDTO.getUserPassword();
        if (userPassword == null){
            return CodeMsg.PASSWORD_EMPTY.getMsg();
//            throw new GlobalException(CodeMsg.PASSWORD_EMPTY);
        }

        String userName = loginDTO.getUserName();
        if (userName == null) {
            userName="未命名用户";
        }
        User user = new User();
        user.setUserName(userName);
        user.setAccountId(accountId);
        user.setGmtCreate(System.currentTimeMillis());
        user.setUserPassword(userPassword);
        userMapper.insert(user);
        redisService.delete(PhoneCodeKey.phoneCode, ""+accountId);
        return "success";



    }

    @Transactional(rollbackFor = Exception.class)
    public void sendPhoneCode(String phoneNum){
        // 首先对手机号进行检测
        if (phoneNum==null){
            throw new GlobalException(CodeMsg.MOBILE_EMPTY);
        }
        if (phoneNum.length()!=PHONE_NUMBER_LENGTH ||
                !VALID_PHONE_NUMBER_FIRST.equals(phoneNum.charAt(0) + "")){
            throw new GlobalException(CodeMsg.MOBILE_ERROR);
        }


        SendSmsUtil sendSmsUtil = new SendSmsUtil();
        Result<PhoneTextDTO> phoneTextResult = sendSmsUtil.sendPhoneText(phoneNum);
        if (!phoneTextResult.getData().getSuccess()){
            throw new RuntimeException(phoneTextResult.getData().getErrorMsg());
        }

        boolean set = redisService.set(PhoneCodeKey.phoneCode,
                                "" + phoneNum,
                                phoneTextResult.getData().getPhoneCode() + "");

        if (!set){
            throw new GlobalException(CodeMsg.REDIS_SET_ERROR);
        }
    }

    public void createOrUpdate(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(userExample);
        if (users.size() <=0) {
            // 这样的就是注册用户
            user.setGmtCreate(System.currentTimeMillis());
            userMapper.insert(user);
        }else if (users.size() == 1){
            // 登录 并进行更新
            User dbUser = users.get(0);
            User updateUser = new User();
            updateUser.setAvatarUrl(user.getAvatarUrl());
            updateUser.setToken(user.getToken());
            updateUser.setUserName(user.getUserName());

            UserExample example = new UserExample();
            example.createCriteria().andIdEqualTo(dbUser.getId());
            userMapper.updateByExampleSelective(updateUser, example);
        }else{
            throw new GlobalException(CodeMsg.HAVE_MANY_USER_ERROR);
        }

    }

    public User getById(Long id) {
        return null;
    }




    public User getByToken(HttpServletResponse response, String token) {
        //参数验证
        if (StringUtils.isEmpty(token)){
            return null;
        }
        User user = redisService.get(UserKey.token, token, User.class);
        // 延长有效期
        if (user!=null){
            addCookie(response, token, user);
        }
        return user;
    }


    public Boolean updatePassword(String token, Long id, String passwordNew) {
//        // 取User对象
//        User user = getById(id);
//        if (user == null){
//            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
//        }
//
//        // 更新数据库
//        User toBeUpdateUser = new User();
//        toBeUpdateUser.setId(id);
//        toBeUpdateUser.setPassword(MD5Util.formPassToDBPass(formPass, user.getSalt()));
//        userDao.update(toBeUpdateUser);
//
//        // 处理缓存
//        redisService.delete(UserKey.getById, ""+id);
//        user.setPassword(toBeUpdateUser.getPassword());
//        //这里不能删除，因为删除你就无法登录了，只能进行更新
//        redisService.set(UserKey.token, token, user);
        return true;
    }

    /**
     * 添加cookie
     * @param response
     * @param token
     * @param user
     */
    private void addCookie(HttpServletResponse response, String token, User user) {
        redisService.set(UserKey.token, token, user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(UserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
