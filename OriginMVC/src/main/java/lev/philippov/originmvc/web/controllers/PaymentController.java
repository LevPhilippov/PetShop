package lev.philippov.originmvc.web.controllers;

import lev.philippov.originmvc.services.MailService;
import lev.philippov.originmvc.services.OrderService;
import lev.philippov.originmvc.utils.MailBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.mail.MessagingException;

@Controller
@RequestMapping("/payment")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    @Value("${paypal.clientId}")
    private String clientId;
    private final MailService mailService;
    private final OrderService orderService;

    @GetMapping("/{orderId}")
    public String orderConfirmation(@PathVariable(name = "orderId", required = false) Long orderId, Model model) throws MessagingException {
        lev.philippov.originmvc.domain.Order order = orderService.getOrderById(orderId);
        String confirmationEmail = order.getOrderDetails().getEmail();
        mailService.sendOrderMessage(confirmationEmail,String.format("Order #%d confirmation",orderId),mailService.getMailBuilder().buildOrderEmail(order));
        model.addAttribute("paypalClientId",clientId);
        model.addAttribute("order",order);
        return "order/order_success";
    }

    @GetMapping("/result/{orderId}")
    public String paymentResult(@PathVariable(name = "orderId") Long orderId, Model model) {
        return "payments/paypal-result";
    }
}
