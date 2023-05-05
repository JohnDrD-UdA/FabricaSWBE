package com.udea.gr.web.rest;

import com.udea.gr.domain.Planestudios;
import com.udea.gr.repository.PlanestudiosRepository;
import com.udea.gr.service.PlanestudiosService;
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
 * REST controller for managing {@link com.udea.gr.domain.Planestudios}.
 */
@RestController
@RequestMapping("/api")
public class PlanestudiosResource {

    private final Logger log = LoggerFactory.getLogger(PlanestudiosResource.class);

    private static final String ENTITY_NAME = "planestudios";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlanestudiosService planestudiosService;

    private final PlanestudiosRepository planestudiosRepository;

    public PlanestudiosResource(PlanestudiosService planestudiosService, PlanestudiosRepository planestudiosRepository) {
        this.planestudiosService = planestudiosService;
        this.planestudiosRepository = planestudiosRepository;
    }

    /**
     * {@code POST  /planestudios} : Create a new planestudios.
     *
     * @param planestudios the planestudios to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new planestudios, or with status {@code 400 (Bad Request)} if the planestudios has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/planestudios")
    public ResponseEntity<Planestudios> createPlanestudios(@Valid @RequestBody Planestudios planestudios) throws URISyntaxException {
        log.debug("REST request to save Planestudios : {}", planestudios);
        if (planestudios.getId() != null) {
            throw new BadRequestAlertException("A new planestudios cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Planestudios result = planestudiosService.save(planestudios);
        return ResponseEntity
            .created(new URI("/api/planestudios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /planestudios/:id} : Updates an existing planestudios.
     *
     * @param id the id of the planestudios to save.
     * @param planestudios the planestudios to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated planestudios,
     * or with status {@code 400 (Bad Request)} if the planestudios is not valid,
     * or with status {@code 500 (Internal Server Error)} if the planestudios couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/planestudios/{id}")
    public ResponseEntity<Planestudios> updatePlanestudios(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Planestudios planestudios
    ) throws URISyntaxException {
        log.debug("REST request to update Planestudios : {}, {}", id, planestudios);
        if (planestudios.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, planestudios.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!planestudiosRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Planestudios result = planestudiosService.update(planestudios);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, planestudios.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /planestudios/:id} : Partial updates given fields of an existing planestudios, field will ignore if it is null
     *
     * @param id the id of the planestudios to save.
     * @param planestudios the planestudios to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated planestudios,
     * or with status {@code 400 (Bad Request)} if the planestudios is not valid,
     * or with status {@code 404 (Not Found)} if the planestudios is not found,
     * or with status {@code 500 (Internal Server Error)} if the planestudios couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/planestudios/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Planestudios> partialUpdatePlanestudios(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Planestudios planestudios
    ) throws URISyntaxException {
        log.debug("REST request to partial update Planestudios partially : {}, {}", id, planestudios);
        if (planestudios.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, planestudios.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!planestudiosRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Planestudios> result = planestudiosService.partialUpdate(planestudios);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, planestudios.getId().toString())
        );
    }

    /**
     * {@code GET  /planestudios} : get all the planestudios.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of planestudios in body.
     */
    @GetMapping("/planestudios")
    public List<Planestudios> getAllPlanestudios() {
        log.debug("REST request to get all Planestudios");
        return planestudiosService.findAll();
    }

    /**
     * {@code GET  /planestudios/:id} : get the "id" planestudios.
     *
     * @param id the id of the planestudios to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the planestudios, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/planestudios/{id}")
    public ResponseEntity<Planestudios> getPlanestudios(@PathVariable Long id) {
        log.debug("REST request to get Planestudios : {}", id);
        Optional<Planestudios> planestudios = planestudiosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(planestudios);
    }

    /**
     * {@code DELETE  /planestudios/:id} : delete the "id" planestudios.
     *
     * @param id the id of the planestudios to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/planestudios/{id}")
    public ResponseEntity<Void> deletePlanestudios(@PathVariable Long id) {
        log.debug("REST request to delete Planestudios : {}", id);
        planestudiosService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
