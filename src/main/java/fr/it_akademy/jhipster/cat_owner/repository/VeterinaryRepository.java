package fr.it_akademy.jhipster.cat_owner.repository;

import fr.it_akademy.jhipster.cat_owner.domain.Veterinary;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Veterinary entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VeterinaryRepository extends JpaRepository<Veterinary, Long> {}
