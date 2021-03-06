package lev.philippov.originmvc.web.controllers;

import lev.philippov.originmvc.exceptions.ServerException;
import lev.philippov.originmvc.exceptions.WrongAccessException;
import lev.philippov.originmvc.domain.Order;
import lev.philippov.originmvc.domain.OrderDetails;
import lev.philippov.originmvc.services.OrderService;
import lev.philippov.originmvc.utils.Cart;
import lombok.RequiredArgsConstructor;
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
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final Logger logger = LoggerFactory.getLogger(OrderController.class);;
    private final String ONE_CLICK_FORM="order/one_click_form";
    private final String ORDER_DETAILS_FORM="order/order_details";
    private final Cart cart;
    private final OrderService orderService;

    @RequestMapping(method = RequestMethod.GET)
    public String showOrderDetails(Model model) {
        cartEmptyCheck(cart);
        model.addAttribute("details", new OrderDetails());
        model.addAttribute(cart);
        return ORDER_DETAILS_FORM;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String saveOrder(Principal principal,
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
                                   HttpServletRequest request,
                                   Model model, Principal principal) throws IOException {

        if(request.getParameter("roboCheck") == null && principal == null) {
            throw new WrongAccessException("You are fucking robot!");
        }

        if(result.hasErrors()) {
            result.getFieldErrors().forEach(fe-> model.addAttribute(fe.getField()+"Alert", fe.getDefaultMessage()));
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
        return "redirect:/payment/"+ order.getId();
    }

    private void cartEmptyCheck(Cart cart) {
        if(cart.getOrderItems().size()==0) {
            logger.error("Somehow user managed to order an empty cart!\n");
            throw new ServerException("Cart is empty");
        }
    }

}
