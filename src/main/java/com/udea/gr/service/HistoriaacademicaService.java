package com.udea.gr.service;

import com.udea.gr.domain.Historiaacademica;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Historiaacademica}.
 */
public interface HistoriaacademicaService {
    /**
     * Save a historiaacademica.
     *
     * @param historiaacademica the entity to save.
     * @return the persisted entity.
     */
    Historiaacademica save(Historiaacademica historiaacademica);

    /**
     * Updates a historiaacademica.
     *
     * @param historiaacademica the entity to update.
     * @return the persisted entity.
     */
    Historiaacademica update(Historiaacademica historiaacademica);

    /**
     * Partially updates a historiaacademica.
     *
     * @param historiaacademica the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Historiaacademica> partialUpdate(Historiaacademica historiaacademica);

    /**
     * Get all the historiaacademicas.
     *
     * @return the list of entities.
     */
    List<Historiaacademica> findAll();

    /**
     * Get all the historiaacademicas with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Historiaacademica> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" historiaacademica.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Historiaacademica> findOne(Long id);

    /**
     * Delete the "id" historiaacademica.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
