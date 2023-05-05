package com.udea.gr.service;

import com.udea.gr.domain.Pazysalvo;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Pazysalvo}.
 */
public interface PazysalvoService {
    /**
     * Save a pazysalvo.
     *
     * @param pazysalvo the entity to save.
     * @return the persisted entity.
     */
    Pazysalvo save(Pazysalvo pazysalvo);

    /**
     * Updates a pazysalvo.
     *
     * @param pazysalvo the entity to update.
     * @return the persisted entity.
     */
    Pazysalvo update(Pazysalvo pazysalvo);

    /**
     * Partially updates a pazysalvo.
     *
     * @param pazysalvo the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Pazysalvo> partialUpdate(Pazysalvo pazysalvo);

    /**
     * Get all the pazysalvos.
     *
     * @return the list of entities.
     */
    List<Pazysalvo> findAll();

    /**
     * Get the "id" pazysalvo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Pazysalvo> findOne(Long id);

    /**
     * Delete the "id" pazysalvo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
