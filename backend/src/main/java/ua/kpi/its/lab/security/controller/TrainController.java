package ua.kpi.its.lab.security.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.kpi.its.lab.security.dto.CreateTrainRequest;
import ua.kpi.its.lab.security.dto.TrainDto;
import ua.kpi.its.lab.security.svc.TrainControllerService;

@RestController
@RequestMapping("/api/train")
public class TrainController {

    private final TrainControllerService trainService;

    @Autowired
    public TrainController(TrainControllerService trainService) {
        this.trainService = trainService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createTrain(@RequestBody CreateTrainRequest createTrainRequest) {
        trainService.create(createTrainRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public TrainDto getTrain(@PathVariable("id") int id) {
        return trainService.buildTrainDtoFromTrainById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteTrain(@PathVariable("id") int id) {
        trainService.deleteTrain(id);
    }

    @PatchMapping
    public TrainDto updateTrain(@RequestBody TrainDto trainDto) {
        return trainService.updateTrain(trainDto);
    }

    @GetMapping("/hello")
    public String getHello(){
        return "Hello World!";
    }

}
