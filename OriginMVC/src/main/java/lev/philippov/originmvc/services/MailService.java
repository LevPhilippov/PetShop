package lev.philippov.originmvc.services;

import lev.philippov.originmvc.models.Order;
import lev.philippov.originmvc.models.PrivateDetails;
import lev.philippov.originmvc.utils.MailBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@Service
public class MailService {

    private JavaMailSender javaMailSender;
    private MailBuilder mailBuilder;

    @Autowired
    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Autowired
    public void setMailBuilder(MailBuilder mailBuilder) {
        this.mailBuilder = mailBuilder;
    }

    public MailBuilder getMailBuilder() {
        return mailBuilder;
    }

    public void sendOrderMessage(String email, String subject, String text) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom("develop.filippov.l.a.89@mail.ru");
        helper.setTo(email);
        helper.setText(text);
        helper.setSubject(subject);
        javaMailSender.send(mimeMessage);
    }
}
