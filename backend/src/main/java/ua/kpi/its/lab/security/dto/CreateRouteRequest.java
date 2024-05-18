package ua.kpi.its.lab.security.dto;
import java.time.LocalDateTime;
import java.util.List;

public record CreateRouteRequest(
        String departurePoint,
        String destination,
        LocalDateTime dateOfShipment,
        Integer mileage,
        Double priceOfTicket,
        Boolean isCircular,
        List<TrainDto> trainDtos
        )
{
}
