package ua.kpi.its.lab.security.builders;


import ua.kpi.its.lab.security.entity.Train;

public interface TrainBuilder {

    TrainBuilderImpl builder();
    Train build();

}
