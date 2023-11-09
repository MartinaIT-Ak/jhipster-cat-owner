package fr.it_akademy.jhipster.cat_owner.service.impl;

import fr.it_akademy.jhipster.cat_owner.domain.Veterinary;
import fr.it_akademy.jhipster.cat_owner.repository.VeterinaryRepository;
import fr.it_akademy.jhipster.cat_owner.service.VeterinaryService;
import fr.it_akademy.jhipster.cat_owner.service.dto.VeterinaryDTO;
import fr.it_akademy.jhipster.cat_owner.service.mapper.VeterinaryMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link fr.it_akademy.jhipster.cat_owner.domain.Veterinary}.
 */
@Service
@Transactional
public class VeterinaryServiceImpl implements VeterinaryService {

    private final Logger log = LoggerFactory.getLogger(VeterinaryServiceImpl.class);

    private final VeterinaryRepository veterinaryRepository;

    private final VeterinaryMapper veterinaryMapper;

    public VeterinaryServiceImpl(VeterinaryRepository veterinaryRepository, VeterinaryMapper veterinaryMapper) {
        this.veterinaryRepository = veterinaryRepository;
        this.veterinaryMapper = veterinaryMapper;
    }

    @Override
    public VeterinaryDTO save(VeterinaryDTO veterinaryDTO) {
        log.debug("Request to save Veterinary : {}", veterinaryDTO);
        Veterinary veterinary = veterinaryMapper.toEntity(veterinaryDTO);
        veterinary = veterinaryRepository.save(veterinary);
        return veterinaryMapper.toDto(veterinary);
    }

    @Override
    public VeterinaryDTO update(VeterinaryDTO veterinaryDTO) {
        log.debug("Request to update Veterinary : {}", veterinaryDTO);
        Veterinary veterinary = veterinaryMapper.toEntity(veterinaryDTO);
        veterinary = veterinaryRepository.save(veterinary);
        return veterinaryMapper.toDto(veterinary);
    }

    @Override
    public Optional<VeterinaryDTO> partialUpdate(VeterinaryDTO veterinaryDTO) {
        log.debug("Request to partially update Veterinary : {}", veterinaryDTO);

        return veterinaryRepository
            .findById(veterinaryDTO.getId())
            .map(existingVeterinary -> {
                veterinaryMapper.partialUpdate(existingVeterinary, veterinaryDTO);

                return existingVeterinary;
            })
            .map(veterinaryRepository::save)
            .map(veterinaryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VeterinaryDTO> findAll() {
        log.debug("Request to get all Veterinaries");
        return veterinaryRepository.findAll().stream().map(veterinaryMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VeterinaryDTO> findOne(Long id) {
        log.debug("Request to get Veterinary : {}", id);
        return veterinaryRepository.findById(id).map(veterinaryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Veterinary : {}", id);
        veterinaryRepository.deleteById(id);
    }
}
