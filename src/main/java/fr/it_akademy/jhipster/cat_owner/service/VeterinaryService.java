package fr.it_akademy.jhipster.cat_owner.service;

import fr.it_akademy.jhipster.cat_owner.service.dto.VeterinaryDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link fr.it_akademy.jhipster.cat_owner.domain.Veterinary}.
 */
public interface VeterinaryService {
    /**
     * Save a veterinary.
     *
     * @param veterinaryDTO the entity to save.
     * @return the persisted entity.
     */
    VeterinaryDTO save(VeterinaryDTO veterinaryDTO);

    /**
     * Updates a veterinary.
     *
     * @param veterinaryDTO the entity to update.
     * @return the persisted entity.
     */
    VeterinaryDTO update(VeterinaryDTO veterinaryDTO);

    /**
     * Partially updates a veterinary.
     *
     * @param veterinaryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<VeterinaryDTO> partialUpdate(VeterinaryDTO veterinaryDTO);

    /**
     * Get all the veterinaries.
     *
     * @return the list of entities.
     */
    List<VeterinaryDTO> findAll();

    /**
     * Get the "id" veterinary.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VeterinaryDTO> findOne(Long id);

    /**
     * Delete the "id" veterinary.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
