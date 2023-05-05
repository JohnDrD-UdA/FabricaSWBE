package com.udea.gr.service.impl;

import com.udea.gr.domain.Pazysalvo;
import com.udea.gr.repository.PazysalvoRepository;
import com.udea.gr.service.PazysalvoService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Pazysalvo}.
 */
@Service
@Transactional
public class PazysalvoServiceImpl implements PazysalvoService {

    private final Logger log = LoggerFactory.getLogger(PazysalvoServiceImpl.class);

    private final PazysalvoRepository pazysalvoRepository;

    public PazysalvoServiceImpl(PazysalvoRepository pazysalvoRepository) {
        this.pazysalvoRepository = pazysalvoRepository;
    }

    @Override
    public Pazysalvo save(Pazysalvo pazysalvo) {
        log.debug("Request to save Pazysalvo : {}", pazysalvo);
        return pazysalvoRepository.save(pazysalvo);
    }

    @Override
    public Pazysalvo update(Pazysalvo pazysalvo) {
        log.debug("Request to update Pazysalvo : {}", pazysalvo);
        return pazysalvoRepository.save(pazysalvo);
    }

    @Override
    public Optional<Pazysalvo> partialUpdate(Pazysalvo pazysalvo) {
        log.debug("Request to partially update Pazysalvo : {}", pazysalvo);

        return pazysalvoRepository
            .findById(pazysalvo.getId())
            .map(existingPazysalvo -> {
                if (pazysalvo.getFecha() != null) {
                    existingPazysalvo.setFecha(pazysalvo.getFecha());
                }
                if (pazysalvo.getMateriasobl() != null) {
                    existingPazysalvo.setMateriasobl(pazysalvo.getMateriasobl());
                }
                if (pazysalvo.getMateriaselec() != null) {
                    existingPazysalvo.setMateriaselec(pazysalvo.getMateriaselec());
                }
                if (pazysalvo.getPendientesnota() != null) {
                    existingPazysalvo.setPendientesnota(pazysalvo.getPendientesnota());
                }
                if (pazysalvo.getBiblioteca() != null) {
                    existingPazysalvo.setBiblioteca(pazysalvo.getBiblioteca());
                }
                if (pazysalvo.getCartera() != null) {
                    existingPazysalvo.setCartera(pazysalvo.getCartera());
                }
                if (pazysalvo.getImpedimento() != null) {
                    existingPazysalvo.setImpedimento(pazysalvo.getImpedimento());
                }

                return existingPazysalvo;
            })
            .map(pazysalvoRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pazysalvo> findAll() {
        log.debug("Request to get all Pazysalvos");
        return pazysalvoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Pazysalvo> findOne(Long id) {
        log.debug("Request to get Pazysalvo : {}", id);
        return pazysalvoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Pazysalvo : {}", id);
        pazysalvoRepository.deleteById(id);
    }
}
