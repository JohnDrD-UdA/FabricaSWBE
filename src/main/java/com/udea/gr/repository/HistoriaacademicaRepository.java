package com.udea.gr.repository;

import com.udea.gr.domain.Historiaacademica;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Historiaacademica entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HistoriaacademicaRepository extends JpaRepository<Historiaacademica, Long> {}
