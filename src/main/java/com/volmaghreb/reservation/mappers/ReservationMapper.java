package com.volmaghreb.reservation.mappers;

import com.volmaghreb.reservation.dtos.ReservationDto;
import com.volmaghreb.reservation.entities.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {FlightMapper.class, UserMapper.class, TravelerMapper.class})
public interface ReservationMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "reservationTime", target = "reservationDate")
    ReservationDto toDto(Reservation reservation);

    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "reservationDate", target = "reservationTime")
    @Mapping(target = "payment", ignore = true)
    @Mapping(target = "reservationDateTime", ignore = true)
    @Mapping(target = "seat", ignore = true)
    @Mapping(target = "seatClass", ignore = true)
    @Mapping(target = "seatNumber", ignore = true)
    @Mapping(target = "totalPrice", ignore = true)
    Reservation toEntity(ReservationDto reservationDto);
}
