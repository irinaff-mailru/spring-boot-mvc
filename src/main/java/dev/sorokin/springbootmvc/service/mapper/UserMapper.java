package dev.sorokin.springbootmvc.service.mapper;

import dev.sorokin.springbootmvc.api.dto.RequestUserDto;
import dev.sorokin.springbootmvc.api.dto.ResponseUserDto;
import dev.sorokin.springbootmvc.domain.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",  uses = {PetMapper.class})
public interface UserMapper {

    User toEntity(RequestUserDto source);

    ResponseUserDto toWeb(User source);
}
