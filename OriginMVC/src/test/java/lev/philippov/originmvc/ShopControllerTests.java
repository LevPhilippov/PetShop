package lev.philippov.originmvc;

import lev.philippov.originmvc.models.Product;
import lev.philippov.originmvc.services.ProductService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ShopControllerTests extends LocateChangingParentTest{

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProductService productServiceMock;

    @Test
    public void getAll() throws Exception {
        List<Product> list = new ArrayList<Product>(
                List.of(new Product(1L, "Dog", new BigDecimal(99)),
                        new Product(2L,"Cat",new BigDecimal(77)),
                        new Product(3L,"Horse",new BigDecimal(250))));

        BDDMockito.given(productServiceMock.findFiltered(any(),any(),any())).willReturn(new PageImpl<Product>(list, Pageable.ofSize(5),1));

        mockMvc.perform(get("/shop"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful()).andExpect(model().attribute("products",list));
    }





}
