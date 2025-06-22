package com.volmaghreb.reservation.mappers;

import com.volmaghreb.reservation.dtos.UserProfileDTO;
import com.volmaghreb.reservation.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserProfileDTO toDto(User user);
    User toEntity(UserProfileDTO userDto);
}
