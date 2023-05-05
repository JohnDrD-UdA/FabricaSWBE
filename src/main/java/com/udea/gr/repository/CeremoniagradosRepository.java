package com.udea.gr.repository;

import com.udea.gr.domain.Ceremoniagrados;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Ceremoniagrados entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CeremoniagradosRepository extends JpaRepository<Ceremoniagrados, Long> {}
