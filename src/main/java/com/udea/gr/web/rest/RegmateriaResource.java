package com.udea.gr.web.rest;

import com.udea.gr.domain.Regmateria;
import com.udea.gr.repository.RegmateriaRepository;
import com.udea.gr.service.RegmateriaService;
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
 * REST controller for managing {@link com.udea.gr.domain.Regmateria}.
 */
@RestController
@RequestMapping("/api")
public class RegmateriaResource {

    private final Logger log = LoggerFactory.getLogger(RegmateriaResource.class);

    private static final String ENTITY_NAME = "regmateria";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RegmateriaService regmateriaService;

    private final RegmateriaRepository regmateriaRepository;

    public RegmateriaResource(RegmateriaService regmateriaService, RegmateriaRepository regmateriaRepository) {
        this.regmateriaService = regmateriaService;
        this.regmateriaRepository = regmateriaRepository;
    }

    /**
     * {@code POST  /regmaterias} : Create a new regmateria.
     *
     * @param regmateria the regmateria to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new regmateria, or with status {@code 400 (Bad Request)} if the regmateria has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/regmaterias")
    public ResponseEntity<Regmateria> createRegmateria(@Valid @RequestBody Regmateria regmateria) throws URISyntaxException {
        log.debug("REST request to save Regmateria : {}", regmateria);
        if (regmateria.getId() != null) {
            throw new BadRequestAlertException("A new regmateria cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Regmateria result = regmateriaService.save(regmateria);
        return ResponseEntity
            .created(new URI("/api/regmaterias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /regmaterias/:id} : Updates an existing regmateria.
     *
     * @param id the id of the regmateria to save.
     * @param regmateria the regmateria to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated regmateria,
     * or with status {@code 400 (Bad Request)} if the regmateria is not valid,
     * or with status {@code 500 (Internal Server Error)} if the regmateria couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/regmaterias/{id}")
    public ResponseEntity<Regmateria> updateRegmateria(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Regmateria regmateria
    ) throws URISyntaxException {
        log.debug("REST request to update Regmateria : {}, {}", id, regmateria);
        if (regmateria.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, regmateria.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!regmateriaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Regmateria result = regmateriaService.update(regmateria);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, regmateria.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /regmaterias/:id} : Partial updates given fields of an existing regmateria, field will ignore if it is null
     *
     * @param id the id of the regmateria to save.
     * @param regmateria the regmateria to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated regmateria,
     * or with status {@code 400 (Bad Request)} if the regmateria is not valid,
     * or with status {@code 404 (Not Found)} if the regmateria is not found,
     * or with status {@code 500 (Internal Server Error)} if the regmateria couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/regmaterias/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Regmateria> partialUpdateRegmateria(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Regmateria regmateria
    ) throws URISyntaxException {
        log.debug("REST request to partial update Regmateria partially : {}, {}", id, regmateria);
        if (regmateria.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, regmateria.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!regmateriaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Regmateria> result = regmateriaService.partialUpdate(regmateria);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, regmateria.getId().toString())
        );
    }

    /**
     * {@code GET  /regmaterias} : get all the regmaterias.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of regmaterias in body.
     */
    @GetMapping("/regmaterias")
    public List<Regmateria> getAllRegmaterias() {
        log.debug("REST request to get all Regmaterias");
        return regmateriaService.findAll();
    }

    /**
     * {@code GET  /regmaterias/:id} : get the "id" regmateria.
     *
     * @param id the id of the regmateria to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the regmateria, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/regmaterias/{id}")
    public ResponseEntity<Regmateria> getRegmateria(@PathVariable Long id) {
        log.debug("REST request to get Regmateria : {}", id);
        Optional<Regmateria> regmateria = regmateriaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(regmateria);
    }

    /**
     * {@code DELETE  /regmaterias/:id} : delete the "id" regmateria.
     *
     * @param id the id of the regmateria to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/regmaterias/{id}")
    public ResponseEntity<Void> deleteRegmateria(@PathVariable Long id) {
        log.debug("REST request to delete Regmateria : {}", id);
        regmateriaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
