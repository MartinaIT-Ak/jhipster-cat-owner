package fr.it_akademy.jhipster.cat_owner.service;

import fr.it_akademy.jhipster.cat_owner.service.dto.DogDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link fr.it_akademy.jhipster.cat_owner.domain.Dog}.
 */
public interface DogService {
    /**
     * Save a dog.
     *
     * @param dogDTO the entity to save.
     * @return the persisted entity.
     */
    DogDTO save(DogDTO dogDTO);

    /**
     * Updates a dog.
     *
     * @param dogDTO the entity to update.
     * @return the persisted entity.
     */
    DogDTO update(DogDTO dogDTO);

    /**
     * Partially updates a dog.
     *
     * @param dogDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DogDTO> partialUpdate(DogDTO dogDTO);

    /**
     * Get all the dogs.
     *
     * @return the list of entities.
     */
    List<DogDTO> findAll();

    /**
     * Get the "id" dog.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DogDTO> findOne(Long id);

    /**
     * Delete the "id" dog.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
