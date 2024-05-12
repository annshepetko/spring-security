package ua.kpi.its.lab.security.svc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kpi.its.lab.security.builders.RouteBuilderImpl;
import ua.kpi.its.lab.security.builders.TrainBuilderImpl;

import ua.kpi.its.lab.security.dto.RouteDto;
import ua.kpi.its.lab.security.dto.TrainDto;
import ua.kpi.its.lab.security.entity.Route;
import ua.kpi.its.lab.security.entity.Train;
import ua.kpi.its.lab.security.repo.RouteRepository;
import ua.kpi.its.lab.security.svc.impl.TrainServiceImpl;

import java.util.List;


@Service
public class TrainControllerService {

    private final TrainServiceImpl trainService;
    private final RouteRepository routeRepository;

    @Autowired
    public TrainControllerService(TrainServiceImpl trainService, RouteRepository routeRepository) {
        this.trainService = trainService;
        this.routeRepository = routeRepository;
    }

    /**
     * Створює новий поїзд на основі DTO об'єкта.
     *
     * @param trainDto Інформація про поїзд для створення.
     */
    public void create(TrainDto trainDto){
        Train train = buildTrainFromRequest(trainDto);
        trainService.create(train);
    }

    /**
     * Конвертує DTO об'єкт в об'єкт поїзда.
     *
     * @param createTrainRequest Інформація про поїзд у форматі [TrainDto].
     * @return Об'єкт поїзда у форматі [Train].
     */
    private Train buildTrainFromRequest(TrainDto createTrainRequest){
        return new TrainBuilderImpl().builder()
                .model(createTrainRequest.model())
                .hasConditioner(createTrainRequest.hasConditioner())
                .numberOfSeats(createTrainRequest.numberOfSeats())
                .producer(createTrainRequest.producer())
                .dateOfCommissioning(createTrainRequest.dateOfCommissioning())
                .type(createTrainRequest.type())
                .weight(createTrainRequest.weight())
                .routes(createTrainRequest.routeDtos().stream()
                        .map(r -> new RouteBuilderImpl().builder()
                                .dateOfShipment(r.dateOfShipment())
                                .departurePoint(r.departurePoint())
                                .destination(r.destination())
                                .isCircular(r.isCircular())
                                .mileage(r.mileage())
                                .priceOfTicket(r.priceOfTicket())
                                .build()
                        ).toList()
                )
                .build();
    }

    /**
     * Конвертує маршрути поїзда в DTO об'єкти.
     *
     * @param train Об'єкт поїзда у форматі [Train].
     * @return Список DTO об'єктів маршрутів.
     */
    private List<RouteDto> buildRouteDtosForTrain(Train train){
        List<RouteDto> routeDtos = train.getRoutes().stream().map(r -> new RouteDto(
                        r.getId(),
                        r.getDeparturePoint(),
                        r.getDestination(),
                        r.getDateOfShipment(),
                        r.getMileage(),
                        r.getPriceOfTicket(),
                        r.getIsCircular())
                )
                .toList();

        return routeDtos;
    }

    /**
     * Конвертує об'єкт поїзда в DTO формат.
     *
     * @param train Об'єкт поїзда у форматі [Train].
     * @return Інформація про поїзд у форматі [TrainDto].
     */
    public TrainDto buildTrainDtoFromTrain(Train train){
        return new TrainDto(
                train.getId(),
                train.getModel(),
                train.getProducer(),
                train.getType(),
                train.getDateOfCommissioning(),
                train.getNumberOfSeats(),
                train.getWeight(),
                train.getHasConditioner(),
                buildRouteDtosForTrain(train)
        );
    }

    /**
     * Отримує DTO об'єкт поїзда за його ідентифікатором.
     *
     * @param id Ідентифікатор поїзда.
     * @return Інформація про поїзд у форматі [TrainDto].
     */
    public TrainDto buildTrainDtoFromTrainById(Integer id){
        Train train = trainService.findById(id);
        return new TrainDto(
                train.getId(),
                train.getModel(),
                train.getProducer(),
                train.getType(),
                train.getDateOfCommissioning(),
                train.getNumberOfSeats(),
                train.getWeight(),
                train.getHasConditioner(),
                buildRouteDtosForTrain(train)
        );
    }

    /**
     * Оновлює інформацію про поїзд на основі DTO об'єкта.
     *
     * @param trainDto Інформація про поїзд для оновлення.
     * @return Оновлена інформація про поїзд у форматі [TrainDto].
     */
    public TrainDto updateTrain(TrainDto trainDto) {
        List<Route> routes = routeRepository.findAllById(trainDto.routeDtos().stream().map(r -> r.id()).toList());
        Train train = new TrainBuilderImpl().builder()
                .routes(routes)
                .type(trainDto.type())
                .weight(trainDto.weight())
                .dateOfCommissioning(trainDto.dateOfCommissioning())
                .model(trainDto.model())
                .producer(trainDto.producer())
                .numberOfSeats(trainDto.numberOfSeats())
                .hasConditioner(trainDto.hasConditioner())
                .build();

        return buildTrainDtoFromTrain(train);
    }

    /**
     * Видаляє поїзд за його ідентифікатором.
     *
     * @param id Ідентифікатор поїзда, який потрібно видалити.
     */
    public void deleteTrain(Integer id) {
        trainService.delete(id);
    }
}
