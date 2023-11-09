package fr.it_akademy.jhipster.cat_owner.service.mapper;

import fr.it_akademy.jhipster.cat_owner.domain.Dog;
import fr.it_akademy.jhipster.cat_owner.domain.Owner;
import fr.it_akademy.jhipster.cat_owner.domain.Veterinary;
import fr.it_akademy.jhipster.cat_owner.service.dto.DogDTO;
import fr.it_akademy.jhipster.cat_owner.service.dto.OwnerDTO;
import fr.it_akademy.jhipster.cat_owner.service.dto.VeterinaryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Dog} and its DTO {@link DogDTO}.
 */
@Mapper(componentModel = "spring")
public interface DogMapper extends EntityMapper<DogDTO, Dog> {
    @Mapping(target = "owner", source = "owner", qualifiedByName = "ownerId")
    @Mapping(target = "veterinary", source = "veterinary", qualifiedByName = "veterinaryId")
    DogDTO toDto(Dog s);

    @Named("ownerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OwnerDTO toDtoOwnerId(Owner owner);

    @Named("veterinaryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    VeterinaryDTO toDtoVeterinaryId(Veterinary veterinary);
}
