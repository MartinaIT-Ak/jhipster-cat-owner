package fr.it_akademy.jhipster.cat_owner.service.mapper;

import fr.it_akademy.jhipster.cat_owner.domain.Owner;
import fr.it_akademy.jhipster.cat_owner.domain.Veterinary;
import fr.it_akademy.jhipster.cat_owner.service.dto.OwnerDTO;
import fr.it_akademy.jhipster.cat_owner.service.dto.VeterinaryDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Owner} and its DTO {@link OwnerDTO}.
 */
@Mapper(componentModel = "spring")
public interface OwnerMapper extends EntityMapper<OwnerDTO, Owner> {
    @Mapping(target = "veterinaries", source = "veterinaries", qualifiedByName = "veterinaryIdSet")
    OwnerDTO toDto(Owner s);

    @Mapping(target = "removeVeterinary", ignore = true)
    Owner toEntity(OwnerDTO ownerDTO);

    @Named("veterinaryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    VeterinaryDTO toDtoVeterinaryId(Veterinary veterinary);

    @Named("veterinaryIdSet")
    default Set<VeterinaryDTO> toDtoVeterinaryIdSet(Set<Veterinary> veterinary) {
        return veterinary.stream().map(this::toDtoVeterinaryId).collect(Collectors.toSet());
    }
}
