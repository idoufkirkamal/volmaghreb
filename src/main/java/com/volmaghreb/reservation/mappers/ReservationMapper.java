package com.volmaghreb.reservation.mappers;

import com.volmaghreb.reservation.dtos.ReservationDto;
import com.volmaghreb.reservation.entities.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {FlightMapper.class, UserMapper.class})
public interface ReservationMapper {

    @Mapping(source = "user.id", target = "userId")
    ReservationDto toDto(Reservation reservation);

    Reservation toEntity(ReservationDto reservationDto);
}
