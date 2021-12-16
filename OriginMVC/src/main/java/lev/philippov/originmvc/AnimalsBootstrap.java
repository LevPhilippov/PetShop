package lev.philippov.originmvc;

import lev.philippov.originmvc.domain.structure.Bird;
import lev.philippov.originmvc.domain.structure.components.*;
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
//        int select_a_from_animal_a = entityManager.createQuery("SELECT b from Bird b").getMaxResults();
//        if(select_a_from_animal_a == 0) {
            WingsComponent wingsComponent = WingsComponent.builder()
                    .wingspan(Param.builder().name("Wings span").value("15").typeOfMeasure(TypeOfMeasure.sm).build())
                    .liftPower(Param.builder().name("Lift power").value("13").typeOfMeasure(TypeOfMeasure.N).build())
                    .title("Wings").build();
           LegsComponent legsComponent = LegsComponent.builder()
                           .lenghOfLegs(new Param("Lengh of legs","15",TypeOfMeasure.sm))
                           .qtyOfLegs(new Param("Quantity of legs","4",null))
                           .title("Legs").build();
           BodyComponent bodyComponent = BodyComponent.builder()
                           .weight(new Param("Weigh","15",TypeOfMeasure.kilo))
                               .title("Body").build();

            Bird bird = Bird.builder()
                    .title("Bird")
                    .qty(Inventory.builder().qty(5).build())
                    .price(new BigDecimal("13.77"))
                    .legs(legsComponent)
                    .wings(wingsComponent)
                    .body(bodyComponent).build();


            entityManager.persist(bird);
            entityManager.flush();
//        }
//        Object select_b_from_bird_b = entityManager.createQuery("SELECT b from Bird b").getSingleResult();
//        if(select_b_from_bird_b instanceof Bird) {
//            System.out.println(select_b_from_bird_b);
//        }
    }
}
