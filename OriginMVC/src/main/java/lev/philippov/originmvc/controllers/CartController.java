package lev.philippov.originmvc.controllers;

import lev.philippov.originmvc.exceptions.ServerException;
import lev.philippov.originmvc.exceptions.WrongAccessException;
import lev.philippov.originmvc.models.Order;
import lev.philippov.originmvc.models.OrderDetails;
import lev.philippov.originmvc.services.OrderService;
import lev.philippov.originmvc.services.ProductService;
import lev.philippov.originmvc.utils.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping("/cart")
public class CartController {

    private Cart cart;
    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setCart(Cart cart) {
        this.cart = cart;
    }

    @RequestMapping
    public String show(Model model) {
        model.addAttribute("cart", cart);
        model.addAttribute("title", "Cart");
        return "order/cart";
    }

    @RequestMapping("/add/{id}")
    public void add(@PathVariable Long id,HttpServletRequest request, HttpServletResponse response) throws Throwable {
        this.cart.add(productService.findById(id));
        response.sendRedirect(request.getHeader("referer"));
    }

    @RequestMapping("/remove/{id}")
    public String remove(@PathVariable Long id) {
        this.cart.remove(productService.getById(id));
        return "redirect:/cart";
    }
}

