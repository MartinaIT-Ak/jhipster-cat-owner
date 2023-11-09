package fr.it_akademy.jhipster.cat_owner.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.it_akademy.jhipster.cat_owner.IntegrationTest;
import fr.it_akademy.jhipster.cat_owner.domain.Dog;
import fr.it_akademy.jhipster.cat_owner.repository.DogRepository;
import fr.it_akademy.jhipster.cat_owner.service.dto.DogDTO;
import fr.it_akademy.jhipster.cat_owner.service.mapper.DogMapper;
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
 * Integration tests for the {@link DogResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DogResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_RACE = "AAAAAAAAAA";
    private static final String UPDATED_RACE = "BBBBBBBBBB";

    private static final Long DEFAULT_AGE = 1L;
    private static final Long UPDATED_AGE = 2L;

    private static final String DEFAULT_HEALTH_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_HEALTH_STATUS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/dogs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DogRepository dogRepository;

    @Autowired
    private DogMapper dogMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDogMockMvc;

    private Dog dog;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dog createEntity(EntityManager em) {
        Dog dog = new Dog().name(DEFAULT_NAME).race(DEFAULT_RACE).age(DEFAULT_AGE).healthStatus(DEFAULT_HEALTH_STATUS);
        return dog;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dog createUpdatedEntity(EntityManager em) {
        Dog dog = new Dog().name(UPDATED_NAME).race(UPDATED_RACE).age(UPDATED_AGE).healthStatus(UPDATED_HEALTH_STATUS);
        return dog;
    }

    @BeforeEach
    public void initTest() {
        dog = createEntity(em);
    }

    @Test
    @Transactional
    void createDog() throws Exception {
        int databaseSizeBeforeCreate = dogRepository.findAll().size();
        // Create the Dog
        DogDTO dogDTO = dogMapper.toDto(dog);
        restDogMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dogDTO)))
            .andExpect(status().isCreated());

        // Validate the Dog in the database
        List<Dog> dogList = dogRepository.findAll();
        assertThat(dogList).hasSize(databaseSizeBeforeCreate + 1);
        Dog testDog = dogList.get(dogList.size() - 1);
        assertThat(testDog.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDog.getRace()).isEqualTo(DEFAULT_RACE);
        assertThat(testDog.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testDog.getHealthStatus()).isEqualTo(DEFAULT_HEALTH_STATUS);
    }

    @Test
    @Transactional
    void createDogWithExistingId() throws Exception {
        // Create the Dog with an existing ID
        dog.setId(1L);
        DogDTO dogDTO = dogMapper.toDto(dog);

        int databaseSizeBeforeCreate = dogRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDogMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dogDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Dog in the database
        List<Dog> dogList = dogRepository.findAll();
        assertThat(dogList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDogs() throws Exception {
        // Initialize the database
        dogRepository.saveAndFlush(dog);

        // Get all the dogList
        restDogMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dog.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].race").value(hasItem(DEFAULT_RACE)))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE.intValue())))
            .andExpect(jsonPath("$.[*].healthStatus").value(hasItem(DEFAULT_HEALTH_STATUS)));
    }

    @Test
    @Transactional
    void getDog() throws Exception {
        // Initialize the database
        dogRepository.saveAndFlush(dog);

        // Get the dog
        restDogMockMvc
            .perform(get(ENTITY_API_URL_ID, dog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dog.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.race").value(DEFAULT_RACE))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE.intValue()))
            .andExpect(jsonPath("$.healthStatus").value(DEFAULT_HEALTH_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingDog() throws Exception {
        // Get the dog
        restDogMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDog() throws Exception {
        // Initialize the database
        dogRepository.saveAndFlush(dog);

        int databaseSizeBeforeUpdate = dogRepository.findAll().size();

        // Update the dog
        Dog updatedDog = dogRepository.findById(dog.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDog are not directly saved in db
        em.detach(updatedDog);
        updatedDog.name(UPDATED_NAME).race(UPDATED_RACE).age(UPDATED_AGE).healthStatus(UPDATED_HEALTH_STATUS);
        DogDTO dogDTO = dogMapper.toDto(updatedDog);

        restDogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dogDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dogDTO))
            )
            .andExpect(status().isOk());

        // Validate the Dog in the database
        List<Dog> dogList = dogRepository.findAll();
        assertThat(dogList).hasSize(databaseSizeBeforeUpdate);
        Dog testDog = dogList.get(dogList.size() - 1);
        assertThat(testDog.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDog.getRace()).isEqualTo(UPDATED_RACE);
        assertThat(testDog.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testDog.getHealthStatus()).isEqualTo(UPDATED_HEALTH_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingDog() throws Exception {
        int databaseSizeBeforeUpdate = dogRepository.findAll().size();
        dog.setId(longCount.incrementAndGet());

        // Create the Dog
        DogDTO dogDTO = dogMapper.toDto(dog);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dogDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dog in the database
        List<Dog> dogList = dogRepository.findAll();
        assertThat(dogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDog() throws Exception {
        int databaseSizeBeforeUpdate = dogRepository.findAll().size();
        dog.setId(longCount.incrementAndGet());

        // Create the Dog
        DogDTO dogDTO = dogMapper.toDto(dog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dog in the database
        List<Dog> dogList = dogRepository.findAll();
        assertThat(dogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDog() throws Exception {
        int databaseSizeBeforeUpdate = dogRepository.findAll().size();
        dog.setId(longCount.incrementAndGet());

        // Create the Dog
        DogDTO dogDTO = dogMapper.toDto(dog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDogMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dogDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Dog in the database
        List<Dog> dogList = dogRepository.findAll();
        assertThat(dogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDogWithPatch() throws Exception {
        // Initialize the database
        dogRepository.saveAndFlush(dog);

        int databaseSizeBeforeUpdate = dogRepository.findAll().size();

        // Update the dog using partial update
        Dog partialUpdatedDog = new Dog();
        partialUpdatedDog.setId(dog.getId());

        partialUpdatedDog.race(UPDATED_RACE).healthStatus(UPDATED_HEALTH_STATUS);

        restDogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDog.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDog))
            )
            .andExpect(status().isOk());

        // Validate the Dog in the database
        List<Dog> dogList = dogRepository.findAll();
        assertThat(dogList).hasSize(databaseSizeBeforeUpdate);
        Dog testDog = dogList.get(dogList.size() - 1);
        assertThat(testDog.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDog.getRace()).isEqualTo(UPDATED_RACE);
        assertThat(testDog.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testDog.getHealthStatus()).isEqualTo(UPDATED_HEALTH_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateDogWithPatch() throws Exception {
        // Initialize the database
        dogRepository.saveAndFlush(dog);

        int databaseSizeBeforeUpdate = dogRepository.findAll().size();

        // Update the dog using partial update
        Dog partialUpdatedDog = new Dog();
        partialUpdatedDog.setId(dog.getId());

        partialUpdatedDog.name(UPDATED_NAME).race(UPDATED_RACE).age(UPDATED_AGE).healthStatus(UPDATED_HEALTH_STATUS);

        restDogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDog.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDog))
            )
            .andExpect(status().isOk());

        // Validate the Dog in the database
        List<Dog> dogList = dogRepository.findAll();
        assertThat(dogList).hasSize(databaseSizeBeforeUpdate);
        Dog testDog = dogList.get(dogList.size() - 1);
        assertThat(testDog.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDog.getRace()).isEqualTo(UPDATED_RACE);
        assertThat(testDog.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testDog.getHealthStatus()).isEqualTo(UPDATED_HEALTH_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingDog() throws Exception {
        int databaseSizeBeforeUpdate = dogRepository.findAll().size();
        dog.setId(longCount.incrementAndGet());

        // Create the Dog
        DogDTO dogDTO = dogMapper.toDto(dog);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dogDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dog in the database
        List<Dog> dogList = dogRepository.findAll();
        assertThat(dogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDog() throws Exception {
        int databaseSizeBeforeUpdate = dogRepository.findAll().size();
        dog.setId(longCount.incrementAndGet());

        // Create the Dog
        DogDTO dogDTO = dogMapper.toDto(dog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dog in the database
        List<Dog> dogList = dogRepository.findAll();
        assertThat(dogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDog() throws Exception {
        int databaseSizeBeforeUpdate = dogRepository.findAll().size();
        dog.setId(longCount.incrementAndGet());

        // Create the Dog
        DogDTO dogDTO = dogMapper.toDto(dog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDogMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(dogDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Dog in the database
        List<Dog> dogList = dogRepository.findAll();
        assertThat(dogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDog() throws Exception {
        // Initialize the database
        dogRepository.saveAndFlush(dog);

        int databaseSizeBeforeDelete = dogRepository.findAll().size();

        // Delete the dog
        restDogMockMvc.perform(delete(ENTITY_API_URL_ID, dog.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Dog> dogList = dogRepository.findAll();
        assertThat(dogList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
