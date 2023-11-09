package fr.it_akademy.jhipster.cat_owner.service;

import fr.it_akademy.jhipster.cat_owner.service.dto.OwnerDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link fr.it_akademy.jhipster.cat_owner.domain.Owner}.
 */
public interface OwnerService {
    /**
     * Save a owner.
     *
     * @param ownerDTO the entity to save.
     * @return the persisted entity.
     */
    OwnerDTO save(OwnerDTO ownerDTO);

    /**
     * Updates a owner.
     *
     * @param ownerDTO the entity to update.
     * @return the persisted entity.
     */
    OwnerDTO update(OwnerDTO ownerDTO);

    /**
     * Partially updates a owner.
     *
     * @param ownerDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OwnerDTO> partialUpdate(OwnerDTO ownerDTO);

    /**
     * Get all the owners.
     *
     * @return the list of entities.
     */
    List<OwnerDTO> findAll();

    /**
     * Get all the owners with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OwnerDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" owner.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OwnerDTO> findOne(Long id);

    /**
     * Delete the "id" owner.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
