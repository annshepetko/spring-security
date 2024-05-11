package ua.kpi.its.lab.security.builders;



import ua.kpi.its.lab.security.entity.Route;
import ua.kpi.its.lab.security.entity.Train;
import ua.kpi.its.lab.security.entity.TrainTypes;

import java.time.LocalDateTime;
import java.util.List;

public class TrainBuilderImpl implements TrainBuilder {

    private Train train;

    public TrainBuilderImpl() {
        this.train = new Train();
    }

    @Override
    public TrainBuilderImpl builder() {
        return this;
    }

    public TrainBuilderImpl model(String model) {
        train.setModel(model);
        return this;
    }

    public TrainBuilderImpl producer(String producerName) {
        train.setProducer(producerName);
        return this;
    }

    public TrainBuilderImpl type(TrainTypes type) {
        train.setType(type);
        return this;
    }

    public TrainBuilderImpl dateOfCommissioning(LocalDateTime date) {
        train.setDateOfCommissioning(date);
        return this;
    }

    public TrainBuilderImpl numberOfSeats(Integer seats) {
        train.setNumberOfSeats(seats);
        return this;
    }

    public TrainBuilderImpl weight(Double weight) {
        train.setWeight(weight);
        return this;
    }

    public TrainBuilderImpl hasConditioner(Boolean hasConditioner) {
        train.setHasConditioner(hasConditioner);
        return this;
    }

    public TrainBuilderImpl routes(List<Route> routes) {
        train.setRoutes(routes);
        return this;
    }

    @Override
    public Train build() {
        return this.train;
    }
}