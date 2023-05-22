package com.udea.gr.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.udea.gr.IntegrationTest;
import com.udea.gr.domain.Estudiante;
import com.udea.gr.domain.Historiaacademica;
import com.udea.gr.domain.Planestudios;
import com.udea.gr.repository.HistoriaacademicaRepository;
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
 * Integration tests for the {@link HistoriaacademicaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HistoriaacademicaResourceIT {

    private static final String ENTITY_API_URL = "/api/historiaacademicas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private HistoriaacademicaRepository historiaacademicaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHistoriaacademicaMockMvc;

    private Historiaacademica historiaacademica;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Historiaacademica createEntity(EntityManager em) {
        Historiaacademica historiaacademica = new Historiaacademica();
        // Add required entity
        Planestudios planestudios;
        if (TestUtil.findAll(em, Planestudios.class).isEmpty()) {
            planestudios = PlanestudiosResourceIT.createEntity(em);
            em.persist(planestudios);
            em.flush();
        } else {
            planestudios = TestUtil.findAll(em, Planestudios.class).get(0);
        }
        historiaacademica.setPlanestudiosId(planestudios);
        // Add required entity
        Estudiante estudiante;
        if (TestUtil.findAll(em, Estudiante.class).isEmpty()) {
            estudiante = EstudianteResourceIT.createEntity(em);
            em.persist(estudiante);
            em.flush();
        } else {
            estudiante = TestUtil.findAll(em, Estudiante.class).get(0);
        }
        historiaacademica.setEstudianteid(estudiante);
        return historiaacademica;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Historiaacademica createUpdatedEntity(EntityManager em) {
        Historiaacademica historiaacademica = new Historiaacademica();
        // Add required entity
        Planestudios planestudios;
        if (TestUtil.findAll(em, Planestudios.class).isEmpty()) {
            planestudios = PlanestudiosResourceIT.createUpdatedEntity(em);
            em.persist(planestudios);
            em.flush();
        } else {
            planestudios = TestUtil.findAll(em, Planestudios.class).get(0);
        }
        historiaacademica.setPlanestudiosId(planestudios);
        // Add required entity
        Estudiante estudiante;
        if (TestUtil.findAll(em, Estudiante.class).isEmpty()) {
            estudiante = EstudianteResourceIT.createUpdatedEntity(em);
            em.persist(estudiante);
            em.flush();
        } else {
            estudiante = TestUtil.findAll(em, Estudiante.class).get(0);
        }
        historiaacademica.setEstudianteid(estudiante);
        return historiaacademica;
    }

    @BeforeEach
    public void initTest() {
        historiaacademica = createEntity(em);
    }

    @Test
    @Transactional
    void createHistoriaacademica() throws Exception {
        int databaseSizeBeforeCreate = historiaacademicaRepository.findAll().size();
        // Create the Historiaacademica
        restHistoriaacademicaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(historiaacademica))
            )
            .andExpect(status().isCreated());

        // Validate the Historiaacademica in the database
        List<Historiaacademica> historiaacademicaList = historiaacademicaRepository.findAll();
        assertThat(historiaacademicaList).hasSize(databaseSizeBeforeCreate + 1);
        Historiaacademica testHistoriaacademica = historiaacademicaList.get(historiaacademicaList.size() - 1);
    }

    @Test
    @Transactional
    void createHistoriaacademicaWithExistingId() throws Exception {
        // Create the Historiaacademica with an existing ID
        historiaacademica.setId(1L);

        int databaseSizeBeforeCreate = historiaacademicaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHistoriaacademicaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(historiaacademica))
            )
            .andExpect(status().isBadRequest());

        // Validate the Historiaacademica in the database
        List<Historiaacademica> historiaacademicaList = historiaacademicaRepository.findAll();
        assertThat(historiaacademicaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllHistoriaacademicas() throws Exception {
        // Initialize the database
        historiaacademicaRepository.saveAndFlush(historiaacademica);

        // Get all the historiaacademicaList
        restHistoriaacademicaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(historiaacademica.getId().intValue())));
    }

    @Test
    @Transactional
    void getHistoriaacademica() throws Exception {
        // Initialize the database
        historiaacademicaRepository.saveAndFlush(historiaacademica);

        // Get the historiaacademica
        restHistoriaacademicaMockMvc
            .perform(get(ENTITY_API_URL_ID, historiaacademica.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(historiaacademica.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingHistoriaacademica() throws Exception {
        // Get the historiaacademica
        restHistoriaacademicaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingHistoriaacademica() throws Exception {
        // Initialize the database
        historiaacademicaRepository.saveAndFlush(historiaacademica);

        int databaseSizeBeforeUpdate = historiaacademicaRepository.findAll().size();

        // Update the historiaacademica
        Historiaacademica updatedHistoriaacademica = historiaacademicaRepository.findById(historiaacademica.getId()).get();
        // Disconnect from session so that the updates on updatedHistoriaacademica are not directly saved in db
        em.detach(updatedHistoriaacademica);

        restHistoriaacademicaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedHistoriaacademica.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedHistoriaacademica))
            )
            .andExpect(status().isOk());

        // Validate the Historiaacademica in the database
        List<Historiaacademica> historiaacademicaList = historiaacademicaRepository.findAll();
        assertThat(historiaacademicaList).hasSize(databaseSizeBeforeUpdate);
        Historiaacademica testHistoriaacademica = historiaacademicaList.get(historiaacademicaList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingHistoriaacademica() throws Exception {
        int databaseSizeBeforeUpdate = historiaacademicaRepository.findAll().size();
        historiaacademica.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHistoriaacademicaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, historiaacademica.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(historiaacademica))
            )
            .andExpect(status().isBadRequest());

        // Validate the Historiaacademica in the database
        List<Historiaacademica> historiaacademicaList = historiaacademicaRepository.findAll();
        assertThat(historiaacademicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHistoriaacademica() throws Exception {
        int databaseSizeBeforeUpdate = historiaacademicaRepository.findAll().size();
        historiaacademica.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHistoriaacademicaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(historiaacademica))
            )
            .andExpect(status().isBadRequest());

        // Validate the Historiaacademica in the database
        List<Historiaacademica> historiaacademicaList = historiaacademicaRepository.findAll();
        assertThat(historiaacademicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHistoriaacademica() throws Exception {
        int databaseSizeBeforeUpdate = historiaacademicaRepository.findAll().size();
        historiaacademica.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHistoriaacademicaMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(historiaacademica))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Historiaacademica in the database
        List<Historiaacademica> historiaacademicaList = historiaacademicaRepository.findAll();
        assertThat(historiaacademicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHistoriaacademicaWithPatch() throws Exception {
        // Initialize the database
        historiaacademicaRepository.saveAndFlush(historiaacademica);

        int databaseSizeBeforeUpdate = historiaacademicaRepository.findAll().size();

        // Update the historiaacademica using partial update
        Historiaacademica partialUpdatedHistoriaacademica = new Historiaacademica();
        partialUpdatedHistoriaacademica.setId(historiaacademica.getId());

        restHistoriaacademicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHistoriaacademica.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHistoriaacademica))
            )
            .andExpect(status().isOk());

        // Validate the Historiaacademica in the database
        List<Historiaacademica> historiaacademicaList = historiaacademicaRepository.findAll();
        assertThat(historiaacademicaList).hasSize(databaseSizeBeforeUpdate);
        Historiaacademica testHistoriaacademica = historiaacademicaList.get(historiaacademicaList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateHistoriaacademicaWithPatch() throws Exception {
        // Initialize the database
        historiaacademicaRepository.saveAndFlush(historiaacademica);

        int databaseSizeBeforeUpdate = historiaacademicaRepository.findAll().size();

        // Update the historiaacademica using partial update
        Historiaacademica partialUpdatedHistoriaacademica = new Historiaacademica();
        partialUpdatedHistoriaacademica.setId(historiaacademica.getId());

        restHistoriaacademicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHistoriaacademica.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHistoriaacademica))
            )
            .andExpect(status().isOk());

        // Validate the Historiaacademica in the database
        List<Historiaacademica> historiaacademicaList = historiaacademicaRepository.findAll();
        assertThat(historiaacademicaList).hasSize(databaseSizeBeforeUpdate);
        Historiaacademica testHistoriaacademica = historiaacademicaList.get(historiaacademicaList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingHistoriaacademica() throws Exception {
        int databaseSizeBeforeUpdate = historiaacademicaRepository.findAll().size();
        historiaacademica.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHistoriaacademicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, historiaacademica.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(historiaacademica))
            )
            .andExpect(status().isBadRequest());

        // Validate the Historiaacademica in the database
        List<Historiaacademica> historiaacademicaList = historiaacademicaRepository.findAll();
        assertThat(historiaacademicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHistoriaacademica() throws Exception {
        int databaseSizeBeforeUpdate = historiaacademicaRepository.findAll().size();
        historiaacademica.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHistoriaacademicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(historiaacademica))
            )
            .andExpect(status().isBadRequest());

        // Validate the Historiaacademica in the database
        List<Historiaacademica> historiaacademicaList = historiaacademicaRepository.findAll();
        assertThat(historiaacademicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHistoriaacademica() throws Exception {
        int databaseSizeBeforeUpdate = historiaacademicaRepository.findAll().size();
        historiaacademica.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHistoriaacademicaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(historiaacademica))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Historiaacademica in the database
        List<Historiaacademica> historiaacademicaList = historiaacademicaRepository.findAll();
        assertThat(historiaacademicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHistoriaacademica() throws Exception {
        // Initialize the database
        historiaacademicaRepository.saveAndFlush(historiaacademica);

        int databaseSizeBeforeDelete = historiaacademicaRepository.findAll().size();

        // Delete the historiaacademica
        restHistoriaacademicaMockMvc
            .perform(delete(ENTITY_API_URL_ID, historiaacademica.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Historiaacademica> historiaacademicaList = historiaacademicaRepository.findAll();
        assertThat(historiaacademicaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
