package com.udea.gr.service.impl;

import com.udea.gr.domain.Planestudios;
import com.udea.gr.repository.PlanestudiosRepository;
import com.udea.gr.service.PlanestudiosService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Planestudios}.
 */
@Service
@Transactional
public class PlanestudiosServiceImpl implements PlanestudiosService {

    private final Logger log = LoggerFactory.getLogger(PlanestudiosServiceImpl.class);

    private final PlanestudiosRepository planestudiosRepository;

    public PlanestudiosServiceImpl(PlanestudiosRepository planestudiosRepository) {
        this.planestudiosRepository = planestudiosRepository;
    }

    @Override
    public Planestudios save(Planestudios planestudios) {
        log.debug("Request to save Planestudios : {}", planestudios);
        return planestudiosRepository.save(planestudios);
    }

    @Override
    public Planestudios update(Planestudios planestudios) {
        log.debug("Request to update Planestudios : {}", planestudios);
        return planestudiosRepository.save(planestudios);
    }

    @Override
    public Optional<Planestudios> partialUpdate(Planestudios planestudios) {
        log.debug("Request to partially update Planestudios : {}", planestudios);

        return planestudiosRepository
            .findById(planestudios.getId())
            .map(existingPlanestudios -> {
                if (planestudios.getIdprograma() != null) {
                    existingPlanestudios.setIdprograma(planestudios.getIdprograma());
                }
                if (planestudios.getFacultad() != null) {
                    existingPlanestudios.setFacultad(planestudios.getFacultad());
                }

                return existingPlanestudios;
            })
            .map(planestudiosRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Planestudios> findAll() {
        log.debug("Request to get all Planestudios");
        return planestudiosRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Planestudios> findOne(Long id) {
        log.debug("Request to get Planestudios : {}", id);
        return planestudiosRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Planestudios : {}", id);
        planestudiosRepository.deleteById(id);
    }
}
