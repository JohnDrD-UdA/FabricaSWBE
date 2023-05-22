package com.udea.gr.service;

import com.udea.gr.domain.Estudiante;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Estudiante}.
 */
public interface EstudianteService {
    /**
     * Save a estudiante.
     *
     * @param estudiante the entity to save.
     * @return the persisted entity.
     */
    Estudiante save(Estudiante estudiante);

    /**
     * Updates a estudiante.
     *
     * @param estudiante the entity to update.
     * @return the persisted entity.
     */
    Estudiante update(Estudiante estudiante);

    /**
     * Partially updates a estudiante.
     *
     * @param estudiante the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Estudiante> partialUpdate(Estudiante estudiante);

    /**
     * Get all the estudiantes.
     *
     * @return the list of entities.
     */
    List<Estudiante> findAll();

    /**
     * Get all the estudiantes with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Estudiante> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" estudiante.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Estudiante> findOne(Long id);

    /**
     * Delete the "id" estudiante.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
