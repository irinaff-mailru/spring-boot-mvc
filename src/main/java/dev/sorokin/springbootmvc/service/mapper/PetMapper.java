package dev.sorokin.springbootmvc.service.mapper;

import dev.sorokin.springbootmvc.api.dto.RequestPetDto;
import dev.sorokin.springbootmvc.api.dto.ResponsePetDto;
import dev.sorokin.springbootmvc.domain.entity.Pet;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PetMapper {

    List<Pet> toEntities(List<RequestPetDto> source);

    Pet toEntity(RequestPetDto source);

    List<ResponsePetDto> toWeb(List<Pet> source);

    ResponsePetDto toWeb(Pet source);
}
