package ua.kpi.its.lab.security.svc.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kpi.its.lab.security.entity.Route;
import ua.kpi.its.lab.security.repo.RouteRepository;
import ua.kpi.its.lab.security.svc.EntityService;


import java.util.List;

@Service
public class RouteServiceImpl implements EntityService {

    private RouteRepository repository;

    @Autowired
    public RouteServiceImpl(RouteRepository routeRepository){
        this.repository = routeRepository;
    }

    @Override
    public <T> T findById(Integer id) {
        return (T) repository.findById(id).orElseThrow(() -> new EntityNotFoundException("No route are found"));
    }

    @Override
    public <T> List<T> findAll() {
        return (List<T>) repository.findAll();
    }

    @Override
    public <T> void create(T entity) {
        repository.save((Route) entity);
    }

    @Override
    public <T> void update(T entity) {
        repository.save((Route) entity);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
