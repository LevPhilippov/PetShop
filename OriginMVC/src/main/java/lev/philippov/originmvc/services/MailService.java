package lev.philippov.originmvc.services;

import lev.philippov.originmvc.utils.MailBuilder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;


@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;
    @Getter
    private final MailBuilder mailBuilder;

    @Value("${mail.username}")
    private String EMAIL_SENDER_URI;

    @Async
    public void sendOrderMessage(String email, String subject, String text) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(EMAIL_SENDER_URI);
        helper.setTo(email);
        helper.setText(text);
        helper.setSubject(subject);
        javaMailSender.send(mimeMessage);
    }
}
