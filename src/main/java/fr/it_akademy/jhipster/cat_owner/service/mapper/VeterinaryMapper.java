package fr.it_akademy.jhipster.cat_owner.service.mapper;

import fr.it_akademy.jhipster.cat_owner.domain.Veterinary;
import fr.it_akademy.jhipster.cat_owner.service.dto.VeterinaryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Veterinary} and its DTO {@link VeterinaryDTO}.
 */
@Mapper(componentModel = "spring")
public interface VeterinaryMapper extends EntityMapper<VeterinaryDTO, Veterinary> {}
