package com.exam.travel.utils;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.exam.travel.dto.PhoneTextDTO;
import com.exam.travel.result.CodeMsg;
import com.exam.travel.result.Result;


/**
 * @Author w1586
 * @Date 2020/3/14 21:46
 * @Cersion 1.0
 */
public class SendSmsUtil {

//    @Value("${aliyun.sms.accessKeyId}")
//    public String accessKeyId = "LTAI4FuTCbae3XHfYRFn5dun";
//
//    @Value("${p5VaKznLqCHCkiQQO5R8Kxk0pNnL8K}")
//    public String accessSecret;


    public Result<PhoneTextDTO> sendPhoneText(String phoneNum){

        int phoneCode = RandomUtil.getPhoneCode(8999);

        PhoneTextDTO phoneTextDTO = new PhoneTextDTO();
        DefaultProfile profile = DefaultProfile.getProfile(
                "cn-hangzhou",
                "LTAI4FtTrxLjiaYnTGCoaTFD",
                "K04Uuddocinik9inZffYgN4SMqhEUm");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phoneNum);
        request.putQueryParameter("SignName", "码匠社区");
        request.putQueryParameter("TemplateCode", "SMS_182669487");
        request.putQueryParameter("TemplateParam", "{\"code\":"+phoneCode+"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            phoneTextDTO.setSuccess(true);
            phoneTextDTO.setPhoneCode(phoneCode+"");
            phoneTextDTO.setErrorMsg(null);
            return Result.success(phoneTextDTO);
        } catch (ServerException e) {
            e.printStackTrace();
            phoneTextDTO.setErrorMsg(CodeMsg.SEND_PHONE_CODE_SERVER_ERROR.getMsg());
            phoneTextDTO.setPhoneCode(null);
            phoneTextDTO.setSuccess(false);
            return Result.success(phoneTextDTO);
        } catch (ClientException e) {
            e.printStackTrace();
            phoneTextDTO.setErrorMsg(CodeMsg.SEND_PHONE_CODE_CLIENT_ERROR.getMsg());
            phoneTextDTO.setPhoneCode(null);
            phoneTextDTO.setSuccess(false);
            return Result.success(phoneTextDTO);
        }

    }


}
