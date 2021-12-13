package lev.philippov.originmvc.controllers;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import lev.philippov.originmvc.exceptions.ServerException;
import lev.philippov.originmvc.models.PrivateDetails;
import lev.philippov.originmvc.services.MailService;
import lev.philippov.originmvc.services.OrderService;
import lev.philippov.originmvc.utils.MailBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    @Value("${paypal.clientId}")
    private String clientId;
//    @Value("${paypal.clientSecret}")
//    private String clientSecret;
//    @Value("${paypal.mode}")
//    private String mode;

    private MailService mailService;
    private OrderService orderService;
    Logger logger;

    @Autowired
    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{orderId}")
    public String orderConfirmation(@PathVariable(name = "orderId", required = false) Long orderId, Model model) throws MessagingException {
        lev.philippov.originmvc.models.Order order = orderService.getOrderById(orderId);
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


//    private APIContext apiContext;
//
//    @PostConstruct
//    public void init() {
//        this.apiContext = new APIContext(clientId, clientSecret, mode);
//        this.logger = LoggerFactory.getLogger(PaymentController.class);
//    }
//
//    @Deprecated
//    @GetMapping("/buy/{orderId}")
//    public String buy(@PathVariable(value = "orderId") Long orderId, Model model, HttpServletRequest request) {
//
//        lev.philippov.originmvc.models.Order order = orderService.getOrderById(orderId);
//        Payer payer = new Payer();
//        payer.setPaymentMethod("paypal"); //could be wallet or card
//        RedirectUrls redirectUrls = new RedirectUrls();
//        redirectUrls.setCancelUrl(request.getContextPath() + "/paypal/cancel");
//        redirectUrls.setReturnUrl(request.getContextPath() + "/paypal/success/"+orderId);
//
//        Amount amount = new Amount();
//        amount.setCurrency("RUB");
//        amount.setTotal(order.getPrice().toString());
//
//        Transaction transaction = new Transaction();
//        transaction.setAmount(amount);
//        transaction.setDescription("PetShop buy");
//
//        List<Transaction> transactions = new ArrayList<>(List.of(transaction));
//
//        Payment payment = new Payment();
//        payment.setPayer(payer);
//        payment.setRedirectUrls(redirectUrls);
//        payment.setTransactions(transactions);
//        payment.setIntent("sale");
//        Payment doPayment = null;
//        try {
//            doPayment = payment.create(apiContext);
//        } catch (PayPalRESTException e) {
//            logger.error(e.getMessage());
//        }
//        if (doPayment == null) {
//            throw new ServerException("Something went wrong and payment process stucks.");
//        }
//        model.addAttribute("error","");
//        String redirectLink = "paypal-result";
//        Iterator<Links> links = doPayment.getLinks().iterator();
//        for (Links link : doPayment.getLinks()) {
//            if (link.getRel().equalsIgnoreCase("approval_url")) {
//                redirectLink = "redirect:" + link.getHref();
//            }
//        }
//        return redirectLink;
//    }
//
//    @Deprecated
//    @GetMapping("/success/{orderId}")
//    public String success(@PathVariable(name = "orderId", required = true) Long orderId,
//                          HttpServletRequest request, Model model) {
//            String paymentId = request.getParameter("paymentId");
//            String payerId = request.getParameter("PayerID");
//        System.out.println("---------------------------------");
//        System.out.println(payerId);
//        System.out.println(paymentId);
//
//            if (paymentId == null || paymentId.isEmpty() || payerId == null || payerId.isEmpty()) {
//                return "redirect:/";
//            }
//
//            Payment payment = new Payment();
//            payment.setId(paymentId);
//
//            PaymentExecution paymentExecution = new PaymentExecution();
//            paymentExecution.setPayerId(payerId);
//
//            try {
//            Payment executedPayment = payment.execute(apiContext, paymentExecution);
//                if (executedPayment.getState().equals("approved")) {
//
//                } else {
//                    model.addAttribute("error","Something went wrong.");
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        return "payments/paypal-result";
//    }
//
//    @Deprecated
//    @GetMapping(path = "/cansel")
//    public String cansel(Model model) {
//        model.addAttribute("message", "Оплата заказа была отменена");
//        return "payments/paypal-result";
//    }
}
