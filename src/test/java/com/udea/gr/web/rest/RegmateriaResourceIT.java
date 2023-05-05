package com.udea.gr.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.udea.gr.IntegrationTest;
import com.udea.gr.domain.Historiaacademica;
import com.udea.gr.domain.Materia;
import com.udea.gr.domain.Regmateria;
import com.udea.gr.repository.RegmateriaRepository;
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
 * Integration tests for the {@link RegmateriaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RegmateriaResourceIT {

    private static final String DEFAULT_ESTADO = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/regmaterias";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RegmateriaRepository regmateriaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRegmateriaMockMvc;

    private Regmateria regmateria;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Regmateria createEntity(EntityManager em) {
        Regmateria regmateria = new Regmateria().estado(DEFAULT_ESTADO);
        // Add required entity
        Materia materia;
        if (TestUtil.findAll(em, Materia.class).isEmpty()) {
            materia = MateriaResourceIT.createEntity(em);
            em.persist(materia);
            em.flush();
        } else {
            materia = TestUtil.findAll(em, Materia.class).get(0);
        }
        regmateria.setMateriaId(materia);
        // Add required entity
        Historiaacademica historiaacademica;
        if (TestUtil.findAll(em, Historiaacademica.class).isEmpty()) {
            historiaacademica = HistoriaacademicaResourceIT.createEntity(em);
            em.persist(historiaacademica);
            em.flush();
        } else {
            historiaacademica = TestUtil.findAll(em, Historiaacademica.class).get(0);
        }
        regmateria.setHistoriaacademicaId(historiaacademica);
        return regmateria;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Regmateria createUpdatedEntity(EntityManager em) {
        Regmateria regmateria = new Regmateria().estado(UPDATED_ESTADO);
        // Add required entity
        Materia materia;
        if (TestUtil.findAll(em, Materia.class).isEmpty()) {
            materia = MateriaResourceIT.createUpdatedEntity(em);
            em.persist(materia);
            em.flush();
        } else {
            materia = TestUtil.findAll(em, Materia.class).get(0);
        }
        regmateria.setMateriaId(materia);
        // Add required entity
        Historiaacademica historiaacademica;
        if (TestUtil.findAll(em, Historiaacademica.class).isEmpty()) {
            historiaacademica = HistoriaacademicaResourceIT.createUpdatedEntity(em);
            em.persist(historiaacademica);
            em.flush();
        } else {
            historiaacademica = TestUtil.findAll(em, Historiaacademica.class).get(0);
        }
        regmateria.setHistoriaacademicaId(historiaacademica);
        return regmateria;
    }

    @BeforeEach
    public void initTest() {
        regmateria = createEntity(em);
    }

    @Test
    @Transactional
    void createRegmateria() throws Exception {
        int databaseSizeBeforeCreate = regmateriaRepository.findAll().size();
        // Create the Regmateria
        restRegmateriaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(regmateria)))
            .andExpect(status().isCreated());

        // Validate the Regmateria in the database
        List<Regmateria> regmateriaList = regmateriaRepository.findAll();
        assertThat(regmateriaList).hasSize(databaseSizeBeforeCreate + 1);
        Regmateria testRegmateria = regmateriaList.get(regmateriaList.size() - 1);
        assertThat(testRegmateria.getEstado()).isEqualTo(DEFAULT_ESTADO);
    }

    @Test
    @Transactional
    void createRegmateriaWithExistingId() throws Exception {
        // Create the Regmateria with an existing ID
        regmateria.setId(1L);

        int databaseSizeBeforeCreate = regmateriaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRegmateriaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(regmateria)))
            .andExpect(status().isBadRequest());

        // Validate the Regmateria in the database
        List<Regmateria> regmateriaList = regmateriaRepository.findAll();
        assertThat(regmateriaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEstadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = regmateriaRepository.findAll().size();
        // set the field null
        regmateria.setEstado(null);

        // Create the Regmateria, which fails.

        restRegmateriaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(regmateria)))
            .andExpect(status().isBadRequest());

        List<Regmateria> regmateriaList = regmateriaRepository.findAll();
        assertThat(regmateriaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRegmaterias() throws Exception {
        // Initialize the database
        regmateriaRepository.saveAndFlush(regmateria);

        // Get all the regmateriaList
        restRegmateriaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(regmateria.getId().intValue())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO)));
    }

    @Test
    @Transactional
    void getRegmateria() throws Exception {
        // Initialize the database
        regmateriaRepository.saveAndFlush(regmateria);

        // Get the regmateria
        restRegmateriaMockMvc
            .perform(get(ENTITY_API_URL_ID, regmateria.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(regmateria.getId().intValue()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO));
    }

    @Test
    @Transactional
    void getNonExistingRegmateria() throws Exception {
        // Get the regmateria
        restRegmateriaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRegmateria() throws Exception {
        // Initialize the database
        regmateriaRepository.saveAndFlush(regmateria);

        int databaseSizeBeforeUpdate = regmateriaRepository.findAll().size();

        // Update the regmateria
        Regmateria updatedRegmateria = regmateriaRepository.findById(regmateria.getId()).get();
        // Disconnect from session so that the updates on updatedRegmateria are not directly saved in db
        em.detach(updatedRegmateria);
        updatedRegmateria.estado(UPDATED_ESTADO);

        restRegmateriaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRegmateria.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRegmateria))
            )
            .andExpect(status().isOk());

        // Validate the Regmateria in the database
        List<Regmateria> regmateriaList = regmateriaRepository.findAll();
        assertThat(regmateriaList).hasSize(databaseSizeBeforeUpdate);
        Regmateria testRegmateria = regmateriaList.get(regmateriaList.size() - 1);
        assertThat(testRegmateria.getEstado()).isEqualTo(UPDATED_ESTADO);
    }

    @Test
    @Transactional
    void putNonExistingRegmateria() throws Exception {
        int databaseSizeBeforeUpdate = regmateriaRepository.findAll().size();
        regmateria.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegmateriaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, regmateria.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(regmateria))
            )
            .andExpect(status().isBadRequest());

        // Validate the Regmateria in the database
        List<Regmateria> regmateriaList = regmateriaRepository.findAll();
        assertThat(regmateriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRegmateria() throws Exception {
        int databaseSizeBeforeUpdate = regmateriaRepository.findAll().size();
        regmateria.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegmateriaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(regmateria))
            )
            .andExpect(status().isBadRequest());

        // Validate the Regmateria in the database
        List<Regmateria> regmateriaList = regmateriaRepository.findAll();
        assertThat(regmateriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRegmateria() throws Exception {
        int databaseSizeBeforeUpdate = regmateriaRepository.findAll().size();
        regmateria.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegmateriaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(regmateria)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Regmateria in the database
        List<Regmateria> regmateriaList = regmateriaRepository.findAll();
        assertThat(regmateriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRegmateriaWithPatch() throws Exception {
        // Initialize the database
        regmateriaRepository.saveAndFlush(regmateria);

        int databaseSizeBeforeUpdate = regmateriaRepository.findAll().size();

        // Update the regmateria using partial update
        Regmateria partialUpdatedRegmateria = new Regmateria();
        partialUpdatedRegmateria.setId(regmateria.getId());

        partialUpdatedRegmateria.estado(UPDATED_ESTADO);

        restRegmateriaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRegmateria.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRegmateria))
            )
            .andExpect(status().isOk());

        // Validate the Regmateria in the database
        List<Regmateria> regmateriaList = regmateriaRepository.findAll();
        assertThat(regmateriaList).hasSize(databaseSizeBeforeUpdate);
        Regmateria testRegmateria = regmateriaList.get(regmateriaList.size() - 1);
        assertThat(testRegmateria.getEstado()).isEqualTo(UPDATED_ESTADO);
    }

    @Test
    @Transactional
    void fullUpdateRegmateriaWithPatch() throws Exception {
        // Initialize the database
        regmateriaRepository.saveAndFlush(regmateria);

        int databaseSizeBeforeUpdate = regmateriaRepository.findAll().size();

        // Update the regmateria using partial update
        Regmateria partialUpdatedRegmateria = new Regmateria();
        partialUpdatedRegmateria.setId(regmateria.getId());

        partialUpdatedRegmateria.estado(UPDATED_ESTADO);

        restRegmateriaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRegmateria.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRegmateria))
            )
            .andExpect(status().isOk());

        // Validate the Regmateria in the database
        List<Regmateria> regmateriaList = regmateriaRepository.findAll();
        assertThat(regmateriaList).hasSize(databaseSizeBeforeUpdate);
        Regmateria testRegmateria = regmateriaList.get(regmateriaList.size() - 1);
        assertThat(testRegmateria.getEstado()).isEqualTo(UPDATED_ESTADO);
    }

    @Test
    @Transactional
    void patchNonExistingRegmateria() throws Exception {
        int databaseSizeBeforeUpdate = regmateriaRepository.findAll().size();
        regmateria.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegmateriaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, regmateria.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(regmateria))
            )
            .andExpect(status().isBadRequest());

        // Validate the Regmateria in the database
        List<Regmateria> regmateriaList = regmateriaRepository.findAll();
        assertThat(regmateriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRegmateria() throws Exception {
        int databaseSizeBeforeUpdate = regmateriaRepository.findAll().size();
        regmateria.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegmateriaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(regmateria))
            )
            .andExpect(status().isBadRequest());

        // Validate the Regmateria in the database
        List<Regmateria> regmateriaList = regmateriaRepository.findAll();
        assertThat(regmateriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRegmateria() throws Exception {
        int databaseSizeBeforeUpdate = regmateriaRepository.findAll().size();
        regmateria.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegmateriaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(regmateria))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Regmateria in the database
        List<Regmateria> regmateriaList = regmateriaRepository.findAll();
        assertThat(regmateriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRegmateria() throws Exception {
        // Initialize the database
        regmateriaRepository.saveAndFlush(regmateria);

        int databaseSizeBeforeDelete = regmateriaRepository.findAll().size();

        // Delete the regmateria
        restRegmateriaMockMvc
            .perform(delete(ENTITY_API_URL_ID, regmateria.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Regmateria> regmateriaList = regmateriaRepository.findAll();
        assertThat(regmateriaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
