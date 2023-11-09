package fr.it_akademy.jhipster.cat_owner.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.it_akademy.jhipster.cat_owner.IntegrationTest;
import fr.it_akademy.jhipster.cat_owner.domain.Veterinary;
import fr.it_akademy.jhipster.cat_owner.repository.VeterinaryRepository;
import fr.it_akademy.jhipster.cat_owner.service.dto.VeterinaryDTO;
import fr.it_akademy.jhipster.cat_owner.service.mapper.VeterinaryMapper;
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
 * Integration tests for the {@link VeterinaryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VeterinaryResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final Long DEFAULT_PHONE_NUMBER = 1L;
    private static final Long UPDATED_PHONE_NUMBER = 2L;

    private static final String ENTITY_API_URL = "/api/veterinaries";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VeterinaryRepository veterinaryRepository;

    @Autowired
    private VeterinaryMapper veterinaryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVeterinaryMockMvc;

    private Veterinary veterinary;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Veterinary createEntity(EntityManager em) {
        Veterinary veterinary = new Veterinary()
            .title(DEFAULT_TITLE)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .address(DEFAULT_ADDRESS)
            .phoneNumber(DEFAULT_PHONE_NUMBER);
        return veterinary;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Veterinary createUpdatedEntity(EntityManager em) {
        Veterinary veterinary = new Veterinary()
            .title(UPDATED_TITLE)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .address(UPDATED_ADDRESS)
            .phoneNumber(UPDATED_PHONE_NUMBER);
        return veterinary;
    }

    @BeforeEach
    public void initTest() {
        veterinary = createEntity(em);
    }

    @Test
    @Transactional
    void createVeterinary() throws Exception {
        int databaseSizeBeforeCreate = veterinaryRepository.findAll().size();
        // Create the Veterinary
        VeterinaryDTO veterinaryDTO = veterinaryMapper.toDto(veterinary);
        restVeterinaryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(veterinaryDTO)))
            .andExpect(status().isCreated());

        // Validate the Veterinary in the database
        List<Veterinary> veterinaryList = veterinaryRepository.findAll();
        assertThat(veterinaryList).hasSize(databaseSizeBeforeCreate + 1);
        Veterinary testVeterinary = veterinaryList.get(veterinaryList.size() - 1);
        assertThat(testVeterinary.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testVeterinary.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testVeterinary.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testVeterinary.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testVeterinary.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void createVeterinaryWithExistingId() throws Exception {
        // Create the Veterinary with an existing ID
        veterinary.setId(1L);
        VeterinaryDTO veterinaryDTO = veterinaryMapper.toDto(veterinary);

        int databaseSizeBeforeCreate = veterinaryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVeterinaryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(veterinaryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Veterinary in the database
        List<Veterinary> veterinaryList = veterinaryRepository.findAll();
        assertThat(veterinaryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVeterinaries() throws Exception {
        // Initialize the database
        veterinaryRepository.saveAndFlush(veterinary);

        // Get all the veterinaryList
        restVeterinaryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(veterinary.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.intValue())));
    }

    @Test
    @Transactional
    void getVeterinary() throws Exception {
        // Initialize the database
        veterinaryRepository.saveAndFlush(veterinary);

        // Get the veterinary
        restVeterinaryMockMvc
            .perform(get(ENTITY_API_URL_ID, veterinary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(veterinary.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingVeterinary() throws Exception {
        // Get the veterinary
        restVeterinaryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVeterinary() throws Exception {
        // Initialize the database
        veterinaryRepository.saveAndFlush(veterinary);

        int databaseSizeBeforeUpdate = veterinaryRepository.findAll().size();

        // Update the veterinary
        Veterinary updatedVeterinary = veterinaryRepository.findById(veterinary.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVeterinary are not directly saved in db
        em.detach(updatedVeterinary);
        updatedVeterinary
            .title(UPDATED_TITLE)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .address(UPDATED_ADDRESS)
            .phoneNumber(UPDATED_PHONE_NUMBER);
        VeterinaryDTO veterinaryDTO = veterinaryMapper.toDto(updatedVeterinary);

        restVeterinaryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, veterinaryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(veterinaryDTO))
            )
            .andExpect(status().isOk());

        // Validate the Veterinary in the database
        List<Veterinary> veterinaryList = veterinaryRepository.findAll();
        assertThat(veterinaryList).hasSize(databaseSizeBeforeUpdate);
        Veterinary testVeterinary = veterinaryList.get(veterinaryList.size() - 1);
        assertThat(testVeterinary.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testVeterinary.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testVeterinary.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testVeterinary.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testVeterinary.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void putNonExistingVeterinary() throws Exception {
        int databaseSizeBeforeUpdate = veterinaryRepository.findAll().size();
        veterinary.setId(longCount.incrementAndGet());

        // Create the Veterinary
        VeterinaryDTO veterinaryDTO = veterinaryMapper.toDto(veterinary);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVeterinaryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, veterinaryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(veterinaryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Veterinary in the database
        List<Veterinary> veterinaryList = veterinaryRepository.findAll();
        assertThat(veterinaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVeterinary() throws Exception {
        int databaseSizeBeforeUpdate = veterinaryRepository.findAll().size();
        veterinary.setId(longCount.incrementAndGet());

        // Create the Veterinary
        VeterinaryDTO veterinaryDTO = veterinaryMapper.toDto(veterinary);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVeterinaryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(veterinaryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Veterinary in the database
        List<Veterinary> veterinaryList = veterinaryRepository.findAll();
        assertThat(veterinaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVeterinary() throws Exception {
        int databaseSizeBeforeUpdate = veterinaryRepository.findAll().size();
        veterinary.setId(longCount.incrementAndGet());

        // Create the Veterinary
        VeterinaryDTO veterinaryDTO = veterinaryMapper.toDto(veterinary);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVeterinaryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(veterinaryDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Veterinary in the database
        List<Veterinary> veterinaryList = veterinaryRepository.findAll();
        assertThat(veterinaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVeterinaryWithPatch() throws Exception {
        // Initialize the database
        veterinaryRepository.saveAndFlush(veterinary);

        int databaseSizeBeforeUpdate = veterinaryRepository.findAll().size();

        // Update the veterinary using partial update
        Veterinary partialUpdatedVeterinary = new Veterinary();
        partialUpdatedVeterinary.setId(veterinary.getId());

        partialUpdatedVeterinary
            .title(UPDATED_TITLE)
            .lastName(UPDATED_LAST_NAME)
            .address(UPDATED_ADDRESS)
            .phoneNumber(UPDATED_PHONE_NUMBER);

        restVeterinaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVeterinary.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVeterinary))
            )
            .andExpect(status().isOk());

        // Validate the Veterinary in the database
        List<Veterinary> veterinaryList = veterinaryRepository.findAll();
        assertThat(veterinaryList).hasSize(databaseSizeBeforeUpdate);
        Veterinary testVeterinary = veterinaryList.get(veterinaryList.size() - 1);
        assertThat(testVeterinary.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testVeterinary.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testVeterinary.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testVeterinary.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testVeterinary.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void fullUpdateVeterinaryWithPatch() throws Exception {
        // Initialize the database
        veterinaryRepository.saveAndFlush(veterinary);

        int databaseSizeBeforeUpdate = veterinaryRepository.findAll().size();

        // Update the veterinary using partial update
        Veterinary partialUpdatedVeterinary = new Veterinary();
        partialUpdatedVeterinary.setId(veterinary.getId());

        partialUpdatedVeterinary
            .title(UPDATED_TITLE)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .address(UPDATED_ADDRESS)
            .phoneNumber(UPDATED_PHONE_NUMBER);

        restVeterinaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVeterinary.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVeterinary))
            )
            .andExpect(status().isOk());

        // Validate the Veterinary in the database
        List<Veterinary> veterinaryList = veterinaryRepository.findAll();
        assertThat(veterinaryList).hasSize(databaseSizeBeforeUpdate);
        Veterinary testVeterinary = veterinaryList.get(veterinaryList.size() - 1);
        assertThat(testVeterinary.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testVeterinary.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testVeterinary.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testVeterinary.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testVeterinary.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void patchNonExistingVeterinary() throws Exception {
        int databaseSizeBeforeUpdate = veterinaryRepository.findAll().size();
        veterinary.setId(longCount.incrementAndGet());

        // Create the Veterinary
        VeterinaryDTO veterinaryDTO = veterinaryMapper.toDto(veterinary);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVeterinaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, veterinaryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(veterinaryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Veterinary in the database
        List<Veterinary> veterinaryList = veterinaryRepository.findAll();
        assertThat(veterinaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVeterinary() throws Exception {
        int databaseSizeBeforeUpdate = veterinaryRepository.findAll().size();
        veterinary.setId(longCount.incrementAndGet());

        // Create the Veterinary
        VeterinaryDTO veterinaryDTO = veterinaryMapper.toDto(veterinary);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVeterinaryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(veterinaryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Veterinary in the database
        List<Veterinary> veterinaryList = veterinaryRepository.findAll();
        assertThat(veterinaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVeterinary() throws Exception {
        int databaseSizeBeforeUpdate = veterinaryRepository.findAll().size();
        veterinary.setId(longCount.incrementAndGet());

        // Create the Veterinary
        VeterinaryDTO veterinaryDTO = veterinaryMapper.toDto(veterinary);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVeterinaryMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(veterinaryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Veterinary in the database
        List<Veterinary> veterinaryList = veterinaryRepository.findAll();
        assertThat(veterinaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVeterinary() throws Exception {
        // Initialize the database
        veterinaryRepository.saveAndFlush(veterinary);

        int databaseSizeBeforeDelete = veterinaryRepository.findAll().size();

        // Delete the veterinary
        restVeterinaryMockMvc
            .perform(delete(ENTITY_API_URL_ID, veterinary.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Veterinary> veterinaryList = veterinaryRepository.findAll();
        assertThat(veterinaryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
