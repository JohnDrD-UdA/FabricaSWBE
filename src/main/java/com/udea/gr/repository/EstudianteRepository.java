package com.udea.gr.repository;

import com.udea.gr.domain.Estudiante;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Estudiante entity.
 */
@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
    @Query("select estudiante from Estudiante estudiante where estudiante.userid.login = ?#{principal.username}")
    List<Estudiante> findByUseridIsCurrentUser();

    default Optional<Estudiante> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Estudiante> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Estudiante> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct estudiante from Estudiante estudiante left join fetch estudiante.userid",
        countQuery = "select count(distinct estudiante) from Estudiante estudiante"
    )
    Page<Estudiante> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct estudiante from Estudiante estudiante left join fetch estudiante.userid")
    List<Estudiante> findAllWithToOneRelationships();

    @Query("select estudiante from Estudiante estudiante left join fetch estudiante.userid where estudiante.id =:id")
    Optional<Estudiante> findOneWithToOneRelationships(@Param("id") Long id);
}
