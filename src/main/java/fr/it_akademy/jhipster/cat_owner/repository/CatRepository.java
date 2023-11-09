package fr.it_akademy.jhipster.cat_owner.repository;

import fr.it_akademy.jhipster.cat_owner.domain.Cat;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Cat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CatRepository extends JpaRepository<Cat, Long> {}
