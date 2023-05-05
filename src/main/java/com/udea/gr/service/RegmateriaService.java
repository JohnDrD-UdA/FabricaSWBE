package com.udea.gr.service;

import com.udea.gr.domain.Regmateria;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Regmateria}.
 */
public interface RegmateriaService {
    /**
     * Save a regmateria.
     *
     * @param regmateria the entity to save.
     * @return the persisted entity.
     */
    Regmateria save(Regmateria regmateria);

    /**
     * Updates a regmateria.
     *
     * @param regmateria the entity to update.
     * @return the persisted entity.
     */
    Regmateria update(Regmateria regmateria);

    /**
     * Partially updates a regmateria.
     *
     * @param regmateria the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Regmateria> partialUpdate(Regmateria regmateria);

    /**
     * Get all the regmaterias.
     *
     * @return the list of entities.
     */
    List<Regmateria> findAll();

    /**
     * Get the "id" regmateria.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Regmateria> findOne(Long id);

    /**
     * Delete the "id" regmateria.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
