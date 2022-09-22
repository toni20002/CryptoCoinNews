package com.cryptocoinnews.email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailSenderService {
    public static final String UTF_8 = "UTF-8";
    private final JavaMailSender javaMailSender;


    public boolean sendEmailWithAttachment(String to, String from, String subject, String content,
                                           String attachmentFileName, String filePath, String fileExtension) throws MessagingException {
        MimeMessage mimeMessage = createMimeMessage(to, from, subject, content);
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, UTF_8);
        mimeMessageHelper.setText(content, true);

        File file = new File(filePath);
        mimeMessageHelper.addAttachment(attachmentFileName + fileExtension, file);
        return validateAndSend(to, mimeMessage);
    }

    private boolean validateAndSend(String to, MimeMessage mimeMessage) {
        try {
            this.javaMailSender.send(mimeMessage);
            log.info("Email sent to " + to);
            return true;
        } catch (MailSendException e) {
            log.error("Error when sending email to {}", to, e);
        }
        return false;
    }

    private MimeMessage createMimeMessage(String to, String from, String subject, String content) throws MessagingException {
        MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
        mimeMessage.setFrom(from);
        mimeMessage.setRecipient(Message.RecipientType.TO, InternetAddress.parse(to)[0]);
        mimeMessage.setSubject(subject);
        mimeMessage.setText(content);

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, UTF_8);
        mimeMessageHelper.setText(content, true);
        return mimeMessage;
    }
}
