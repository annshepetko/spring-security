package ua.kpi.its.lab.rest.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

import ua.kpi.its.lab.security.dto.TrainDto
import ua.kpi.its.lab.security.svc.TrainControllerService

/**
 * Контролер для управління поїздами.
 *
 * @property trainService Сервіс для обробки запитів по поїздах.
 */

@RestController
@RequestMapping("/api/train")
class TrainController @Autowired constructor(
    private val trainService: TrainControllerService
) {

    /**
     * Створює новий поїзд.
     *
     * @param createTrainRequest Запит на створення поїзда.
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    fun createTrain(@RequestBody createTrainRequest: TrainDto) {
        trainService.create(createTrainRequest)
    }

    /**
     * Отримує інформацію про поїзд за його ідентифікатором.
     *
     * @param id Ідентифікатор поїзда.
     * @return Інформація про поїзд у форматі [TrainDto].
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    fun getTrain(@PathVariable("id") id: Int): TrainDto {
        return trainService.buildTrainDtoFromTrainById(id)
    }

    /**
     * Видаляє поїзд за його ідентифікатором.
     *
     * @param id Ідентифікатор поїзда, який потрібно видалити.
     */
    @DeleteMapping("/{id}")
    fun deleteTrain(@PathVariable("id") id: Int) {
        trainService.deleteTrain(id)
    }

    /**
     * Оновлює інформацію про поїзд.
     *
     * @param trainDto Інформація про поїзд для оновлення.
     * @return Оновлена інформація про поїзд у форматі [TrainDto].
     */
    @PatchMapping
    fun updateTrain(@RequestBody trainDto: TrainDto): TrainDto {
        return trainService.updateTrain(trainDto)
    }
}
