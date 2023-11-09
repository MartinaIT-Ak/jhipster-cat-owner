package fr.it_akademy.jhipster.cat_owner.service;

import fr.it_akademy.jhipster.cat_owner.service.dto.CatDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link fr.it_akademy.jhipster.cat_owner.domain.Cat}.
 */
public interface CatService {
    /**
     * Save a cat.
     *
     * @param catDTO the entity to save.
     * @return the persisted entity.
     */
    CatDTO save(CatDTO catDTO);

    /**
     * Updates a cat.
     *
     * @param catDTO the entity to update.
     * @return the persisted entity.
     */
    CatDTO update(CatDTO catDTO);

    /**
     * Partially updates a cat.
     *
     * @param catDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CatDTO> partialUpdate(CatDTO catDTO);

    /**
     * Get all the cats.
     *
     * @return the list of entities.
     */
    List<CatDTO> findAll();

    /**
     * Get the "id" cat.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CatDTO> findOne(Long id);

    /**
     * Delete the "id" cat.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
