package lev.philippov.originmvc;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@PropertySource("classpath:application.properties")
@SpringBootTest(classes = OriginMvcApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SSlTest {

    private static final String WELCOME_URL = "https://localhost:8443/app";

    @Value("${trust.store}")
    private Resource trustStore;

    @Value("${trust.store.password}")
    private String trustStorePassword;

    @Test
    public void injectTest() throws IOException {
        assertEquals("12345678", trustStorePassword);
        assertEquals("file:/C:/Users/filip/IdeaProjects/PetShop/OriginMVC/target/classes/keystore/localhost.p12", trustStore.getURI().toString());
    }

    @Test
    public void whenGETanHTTPSResource_thenCorrectResponse() throws Exception {
        SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(trustStore.getURL(), trustStorePassword.toCharArray())
                .build();
        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext);
        HttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(socketFactory)
                .build();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        RestTemplate restTemplate = new RestTemplate(factory);
        ResponseEntity<String> response = restTemplate.getForEntity(WELCOME_URL, String.class, Collections.emptyMap());
        assertTrue(response.getBody().contains("Приветствую"));
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
