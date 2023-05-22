package com.udea.gr.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.udea.gr.IntegrationTest;
import com.udea.gr.domain.Planestudios;
import com.udea.gr.repository.PlanestudiosRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PlanestudiosResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PlanestudiosResourceIT {

    private static final Integer DEFAULT_IDPROGRAMA = 1;
    private static final Integer UPDATED_IDPROGRAMA = 2;

    private static final String DEFAULT_FACULTAD = "AAAAAAAAAA";
    private static final String UPDATED_FACULTAD = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBREPROGRAMA = "AAAAAAAAAA";
    private static final String UPDATED_NOMBREPROGRAMA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/planestudios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PlanestudiosRepository planestudiosRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlanestudiosMockMvc;

    private Planestudios planestudios;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Planestudios createEntity(EntityManager em) {
        Planestudios planestudios = new Planestudios()
            .idprograma(DEFAULT_IDPROGRAMA)
            .facultad(DEFAULT_FACULTAD)
            .nombreprograma(DEFAULT_NOMBREPROGRAMA);
        return planestudios;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Planestudios createUpdatedEntity(EntityManager em) {
        Planestudios planestudios = new Planestudios()
            .idprograma(UPDATED_IDPROGRAMA)
            .facultad(UPDATED_FACULTAD)
            .nombreprograma(UPDATED_NOMBREPROGRAMA);
        return planestudios;
    }

    @BeforeEach
    public void initTest() {
        planestudios = createEntity(em);
    }

    @Test
    @Transactional
    void createPlanestudios() throws Exception {
        int databaseSizeBeforeCreate = planestudiosRepository.findAll().size();
        // Create the Planestudios
        restPlanestudiosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planestudios)))
            .andExpect(status().isCreated());

        // Validate the Planestudios in the database
        List<Planestudios> planestudiosList = planestudiosRepository.findAll();
        assertThat(planestudiosList).hasSize(databaseSizeBeforeCreate + 1);
        Planestudios testPlanestudios = planestudiosList.get(planestudiosList.size() - 1);
        assertThat(testPlanestudios.getIdprograma()).isEqualTo(DEFAULT_IDPROGRAMA);
        assertThat(testPlanestudios.getFacultad()).isEqualTo(DEFAULT_FACULTAD);
        assertThat(testPlanestudios.getNombreprograma()).isEqualTo(DEFAULT_NOMBREPROGRAMA);
    }

    @Test
    @Transactional
    void createPlanestudiosWithExistingId() throws Exception {
        // Create the Planestudios with an existing ID
        planestudios.setId(1L);

        int databaseSizeBeforeCreate = planestudiosRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlanestudiosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planestudios)))
            .andExpect(status().isBadRequest());

        // Validate the Planestudios in the database
        List<Planestudios> planestudiosList = planestudiosRepository.findAll();
        assertThat(planestudiosList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIdprogramaIsRequired() throws Exception {
        int databaseSizeBeforeTest = planestudiosRepository.findAll().size();
        // set the field null
        planestudios.setIdprograma(null);

        // Create the Planestudios, which fails.

        restPlanestudiosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planestudios)))
            .andExpect(status().isBadRequest());

        List<Planestudios> planestudiosList = planestudiosRepository.findAll();
        assertThat(planestudiosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFacultadIsRequired() throws Exception {
        int databaseSizeBeforeTest = planestudiosRepository.findAll().size();
        // set the field null
        planestudios.setFacultad(null);

        // Create the Planestudios, which fails.

        restPlanestudiosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planestudios)))
            .andExpect(status().isBadRequest());

        List<Planestudios> planestudiosList = planestudiosRepository.findAll();
        assertThat(planestudiosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNombreprogramaIsRequired() throws Exception {
        int databaseSizeBeforeTest = planestudiosRepository.findAll().size();
        // set the field null
        planestudios.setNombreprograma(null);

        // Create the Planestudios, which fails.

        restPlanestudiosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planestudios)))
            .andExpect(status().isBadRequest());

        List<Planestudios> planestudiosList = planestudiosRepository.findAll();
        assertThat(planestudiosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPlanestudios() throws Exception {
        // Initialize the database
        planestudiosRepository.saveAndFlush(planestudios);

        // Get all the planestudiosList
        restPlanestudiosMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planestudios.getId().intValue())))
            .andExpect(jsonPath("$.[*].idprograma").value(hasItem(DEFAULT_IDPROGRAMA)))
            .andExpect(jsonPath("$.[*].facultad").value(hasItem(DEFAULT_FACULTAD)))
            .andExpect(jsonPath("$.[*].nombreprograma").value(hasItem(DEFAULT_NOMBREPROGRAMA)));
    }

    @Test
    @Transactional
    void getPlanestudios() throws Exception {
        // Initialize the database
        planestudiosRepository.saveAndFlush(planestudios);

        // Get the planestudios
        restPlanestudiosMockMvc
            .perform(get(ENTITY_API_URL_ID, planestudios.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(planestudios.getId().intValue()))
            .andExpect(jsonPath("$.idprograma").value(DEFAULT_IDPROGRAMA))
            .andExpect(jsonPath("$.facultad").value(DEFAULT_FACULTAD))
            .andExpect(jsonPath("$.nombreprograma").value(DEFAULT_NOMBREPROGRAMA));
    }

    @Test
    @Transactional
    void getNonExistingPlanestudios() throws Exception {
        // Get the planestudios
        restPlanestudiosMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPlanestudios() throws Exception {
        // Initialize the database
        planestudiosRepository.saveAndFlush(planestudios);

        int databaseSizeBeforeUpdate = planestudiosRepository.findAll().size();

        // Update the planestudios
        Planestudios updatedPlanestudios = planestudiosRepository.findById(planestudios.getId()).get();
        // Disconnect from session so that the updates on updatedPlanestudios are not directly saved in db
        em.detach(updatedPlanestudios);
        updatedPlanestudios.idprograma(UPDATED_IDPROGRAMA).facultad(UPDATED_FACULTAD).nombreprograma(UPDATED_NOMBREPROGRAMA);

        restPlanestudiosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPlanestudios.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPlanestudios))
            )
            .andExpect(status().isOk());

        // Validate the Planestudios in the database
        List<Planestudios> planestudiosList = planestudiosRepository.findAll();
        assertThat(planestudiosList).hasSize(databaseSizeBeforeUpdate);
        Planestudios testPlanestudios = planestudiosList.get(planestudiosList.size() - 1);
        assertThat(testPlanestudios.getIdprograma()).isEqualTo(UPDATED_IDPROGRAMA);
        assertThat(testPlanestudios.getFacultad()).isEqualTo(UPDATED_FACULTAD);
        assertThat(testPlanestudios.getNombreprograma()).isEqualTo(UPDATED_NOMBREPROGRAMA);
    }

    @Test
    @Transactional
    void putNonExistingPlanestudios() throws Exception {
        int databaseSizeBeforeUpdate = planestudiosRepository.findAll().size();
        planestudios.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanestudiosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, planestudios.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(planestudios))
            )
            .andExpect(status().isBadRequest());

        // Validate the Planestudios in the database
        List<Planestudios> planestudiosList = planestudiosRepository.findAll();
        assertThat(planestudiosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPlanestudios() throws Exception {
        int databaseSizeBeforeUpdate = planestudiosRepository.findAll().size();
        planestudios.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanestudiosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(planestudios))
            )
            .andExpect(status().isBadRequest());

        // Validate the Planestudios in the database
        List<Planestudios> planestudiosList = planestudiosRepository.findAll();
        assertThat(planestudiosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPlanestudios() throws Exception {
        int databaseSizeBeforeUpdate = planestudiosRepository.findAll().size();
        planestudios.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanestudiosMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planestudios)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Planestudios in the database
        List<Planestudios> planestudiosList = planestudiosRepository.findAll();
        assertThat(planestudiosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePlanestudiosWithPatch() throws Exception {
        // Initialize the database
        planestudiosRepository.saveAndFlush(planestudios);

        int databaseSizeBeforeUpdate = planestudiosRepository.findAll().size();

        // Update the planestudios using partial update
        Planestudios partialUpdatedPlanestudios = new Planestudios();
        partialUpdatedPlanestudios.setId(planestudios.getId());

        partialUpdatedPlanestudios.idprograma(UPDATED_IDPROGRAMA);

        restPlanestudiosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlanestudios.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlanestudios))
            )
            .andExpect(status().isOk());

        // Validate the Planestudios in the database
        List<Planestudios> planestudiosList = planestudiosRepository.findAll();
        assertThat(planestudiosList).hasSize(databaseSizeBeforeUpdate);
        Planestudios testPlanestudios = planestudiosList.get(planestudiosList.size() - 1);
        assertThat(testPlanestudios.getIdprograma()).isEqualTo(UPDATED_IDPROGRAMA);
        assertThat(testPlanestudios.getFacultad()).isEqualTo(DEFAULT_FACULTAD);
        assertThat(testPlanestudios.getNombreprograma()).isEqualTo(DEFAULT_NOMBREPROGRAMA);
    }

    @Test
    @Transactional
    void fullUpdatePlanestudiosWithPatch() throws Exception {
        // Initialize the database
        planestudiosRepository.saveAndFlush(planestudios);

        int databaseSizeBeforeUpdate = planestudiosRepository.findAll().size();

        // Update the planestudios using partial update
        Planestudios partialUpdatedPlanestudios = new Planestudios();
        partialUpdatedPlanestudios.setId(planestudios.getId());

        partialUpdatedPlanestudios.idprograma(UPDATED_IDPROGRAMA).facultad(UPDATED_FACULTAD).nombreprograma(UPDATED_NOMBREPROGRAMA);

        restPlanestudiosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlanestudios.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlanestudios))
            )
            .andExpect(status().isOk());

        // Validate the Planestudios in the database
        List<Planestudios> planestudiosList = planestudiosRepository.findAll();
        assertThat(planestudiosList).hasSize(databaseSizeBeforeUpdate);
        Planestudios testPlanestudios = planestudiosList.get(planestudiosList.size() - 1);
        assertThat(testPlanestudios.getIdprograma()).isEqualTo(UPDATED_IDPROGRAMA);
        assertThat(testPlanestudios.getFacultad()).isEqualTo(UPDATED_FACULTAD);
        assertThat(testPlanestudios.getNombreprograma()).isEqualTo(UPDATED_NOMBREPROGRAMA);
    }

    @Test
    @Transactional
    void patchNonExistingPlanestudios() throws Exception {
        int databaseSizeBeforeUpdate = planestudiosRepository.findAll().size();
        planestudios.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanestudiosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, planestudios.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(planestudios))
            )
            .andExpect(status().isBadRequest());

        // Validate the Planestudios in the database
        List<Planestudios> planestudiosList = planestudiosRepository.findAll();
        assertThat(planestudiosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPlanestudios() throws Exception {
        int databaseSizeBeforeUpdate = planestudiosRepository.findAll().size();
        planestudios.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanestudiosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(planestudios))
            )
            .andExpect(status().isBadRequest());

        // Validate the Planestudios in the database
        List<Planestudios> planestudiosList = planestudiosRepository.findAll();
        assertThat(planestudiosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPlanestudios() throws Exception {
        int databaseSizeBeforeUpdate = planestudiosRepository.findAll().size();
        planestudios.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanestudiosMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(planestudios))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Planestudios in the database
        List<Planestudios> planestudiosList = planestudiosRepository.findAll();
        assertThat(planestudiosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePlanestudios() throws Exception {
        // Initialize the database
        planestudiosRepository.saveAndFlush(planestudios);

        int databaseSizeBeforeDelete = planestudiosRepository.findAll().size();

        // Delete the planestudios
        restPlanestudiosMockMvc
            .perform(delete(ENTITY_API_URL_ID, planestudios.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Planestudios> planestudiosList = planestudiosRepository.findAll();
        assertThat(planestudiosList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
