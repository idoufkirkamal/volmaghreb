package com.volmaghreb.reservation.mappers;

import com.volmaghreb.reservation.dtos.TravelerDto;
import com.volmaghreb.reservation.entities.Traveler;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TravelerMapper {
    TravelerDto toDto(Traveler traveler);
    Traveler toEntity(TravelerDto travelerDto);
}
