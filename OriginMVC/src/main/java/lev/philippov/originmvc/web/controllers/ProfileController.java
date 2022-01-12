//package lev.philippov.originmvc.controllers;
//
//import lev.philippov.originmvc.exceptions.WrongAccessException;
//import lev.philippov.originmvc.models.Order;
//import lev.philippov.originmvc.services.ProfileService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import javax.servlet.http.HttpSession;
//import java.util.Set;
//
//@Controller
//@RequestMapping(path = "/user")
//public class ProfileController {
//
//    ProfileService profileService;
//
//    @Autowired
//    public void setProfileService(ProfileService profileService) {
//        this.profileService = profileService;
//    }
//
//    //TODO здесь требуется защита endpoint либо на уровне данного метода, либо на уровне SecurityConfig.
//    // Если userId пользователя совпадает с userId в сессии, тогда необходимо показывать результат,
//    // если же нет - возвращать ошибку доступа.
//
//    @GetMapping(path = "/{userId}/orders")
//    public String showOrders(@PathVariable("userId") Long userId, Model model, HttpSession session) {
//        if(!((User) session.getAttribute("user")).getId().equals(userId)) {
//            throw new WrongAccessException("Ошибка доступа!");
//        }
//        Set<Order> orders = profileService.findAllOrdersByUser(userId);
//        model.addAttribute("orders", orders);
//        return "profile/orders_list";
//    }
//}
