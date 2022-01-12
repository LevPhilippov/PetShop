package lev.philippov.originmvc.web.controllers;

import lev.philippov.originmvc.services.ProductService;
import lev.philippov.originmvc.web.models.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
public class ProductRestController {

    ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductDto> findAll() {
        return productService.findAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void remove(@PathVariable UUID id) {
        productService.deleteProduct(id);
    }

    @PostMapping
    public ProductDto save(@Valid @RequestBody ProductDto product) {

        productService.save(product);
        return product;
    }

    @PutMapping
    public ProductDto update(@Valid @RequestBody ProductDto product) {

        productService.update(product);
        return product;
    }

}
