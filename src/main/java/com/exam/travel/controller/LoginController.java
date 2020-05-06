package com.exam.travel.controller;

import com.exam.travel.dto.LoginDTO;
import com.exam.travel.result.CodeMsg;
import com.exam.travel.result.Result;
import com.exam.travel.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @Author w1586
 * @Date 2020/3/15 15:21
 * @Cersion 1.0
 */
@Controller
public class LoginController {

    private static String THERE_IS_NO_USER = "0";
    private static String THERE_HAVE_MANY_USERS = "1";
    private static String REGISTER_SUCCESS = "success";
    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    UserService userService;

    @GetMapping("/login/register")
    public String register(){
        return "register";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }


    @ResponseBody
    @RequestMapping(value = "/login/getPhoneCode", method = RequestMethod.POST)
    public Result<Boolean> getPhoneCode(@Valid LoginDTO loginDTO,
                                  HttpServletRequest request,
                                  HttpServletResponse response)
    {
        logger.info(loginDTO.toString());
        // 这里的accountId账号就是手机号phoneNum
        userService.sendPhoneCode(loginDTO.getAccountId());

        return Result.success(true);
    }

    @ResponseBody
    @RequestMapping(value = "/login/do_register", method = RequestMethod.POST)
    public Result<String> doRegister(@Valid LoginDTO loginDTO,
                                     HttpServletRequest request,
                                     HttpServletResponse response)
    {
        logger.info(loginDTO.toString());
        String register = userService.register(loginDTO);
        if (REGISTER_SUCCESS.equals(register)){
            return Result.success("success");
        }else {
            if (register.equals(CodeMsg.PHONE_CODE_IS_NOT_EXIST.getMsg())){
                return Result.error(CodeMsg.PHONE_CODE_IS_NOT_EXIST);
            }else if (register.equals(CodeMsg.PHONE_CODE_ERROR.getMsg())){
                return Result.error(CodeMsg.PHONE_CODE_ERROR);
            }else if (register.equals(CodeMsg.REGISTER_ACCOUNT_ID_READY_EXIST.getMsg())){
                return Result.error(CodeMsg.REGISTER_ACCOUNT_ID_READY_EXIST);
            }else if (register.equals(CodeMsg.PASSWORD_EMPTY.getMsg())){
                return Result.error(CodeMsg.PASSWORD_EMPTY);
            }else if (register.equals(CodeMsg.PASSWORD_ERROR.getMsg())){
                return Result.error(CodeMsg.PASSWORD_ERROR);
            }else {
                return Result.error(CodeMsg.SERVER_ERROR);
            }
        }

    }



    @ResponseBody
    @RequestMapping(value = "/login/do_login", method = RequestMethod.POST)
    public Result<String> doLogin(@RequestBody LoginDTO loginDTO,
                                  HttpServletRequest request,
                                  HttpServletResponse response)
    {
        logger.info(loginDTO.toString());

        String token = userService.login(response, request,loginDTO);

        if (token.equals(THERE_IS_NO_USER)){
            return Result.error(CodeMsg.USER_IS_NOT_EXISTED);
        }else if (token.equals(THERE_HAVE_MANY_USERS)){
            return Result.error(CodeMsg.HAVE_MANY_USER_ERROR);
        }else {
            return Result.success(token);
        }

    }
}
