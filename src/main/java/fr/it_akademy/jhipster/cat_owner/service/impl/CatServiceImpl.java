package fr.it_akademy.jhipster.cat_owner.service.impl;

import fr.it_akademy.jhipster.cat_owner.domain.Cat;
import fr.it_akademy.jhipster.cat_owner.repository.CatRepository;
import fr.it_akademy.jhipster.cat_owner.service.CatService;
import fr.it_akademy.jhipster.cat_owner.service.dto.CatDTO;
import fr.it_akademy.jhipster.cat_owner.service.mapper.CatMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link fr.it_akademy.jhipster.cat_owner.domain.Cat}.
 */
@Service
@Transactional
public class CatServiceImpl implements CatService {

    private final Logger log = LoggerFactory.getLogger(CatServiceImpl.class);

    private final CatRepository catRepository;

    private final CatMapper catMapper;

    public CatServiceImpl(CatRepository catRepository, CatMapper catMapper) {
        this.catRepository = catRepository;
        this.catMapper = catMapper;
    }

    @Override
    public CatDTO save(CatDTO catDTO) {
        log.debug("Request to save Cat : {}", catDTO);
        Cat cat = catMapper.toEntity(catDTO);
        cat = catRepository.save(cat);
        return catMapper.toDto(cat);
    }

    @Override
    public CatDTO update(CatDTO catDTO) {
        log.debug("Request to update Cat : {}", catDTO);
        Cat cat = catMapper.toEntity(catDTO);
        cat = catRepository.save(cat);
        return catMapper.toDto(cat);
    }

    @Override
    public Optional<CatDTO> partialUpdate(CatDTO catDTO) {
        log.debug("Request to partially update Cat : {}", catDTO);

        return catRepository
            .findById(catDTO.getId())
            .map(existingCat -> {
                catMapper.partialUpdate(existingCat, catDTO);

                return existingCat;
            })
            .map(catRepository::save)
            .map(catMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CatDTO> findAll() {
        log.debug("Request to get all Cats");
        return catRepository.findAll().stream().map(catMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CatDTO> findOne(Long id) {
        log.debug("Request to get Cat : {}", id);
        return catRepository.findById(id).map(catMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Cat : {}", id);
        catRepository.deleteById(id);
    }
}
