package ua.kpi.its.lab.security.svc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kpi.its.lab.security.builders.RouteBuilderImpl;
import ua.kpi.its.lab.security.dto.CreateRouteRequest;
import ua.kpi.its.lab.security.dto.RouteDto;
import ua.kpi.its.lab.security.entity.Route;
import ua.kpi.its.lab.security.svc.impl.RouteServiceImpl;


/**
 * Сервіс для управління маршрутами.
 */
@Service
public class RouteControllerService {

    @Autowired
    private RouteServiceImpl routeService;

    /**
     * Оновлює інформацію про маршрут.
     *
     * @param routeDto Інформація про маршрут для оновлення.
     * @return Оновлена інформація про маршрут у форматі [RouteDto].
     */
    public RouteDto updateRoute(RouteDto routeDto) {
        Route route = exchangeDtoForRoute(routeDto);
        routeService.update(route);
        return exchangeRouteForDto(route);
    }

    /**
     * Конвертує DTO об'єкт в об'єкт маршруту.
     *
     * @param routeDto Інформація про маршрут у форматі [RouteDto].
     * @return Маршрут у форматі [Route].
     */
    private Route exchangeDtoForRoute(RouteDto routeDto) {
        Route route = new RouteBuilderImpl().builder()
                .dateOfShipment(routeDto.dateOfShipment())
                .destination(routeDto.destination())
                .isCircular(routeDto.isCircular())
                .departurePoint(routeDto.departurePoint())
                .mileage(routeDto.mileage())
                .priceOfTicket(routeDto.priceOfTicket())
                .build();
        return route;
    }

    /**
     * Отримує інформацію про маршрут за його ідентифікатором.
     *
     * @param id Ідентифікатор маршруту.
     * @return Інформація про маршрут у форматі [RouteDto].
     */
    public RouteDto getRoute(Integer id) {
        return exchangeRouteForDto(routeService.findById(id));
    }

    /**
     * Видаляє маршрут за його ідентифікатором.
     *
     * @param id Ідентифікатор маршруту, який потрібно видалити.
     */
    public void deleteRoute(Integer id) {
        routeService.delete(id);
    }

    /**
     * Створює новий маршрут.
     *
     * @param routeDto Інформація про маршрут для створення.
     */
    public void createRoute(CreateRouteRequest routeDto) {
        Route route = new RouteBuilderImpl().builder()
                .priceOfTicket(routeDto.priceOfTicket())
                .mileage(routeDto.mileage())
                .departurePoint(routeDto.departurePoint())
                .isCircular(routeDto.isCircular())
                .destination(routeDto.destination())
                .dateOfShipment(routeDto.dateOfShipment())
                .build();

        routeService.create(route);
    }

    /**
     * Конвертує маршрут в DTO об'єкт.
     *
     * @param route Маршрут у форматі [Route].
     * @return Інформація про маршрут у форматі [RouteDto].
     */
    private RouteDto exchangeRouteForDto(Route route) {
        return new RouteDto(
                route.getId(),
                route.getDeparturePoint(),
                route.getDestination(),
                route.getDateOfShipment(),
                route.getMileage(),
                route.getPriceOfTicket(),
                route.getIsCircular()
        );
    }
}
