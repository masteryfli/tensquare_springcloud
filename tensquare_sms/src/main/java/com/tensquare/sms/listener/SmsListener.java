package com.tensquare.sms.listener;

import com.aliyuncs.exceptions.ClientException;
import com.tensquare.sms.utils.SmsUtils;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queues = "sms")
public class SmsListener {

    @Autowired
    private SmsUtils smsUtils;

    @Value("${aliyun.sms.template_code}")
    private String template_code;                   //模板编号

    @Value("${aliyun.sms.sign_name}")
    private String sign_name;                       //签名

    /**
     * 发送短信
     */
    @RabbitHandler
    public void sendSms(Map<String, String> map) {
        System.out.println("手机号：" + map.get("mobile"));
        System.out.println("验证码：" + map.get("captcha"));
        try {
            smsUtils.sendSms(map.get("mobile"), template_code, sign_name, " {\"code\":\""+ map.get("captcha") +"\"}");
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
