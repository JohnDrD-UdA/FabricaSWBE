package com.udea.gr.web.rest;

import com.udea.gr.domain.Historiaacademica;
import com.udea.gr.repository.HistoriaacademicaRepository;
import com.udea.gr.service.HistoriaacademicaService;
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
 * REST controller for managing {@link com.udea.gr.domain.Historiaacademica}.
 */
@RestController
@RequestMapping("/api")
public class HistoriaacademicaResource {

    private final Logger log = LoggerFactory.getLogger(HistoriaacademicaResource.class);

    private static final String ENTITY_NAME = "historiaacademica";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HistoriaacademicaService historiaacademicaService;

    private final HistoriaacademicaRepository historiaacademicaRepository;

    public HistoriaacademicaResource(
        HistoriaacademicaService historiaacademicaService,
        HistoriaacademicaRepository historiaacademicaRepository
    ) {
        this.historiaacademicaService = historiaacademicaService;
        this.historiaacademicaRepository = historiaacademicaRepository;
    }

    /**
     * {@code POST  /historiaacademicas} : Create a new historiaacademica.
     *
     * @param historiaacademica the historiaacademica to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new historiaacademica, or with status {@code 400 (Bad Request)} if the historiaacademica has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/historiaacademicas")
    public ResponseEntity<Historiaacademica> createHistoriaacademica(@Valid @RequestBody Historiaacademica historiaacademica)
        throws URISyntaxException {
        log.debug("REST request to save Historiaacademica : {}", historiaacademica);
        if (historiaacademica.getId() != null) {
            throw new BadRequestAlertException("A new historiaacademica cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Historiaacademica result = historiaacademicaService.save(historiaacademica);
        return ResponseEntity
            .created(new URI("/api/historiaacademicas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /historiaacademicas/:id} : Updates an existing historiaacademica.
     *
     * @param id the id of the historiaacademica to save.
     * @param historiaacademica the historiaacademica to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated historiaacademica,
     * or with status {@code 400 (Bad Request)} if the historiaacademica is not valid,
     * or with status {@code 500 (Internal Server Error)} if the historiaacademica couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/historiaacademicas/{id}")
    public ResponseEntity<Historiaacademica> updateHistoriaacademica(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Historiaacademica historiaacademica
    ) throws URISyntaxException {
        log.debug("REST request to update Historiaacademica : {}, {}", id, historiaacademica);
        if (historiaacademica.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, historiaacademica.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!historiaacademicaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Historiaacademica result = historiaacademicaService.update(historiaacademica);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, historiaacademica.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /historiaacademicas/:id} : Partial updates given fields of an existing historiaacademica, field will ignore if it is null
     *
     * @param id the id of the historiaacademica to save.
     * @param historiaacademica the historiaacademica to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated historiaacademica,
     * or with status {@code 400 (Bad Request)} if the historiaacademica is not valid,
     * or with status {@code 404 (Not Found)} if the historiaacademica is not found,
     * or with status {@code 500 (Internal Server Error)} if the historiaacademica couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/historiaacademicas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Historiaacademica> partialUpdateHistoriaacademica(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Historiaacademica historiaacademica
    ) throws URISyntaxException {
        log.debug("REST request to partial update Historiaacademica partially : {}, {}", id, historiaacademica);
        if (historiaacademica.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, historiaacademica.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!historiaacademicaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Historiaacademica> result = historiaacademicaService.partialUpdate(historiaacademica);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, historiaacademica.getId().toString())
        );
    }

    /**
     * {@code GET  /historiaacademicas} : get all the historiaacademicas.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of historiaacademicas in body.
     */
    @GetMapping("/historiaacademicas")
    public List<Historiaacademica> getAllHistoriaacademicas(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Historiaacademicas");
        return historiaacademicaService.findAll();
    }

    /**
     * {@code GET  /historiaacademicas/:id} : get the "id" historiaacademica.
     *
     * @param id the id of the historiaacademica to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the historiaacademica, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/historiaacademicas/{id}")
    public ResponseEntity<Historiaacademica> getHistoriaacademica(@PathVariable Long id) {
        log.debug("REST request to get Historiaacademica : {}", id);
        Optional<Historiaacademica> historiaacademica = historiaacademicaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(historiaacademica);
    }

    /**
     * {@code DELETE  /historiaacademicas/:id} : delete the "id" historiaacademica.
     *
     * @param id the id of the historiaacademica to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/historiaacademicas/{id}")
    public ResponseEntity<Void> deleteHistoriaacademica(@PathVariable Long id) {
        log.debug("REST request to delete Historiaacademica : {}", id);
        historiaacademicaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
