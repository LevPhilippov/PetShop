package lev.philippov.originmvc.repositories;

import lev.philippov.originmvc.domain.product.structure.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ParamRepository extends JpaRepository<Param, UUID> {
}
