package lev.philippov.originmvc.repositories;

import lev.philippov.originmvc.domain.product.structure.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
public interface AttributeRepository extends JpaRepository<Attribute, UUID> {
}
