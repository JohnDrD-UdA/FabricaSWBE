package com.udea.gr.repository;

import com.udea.gr.domain.Planestudios;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Planestudios entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlanestudiosRepository extends JpaRepository<Planestudios, Long> {}
