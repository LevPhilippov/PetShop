package lev.philippov.originmvc;

import lev.philippov.originmvc.services.ProductService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AdminProductControllerTests extends LocateChangingParentTest{

    @Autowired
    MockMvc mockMvc;

    public ProductService productServiceMock = mock(ProductService.class);

    @Test
    public void start() throws Exception {
        mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Welcome")));
    }

//    @Test
//    @Disabled
//    public void getAll() throws Exception {
//        List<Product> list = new ArrayList<Product>(
//                List.of(new Product(1L, "Dog", new BigDecimal(99)),
//                        new Product(2L,"Cat",new BigDecimal(77)),
//                        new Product(3L,"Horse",new BigDecimal(250))));
//
//        this.productServiceMock = mock(ProductService.class);
//        when(productServiceMock.findAll()).thenReturn(list);
//
//        mockMvc.perform(get("/admin/products"))
//                .andDo(print())
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("http://localhost/login"));
//    }
}
