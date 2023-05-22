package com.udea.gr.service.impl;

import com.udea.gr.Constants.PazySalvoConts;
import com.udea.gr.DTO.pazysalvoValidarResponse;
import com.udea.gr.DTO.studentDataResponse;
import com.udea.gr.domain.Pazysalvo;
import com.udea.gr.repository.PazysalvoRepository;
import com.udea.gr.service.PazysalvoService;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

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

    @Override
    public pazysalvoValidarResponse getRequisitosByUserDoc(String id) {
        try {
            Pazysalvo ps = pazysalvoRepository.GetByUserDoc(id);
            if (ps == null || ps.getId() == null) {
                throw new Exception();
            }
            pazysalvoValidarResponse response = new pazysalvoValidarResponse();
            response.biblioteca = Optional.of(ps.getBiblioteca());
            response.cartera = Optional.of(ps.getCartera());
            response.impedimento = Optional.of(ps.getImpedimento());
            response.materiasElec = Optional.of(ps.getMateriaselec());
            response.materiasOb = Optional.of(ps.getMateriasobl());
            response.pendientesNotas = Optional.of(ps.getPendientesnota());
            response.studentData = Optional.of(new studentDataResponse());
            response.studentData.get().name = ps.getHistoriaacademicaId().getEstudianteid().getNombre();
            response.studentData.get().program = ps.getHistoriaacademicaId().getPlanestudiosId().getNombreprograma();
            response.studentData.get().programCode = String
                    .valueOf(ps.getHistoriaacademicaId().getPlanestudiosId().getIdprograma());
            response.msg = new PazySalvoConts().FOUND;
            return response;
        } catch (Exception e) {
            pazysalvoValidarResponse response = new pazysalvoValidarResponse();
            response.msg = new PazySalvoConts().NOT_FOUND;
            return response;
        }
    }
}
