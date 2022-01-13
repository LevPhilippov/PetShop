package lev.philippov.originmvc.web.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import lev.philippov.originmvc.services.ProductService;
import lev.philippov.originmvc.web.models.AttributeDto;
import lev.philippov.originmvc.web.models.CategoryDto;
import lev.philippov.originmvc.web.models.ParamDto;
import lev.philippov.originmvc.web.models.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;

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
@SessionScope
public class AdminProductController {

    private final ProductService productService;
    private ProductDto productDto = new ProductDto();

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
        model.addAttribute("categories", productService.findAllCategories());
        return "product_page";
    }

    @GetMapping(path = "/add")
    public String addNewProduct(Model model){
        model.addAttribute("product", new ProductDto());
        model.addAttribute("categories", productService.findAllCategories());
        model.addAttribute("aparams",productService.findAllParams());
        model.addAttribute("attribute", new AttributeDto());
        return "admin/edit_product_form";
    }

    //при удалении редиректом сбрасываются фильтры, а должны оставаться. Решено путем переадресации на Header - referer
    @RequestMapping(method = RequestMethod.GET, path = {"/delete/{id}", })
    public void deleteProduct(@PathVariable(name = "id") UUID id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        productService.deleteProduct(id);
        response.sendRedirect(request.getHeader("referer"));
    }

    //при фильры можно хранить прямо в контроллере (как в JavaEE и xhtml) или отправлять/получать из поля в модели. Реализация удалена.
    @RequestMapping(method = RequestMethod.GET, path = "/edit/{id}")
    public String editProduct(@PathVariable(name = "id") UUID id,
                              Model model){
        this.productDto = productService.findById(id);
        model.addAttribute("product", productDto);
        model.addAttribute("categories", productService.findAllCategories());
        model.addAttribute("aparams",productService.findAllParams());
        model.addAttribute("attribute", new AttributeDto());
        return "admin/edit_product_form";
    }

    @RequestMapping(method = RequestMethod.POST, path = "/add/attribute")
    public String addAttribute(@ModelAttribute(name = "attribute") AttributeDto attributeDto,
                              HttpServletRequest request,
                              Model model) throws JsonProcessingException {
        Object paramId = request.getParameter("paramId");
        if(paramId instanceof String) {
            ParamDto param = productService.getParamById((String) paramId);
            attributeDto.setParam(param);
            this.productDto.getAttributes().add(attributeDto);
        }
        model.addAttribute("product", productDto);
        model.addAttribute("categories", productService.findAllCategories());
        model.addAttribute("aparams",productService.findAllParams());
        model.addAttribute("attribute", new AttributeDto());
        return "admin/edit_product_form";
    }

    @RequestMapping(method = RequestMethod.POST, path = "/add/confirm")
    public String editProduct(@ModelAttribute(name = "product") ProductDto product, HttpServletRequest request,
                            Model model){
        Object category = request.getParameter("categoryId");
        if(category instanceof String){
            product.setCategory((String)category);
        }
        this.productDto = product;
        model.addAttribute("product", productDto);
        model.addAttribute("categories", productService.findAllCategories());
        model.addAttribute("aparams",productService.findAllParams());
        model.addAttribute("attribute", new AttributeDto());
        return "admin/edit_product_form";
    }

    @GetMapping(path = "/add/save")
    public String save(){
        productService.save(this.productDto);
        this.productDto = new ProductDto();
        return "redirect:/admin/products";
    }

}
