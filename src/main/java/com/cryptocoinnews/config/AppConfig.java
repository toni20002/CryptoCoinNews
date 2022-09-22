package com.cryptocoinnews.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class AppConfig {

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl impl = new JavaMailSenderImpl();
        impl.setHost("smtp.mailtrap.io");
        impl.setPort(587);
        impl.setUsername("c7cf7e016a45ca");
        impl.setPassword("08690a1276adb5");
        return impl;
    }
}
