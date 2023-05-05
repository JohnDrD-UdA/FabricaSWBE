package com.udea.gr.web.rest;

import com.udea.gr.domain.Pazysalvo;
import com.udea.gr.repository.PazysalvoRepository;
import com.udea.gr.service.PazysalvoService;
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
 * REST controller for managing {@link com.udea.gr.domain.Pazysalvo}.
 */
@RestController
@RequestMapping("/api")
public class PazysalvoResource {

    private final Logger log = LoggerFactory.getLogger(PazysalvoResource.class);

    private static final String ENTITY_NAME = "pazysalvo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PazysalvoService pazysalvoService;

    private final PazysalvoRepository pazysalvoRepository;

    public PazysalvoResource(PazysalvoService pazysalvoService, PazysalvoRepository pazysalvoRepository) {
        this.pazysalvoService = pazysalvoService;
        this.pazysalvoRepository = pazysalvoRepository;
    }

    /**
     * {@code POST  /pazysalvos} : Create a new pazysalvo.
     *
     * @param pazysalvo the pazysalvo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pazysalvo, or with status {@code 400 (Bad Request)} if the pazysalvo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pazysalvos")
    public ResponseEntity<Pazysalvo> createPazysalvo(@Valid @RequestBody Pazysalvo pazysalvo) throws URISyntaxException {
        log.debug("REST request to save Pazysalvo : {}", pazysalvo);
        if (pazysalvo.getId() != null) {
            throw new BadRequestAlertException("A new pazysalvo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Pazysalvo result = pazysalvoService.save(pazysalvo);
        return ResponseEntity
            .created(new URI("/api/pazysalvos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pazysalvos/:id} : Updates an existing pazysalvo.
     *
     * @param id the id of the pazysalvo to save.
     * @param pazysalvo the pazysalvo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pazysalvo,
     * or with status {@code 400 (Bad Request)} if the pazysalvo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pazysalvo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pazysalvos/{id}")
    public ResponseEntity<Pazysalvo> updatePazysalvo(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Pazysalvo pazysalvo
    ) throws URISyntaxException {
        log.debug("REST request to update Pazysalvo : {}, {}", id, pazysalvo);
        if (pazysalvo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pazysalvo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pazysalvoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Pazysalvo result = pazysalvoService.update(pazysalvo);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pazysalvo.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /pazysalvos/:id} : Partial updates given fields of an existing pazysalvo, field will ignore if it is null
     *
     * @param id the id of the pazysalvo to save.
     * @param pazysalvo the pazysalvo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pazysalvo,
     * or with status {@code 400 (Bad Request)} if the pazysalvo is not valid,
     * or with status {@code 404 (Not Found)} if the pazysalvo is not found,
     * or with status {@code 500 (Internal Server Error)} if the pazysalvo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/pazysalvos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Pazysalvo> partialUpdatePazysalvo(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Pazysalvo pazysalvo
    ) throws URISyntaxException {
        log.debug("REST request to partial update Pazysalvo partially : {}, {}", id, pazysalvo);
        if (pazysalvo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pazysalvo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pazysalvoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Pazysalvo> result = pazysalvoService.partialUpdate(pazysalvo);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pazysalvo.getId().toString())
        );
    }

    /**
     * {@code GET  /pazysalvos} : get all the pazysalvos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pazysalvos in body.
     */
    @GetMapping("/pazysalvos")
    public List<Pazysalvo> getAllPazysalvos() {
        log.debug("REST request to get all Pazysalvos");
        return pazysalvoService.findAll();
    }

    /**
     * {@code GET  /pazysalvos/:id} : get the "id" pazysalvo.
     *
     * @param id the id of the pazysalvo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pazysalvo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pazysalvos/{id}")
    public ResponseEntity<Pazysalvo> getPazysalvo(@PathVariable Long id) {
        log.debug("REST request to get Pazysalvo : {}", id);
        Optional<Pazysalvo> pazysalvo = pazysalvoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pazysalvo);
    }

    /**
     * {@code DELETE  /pazysalvos/:id} : delete the "id" pazysalvo.
     *
     * @param id the id of the pazysalvo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pazysalvos/{id}")
    public ResponseEntity<Void> deletePazysalvo(@PathVariable Long id) {
        log.debug("REST request to delete Pazysalvo : {}", id);
        pazysalvoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
