package lev.philippov.originmvc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({MethodSecurityConfig.class,RabbitMQConfig.class,SecurityConfig.class,WebMvcConfig.class, MailConfig.class,OAuth2ClientConfig.class})
public class AppConfig {}
