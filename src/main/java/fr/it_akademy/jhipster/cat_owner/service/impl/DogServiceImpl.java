package fr.it_akademy.jhipster.cat_owner.service.impl;

import fr.it_akademy.jhipster.cat_owner.domain.Dog;
import fr.it_akademy.jhipster.cat_owner.repository.DogRepository;
import fr.it_akademy.jhipster.cat_owner.service.DogService;
import fr.it_akademy.jhipster.cat_owner.service.dto.DogDTO;
import fr.it_akademy.jhipster.cat_owner.service.mapper.DogMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link fr.it_akademy.jhipster.cat_owner.domain.Dog}.
 */
@Service
@Transactional
public class DogServiceImpl implements DogService {

    private final Logger log = LoggerFactory.getLogger(DogServiceImpl.class);

    private final DogRepository dogRepository;

    private final DogMapper dogMapper;

    public DogServiceImpl(DogRepository dogRepository, DogMapper dogMapper) {
        this.dogRepository = dogRepository;
        this.dogMapper = dogMapper;
    }

    @Override
    public DogDTO save(DogDTO dogDTO) {
        log.debug("Request to save Dog : {}", dogDTO);
        Dog dog = dogMapper.toEntity(dogDTO);
        dog = dogRepository.save(dog);
        return dogMapper.toDto(dog);
    }

    @Override
    public DogDTO update(DogDTO dogDTO) {
        log.debug("Request to update Dog : {}", dogDTO);
        Dog dog = dogMapper.toEntity(dogDTO);
        dog = dogRepository.save(dog);
        return dogMapper.toDto(dog);
    }

    @Override
    public Optional<DogDTO> partialUpdate(DogDTO dogDTO) {
        log.debug("Request to partially update Dog : {}", dogDTO);

        return dogRepository
            .findById(dogDTO.getId())
            .map(existingDog -> {
                dogMapper.partialUpdate(existingDog, dogDTO);

                return existingDog;
            })
            .map(dogRepository::save)
            .map(dogMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DogDTO> findAll() {
        log.debug("Request to get all Dogs");
        return dogRepository.findAll().stream().map(dogMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DogDTO> findOne(Long id) {
        log.debug("Request to get Dog : {}", id);
        return dogRepository.findById(id).map(dogMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Dog : {}", id);
        dogRepository.deleteById(id);
    }
}
