package com.udea.gr.service.impl;

import com.udea.gr.domain.Regmateria;
import com.udea.gr.repository.RegmateriaRepository;
import com.udea.gr.service.RegmateriaService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Regmateria}.
 */
@Service
@Transactional
public class RegmateriaServiceImpl implements RegmateriaService {

    private final Logger log = LoggerFactory.getLogger(RegmateriaServiceImpl.class);

    private final RegmateriaRepository regmateriaRepository;

    public RegmateriaServiceImpl(RegmateriaRepository regmateriaRepository) {
        this.regmateriaRepository = regmateriaRepository;
    }

    @Override
    public Regmateria save(Regmateria regmateria) {
        log.debug("Request to save Regmateria : {}", regmateria);
        return regmateriaRepository.save(regmateria);
    }

    @Override
    public Regmateria update(Regmateria regmateria) {
        log.debug("Request to update Regmateria : {}", regmateria);
        return regmateriaRepository.save(regmateria);
    }

    @Override
    public Optional<Regmateria> partialUpdate(Regmateria regmateria) {
        log.debug("Request to partially update Regmateria : {}", regmateria);

        return regmateriaRepository
            .findById(regmateria.getId())
            .map(existingRegmateria -> {
                if (regmateria.getEstado() != null) {
                    existingRegmateria.setEstado(regmateria.getEstado());
                }

                return existingRegmateria;
            })
            .map(regmateriaRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Regmateria> findAll() {
        log.debug("Request to get all Regmaterias");
        return regmateriaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Regmateria> findOne(Long id) {
        log.debug("Request to get Regmateria : {}", id);
        return regmateriaRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Regmateria : {}", id);
        regmateriaRepository.deleteById(id);
    }
}
