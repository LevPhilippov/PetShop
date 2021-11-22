package lev.philippov.originmvc.soap;

import lev.filippov.demomvc.soap.catalog.GetProductListRequest;
import lev.filippov.demomvc.soap.catalog.GetProductListResponse;
import lev.filippov.demomvc.soap.catalog.ProductDto;
import lev.filippov.demomvc.soap.catalog.ProductsList;
import lev.philippov.originmvc.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;
import java.util.stream.Collectors;

@Endpoint
public class CatalogEndpoint {
    private static final String CATALOG_URI = "http://demomvc.filippov.lev/soap/catalog";

    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @PayloadRoot(namespace = CATALOG_URI, localPart = "getProductListRequest")
    @ResponsePayload
    public GetProductListResponse getProductList(@RequestPayload GetProductListRequest request) {
        List<ProductDto> soapDtos = productService.findAll().stream().map(p -> {
            ProductDto dto = new ProductDto();
            dto.setTitle(p.getTitle());
            return dto;
        }).collect(Collectors.toList());
        GetProductListResponse response = new GetProductListResponse();
        response.setName(request.getName());
        ProductsList productsList = new ProductsList();
        productsList.getProducts().addAll(soapDtos);
        response.setProductsList(productsList);
        return response;
    }
}
