package fr.it_akademy.jhipster.cat_owner.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.it_akademy.jhipster.cat_owner.IntegrationTest;
import fr.it_akademy.jhipster.cat_owner.domain.Cat;
import fr.it_akademy.jhipster.cat_owner.repository.CatRepository;
import fr.it_akademy.jhipster.cat_owner.service.dto.CatDTO;
import fr.it_akademy.jhipster.cat_owner.service.mapper.CatMapper;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CatResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CatResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_RACE = "AAAAAAAAAA";
    private static final String UPDATED_RACE = "BBBBBBBBBB";

    private static final Long DEFAULT_AGE = 1L;
    private static final Long UPDATED_AGE = 2L;

    private static final String DEFAULT_HEALT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_HEALT_STATUS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cats";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CatRepository catRepository;

    @Autowired
    private CatMapper catMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCatMockMvc;

    private Cat cat;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cat createEntity(EntityManager em) {
        Cat cat = new Cat().name(DEFAULT_NAME).race(DEFAULT_RACE).age(DEFAULT_AGE).healtStatus(DEFAULT_HEALT_STATUS);
        return cat;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cat createUpdatedEntity(EntityManager em) {
        Cat cat = new Cat().name(UPDATED_NAME).race(UPDATED_RACE).age(UPDATED_AGE).healtStatus(UPDATED_HEALT_STATUS);
        return cat;
    }

    @BeforeEach
    public void initTest() {
        cat = createEntity(em);
    }

    @Test
    @Transactional
    void createCat() throws Exception {
        int databaseSizeBeforeCreate = catRepository.findAll().size();
        // Create the Cat
        CatDTO catDTO = catMapper.toDto(cat);
        restCatMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(catDTO)))
            .andExpect(status().isCreated());

        // Validate the Cat in the database
        List<Cat> catList = catRepository.findAll();
        assertThat(catList).hasSize(databaseSizeBeforeCreate + 1);
        Cat testCat = catList.get(catList.size() - 1);
        assertThat(testCat.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCat.getRace()).isEqualTo(DEFAULT_RACE);
        assertThat(testCat.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testCat.getHealtStatus()).isEqualTo(DEFAULT_HEALT_STATUS);
    }

    @Test
    @Transactional
    void createCatWithExistingId() throws Exception {
        // Create the Cat with an existing ID
        cat.setId(1L);
        CatDTO catDTO = catMapper.toDto(cat);

        int databaseSizeBeforeCreate = catRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCatMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(catDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cat in the database
        List<Cat> catList = catRepository.findAll();
        assertThat(catList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCats() throws Exception {
        // Initialize the database
        catRepository.saveAndFlush(cat);

        // Get all the catList
        restCatMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cat.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].race").value(hasItem(DEFAULT_RACE)))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE.intValue())))
            .andExpect(jsonPath("$.[*].healtStatus").value(hasItem(DEFAULT_HEALT_STATUS)));
    }

    @Test
    @Transactional
    void getCat() throws Exception {
        // Initialize the database
        catRepository.saveAndFlush(cat);

        // Get the cat
        restCatMockMvc
            .perform(get(ENTITY_API_URL_ID, cat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cat.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.race").value(DEFAULT_RACE))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE.intValue()))
            .andExpect(jsonPath("$.healtStatus").value(DEFAULT_HEALT_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingCat() throws Exception {
        // Get the cat
        restCatMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCat() throws Exception {
        // Initialize the database
        catRepository.saveAndFlush(cat);

        int databaseSizeBeforeUpdate = catRepository.findAll().size();

        // Update the cat
        Cat updatedCat = catRepository.findById(cat.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCat are not directly saved in db
        em.detach(updatedCat);
        updatedCat.name(UPDATED_NAME).race(UPDATED_RACE).age(UPDATED_AGE).healtStatus(UPDATED_HEALT_STATUS);
        CatDTO catDTO = catMapper.toDto(updatedCat);

        restCatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, catDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(catDTO))
            )
            .andExpect(status().isOk());

        // Validate the Cat in the database
        List<Cat> catList = catRepository.findAll();
        assertThat(catList).hasSize(databaseSizeBeforeUpdate);
        Cat testCat = catList.get(catList.size() - 1);
        assertThat(testCat.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCat.getRace()).isEqualTo(UPDATED_RACE);
        assertThat(testCat.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testCat.getHealtStatus()).isEqualTo(UPDATED_HEALT_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingCat() throws Exception {
        int databaseSizeBeforeUpdate = catRepository.findAll().size();
        cat.setId(longCount.incrementAndGet());

        // Create the Cat
        CatDTO catDTO = catMapper.toDto(cat);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, catDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(catDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cat in the database
        List<Cat> catList = catRepository.findAll();
        assertThat(catList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCat() throws Exception {
        int databaseSizeBeforeUpdate = catRepository.findAll().size();
        cat.setId(longCount.incrementAndGet());

        // Create the Cat
        CatDTO catDTO = catMapper.toDto(cat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(catDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cat in the database
        List<Cat> catList = catRepository.findAll();
        assertThat(catList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCat() throws Exception {
        int databaseSizeBeforeUpdate = catRepository.findAll().size();
        cat.setId(longCount.incrementAndGet());

        // Create the Cat
        CatDTO catDTO = catMapper.toDto(cat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCatMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(catDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cat in the database
        List<Cat> catList = catRepository.findAll();
        assertThat(catList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCatWithPatch() throws Exception {
        // Initialize the database
        catRepository.saveAndFlush(cat);

        int databaseSizeBeforeUpdate = catRepository.findAll().size();

        // Update the cat using partial update
        Cat partialUpdatedCat = new Cat();
        partialUpdatedCat.setId(cat.getId());

        restCatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCat.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCat))
            )
            .andExpect(status().isOk());

        // Validate the Cat in the database
        List<Cat> catList = catRepository.findAll();
        assertThat(catList).hasSize(databaseSizeBeforeUpdate);
        Cat testCat = catList.get(catList.size() - 1);
        assertThat(testCat.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCat.getRace()).isEqualTo(DEFAULT_RACE);
        assertThat(testCat.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testCat.getHealtStatus()).isEqualTo(DEFAULT_HEALT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateCatWithPatch() throws Exception {
        // Initialize the database
        catRepository.saveAndFlush(cat);

        int databaseSizeBeforeUpdate = catRepository.findAll().size();

        // Update the cat using partial update
        Cat partialUpdatedCat = new Cat();
        partialUpdatedCat.setId(cat.getId());

        partialUpdatedCat.name(UPDATED_NAME).race(UPDATED_RACE).age(UPDATED_AGE).healtStatus(UPDATED_HEALT_STATUS);

        restCatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCat.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCat))
            )
            .andExpect(status().isOk());

        // Validate the Cat in the database
        List<Cat> catList = catRepository.findAll();
        assertThat(catList).hasSize(databaseSizeBeforeUpdate);
        Cat testCat = catList.get(catList.size() - 1);
        assertThat(testCat.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCat.getRace()).isEqualTo(UPDATED_RACE);
        assertThat(testCat.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testCat.getHealtStatus()).isEqualTo(UPDATED_HEALT_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingCat() throws Exception {
        int databaseSizeBeforeUpdate = catRepository.findAll().size();
        cat.setId(longCount.incrementAndGet());

        // Create the Cat
        CatDTO catDTO = catMapper.toDto(cat);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, catDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(catDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cat in the database
        List<Cat> catList = catRepository.findAll();
        assertThat(catList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCat() throws Exception {
        int databaseSizeBeforeUpdate = catRepository.findAll().size();
        cat.setId(longCount.incrementAndGet());

        // Create the Cat
        CatDTO catDTO = catMapper.toDto(cat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(catDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cat in the database
        List<Cat> catList = catRepository.findAll();
        assertThat(catList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCat() throws Exception {
        int databaseSizeBeforeUpdate = catRepository.findAll().size();
        cat.setId(longCount.incrementAndGet());

        // Create the Cat
        CatDTO catDTO = catMapper.toDto(cat);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCatMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(catDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cat in the database
        List<Cat> catList = catRepository.findAll();
        assertThat(catList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCat() throws Exception {
        // Initialize the database
        catRepository.saveAndFlush(cat);

        int databaseSizeBeforeDelete = catRepository.findAll().size();

        // Delete the cat
        restCatMockMvc.perform(delete(ENTITY_API_URL_ID, cat.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Cat> catList = catRepository.findAll();
        assertThat(catList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
