package ua.kpi.its.lab.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.kpi.its.lab.security.dto.RouteDto;
import ua.kpi.its.lab.security.svc.RouteControllerService;

/**
 * Контролер для управління маршрутами.
 */
@RestController
@RequestMapping("/api/route")
public class RouteController {

    @Autowired
    private RouteControllerService routeControllerService;

    /**
     * Отримує інформацію про маршрут за його ідентифікатором.
     *
     * @param id Ідентифікатор маршруту.
     * @return Інформація про маршрут у форматі [RouteDto].
     */
    @GetMapping("/{id}")
    public RouteDto getRoute(@PathVariable("id") Integer id) {
        return null;
    }

    /**
     * Видаляє маршрут за його ідентифікатором.
     *
     * @param id Ідентифікатор маршруту, який потрібно видалити.
     */
    @DeleteMapping("/{id}")
    public void deleteRoute(@PathVariable("id") Integer id) {
        routeControllerService.deleteRoute(id);
    }

    /**
     * Створює новий маршрут.
     *
     * @param routeDto Інформація про маршрут для створення.
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createRoute(@RequestBody RouteDto routeDto) {
        routeControllerService.createRoute(routeDto);
    }

    /**
     * Оновлює інформацію про маршрут.
     *
     * @param routeDto Інформація про маршрут для оновлення.
     * @return Оновлена інформація про маршрут у форматі [RouteDto].
     */
    @PatchMapping
    public RouteDto updateRoute(@RequestBody RouteDto routeDto) {
        return routeControllerService.updateRoute(routeDto);
    }
}
