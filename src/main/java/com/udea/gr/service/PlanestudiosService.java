package com.udea.gr.service;

import com.udea.gr.domain.Planestudios;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Planestudios}.
 */
public interface PlanestudiosService {
    /**
     * Save a planestudios.
     *
     * @param planestudios the entity to save.
     * @return the persisted entity.
     */
    Planestudios save(Planestudios planestudios);

    /**
     * Updates a planestudios.
     *
     * @param planestudios the entity to update.
     * @return the persisted entity.
     */
    Planestudios update(Planestudios planestudios);

    /**
     * Partially updates a planestudios.
     *
     * @param planestudios the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Planestudios> partialUpdate(Planestudios planestudios);

    /**
     * Get all the planestudios.
     *
     * @return the list of entities.
     */
    List<Planestudios> findAll();

    /**
     * Get the "id" planestudios.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Planestudios> findOne(Long id);

    /**
     * Delete the "id" planestudios.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
