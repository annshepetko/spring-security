package ua.kpi.its.lab.security.dto;

import java.time.LocalDateTime;


public record RouteDto(
        Integer id,
        String departurePoint,
        String destination,
        LocalDateTime dateOfShipment,
        Integer mileage,
        Double priceOfTicket,
        Boolean isCircular
) {}