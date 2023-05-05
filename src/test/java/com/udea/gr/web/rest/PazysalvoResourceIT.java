package com.udea.gr.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.udea.gr.IntegrationTest;
import com.udea.gr.domain.Ceremoniagrados;
import com.udea.gr.domain.Historiaacademica;
import com.udea.gr.domain.Pazysalvo;
import com.udea.gr.repository.PazysalvoRepository;
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
 * Integration tests for the {@link PazysalvoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PazysalvoResourceIT {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_MATERIASOBL = false;
    private static final Boolean UPDATED_MATERIASOBL = true;

    private static final Boolean DEFAULT_MATERIASELEC = false;
    private static final Boolean UPDATED_MATERIASELEC = true;

    private static final Boolean DEFAULT_PENDIENTESNOTA = false;
    private static final Boolean UPDATED_PENDIENTESNOTA = true;

    private static final Boolean DEFAULT_BIBLIOTECA = false;
    private static final Boolean UPDATED_BIBLIOTECA = true;

    private static final Boolean DEFAULT_CARTERA = false;
    private static final Boolean UPDATED_CARTERA = true;

    private static final Boolean DEFAULT_IMPEDIMENTO = false;
    private static final Boolean UPDATED_IMPEDIMENTO = true;

    private static final String ENTITY_API_URL = "/api/pazysalvos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PazysalvoRepository pazysalvoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPazysalvoMockMvc;

    private Pazysalvo pazysalvo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pazysalvo createEntity(EntityManager em) {
        Pazysalvo pazysalvo = new Pazysalvo()
            .fecha(DEFAULT_FECHA)
            .materiasobl(DEFAULT_MATERIASOBL)
            .materiaselec(DEFAULT_MATERIASELEC)
            .pendientesnota(DEFAULT_PENDIENTESNOTA)
            .biblioteca(DEFAULT_BIBLIOTECA)
            .cartera(DEFAULT_CARTERA)
            .impedimento(DEFAULT_IMPEDIMENTO);
        // Add required entity
        Ceremoniagrados ceremoniagrados;
        if (TestUtil.findAll(em, Ceremoniagrados.class).isEmpty()) {
            ceremoniagrados = CeremoniagradosResourceIT.createEntity(em);
            em.persist(ceremoniagrados);
            em.flush();
        } else {
            ceremoniagrados = TestUtil.findAll(em, Ceremoniagrados.class).get(0);
        }
        pazysalvo.setCeremoniagradosId(ceremoniagrados);
        // Add required entity
        Historiaacademica historiaacademica;
        if (TestUtil.findAll(em, Historiaacademica.class).isEmpty()) {
            historiaacademica = HistoriaacademicaResourceIT.createEntity(em);
            em.persist(historiaacademica);
            em.flush();
        } else {
            historiaacademica = TestUtil.findAll(em, Historiaacademica.class).get(0);
        }
        pazysalvo.setHistoriaacademicaId(historiaacademica);
        return pazysalvo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pazysalvo createUpdatedEntity(EntityManager em) {
        Pazysalvo pazysalvo = new Pazysalvo()
            .fecha(UPDATED_FECHA)
            .materiasobl(UPDATED_MATERIASOBL)
            .materiaselec(UPDATED_MATERIASELEC)
            .pendientesnota(UPDATED_PENDIENTESNOTA)
            .biblioteca(UPDATED_BIBLIOTECA)
            .cartera(UPDATED_CARTERA)
            .impedimento(UPDATED_IMPEDIMENTO);
        // Add required entity
        Ceremoniagrados ceremoniagrados;
        if (TestUtil.findAll(em, Ceremoniagrados.class).isEmpty()) {
            ceremoniagrados = CeremoniagradosResourceIT.createUpdatedEntity(em);
            em.persist(ceremoniagrados);
            em.flush();
        } else {
            ceremoniagrados = TestUtil.findAll(em, Ceremoniagrados.class).get(0);
        }
        pazysalvo.setCeremoniagradosId(ceremoniagrados);
        // Add required entity
        Historiaacademica historiaacademica;
        if (TestUtil.findAll(em, Historiaacademica.class).isEmpty()) {
            historiaacademica = HistoriaacademicaResourceIT.createUpdatedEntity(em);
            em.persist(historiaacademica);
            em.flush();
        } else {
            historiaacademica = TestUtil.findAll(em, Historiaacademica.class).get(0);
        }
        pazysalvo.setHistoriaacademicaId(historiaacademica);
        return pazysalvo;
    }

    @BeforeEach
    public void initTest() {
        pazysalvo = createEntity(em);
    }

    @Test
    @Transactional
    void createPazysalvo() throws Exception {
        int databaseSizeBeforeCreate = pazysalvoRepository.findAll().size();
        // Create the Pazysalvo
        restPazysalvoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pazysalvo)))
            .andExpect(status().isCreated());

        // Validate the Pazysalvo in the database
        List<Pazysalvo> pazysalvoList = pazysalvoRepository.findAll();
        assertThat(pazysalvoList).hasSize(databaseSizeBeforeCreate + 1);
        Pazysalvo testPazysalvo = pazysalvoList.get(pazysalvoList.size() - 1);
        assertThat(testPazysalvo.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testPazysalvo.getMateriasobl()).isEqualTo(DEFAULT_MATERIASOBL);
        assertThat(testPazysalvo.getMateriaselec()).isEqualTo(DEFAULT_MATERIASELEC);
        assertThat(testPazysalvo.getPendientesnota()).isEqualTo(DEFAULT_PENDIENTESNOTA);
        assertThat(testPazysalvo.getBiblioteca()).isEqualTo(DEFAULT_BIBLIOTECA);
        assertThat(testPazysalvo.getCartera()).isEqualTo(DEFAULT_CARTERA);
        assertThat(testPazysalvo.getImpedimento()).isEqualTo(DEFAULT_IMPEDIMENTO);
    }

    @Test
    @Transactional
    void createPazysalvoWithExistingId() throws Exception {
        // Create the Pazysalvo with an existing ID
        pazysalvo.setId(1L);

        int databaseSizeBeforeCreate = pazysalvoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPazysalvoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pazysalvo)))
            .andExpect(status().isBadRequest());

        // Validate the Pazysalvo in the database
        List<Pazysalvo> pazysalvoList = pazysalvoRepository.findAll();
        assertThat(pazysalvoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = pazysalvoRepository.findAll().size();
        // set the field null
        pazysalvo.setFecha(null);

        // Create the Pazysalvo, which fails.

        restPazysalvoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pazysalvo)))
            .andExpect(status().isBadRequest());

        List<Pazysalvo> pazysalvoList = pazysalvoRepository.findAll();
        assertThat(pazysalvoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMateriasoblIsRequired() throws Exception {
        int databaseSizeBeforeTest = pazysalvoRepository.findAll().size();
        // set the field null
        pazysalvo.setMateriasobl(null);

        // Create the Pazysalvo, which fails.

        restPazysalvoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pazysalvo)))
            .andExpect(status().isBadRequest());

        List<Pazysalvo> pazysalvoList = pazysalvoRepository.findAll();
        assertThat(pazysalvoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMateriaselecIsRequired() throws Exception {
        int databaseSizeBeforeTest = pazysalvoRepository.findAll().size();
        // set the field null
        pazysalvo.setMateriaselec(null);

        // Create the Pazysalvo, which fails.

        restPazysalvoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pazysalvo)))
            .andExpect(status().isBadRequest());

        List<Pazysalvo> pazysalvoList = pazysalvoRepository.findAll();
        assertThat(pazysalvoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPendientesnotaIsRequired() throws Exception {
        int databaseSizeBeforeTest = pazysalvoRepository.findAll().size();
        // set the field null
        pazysalvo.setPendientesnota(null);

        // Create the Pazysalvo, which fails.

        restPazysalvoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pazysalvo)))
            .andExpect(status().isBadRequest());

        List<Pazysalvo> pazysalvoList = pazysalvoRepository.findAll();
        assertThat(pazysalvoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBibliotecaIsRequired() throws Exception {
        int databaseSizeBeforeTest = pazysalvoRepository.findAll().size();
        // set the field null
        pazysalvo.setBiblioteca(null);

        // Create the Pazysalvo, which fails.

        restPazysalvoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pazysalvo)))
            .andExpect(status().isBadRequest());

        List<Pazysalvo> pazysalvoList = pazysalvoRepository.findAll();
        assertThat(pazysalvoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCarteraIsRequired() throws Exception {
        int databaseSizeBeforeTest = pazysalvoRepository.findAll().size();
        // set the field null
        pazysalvo.setCartera(null);

        // Create the Pazysalvo, which fails.

        restPazysalvoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pazysalvo)))
            .andExpect(status().isBadRequest());

        List<Pazysalvo> pazysalvoList = pazysalvoRepository.findAll();
        assertThat(pazysalvoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkImpedimentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = pazysalvoRepository.findAll().size();
        // set the field null
        pazysalvo.setImpedimento(null);

        // Create the Pazysalvo, which fails.

        restPazysalvoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pazysalvo)))
            .andExpect(status().isBadRequest());

        List<Pazysalvo> pazysalvoList = pazysalvoRepository.findAll();
        assertThat(pazysalvoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPazysalvos() throws Exception {
        // Initialize the database
        pazysalvoRepository.saveAndFlush(pazysalvo);

        // Get all the pazysalvoList
        restPazysalvoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pazysalvo.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].materiasobl").value(hasItem(DEFAULT_MATERIASOBL.booleanValue())))
            .andExpect(jsonPath("$.[*].materiaselec").value(hasItem(DEFAULT_MATERIASELEC.booleanValue())))
            .andExpect(jsonPath("$.[*].pendientesnota").value(hasItem(DEFAULT_PENDIENTESNOTA.booleanValue())))
            .andExpect(jsonPath("$.[*].biblioteca").value(hasItem(DEFAULT_BIBLIOTECA.booleanValue())))
            .andExpect(jsonPath("$.[*].cartera").value(hasItem(DEFAULT_CARTERA.booleanValue())))
            .andExpect(jsonPath("$.[*].impedimento").value(hasItem(DEFAULT_IMPEDIMENTO.booleanValue())));
    }

    @Test
    @Transactional
    void getPazysalvo() throws Exception {
        // Initialize the database
        pazysalvoRepository.saveAndFlush(pazysalvo);

        // Get the pazysalvo
        restPazysalvoMockMvc
            .perform(get(ENTITY_API_URL_ID, pazysalvo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pazysalvo.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.materiasobl").value(DEFAULT_MATERIASOBL.booleanValue()))
            .andExpect(jsonPath("$.materiaselec").value(DEFAULT_MATERIASELEC.booleanValue()))
            .andExpect(jsonPath("$.pendientesnota").value(DEFAULT_PENDIENTESNOTA.booleanValue()))
            .andExpect(jsonPath("$.biblioteca").value(DEFAULT_BIBLIOTECA.booleanValue()))
            .andExpect(jsonPath("$.cartera").value(DEFAULT_CARTERA.booleanValue()))
            .andExpect(jsonPath("$.impedimento").value(DEFAULT_IMPEDIMENTO.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingPazysalvo() throws Exception {
        // Get the pazysalvo
        restPazysalvoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPazysalvo() throws Exception {
        // Initialize the database
        pazysalvoRepository.saveAndFlush(pazysalvo);

        int databaseSizeBeforeUpdate = pazysalvoRepository.findAll().size();

        // Update the pazysalvo
        Pazysalvo updatedPazysalvo = pazysalvoRepository.findById(pazysalvo.getId()).get();
        // Disconnect from session so that the updates on updatedPazysalvo are not directly saved in db
        em.detach(updatedPazysalvo);
        updatedPazysalvo
            .fecha(UPDATED_FECHA)
            .materiasobl(UPDATED_MATERIASOBL)
            .materiaselec(UPDATED_MATERIASELEC)
            .pendientesnota(UPDATED_PENDIENTESNOTA)
            .biblioteca(UPDATED_BIBLIOTECA)
            .cartera(UPDATED_CARTERA)
            .impedimento(UPDATED_IMPEDIMENTO);

        restPazysalvoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPazysalvo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPazysalvo))
            )
            .andExpect(status().isOk());

        // Validate the Pazysalvo in the database
        List<Pazysalvo> pazysalvoList = pazysalvoRepository.findAll();
        assertThat(pazysalvoList).hasSize(databaseSizeBeforeUpdate);
        Pazysalvo testPazysalvo = pazysalvoList.get(pazysalvoList.size() - 1);
        assertThat(testPazysalvo.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testPazysalvo.getMateriasobl()).isEqualTo(UPDATED_MATERIASOBL);
        assertThat(testPazysalvo.getMateriaselec()).isEqualTo(UPDATED_MATERIASELEC);
        assertThat(testPazysalvo.getPendientesnota()).isEqualTo(UPDATED_PENDIENTESNOTA);
        assertThat(testPazysalvo.getBiblioteca()).isEqualTo(UPDATED_BIBLIOTECA);
        assertThat(testPazysalvo.getCartera()).isEqualTo(UPDATED_CARTERA);
        assertThat(testPazysalvo.getImpedimento()).isEqualTo(UPDATED_IMPEDIMENTO);
    }

    @Test
    @Transactional
    void putNonExistingPazysalvo() throws Exception {
        int databaseSizeBeforeUpdate = pazysalvoRepository.findAll().size();
        pazysalvo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPazysalvoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pazysalvo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pazysalvo))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pazysalvo in the database
        List<Pazysalvo> pazysalvoList = pazysalvoRepository.findAll();
        assertThat(pazysalvoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPazysalvo() throws Exception {
        int databaseSizeBeforeUpdate = pazysalvoRepository.findAll().size();
        pazysalvo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPazysalvoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pazysalvo))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pazysalvo in the database
        List<Pazysalvo> pazysalvoList = pazysalvoRepository.findAll();
        assertThat(pazysalvoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPazysalvo() throws Exception {
        int databaseSizeBeforeUpdate = pazysalvoRepository.findAll().size();
        pazysalvo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPazysalvoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pazysalvo)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pazysalvo in the database
        List<Pazysalvo> pazysalvoList = pazysalvoRepository.findAll();
        assertThat(pazysalvoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePazysalvoWithPatch() throws Exception {
        // Initialize the database
        pazysalvoRepository.saveAndFlush(pazysalvo);

        int databaseSizeBeforeUpdate = pazysalvoRepository.findAll().size();

        // Update the pazysalvo using partial update
        Pazysalvo partialUpdatedPazysalvo = new Pazysalvo();
        partialUpdatedPazysalvo.setId(pazysalvo.getId());

        partialUpdatedPazysalvo
            .fecha(UPDATED_FECHA)
            .materiasobl(UPDATED_MATERIASOBL)
            .pendientesnota(UPDATED_PENDIENTESNOTA)
            .cartera(UPDATED_CARTERA);

        restPazysalvoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPazysalvo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPazysalvo))
            )
            .andExpect(status().isOk());

        // Validate the Pazysalvo in the database
        List<Pazysalvo> pazysalvoList = pazysalvoRepository.findAll();
        assertThat(pazysalvoList).hasSize(databaseSizeBeforeUpdate);
        Pazysalvo testPazysalvo = pazysalvoList.get(pazysalvoList.size() - 1);
        assertThat(testPazysalvo.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testPazysalvo.getMateriasobl()).isEqualTo(UPDATED_MATERIASOBL);
        assertThat(testPazysalvo.getMateriaselec()).isEqualTo(DEFAULT_MATERIASELEC);
        assertThat(testPazysalvo.getPendientesnota()).isEqualTo(UPDATED_PENDIENTESNOTA);
        assertThat(testPazysalvo.getBiblioteca()).isEqualTo(DEFAULT_BIBLIOTECA);
        assertThat(testPazysalvo.getCartera()).isEqualTo(UPDATED_CARTERA);
        assertThat(testPazysalvo.getImpedimento()).isEqualTo(DEFAULT_IMPEDIMENTO);
    }

    @Test
    @Transactional
    void fullUpdatePazysalvoWithPatch() throws Exception {
        // Initialize the database
        pazysalvoRepository.saveAndFlush(pazysalvo);

        int databaseSizeBeforeUpdate = pazysalvoRepository.findAll().size();

        // Update the pazysalvo using partial update
        Pazysalvo partialUpdatedPazysalvo = new Pazysalvo();
        partialUpdatedPazysalvo.setId(pazysalvo.getId());

        partialUpdatedPazysalvo
            .fecha(UPDATED_FECHA)
            .materiasobl(UPDATED_MATERIASOBL)
            .materiaselec(UPDATED_MATERIASELEC)
            .pendientesnota(UPDATED_PENDIENTESNOTA)
            .biblioteca(UPDATED_BIBLIOTECA)
            .cartera(UPDATED_CARTERA)
            .impedimento(UPDATED_IMPEDIMENTO);

        restPazysalvoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPazysalvo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPazysalvo))
            )
            .andExpect(status().isOk());

        // Validate the Pazysalvo in the database
        List<Pazysalvo> pazysalvoList = pazysalvoRepository.findAll();
        assertThat(pazysalvoList).hasSize(databaseSizeBeforeUpdate);
        Pazysalvo testPazysalvo = pazysalvoList.get(pazysalvoList.size() - 1);
        assertThat(testPazysalvo.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testPazysalvo.getMateriasobl()).isEqualTo(UPDATED_MATERIASOBL);
        assertThat(testPazysalvo.getMateriaselec()).isEqualTo(UPDATED_MATERIASELEC);
        assertThat(testPazysalvo.getPendientesnota()).isEqualTo(UPDATED_PENDIENTESNOTA);
        assertThat(testPazysalvo.getBiblioteca()).isEqualTo(UPDATED_BIBLIOTECA);
        assertThat(testPazysalvo.getCartera()).isEqualTo(UPDATED_CARTERA);
        assertThat(testPazysalvo.getImpedimento()).isEqualTo(UPDATED_IMPEDIMENTO);
    }

    @Test
    @Transactional
    void patchNonExistingPazysalvo() throws Exception {
        int databaseSizeBeforeUpdate = pazysalvoRepository.findAll().size();
        pazysalvo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPazysalvoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pazysalvo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pazysalvo))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pazysalvo in the database
        List<Pazysalvo> pazysalvoList = pazysalvoRepository.findAll();
        assertThat(pazysalvoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPazysalvo() throws Exception {
        int databaseSizeBeforeUpdate = pazysalvoRepository.findAll().size();
        pazysalvo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPazysalvoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pazysalvo))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pazysalvo in the database
        List<Pazysalvo> pazysalvoList = pazysalvoRepository.findAll();
        assertThat(pazysalvoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPazysalvo() throws Exception {
        int databaseSizeBeforeUpdate = pazysalvoRepository.findAll().size();
        pazysalvo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPazysalvoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(pazysalvo))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pazysalvo in the database
        List<Pazysalvo> pazysalvoList = pazysalvoRepository.findAll();
        assertThat(pazysalvoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePazysalvo() throws Exception {
        // Initialize the database
        pazysalvoRepository.saveAndFlush(pazysalvo);

        int databaseSizeBeforeDelete = pazysalvoRepository.findAll().size();

        // Delete the pazysalvo
        restPazysalvoMockMvc
            .perform(delete(ENTITY_API_URL_ID, pazysalvo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pazysalvo> pazysalvoList = pazysalvoRepository.findAll();
        assertThat(pazysalvoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
