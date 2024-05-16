package com.imooc.mall.service;

public interface EmailService {
    void sendSimpleMessage(String to, String subject, String text);

    boolean saveEmailToRedis(String emailAddress, String verificationCode);

    Boolean checkEmailAndCode(String emailAddress, String verificationCode);
}
