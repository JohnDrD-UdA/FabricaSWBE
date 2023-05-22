package com.udea.gr.service.impl;

import com.udea.gr.domain.Estudiante;
import com.udea.gr.repository.EstudianteRepository;
import com.udea.gr.service.EstudianteService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Estudiante}.
 */
@Service
@Transactional
public class EstudianteServiceImpl implements EstudianteService {

    private final Logger log = LoggerFactory.getLogger(EstudianteServiceImpl.class);

    private final EstudianteRepository estudianteRepository;

    public EstudianteServiceImpl(EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    @Override
    public Estudiante save(Estudiante estudiante) {
        log.debug("Request to save Estudiante : {}", estudiante);
        return estudianteRepository.save(estudiante);
    }

    @Override
    public Estudiante update(Estudiante estudiante) {
        log.debug("Request to update Estudiante : {}", estudiante);
        return estudianteRepository.save(estudiante);
    }

    @Override
    public Optional<Estudiante> partialUpdate(Estudiante estudiante) {
        log.debug("Request to partially update Estudiante : {}", estudiante);

        return estudianteRepository
            .findById(estudiante.getId())
            .map(existingEstudiante -> {
                if (estudiante.getNombre() != null) {
                    existingEstudiante.setNombre(estudiante.getNombre());
                }
                if (estudiante.getEmail() != null) {
                    existingEstudiante.setEmail(estudiante.getEmail());
                }
                if (estudiante.getTipodoc() != null) {
                    existingEstudiante.setTipodoc(estudiante.getTipodoc());
                }
                if (estudiante.getDocumento() != null) {
                    existingEstudiante.setDocumento(estudiante.getDocumento());
                }

                return existingEstudiante;
            })
            .map(estudianteRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Estudiante> findAll() {
        log.debug("Request to get all Estudiantes");
        return estudianteRepository.findAll();
    }

    public Page<Estudiante> findAllWithEagerRelationships(Pageable pageable) {
        return estudianteRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Estudiante> findOne(Long id) {
        log.debug("Request to get Estudiante : {}", id);
        return estudianteRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Estudiante : {}", id);
        estudianteRepository.deleteById(id);
    }
}
