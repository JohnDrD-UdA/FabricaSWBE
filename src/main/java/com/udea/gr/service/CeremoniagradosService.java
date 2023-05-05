package com.udea.gr.service;

import com.udea.gr.domain.Ceremoniagrados;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Ceremoniagrados}.
 */
public interface CeremoniagradosService {
    /**
     * Save a ceremoniagrados.
     *
     * @param ceremoniagrados the entity to save.
     * @return the persisted entity.
     */
    Ceremoniagrados save(Ceremoniagrados ceremoniagrados);

    /**
     * Updates a ceremoniagrados.
     *
     * @param ceremoniagrados the entity to update.
     * @return the persisted entity.
     */
    Ceremoniagrados update(Ceremoniagrados ceremoniagrados);

    /**
     * Partially updates a ceremoniagrados.
     *
     * @param ceremoniagrados the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Ceremoniagrados> partialUpdate(Ceremoniagrados ceremoniagrados);

    /**
     * Get all the ceremoniagrados.
     *
     * @return the list of entities.
     */
    List<Ceremoniagrados> findAll();

    /**
     * Get the "id" ceremoniagrados.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Ceremoniagrados> findOne(Long id);

    /**
     * Delete the "id" ceremoniagrados.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
