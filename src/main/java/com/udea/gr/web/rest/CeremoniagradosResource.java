package com.udea.gr.web.rest;

import com.udea.gr.domain.Ceremoniagrados;
import com.udea.gr.repository.CeremoniagradosRepository;
import com.udea.gr.service.CeremoniagradosService;
import com.udea.gr.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.udea.gr.domain.Ceremoniagrados}.
 */
@RestController
@RequestMapping("/api")
public class CeremoniagradosResource {

    private final Logger log = LoggerFactory.getLogger(CeremoniagradosResource.class);

    private static final String ENTITY_NAME = "ceremoniagrados";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CeremoniagradosService ceremoniagradosService;

    private final CeremoniagradosRepository ceremoniagradosRepository;

    public CeremoniagradosResource(CeremoniagradosService ceremoniagradosService, CeremoniagradosRepository ceremoniagradosRepository) {
        this.ceremoniagradosService = ceremoniagradosService;
        this.ceremoniagradosRepository = ceremoniagradosRepository;
    }

    /**
     * {@code POST  /ceremoniagrados} : Create a new ceremoniagrados.
     *
     * @param ceremoniagrados the ceremoniagrados to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ceremoniagrados, or with status {@code 400 (Bad Request)} if the ceremoniagrados has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ceremoniagrados")
    public ResponseEntity<Ceremoniagrados> createCeremoniagrados(@Valid @RequestBody Ceremoniagrados ceremoniagrados)
        throws URISyntaxException {
        log.debug("REST request to save Ceremoniagrados : {}", ceremoniagrados);
        if (ceremoniagrados.getId() != null) {
            throw new BadRequestAlertException("A new ceremoniagrados cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Ceremoniagrados result = ceremoniagradosService.save(ceremoniagrados);
        return ResponseEntity
            .created(new URI("/api/ceremoniagrados/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ceremoniagrados/:id} : Updates an existing ceremoniagrados.
     *
     * @param id the id of the ceremoniagrados to save.
     * @param ceremoniagrados the ceremoniagrados to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ceremoniagrados,
     * or with status {@code 400 (Bad Request)} if the ceremoniagrados is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ceremoniagrados couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ceremoniagrados/{id}")
    public ResponseEntity<Ceremoniagrados> updateCeremoniagrados(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Ceremoniagrados ceremoniagrados
    ) throws URISyntaxException {
        log.debug("REST request to update Ceremoniagrados : {}, {}", id, ceremoniagrados);
        if (ceremoniagrados.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ceremoniagrados.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ceremoniagradosRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Ceremoniagrados result = ceremoniagradosService.update(ceremoniagrados);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ceremoniagrados.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ceremoniagrados/:id} : Partial updates given fields of an existing ceremoniagrados, field will ignore if it is null
     *
     * @param id the id of the ceremoniagrados to save.
     * @param ceremoniagrados the ceremoniagrados to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ceremoniagrados,
     * or with status {@code 400 (Bad Request)} if the ceremoniagrados is not valid,
     * or with status {@code 404 (Not Found)} if the ceremoniagrados is not found,
     * or with status {@code 500 (Internal Server Error)} if the ceremoniagrados couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ceremoniagrados/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Ceremoniagrados> partialUpdateCeremoniagrados(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Ceremoniagrados ceremoniagrados
    ) throws URISyntaxException {
        log.debug("REST request to partial update Ceremoniagrados partially : {}, {}", id, ceremoniagrados);
        if (ceremoniagrados.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ceremoniagrados.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ceremoniagradosRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Ceremoniagrados> result = ceremoniagradosService.partialUpdate(ceremoniagrados);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ceremoniagrados.getId().toString())
        );
    }

    /**
     * {@code GET  /ceremoniagrados} : get all the ceremoniagrados.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ceremoniagrados in body.
     */
    @GetMapping("/ceremoniagrados")
    public List<Ceremoniagrados> getAllCeremoniagrados() {
        log.debug("REST request to get all Ceremoniagrados");
        return ceremoniagradosService.findAll();
    }

    /**
     * {@code GET  /ceremoniagrados/:id} : get the "id" ceremoniagrados.
     *
     * @param id the id of the ceremoniagrados to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ceremoniagrados, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ceremoniagrados/{id}")
    public ResponseEntity<Ceremoniagrados> getCeremoniagrados(@PathVariable Long id) {
        log.debug("REST request to get Ceremoniagrados : {}", id);
        Optional<Ceremoniagrados> ceremoniagrados = ceremoniagradosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ceremoniagrados);
    }

    /**
     * {@code DELETE  /ceremoniagrados/:id} : delete the "id" ceremoniagrados.
     *
     * @param id the id of the ceremoniagrados to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ceremoniagrados/{id}")
    public ResponseEntity<Void> deleteCeremoniagrados(@PathVariable Long id) {
        log.debug("REST request to delete Ceremoniagrados : {}", id);
        ceremoniagradosService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
