package ua.kpi.its.lab.security.svc.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kpi.its.lab.security.entity.Train;
import ua.kpi.its.lab.security.repo.RouteRepository;
import ua.kpi.its.lab.security.repo.TrainRepository;
import ua.kpi.its.lab.security.svc.EntityService;


import java.util.List;

@Service
public class TrainServiceImpl implements EntityService {
    private RouteRepository routeRepository;

    private TrainRepository trainRepository;

    @Autowired
    public TrainServiceImpl(TrainRepository trainRepository) {
        this.trainRepository = trainRepository;
    }

    @Override
    public <T> T findById(Integer id) {
        return (T) trainRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Train do not exist"));
    }

    @Override
    public <T> List<T> findAll() {
        return (List<T>) trainRepository.findAll();
    }

    @Override
    public <T> void create(T entity) {
        trainRepository.save((Train) entity);
    }

    @Override
    public <T> void update(T entity) {
        trainRepository.save((Train) entity);
    }

    @Override
    public void delete(Integer id) {
        Train train = trainRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Train do not exist")
        );
        trainRepository.delete(train);
    }

}
