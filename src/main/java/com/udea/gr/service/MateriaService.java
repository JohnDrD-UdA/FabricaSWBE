package com.udea.gr.service;

import com.udea.gr.domain.Materia;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Materia}.
 */
public interface MateriaService {
    /**
     * Save a materia.
     *
     * @param materia the entity to save.
     * @return the persisted entity.
     */
    Materia save(Materia materia);

    /**
     * Updates a materia.
     *
     * @param materia the entity to update.
     * @return the persisted entity.
     */
    Materia update(Materia materia);

    /**
     * Partially updates a materia.
     *
     * @param materia the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Materia> partialUpdate(Materia materia);

    /**
     * Get all the materias.
     *
     * @return the list of entities.
     */
    List<Materia> findAll();

    /**
     * Get the "id" materia.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Materia> findOne(Long id);

    /**
     * Delete the "id" materia.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
