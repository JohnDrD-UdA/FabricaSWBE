package com.udea.gr.repository;

import com.udea.gr.domain.Pazysalvo;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Pazysalvo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PazysalvoRepository extends JpaRepository<Pazysalvo, Long> {

    @Query("select ps from Pazysalvo ps where ps.historiaacademicaId.estudianteid.documento= :id")
    Pazysalvo GetByUserDoc(@Param("id") String ID);
}
