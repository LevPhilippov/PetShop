package lev.philippov.originmvc.utils;

import lev.philippov.originmvc.models.Order;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

public class MailBuilder {
    private TemplateEngine templateEngine;

    public void setTemplateEngine(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String buildOrderEmail(Order order) {
        Context context = new Context();
        context.setVariable("order", order);
        return templateEngine.process("mail/order-mail", context);
    }

}
