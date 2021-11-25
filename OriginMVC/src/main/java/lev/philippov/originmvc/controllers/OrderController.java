package lev.philippov.originmvc.controllers;

import lev.philippov.originmvc.exceptions.ServerException;
import lev.philippov.originmvc.exceptions.WrongAccessException;
import lev.philippov.originmvc.models.Order;
import lev.philippov.originmvc.models.OrderDetails;
import lev.philippov.originmvc.services.OrderService;
import lev.philippov.originmvc.utils.Cart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping("/order")
public class OrderController {

    private Logger logger;
    private final String ONE_CLICK_FORM="order/one_click_form";
    private final String ORDER_DETAILS_FORM="order/order_details";

    private Cart cart;
    @Autowired
    public void setCart(Cart cart) {
        this.cart = cart;
    }

    private OrderService orderService;
    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostConstruct
    public void init(){
        this.logger = LoggerFactory.getLogger(OrderController.class);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String showOrderDetails(Model model) {
        cartEmptyCheck(cart);
        model.addAttribute("details", new OrderDetails());
        model.addAttribute(cart);
        return ORDER_DETAILS_FORM;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String saveOrder(Principal principal, HttpServletResponse response, HttpServletRequest request,
                          @Valid @ModelAttribute("details") OrderDetails details, final BindingResult result, Model model) throws IOException {
        if(result.hasErrors()) {
            for(FieldError fe : result.getFieldErrors()){
                model.addAttribute(fe.getField()+"Alert", fe.getDefaultMessage());
            }
            model.addAttribute("details", details);
            model.addAttribute(cart);
            return  ORDER_DETAILS_FORM;
        }

        Order order = orderService.saveOrder(cart, principal, details);
        cart.clear();
        return "redirect:/payment/"+ order.getId();
    }

    @RequestMapping(value = "/oneclick", method = RequestMethod.GET)
    public String showOneClickForm(Model model) {
        cartEmptyCheck(cart);
        model.addAttribute("details", new OrderDetails());
        model.addAttribute(cart);
        return ONE_CLICK_FORM;
    }

    @RequestMapping(value = "/oneclick", method = RequestMethod.POST)
    public String showOneClickForm(@Valid @ModelAttribute("details") OrderDetails details, final BindingResult result,
                                   HttpServletRequest request, HttpServletResponse response,
                                   Model model, Principal principal) throws IOException {

        if(request.getParameter("roboCheck") == null && principal == null) {
            throw new WrongAccessException("You are fucking robot!");
        }

        if(result.hasErrors()) {
            for(FieldError fe : result.getFieldErrors()){
                model.addAttribute(fe.getField()+"Alert", fe.getDefaultMessage());
            }
            model.addAttribute("details", details);
            model.addAttribute(cart);
            return  ONE_CLICK_FORM;
        }

        Order order;
        if(principal!= null) {
            order = orderService.saveOrder(cart,principal,details);
        } else {
            order = orderService.saveAnonymousOrder(cart, details);
        }
        cart.clear();
//        response.sendRedirect(request.getContextPath() + "/order/confirm/"+ order.getId());
//        response.sendRedirect(request.getContextPath() + "/shop");
//        return null;
        return "redirect:/payment/"+ order.getId();
    }

    private void cartEmptyCheck(Cart cart) {
        if(cart.getOrderItems().size()==0) {
            logger.error("Somehow user manage to order an empty cart!\n");
            throw new ServerException("Cart is empty");
        }
    }

}
