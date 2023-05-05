package com.udea.gr.service.impl;

import com.udea.gr.domain.Materia;
import com.udea.gr.repository.MateriaRepository;
import com.udea.gr.service.MateriaService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Materia}.
 */
@Service
@Transactional
public class MateriaServiceImpl implements MateriaService {

    private final Logger log = LoggerFactory.getLogger(MateriaServiceImpl.class);

    private final MateriaRepository materiaRepository;

    public MateriaServiceImpl(MateriaRepository materiaRepository) {
        this.materiaRepository = materiaRepository;
    }

    @Override
    public Materia save(Materia materia) {
        log.debug("Request to save Materia : {}", materia);
        return materiaRepository.save(materia);
    }

    @Override
    public Materia update(Materia materia) {
        log.debug("Request to update Materia : {}", materia);
        return materiaRepository.save(materia);
    }

    @Override
    public Optional<Materia> partialUpdate(Materia materia) {
        log.debug("Request to partially update Materia : {}", materia);

        return materiaRepository
            .findById(materia.getId())
            .map(existingMateria -> {
                if (materia.getNombre() != null) {
                    existingMateria.setNombre(materia.getNombre());
                }
                if (materia.getTipomateria() != null) {
                    existingMateria.setTipomateria(materia.getTipomateria());
                }

                return existingMateria;
            })
            .map(materiaRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Materia> findAll() {
        log.debug("Request to get all Materias");
        return materiaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Materia> findOne(Long id) {
        log.debug("Request to get Materia : {}", id);
        return materiaRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Materia : {}", id);
        materiaRepository.deleteById(id);
    }
}
