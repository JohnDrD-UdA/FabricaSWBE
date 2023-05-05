package com.udea.gr.service.impl;

import com.udea.gr.domain.Ceremoniagrados;
import com.udea.gr.repository.CeremoniagradosRepository;
import com.udea.gr.service.CeremoniagradosService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Ceremoniagrados}.
 */
@Service
@Transactional
public class CeremoniagradosServiceImpl implements CeremoniagradosService {

    private final Logger log = LoggerFactory.getLogger(CeremoniagradosServiceImpl.class);

    private final CeremoniagradosRepository ceremoniagradosRepository;

    public CeremoniagradosServiceImpl(CeremoniagradosRepository ceremoniagradosRepository) {
        this.ceremoniagradosRepository = ceremoniagradosRepository;
    }

    @Override
    public Ceremoniagrados save(Ceremoniagrados ceremoniagrados) {
        log.debug("Request to save Ceremoniagrados : {}", ceremoniagrados);
        return ceremoniagradosRepository.save(ceremoniagrados);
    }

    @Override
    public Ceremoniagrados update(Ceremoniagrados ceremoniagrados) {
        log.debug("Request to update Ceremoniagrados : {}", ceremoniagrados);
        return ceremoniagradosRepository.save(ceremoniagrados);
    }

    @Override
    public Optional<Ceremoniagrados> partialUpdate(Ceremoniagrados ceremoniagrados) {
        log.debug("Request to partially update Ceremoniagrados : {}", ceremoniagrados);

        return ceremoniagradosRepository
            .findById(ceremoniagrados.getId())
            .map(existingCeremoniagrados -> {
                if (ceremoniagrados.getFecha() != null) {
                    existingCeremoniagrados.setFecha(ceremoniagrados.getFecha());
                }
                if (ceremoniagrados.getLiminscripcion() != null) {
                    existingCeremoniagrados.setLiminscripcion(ceremoniagrados.getLiminscripcion());
                }
                if (ceremoniagrados.getLugar() != null) {
                    existingCeremoniagrados.setLugar(ceremoniagrados.getLugar());
                }

                return existingCeremoniagrados;
            })
            .map(ceremoniagradosRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Ceremoniagrados> findAll() {
        log.debug("Request to get all Ceremoniagrados");
        return ceremoniagradosRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Ceremoniagrados> findOne(Long id) {
        log.debug("Request to get Ceremoniagrados : {}", id);
        return ceremoniagradosRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Ceremoniagrados : {}", id);
        ceremoniagradosRepository.deleteById(id);
    }
}
