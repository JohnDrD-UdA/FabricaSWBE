package com.udea.gr.repository;

import com.udea.gr.domain.Historiaacademica;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Historiaacademica entity.
 */
@Repository
public interface HistoriaacademicaRepository extends JpaRepository<Historiaacademica, Long> {
    @Query(
        "select historiaacademica from Historiaacademica historiaacademica where historiaacademica.userId.login = ?#{principal.username}"
    )
    List<Historiaacademica> findByUserIdIsCurrentUser();

    default Optional<Historiaacademica> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Historiaacademica> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Historiaacademica> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct historiaacademica from Historiaacademica historiaacademica left join fetch historiaacademica.userId",
        countQuery = "select count(distinct historiaacademica) from Historiaacademica historiaacademica"
    )
    Page<Historiaacademica> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct historiaacademica from Historiaacademica historiaacademica left join fetch historiaacademica.userId")
    List<Historiaacademica> findAllWithToOneRelationships();

    @Query(
        "select historiaacademica from Historiaacademica historiaacademica left join fetch historiaacademica.userId where historiaacademica.id =:id"
    )
    Optional<Historiaacademica> findOneWithToOneRelationships(@Param("id") Long id);
}
