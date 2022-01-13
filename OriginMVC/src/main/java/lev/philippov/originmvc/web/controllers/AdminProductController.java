package lev.philippov.originmvc.web.controllers;


import lev.philippov.originmvc.services.ProductService;
import lev.philippov.originmvc.web.models.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import static lev.philippov.originmvc.utils.ProductsUtils.*;

@Controller
@RequestMapping("/admin/products")
@RequiredArgsConstructor
public class AdminProductController {

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

        model.addAttribute("categories", productService.findAllCategories());
        model.addAttribute("productPage", products);
        model.addAttribute("filters", filters);
        return "admin/products";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{productId}")
    public String showProduct(@PathVariable UUID productId, Model model, HttpServletRequest request,
                              HttpServletResponse response) {
        ProductDto product = productService.findById(productId);
        model.addAttribute("product", product);
        return "product_page";
    }

    @GetMapping(path = "add")
    public String addNewProduct(Model model){
        return "admin/edit_product_form";
    }

    //при удалении редиректом сбрасываются фильтры, а должны оставаться. Решено путем переадресации на Header - referer
    @RequestMapping(method = RequestMethod.GET, path = {"/delete/{id}", })
    public void deleteProduct(@PathVariable(name = "id") UUID id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        productService.deleteProduct(id);
        response.sendRedirect(request.getHeader("referer"));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/edit/{id}")
    public String editProduct(@PathVariable(name = "id", required = false) UUID id,
                              Model model){
        ProductDto product = productService.findById(id);
        model.addAttribute("product", product);
        return "admin/edit_product_form";
    }

    @RequestMapping(method = RequestMethod.POST, path = "/edit")
    public void editProduct(@ModelAttribute(name = "product") ProductDto product,
                            HttpServletResponse response,
                            HttpServletRequest request) throws IOException {
        productService.update(product);
        response.sendRedirect(request.getContextPath() + "/admin/products");
    }
}
