package dev.sorokin.springbootmvc.service.mapper;

import dev.sorokin.springbootmvc.api.dto.RequestUpdateUserDto;
import dev.sorokin.springbootmvc.api.dto.RequestUserDto;
import dev.sorokin.springbootmvc.api.dto.ResponseUserDto;
import dev.sorokin.springbootmvc.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring",  uses = {PetMapper.class})
public interface UserMapper {

    User toEntity(RequestUserDto source);

    void update(@MappingTarget User target, RequestUpdateUserDto source );

    ResponseUserDto toWeb(User source);
}
