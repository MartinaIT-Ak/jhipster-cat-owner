package fr.it_akademy.jhipster.cat_owner.service.impl;

import fr.it_akademy.jhipster.cat_owner.domain.Owner;
import fr.it_akademy.jhipster.cat_owner.repository.OwnerRepository;
import fr.it_akademy.jhipster.cat_owner.service.OwnerService;
import fr.it_akademy.jhipster.cat_owner.service.dto.OwnerDTO;
import fr.it_akademy.jhipster.cat_owner.service.mapper.OwnerMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link fr.it_akademy.jhipster.cat_owner.domain.Owner}.
 */
@Service
@Transactional
public class OwnerServiceImpl implements OwnerService {

    private final Logger log = LoggerFactory.getLogger(OwnerServiceImpl.class);

    private final OwnerRepository ownerRepository;

    private final OwnerMapper ownerMapper;

    public OwnerServiceImpl(OwnerRepository ownerRepository, OwnerMapper ownerMapper) {
        this.ownerRepository = ownerRepository;
        this.ownerMapper = ownerMapper;
    }

    @Override
    public OwnerDTO save(OwnerDTO ownerDTO) {
        log.debug("Request to save Owner : {}", ownerDTO);
        Owner owner = ownerMapper.toEntity(ownerDTO);
        owner = ownerRepository.save(owner);
        return ownerMapper.toDto(owner);
    }

    @Override
    public OwnerDTO update(OwnerDTO ownerDTO) {
        log.debug("Request to update Owner : {}", ownerDTO);
        Owner owner = ownerMapper.toEntity(ownerDTO);
        owner = ownerRepository.save(owner);
        return ownerMapper.toDto(owner);
    }

    @Override
    public Optional<OwnerDTO> partialUpdate(OwnerDTO ownerDTO) {
        log.debug("Request to partially update Owner : {}", ownerDTO);

        return ownerRepository
            .findById(ownerDTO.getId())
            .map(existingOwner -> {
                ownerMapper.partialUpdate(existingOwner, ownerDTO);

                return existingOwner;
            })
            .map(ownerRepository::save)
            .map(ownerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OwnerDTO> findAll() {
        log.debug("Request to get all Owners");
        return ownerRepository.findAll().stream().map(ownerMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    public Page<OwnerDTO> findAllWithEagerRelationships(Pageable pageable) {
        return ownerRepository.findAllWithEagerRelationships(pageable).map(ownerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OwnerDTO> findOne(Long id) {
        log.debug("Request to get Owner : {}", id);
        return ownerRepository.findOneWithEagerRelationships(id).map(ownerMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Owner : {}", id);
        ownerRepository.deleteById(id);
    }
}
