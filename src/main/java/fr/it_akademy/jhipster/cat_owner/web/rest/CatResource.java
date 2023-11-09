package fr.it_akademy.jhipster.cat_owner.web.rest;

import fr.it_akademy.jhipster.cat_owner.repository.CatRepository;
import fr.it_akademy.jhipster.cat_owner.service.CatService;
import fr.it_akademy.jhipster.cat_owner.service.dto.CatDTO;
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
 * REST controller for managing {@link fr.it_akademy.jhipster.cat_owner.domain.Cat}.
 */
@RestController
@RequestMapping("/api/cats")
public class CatResource {

    private final Logger log = LoggerFactory.getLogger(CatResource.class);

    private static final String ENTITY_NAME = "cat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CatService catService;

    private final CatRepository catRepository;

    public CatResource(CatService catService, CatRepository catRepository) {
        this.catService = catService;
        this.catRepository = catRepository;
    }

    /**
     * {@code POST  /cats} : Create a new cat.
     *
     * @param catDTO the catDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new catDTO, or with status {@code 400 (Bad Request)} if the cat has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CatDTO> createCat(@RequestBody CatDTO catDTO) throws URISyntaxException {
        log.debug("REST request to save Cat : {}", catDTO);
        if (catDTO.getId() != null) {
            throw new BadRequestAlertException("A new cat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CatDTO result = catService.save(catDTO);
        return ResponseEntity
            .created(new URI("/api/cats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cats/:id} : Updates an existing cat.
     *
     * @param id the id of the catDTO to save.
     * @param catDTO the catDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated catDTO,
     * or with status {@code 400 (Bad Request)} if the catDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the catDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CatDTO> updateCat(@PathVariable(value = "id", required = false) final Long id, @RequestBody CatDTO catDTO)
        throws URISyntaxException {
        log.debug("REST request to update Cat : {}, {}", id, catDTO);
        if (catDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, catDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!catRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CatDTO result = catService.update(catDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, catDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cats/:id} : Partial updates given fields of an existing cat, field will ignore if it is null
     *
     * @param id the id of the catDTO to save.
     * @param catDTO the catDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated catDTO,
     * or with status {@code 400 (Bad Request)} if the catDTO is not valid,
     * or with status {@code 404 (Not Found)} if the catDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the catDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CatDTO> partialUpdateCat(@PathVariable(value = "id", required = false) final Long id, @RequestBody CatDTO catDTO)
        throws URISyntaxException {
        log.debug("REST request to partial update Cat partially : {}, {}", id, catDTO);
        if (catDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, catDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!catRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CatDTO> result = catService.partialUpdate(catDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, catDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cats} : get all the cats.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cats in body.
     */
    @GetMapping("")
    public List<CatDTO> getAllCats() {
        log.debug("REST request to get all Cats");
        return catService.findAll();
    }

    /**
     * {@code GET  /cats/:id} : get the "id" cat.
     *
     * @param id the id of the catDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the catDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CatDTO> getCat(@PathVariable Long id) {
        log.debug("REST request to get Cat : {}", id);
        Optional<CatDTO> catDTO = catService.findOne(id);
        return ResponseUtil.wrapOrNotFound(catDTO);
    }

    /**
     * {@code DELETE  /cats/:id} : delete the "id" cat.
     *
     * @param id the id of the catDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCat(@PathVariable Long id) {
        log.debug("REST request to delete Cat : {}", id);
        catService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
