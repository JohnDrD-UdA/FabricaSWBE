package com.udea.gr.repository;

import com.udea.gr.domain.Pazysalvo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Pazysalvo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PazysalvoRepository extends JpaRepository<Pazysalvo, Long> {}
