package ua.kpi.its.lab.security.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

@Entity
public class Route implements Comparable<Route> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String departurePoint;
    private String destination;
    private LocalDateTime dateOfShipment;
    private Integer mileage;

    private Double priceOfTicket;

    private Boolean isCircular;

    @Override
    public String toString() {

        return "Route{" +
                "id=" + id +
                ", departurePoint='" + departurePoint + '\'' +
                ", destination='" + destination + '\'' +
                ", dateOfShipment=" + dateOfShipment +
                ", mileage=" + mileage +
                ", priceOfTicket=" + priceOfTicket +
                ", isAnnular=" + isCircular +

                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeparturePoint() {
        return departurePoint;
    }

    public void setDeparturePoint(String departurePoint) {
        this.departurePoint = departurePoint;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDateTime getDateOfShipment() {
        return dateOfShipment;
    }

    public void setDateOfShipment(LocalDateTime dateOfShipment) {
        this.dateOfShipment = dateOfShipment;
    }

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    public Double getPriceOfTicket() {
        return priceOfTicket;
    }

    public void setPriceOfTicket(Double priceOfTicket) {
        this.priceOfTicket = priceOfTicket;
    }

    public Boolean getIsCircular() {
        return isCircular;
    }

    public void setIsCircular(Boolean isCircular) {
        isCircular = isCircular;
    }

    @Override
    public int compareTo(@NotNull Route o) {
        int diffBetwenId = this.id - o.id;
        if (diffBetwenId != 0){
            return diffBetwenId;
        }
        return this.mileage - o.mileage;
    }
}

