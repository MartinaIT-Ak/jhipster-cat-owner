package fr.it_akademy.jhipster.cat_owner.web.rest;

import fr.it_akademy.jhipster.cat_owner.repository.VeterinaryRepository;
import fr.it_akademy.jhipster.cat_owner.service.VeterinaryService;
import fr.it_akademy.jhipster.cat_owner.service.dto.VeterinaryDTO;
import fr.it_akademy.jhipster.cat_owner.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link fr.it_akademy.jhipster.cat_owner.domain.Veterinary}.
 */
@RestController
@RequestMapping("/api/veterinaries")
public class VeterinaryResource {

    private final Logger log = LoggerFactory.getLogger(VeterinaryResource.class);

    private static final String ENTITY_NAME = "veterinary";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VeterinaryService veterinaryService;

    private final VeterinaryRepository veterinaryRepository;

    public VeterinaryResource(VeterinaryService veterinaryService, VeterinaryRepository veterinaryRepository) {
        this.veterinaryService = veterinaryService;
        this.veterinaryRepository = veterinaryRepository;
    }

    /**
     * {@code POST  /veterinaries} : Create a new veterinary.
     *
     * @param veterinaryDTO the veterinaryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new veterinaryDTO, or with status {@code 400 (Bad Request)} if the veterinary has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<VeterinaryDTO> createVeterinary(@RequestBody VeterinaryDTO veterinaryDTO) throws URISyntaxException {
        log.debug("REST request to save Veterinary : {}", veterinaryDTO);
        if (veterinaryDTO.getId() != null) {
            throw new BadRequestAlertException("A new veterinary cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VeterinaryDTO result = veterinaryService.save(veterinaryDTO);
        return ResponseEntity
            .created(new URI("/api/veterinaries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /veterinaries/:id} : Updates an existing veterinary.
     *
     * @param id the id of the veterinaryDTO to save.
     * @param veterinaryDTO the veterinaryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated veterinaryDTO,
     * or with status {@code 400 (Bad Request)} if the veterinaryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the veterinaryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<VeterinaryDTO> updateVeterinary(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody VeterinaryDTO veterinaryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Veterinary : {}, {}", id, veterinaryDTO);
        if (veterinaryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, veterinaryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!veterinaryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        VeterinaryDTO result = veterinaryService.update(veterinaryDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, veterinaryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /veterinaries/:id} : Partial updates given fields of an existing veterinary, field will ignore if it is null
     *
     * @param id the id of the veterinaryDTO to save.
     * @param veterinaryDTO the veterinaryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated veterinaryDTO,
     * or with status {@code 400 (Bad Request)} if the veterinaryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the veterinaryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the veterinaryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<VeterinaryDTO> partialUpdateVeterinary(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody VeterinaryDTO veterinaryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Veterinary partially : {}, {}", id, veterinaryDTO);
        if (veterinaryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, veterinaryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!veterinaryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VeterinaryDTO> result = veterinaryService.partialUpdate(veterinaryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, veterinaryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /veterinaries} : get all the veterinaries.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of veterinaries in body.
     */
    @GetMapping("")
    public List<VeterinaryDTO> getAllVeterinaries() {
        log.debug("REST request to get all Veterinaries");
        return veterinaryService.findAll();
    }

    /**
     * {@code GET  /veterinaries/:id} : get the "id" veterinary.
     *
     * @param id the id of the veterinaryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the veterinaryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<VeterinaryDTO> getVeterinary(@PathVariable Long id) {
        log.debug("REST request to get Veterinary : {}", id);
        Optional<VeterinaryDTO> veterinaryDTO = veterinaryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(veterinaryDTO);
    }

    /**
     * {@code DELETE  /veterinaries/:id} : delete the "id" veterinary.
     *
     * @param id the id of the veterinaryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVeterinary(@PathVariable Long id) {
        log.debug("REST request to delete Veterinary : {}", id);
        veterinaryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
