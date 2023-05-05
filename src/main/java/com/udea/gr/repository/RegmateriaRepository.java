package com.udea.gr.repository;

import com.udea.gr.domain.Regmateria;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Regmateria entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegmateriaRepository extends JpaRepository<Regmateria, Long> {}
