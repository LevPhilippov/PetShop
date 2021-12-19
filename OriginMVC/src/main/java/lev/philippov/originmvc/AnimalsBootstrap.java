package lev.philippov.originmvc;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.math.BigDecimal;

@Component
public class AnimalsBootstrap implements CommandLineRunner {

    @PersistenceContext
    private EntityManager entityManager;


    @Transactional
    @Override
    public void run(String... args) throws Exception {
    }
}
