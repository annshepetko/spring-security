package ua.kpi.its.lab.security.builders;

import ua.kpi.its.lab.security.entity.Route;
import ua.kpi.its.lab.security.entity.Train;

import java.time.LocalDateTime;
import java.util.List;

public class RouteBuilderImpl implements RouteBuilder {

    private Route route;

    public RouteBuilderImpl() {
        this.route = new Route();
    }
    public RouteBuilderImpl departurePoint(String departurePoint) {
        route.setDeparturePoint(departurePoint);
        return this;
    }
    public RouteBuilderImpl destination(String destination) {
        route.setDestination(destination);
        return this;
    }
    public RouteBuilderImpl trains(List<Train> trains){
        route.setTrains(trains);
        return this;
    }
    public RouteBuilderImpl dateOfShipment(LocalDateTime date) {
        route.setDateOfShipment(date);
        return this;
    }
    public RouteBuilderImpl mileage(Integer mileage) {
        route.setMileage(mileage);
        return this;
    }
    public RouteBuilderImpl priceOfTicket(Double price) {
        route.setPriceOfTicket(price);
        return this;
    }
    public RouteBuilderImpl isCircular(Boolean isCircular) {
        route.setIsCircular(isCircular);
        return this;
    }

    @Override
    public Route build() {
        return this.route;
    }

    @Override
    public RouteBuilderImpl builder() {
        return this;
    }
}
