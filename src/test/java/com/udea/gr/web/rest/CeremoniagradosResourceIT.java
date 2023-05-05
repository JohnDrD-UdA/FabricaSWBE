package com.udea.gr.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.udea.gr.IntegrationTest;
import com.udea.gr.domain.Ceremoniagrados;
import com.udea.gr.repository.CeremoniagradosRepository;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link CeremoniagradosResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CeremoniagradosResourceIT {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_LIMINSCRIPCION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LIMINSCRIPCION = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_LUGAR = "AAAAAAAAAA";
    private static final String UPDATED_LUGAR = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ceremoniagrados";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CeremoniagradosRepository ceremoniagradosRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCeremoniagradosMockMvc;

    private Ceremoniagrados ceremoniagrados;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ceremoniagrados createEntity(EntityManager em) {
        Ceremoniagrados ceremoniagrados = new Ceremoniagrados()
            .fecha(DEFAULT_FECHA)
            .liminscripcion(DEFAULT_LIMINSCRIPCION)
            .lugar(DEFAULT_LUGAR);
        return ceremoniagrados;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ceremoniagrados createUpdatedEntity(EntityManager em) {
        Ceremoniagrados ceremoniagrados = new Ceremoniagrados()
            .fecha(UPDATED_FECHA)
            .liminscripcion(UPDATED_LIMINSCRIPCION)
            .lugar(UPDATED_LUGAR);
        return ceremoniagrados;
    }

    @BeforeEach
    public void initTest() {
        ceremoniagrados = createEntity(em);
    }

    @Test
    @Transactional
    void createCeremoniagrados() throws Exception {
        int databaseSizeBeforeCreate = ceremoniagradosRepository.findAll().size();
        // Create the Ceremoniagrados
        restCeremoniagradosMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ceremoniagrados))
            )
            .andExpect(status().isCreated());

        // Validate the Ceremoniagrados in the database
        List<Ceremoniagrados> ceremoniagradosList = ceremoniagradosRepository.findAll();
        assertThat(ceremoniagradosList).hasSize(databaseSizeBeforeCreate + 1);
        Ceremoniagrados testCeremoniagrados = ceremoniagradosList.get(ceremoniagradosList.size() - 1);
        assertThat(testCeremoniagrados.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testCeremoniagrados.getLiminscripcion()).isEqualTo(DEFAULT_LIMINSCRIPCION);
        assertThat(testCeremoniagrados.getLugar()).isEqualTo(DEFAULT_LUGAR);
    }

    @Test
    @Transactional
    void createCeremoniagradosWithExistingId() throws Exception {
        // Create the Ceremoniagrados with an existing ID
        ceremoniagrados.setId(1L);

        int databaseSizeBeforeCreate = ceremoniagradosRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCeremoniagradosMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ceremoniagrados))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ceremoniagrados in the database
        List<Ceremoniagrados> ceremoniagradosList = ceremoniagradosRepository.findAll();
        assertThat(ceremoniagradosList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = ceremoniagradosRepository.findAll().size();
        // set the field null
        ceremoniagrados.setFecha(null);

        // Create the Ceremoniagrados, which fails.

        restCeremoniagradosMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ceremoniagrados))
            )
            .andExpect(status().isBadRequest());

        List<Ceremoniagrados> ceremoniagradosList = ceremoniagradosRepository.findAll();
        assertThat(ceremoniagradosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLiminscripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = ceremoniagradosRepository.findAll().size();
        // set the field null
        ceremoniagrados.setLiminscripcion(null);

        // Create the Ceremoniagrados, which fails.

        restCeremoniagradosMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ceremoniagrados))
            )
            .andExpect(status().isBadRequest());

        List<Ceremoniagrados> ceremoniagradosList = ceremoniagradosRepository.findAll();
        assertThat(ceremoniagradosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLugarIsRequired() throws Exception {
        int databaseSizeBeforeTest = ceremoniagradosRepository.findAll().size();
        // set the field null
        ceremoniagrados.setLugar(null);

        // Create the Ceremoniagrados, which fails.

        restCeremoniagradosMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ceremoniagrados))
            )
            .andExpect(status().isBadRequest());

        List<Ceremoniagrados> ceremoniagradosList = ceremoniagradosRepository.findAll();
        assertThat(ceremoniagradosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCeremoniagrados() throws Exception {
        // Initialize the database
        ceremoniagradosRepository.saveAndFlush(ceremoniagrados);

        // Get all the ceremoniagradosList
        restCeremoniagradosMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ceremoniagrados.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].liminscripcion").value(hasItem(DEFAULT_LIMINSCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].lugar").value(hasItem(DEFAULT_LUGAR)));
    }

    @Test
    @Transactional
    void getCeremoniagrados() throws Exception {
        // Initialize the database
        ceremoniagradosRepository.saveAndFlush(ceremoniagrados);

        // Get the ceremoniagrados
        restCeremoniagradosMockMvc
            .perform(get(ENTITY_API_URL_ID, ceremoniagrados.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ceremoniagrados.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.liminscripcion").value(DEFAULT_LIMINSCRIPCION.toString()))
            .andExpect(jsonPath("$.lugar").value(DEFAULT_LUGAR));
    }

    @Test
    @Transactional
    void getNonExistingCeremoniagrados() throws Exception {
        // Get the ceremoniagrados
        restCeremoniagradosMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCeremoniagrados() throws Exception {
        // Initialize the database
        ceremoniagradosRepository.saveAndFlush(ceremoniagrados);

        int databaseSizeBeforeUpdate = ceremoniagradosRepository.findAll().size();

        // Update the ceremoniagrados
        Ceremoniagrados updatedCeremoniagrados = ceremoniagradosRepository.findById(ceremoniagrados.getId()).get();
        // Disconnect from session so that the updates on updatedCeremoniagrados are not directly saved in db
        em.detach(updatedCeremoniagrados);
        updatedCeremoniagrados.fecha(UPDATED_FECHA).liminscripcion(UPDATED_LIMINSCRIPCION).lugar(UPDATED_LUGAR);

        restCeremoniagradosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCeremoniagrados.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCeremoniagrados))
            )
            .andExpect(status().isOk());

        // Validate the Ceremoniagrados in the database
        List<Ceremoniagrados> ceremoniagradosList = ceremoniagradosRepository.findAll();
        assertThat(ceremoniagradosList).hasSize(databaseSizeBeforeUpdate);
        Ceremoniagrados testCeremoniagrados = ceremoniagradosList.get(ceremoniagradosList.size() - 1);
        assertThat(testCeremoniagrados.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testCeremoniagrados.getLiminscripcion()).isEqualTo(UPDATED_LIMINSCRIPCION);
        assertThat(testCeremoniagrados.getLugar()).isEqualTo(UPDATED_LUGAR);
    }

    @Test
    @Transactional
    void putNonExistingCeremoniagrados() throws Exception {
        int databaseSizeBeforeUpdate = ceremoniagradosRepository.findAll().size();
        ceremoniagrados.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCeremoniagradosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ceremoniagrados.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ceremoniagrados))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ceremoniagrados in the database
        List<Ceremoniagrados> ceremoniagradosList = ceremoniagradosRepository.findAll();
        assertThat(ceremoniagradosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCeremoniagrados() throws Exception {
        int databaseSizeBeforeUpdate = ceremoniagradosRepository.findAll().size();
        ceremoniagrados.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCeremoniagradosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ceremoniagrados))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ceremoniagrados in the database
        List<Ceremoniagrados> ceremoniagradosList = ceremoniagradosRepository.findAll();
        assertThat(ceremoniagradosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCeremoniagrados() throws Exception {
        int databaseSizeBeforeUpdate = ceremoniagradosRepository.findAll().size();
        ceremoniagrados.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCeremoniagradosMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ceremoniagrados))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ceremoniagrados in the database
        List<Ceremoniagrados> ceremoniagradosList = ceremoniagradosRepository.findAll();
        assertThat(ceremoniagradosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCeremoniagradosWithPatch() throws Exception {
        // Initialize the database
        ceremoniagradosRepository.saveAndFlush(ceremoniagrados);

        int databaseSizeBeforeUpdate = ceremoniagradosRepository.findAll().size();

        // Update the ceremoniagrados using partial update
        Ceremoniagrados partialUpdatedCeremoniagrados = new Ceremoniagrados();
        partialUpdatedCeremoniagrados.setId(ceremoniagrados.getId());

        restCeremoniagradosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCeremoniagrados.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCeremoniagrados))
            )
            .andExpect(status().isOk());

        // Validate the Ceremoniagrados in the database
        List<Ceremoniagrados> ceremoniagradosList = ceremoniagradosRepository.findAll();
        assertThat(ceremoniagradosList).hasSize(databaseSizeBeforeUpdate);
        Ceremoniagrados testCeremoniagrados = ceremoniagradosList.get(ceremoniagradosList.size() - 1);
        assertThat(testCeremoniagrados.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testCeremoniagrados.getLiminscripcion()).isEqualTo(DEFAULT_LIMINSCRIPCION);
        assertThat(testCeremoniagrados.getLugar()).isEqualTo(DEFAULT_LUGAR);
    }

    @Test
    @Transactional
    void fullUpdateCeremoniagradosWithPatch() throws Exception {
        // Initialize the database
        ceremoniagradosRepository.saveAndFlush(ceremoniagrados);

        int databaseSizeBeforeUpdate = ceremoniagradosRepository.findAll().size();

        // Update the ceremoniagrados using partial update
        Ceremoniagrados partialUpdatedCeremoniagrados = new Ceremoniagrados();
        partialUpdatedCeremoniagrados.setId(ceremoniagrados.getId());

        partialUpdatedCeremoniagrados.fecha(UPDATED_FECHA).liminscripcion(UPDATED_LIMINSCRIPCION).lugar(UPDATED_LUGAR);

        restCeremoniagradosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCeremoniagrados.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCeremoniagrados))
            )
            .andExpect(status().isOk());

        // Validate the Ceremoniagrados in the database
        List<Ceremoniagrados> ceremoniagradosList = ceremoniagradosRepository.findAll();
        assertThat(ceremoniagradosList).hasSize(databaseSizeBeforeUpdate);
        Ceremoniagrados testCeremoniagrados = ceremoniagradosList.get(ceremoniagradosList.size() - 1);
        assertThat(testCeremoniagrados.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testCeremoniagrados.getLiminscripcion()).isEqualTo(UPDATED_LIMINSCRIPCION);
        assertThat(testCeremoniagrados.getLugar()).isEqualTo(UPDATED_LUGAR);
    }

    @Test
    @Transactional
    void patchNonExistingCeremoniagrados() throws Exception {
        int databaseSizeBeforeUpdate = ceremoniagradosRepository.findAll().size();
        ceremoniagrados.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCeremoniagradosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ceremoniagrados.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ceremoniagrados))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ceremoniagrados in the database
        List<Ceremoniagrados> ceremoniagradosList = ceremoniagradosRepository.findAll();
        assertThat(ceremoniagradosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCeremoniagrados() throws Exception {
        int databaseSizeBeforeUpdate = ceremoniagradosRepository.findAll().size();
        ceremoniagrados.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCeremoniagradosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ceremoniagrados))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ceremoniagrados in the database
        List<Ceremoniagrados> ceremoniagradosList = ceremoniagradosRepository.findAll();
        assertThat(ceremoniagradosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCeremoniagrados() throws Exception {
        int databaseSizeBeforeUpdate = ceremoniagradosRepository.findAll().size();
        ceremoniagrados.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCeremoniagradosMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ceremoniagrados))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ceremoniagrados in the database
        List<Ceremoniagrados> ceremoniagradosList = ceremoniagradosRepository.findAll();
        assertThat(ceremoniagradosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCeremoniagrados() throws Exception {
        // Initialize the database
        ceremoniagradosRepository.saveAndFlush(ceremoniagrados);

        int databaseSizeBeforeDelete = ceremoniagradosRepository.findAll().size();

        // Delete the ceremoniagrados
        restCeremoniagradosMockMvc
            .perform(delete(ENTITY_API_URL_ID, ceremoniagrados.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Ceremoniagrados> ceremoniagradosList = ceremoniagradosRepository.findAll();
        assertThat(ceremoniagradosList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
