package com.aqua.rbaccommon.common;

//import com.aliyuncs.CommonRequest;
//import com.aliyuncs.CommonResponse;
//import com.aliyuncs.DefaultAcsClient;
//import com.aliyuncs.IAcsClient;
//import com.aliyuncs.exceptions.ClientException;
//import com.aliyuncs.http.MethodType;
//import com.aliyuncs.profile.DefaultProfile;

/**
 * @author water king
 * @time 2024/2/10
 */
public class SMSUtils {

//    public void sendSms(String phoneNumber) {
//        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI5tPzBcaD3QkNQ4d9rg", "RcMmZK1ZkwCg7p3fWHuGJIUSlQaP");
//        IAcsClient client = new DefaultAcsClient(profile);
//        CommonRequest request = new CommonRequest();
//        request.setMethod(MethodType.POST);
//        request.setDomain("dysmsapi.aliyuncs.com");
//        request.setVersion("2017-05-25");
//        request.setAction("SendSms");
//        request.putQueryParameter("PhoneNumbers", phoneNumber);
//        request.putQueryParameter("SignName", "小白杨");
//        request.putQueryParameter("TemplateCode", "SMS_273765650");
//        String code = String.valueOf(Math.round(Math.random() * 899999 + 100000));
//        request.putQueryParameter("TemplateParam", "{\"code\":\"" + code + "\"}");
//        try {
//            CommonResponse response = client.getCommonResponse(request);
//            System.out.println("短信发送成功" + code);
//
//            redisTemplate.opsForValue().set(phoneNumber, code, 5, TimeUnit.MINUTES);
//        } catch (ClientException e) {
//            throw new RuntimeException("短信发送失败");
//        }
//
//    }
}
