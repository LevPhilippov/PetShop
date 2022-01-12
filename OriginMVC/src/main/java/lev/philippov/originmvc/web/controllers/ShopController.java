package lev.philippov.originmvc.web.controllers;

import lev.philippov.originmvc.services.ProductService;
import lev.philippov.originmvc.web.models.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

import static lev.philippov.originmvc.utils.ProductsUtils.*;

@Controller
@RequestMapping("/shop")
@RequiredArgsConstructor
public class ShopController {

    private final ProductService productService;

    @RequestMapping(method = RequestMethod.GET)
    public String findAll(Model model, HttpServletRequest request,
                          @RequestParam(name = "pageNbr", defaultValue = "0") Integer pageNbr,
                          @RequestParam (name = "itemsOnPage", required = false)Integer pageSize,
                          HttpSession session){

        if(pageSize != null) {
            session.setAttribute("pageSize",pageSize);
            return "redirect:/shop";
        }

        Map<String, String> params = parseFilters(request);
        String filters = parseFiltersMapToString(params);
        Page<ProductDto> products = productService.findFiltered(pageNbr, params, (Integer) session.getAttribute("pageSize"));

        putProductsFromCookiesToAModel(request, model);
        model.addAttribute("categories", productService.findAllCategories());
        model.addAttribute("productPage", products);
        model.addAttribute("filters", filters);
        return "shop";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{productId}")
    public String showProduct(@PathVariable UUID productId, Model model, HttpServletRequest request,
                              HttpServletResponse response) {
        ProductDto product = productService.findById(productId);
        setShownProductToCookie(productId,request, response);
        model.addAttribute("product", product);
        return "product_page";
    }

    private void setShownProductToCookie(UUID productId, HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        Deque<Cookie> shownProductCookies = new LinkedList<>();
        Arrays.stream(cookies).filter(cookie -> cookie.getName().matches("^prod_[0-9]*$"))
                .sorted((o1, o2) -> Long.parseLong(o1.getName().split("_")[1])>Long.parseLong(o2.getName().split("_")[1]) ? 1 : -1)
                .forEach(shownProductCookies :: add);
        if(shownProductCookies.size()>=4) {
            Cookie cookieToRemove = shownProductCookies.pollFirst();
            cookieToRemove.setValue(null);
            cookieToRemove.setMaxAge(0);
            response.addCookie(cookieToRemove);
        }
        long cookieIndex = 0L;
        if(shownProductCookies.size()>0){
            cookieIndex = Long.parseLong(shownProductCookies.peekLast().getName().split("_")[1]);
        }

        Cookie cookie = new Cookie("prod_" + (cookieIndex+1) , productId.toString());
        cookie.setMaxAge(60);
        response.addCookie(cookie);
    }

    // for show 4 last shown products
    private void putProductsFromCookiesToAModel(HttpServletRequest request,Model model) {
        Cookie[] cookies = request.getCookies();
        Set<UUID> shownProductIds = new HashSet<>();
        if(cookies == null) return;

        Arrays.stream(cookies).filter(cookie -> cookie.getName().matches("^prod_[0-9]*$"))
                .sorted((o1, o2) -> Long.parseLong(o1.getName().split("_")[1])>Long.parseLong(o2.getName().split("_")[1]) ? 1 : -1)
                .forEach(c -> shownProductIds.add(UUID.fromString(c.getValue())));

        if(shownProductIds.size() > 0) {
            List<ProductDto> allByIds = productService.findAllByIds(shownProductIds);
            model.addAttribute("shownProds", allByIds);
        }
    }

}
