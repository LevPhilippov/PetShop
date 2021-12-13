package lev.philippov.originmvc;

import lev.philippov.originmvc.services.ProductService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.util.Locale;

import static org.mockito.Mockito.mock;

public class LocateChangingParentTest {

    public static Locale defaultLocale = Locale.getDefault();
    public ProductService productServiceMock = mock(ProductService.class);


    @BeforeAll
    public static void setLocale(){
        Locale.setDefault(Locale.ENGLISH);
    }

    @AfterAll
    public static void turnLocaleBack(){
        Locale.setDefault(defaultLocale);
    }

}
