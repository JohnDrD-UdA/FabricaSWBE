package com.udea.gr.service.impl;

import com.udea.gr.domain.Historiaacademica;
import com.udea.gr.repository.HistoriaacademicaRepository;
import com.udea.gr.service.HistoriaacademicaService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Historiaacademica}.
 */
@Service
@Transactional
public class HistoriaacademicaServiceImpl implements HistoriaacademicaService {

    private final Logger log = LoggerFactory.getLogger(HistoriaacademicaServiceImpl.class);

    private final HistoriaacademicaRepository historiaacademicaRepository;

    public HistoriaacademicaServiceImpl(HistoriaacademicaRepository historiaacademicaRepository) {
        this.historiaacademicaRepository = historiaacademicaRepository;
    }

    @Override
    public Historiaacademica save(Historiaacademica historiaacademica) {
        log.debug("Request to save Historiaacademica : {}", historiaacademica);
        return historiaacademicaRepository.save(historiaacademica);
    }

    @Override
    public Historiaacademica update(Historiaacademica historiaacademica) {
        log.debug("Request to update Historiaacademica : {}", historiaacademica);
        return historiaacademicaRepository.save(historiaacademica);
    }

    @Override
    public Optional<Historiaacademica> partialUpdate(Historiaacademica historiaacademica) {
        log.debug("Request to partially update Historiaacademica : {}", historiaacademica);

        return historiaacademicaRepository
            .findById(historiaacademica.getId())
            .map(existingHistoriaacademica -> {
                return existingHistoriaacademica;
            })
            .map(historiaacademicaRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Historiaacademica> findAll() {
        log.debug("Request to get all Historiaacademicas");
        return historiaacademicaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Historiaacademica> findOne(Long id) {
        log.debug("Request to get Historiaacademica : {}", id);
        return historiaacademicaRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Historiaacademica : {}", id);
        historiaacademicaRepository.deleteById(id);
    }
}
