package fr.it_akademy.jhipster.cat_owner.service.mapper;

import fr.it_akademy.jhipster.cat_owner.domain.Cat;
import fr.it_akademy.jhipster.cat_owner.domain.Owner;
import fr.it_akademy.jhipster.cat_owner.domain.Veterinary;
import fr.it_akademy.jhipster.cat_owner.service.dto.CatDTO;
import fr.it_akademy.jhipster.cat_owner.service.dto.OwnerDTO;
import fr.it_akademy.jhipster.cat_owner.service.dto.VeterinaryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cat} and its DTO {@link CatDTO}.
 */
@Mapper(componentModel = "spring")
public interface CatMapper extends EntityMapper<CatDTO, Cat> {
    @Mapping(target = "veterinary", source = "veterinary", qualifiedByName = "veterinaryId")
    @Mapping(target = "owner", source = "owner", qualifiedByName = "ownerId")
    CatDTO toDto(Cat s);

    @Named("veterinaryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    VeterinaryDTO toDtoVeterinaryId(Veterinary veterinary);

    @Named("ownerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OwnerDTO toDtoOwnerId(Owner owner);
}
