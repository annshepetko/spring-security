package ua.kpi.its.lab.security.entity;

import jakarta.persistence.*;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Train implements Comparable<Train> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String model;
    private String producer;
    private TrainTypes type;
    private LocalDateTime dateOfCommissioning;
    private Integer numberOfSeats;
    private Double weight;
    private Boolean hasConditioner;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            joinColumns = @JoinColumn(name = "train_id"),
            inverseJoinColumns = @JoinColumn(name = "route_id")
    )
    private List<Route> routes;

    public Integer getId() {
        return this.id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getModel() {
        return this.model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public String getProducer() {
        return this.producer;
    }
    public void setProducer(String producer) {
        this.producer = producer;
    }
    public TrainTypes getType() {
        return this.type;
    }
    public void setType(TrainTypes type) {
        this.type = type;
    }
    public LocalDateTime getDateOfCommissioning() {
        return this.dateOfCommissioning;
    }
    public void setDateOfCommissioning(LocalDateTime dateOfCommissioning) {
        this.dateOfCommissioning = dateOfCommissioning;
    }
    public Integer getNumberOfSeats() {
        return this.numberOfSeats;
    }
    public void setNumberOfSeats(Integer numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }
    public Double getWeight() {
        return this.weight;
    }
    public void setWeight(Double weight) {
        this.weight = weight;
    }
    public Boolean getHasConditioner() {
        return this.hasConditioner;
    }
    public void setHasConditioner(Boolean hasConditioner) {
        this.hasConditioner = hasConditioner;
    }
    public List<Route> getRoutes() {
        return this.routes;
    }
    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }
    @Override
    public String toString() {
        return "Train{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", producer='" + producer + '\'' +
                ", type='" + type.name() + '\'' +
                ", dateOfCommissioning=" + dateOfCommissioning +
                ", numberOfSeats=" + numberOfSeats +
                ", weight=" + weight +
                ", hasConditioner=" + hasConditioner +
                ", routes=" + routes +
                '}';
    }
    @Override
    public int compareTo(@NotNull Train o) {
        int idComparison = this.id.compareTo(o.id);

        if (idComparison != 0) {
            return idComparison;
        }
        return this.model.compareTo(o.model);
    }
}
