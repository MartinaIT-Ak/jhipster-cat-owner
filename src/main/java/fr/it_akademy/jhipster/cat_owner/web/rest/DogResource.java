package fr.it_akademy.jhipster.cat_owner.web.rest;

import fr.it_akademy.jhipster.cat_owner.repository.DogRepository;
import fr.it_akademy.jhipster.cat_owner.service.DogService;
import fr.it_akademy.jhipster.cat_owner.service.dto.DogDTO;
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
 * REST controller for managing {@link fr.it_akademy.jhipster.cat_owner.domain.Dog}.
 */
@RestController
@RequestMapping("/api/dogs")
public class DogResource {

    private final Logger log = LoggerFactory.getLogger(DogResource.class);

    private static final String ENTITY_NAME = "dog";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DogService dogService;

    private final DogRepository dogRepository;

    public DogResource(DogService dogService, DogRepository dogRepository) {
        this.dogService = dogService;
        this.dogRepository = dogRepository;
    }

    /**
     * {@code POST  /dogs} : Create a new dog.
     *
     * @param dogDTO the dogDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dogDTO, or with status {@code 400 (Bad Request)} if the dog has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<DogDTO> createDog(@RequestBody DogDTO dogDTO) throws URISyntaxException {
        log.debug("REST request to save Dog : {}", dogDTO);
        if (dogDTO.getId() != null) {
            throw new BadRequestAlertException("A new dog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DogDTO result = dogService.save(dogDTO);
        return ResponseEntity
            .created(new URI("/api/dogs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dogs/:id} : Updates an existing dog.
     *
     * @param id the id of the dogDTO to save.
     * @param dogDTO the dogDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dogDTO,
     * or with status {@code 400 (Bad Request)} if the dogDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dogDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DogDTO> updateDog(@PathVariable(value = "id", required = false) final Long id, @RequestBody DogDTO dogDTO)
        throws URISyntaxException {
        log.debug("REST request to update Dog : {}, {}", id, dogDTO);
        if (dogDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dogDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dogRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DogDTO result = dogService.update(dogDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dogDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /dogs/:id} : Partial updates given fields of an existing dog, field will ignore if it is null
     *
     * @param id the id of the dogDTO to save.
     * @param dogDTO the dogDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dogDTO,
     * or with status {@code 400 (Bad Request)} if the dogDTO is not valid,
     * or with status {@code 404 (Not Found)} if the dogDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the dogDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DogDTO> partialUpdateDog(@PathVariable(value = "id", required = false) final Long id, @RequestBody DogDTO dogDTO)
        throws URISyntaxException {
        log.debug("REST request to partial update Dog partially : {}, {}", id, dogDTO);
        if (dogDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dogDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dogRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DogDTO> result = dogService.partialUpdate(dogDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dogDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /dogs} : get all the dogs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dogs in body.
     */
    @GetMapping("")
    public List<DogDTO> getAllDogs() {
        log.debug("REST request to get all Dogs");
        return dogService.findAll();
    }

    /**
     * {@code GET  /dogs/:id} : get the "id" dog.
     *
     * @param id the id of the dogDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dogDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DogDTO> getDog(@PathVariable Long id) {
        log.debug("REST request to get Dog : {}", id);
        Optional<DogDTO> dogDTO = dogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dogDTO);
    }

    /**
     * {@code DELETE  /dogs/:id} : delete the "id" dog.
     *
     * @param id the id of the dogDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDog(@PathVariable Long id) {
        log.debug("REST request to delete Dog : {}", id);
        dogService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
