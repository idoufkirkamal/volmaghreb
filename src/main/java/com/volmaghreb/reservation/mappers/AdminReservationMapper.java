package com.volmaghreb.reservation.mappers;

import com.volmaghreb.reservation.dtos.AdminReservationDto;
import com.volmaghreb.reservation.entities.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {FlightMapper.class})
public interface AdminReservationMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.firstname", target = "userFirstname")
    @Mapping(source = "user.lastname", target = "userLastname")
    @Mapping(source = "user.email", target = "userEmail")
    @Mapping(source = "traveler.id", target = "travelerId")
    @Mapping(source = "traveler.name", target = "travelerName")
    @Mapping(source = "traveler.firstname", target = "travelerFirstname")
    @Mapping(source = "traveler.lastname", target = "travelerLastname")
    @Mapping(source = "payment.id", target = "paymentId")
    @Mapping(source = "payment.status", target = "paymentStatus")
    @Mapping(source = "payment.totalAmount", target = "paymentAmount")
    AdminReservationDto toDto(Reservation reservation);
}
