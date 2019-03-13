package org.softuni.cardealer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.entities.Car;
import org.softuni.cardealer.domain.models.service.CarServiceModel;
import org.softuni.cardealer.repository.CarRepository;
import org.softuni.cardealer.service.CarService;
import org.softuni.cardealer.service.CarServiceImpl;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
public class CarServiceTest {
    private static final String CAR_ID = "Car id 1";
    private static final String CAR_MAKE = "BMW";
    private static final String CAR_MODEL = "X4";
    private static final Long CAR_TRAVELLED_DISTANCE = 1000L;

    private static final Long CAR_TRAVELLED_DISTANCE_2 = 2000L;
    private static final String CAR_MAKE_2 = "Mitsubishi";
    private static final String CAR_MODEL_2 = "Lancer";

    private CarRepository carRepository;
    private CarService carService;
    private ModelMapper modelMapper;
    private Car validCar;

    public CarServiceTest() {
        this.modelMapper = new ModelMapper();

        this.validCar = new Car();
        validCar.setId(CAR_ID);
        validCar.setModel(CAR_MODEL);
        validCar.setTravelledDistance(CAR_TRAVELLED_DISTANCE);
        validCar.setParts(null);
        validCar.setMake(CAR_MAKE);
    }

    @Before
    public void init() {
        this.carRepository = Mockito.mock(CarRepository.class);

        Mockito.when(this.carRepository.findById(CAR_ID)).thenReturn(Optional.of(this.validCar));

        Mockito.when(this.carRepository.saveAndFlush(this.validCar)).thenReturn(this.validCar); //apparently works with object's location in memory
        this.carService = new CarServiceImpl(this.carRepository, this.modelMapper);
    }

    @Test
    public void saveCar_whenSavingCarWithCorrectValues_successfulSave() {
        CarServiceModel serviceModel = this.modelMapper.map(this.validCar, CarServiceModel.class);

        CarServiceModel serviceModelAfterSave = this.carService.saveCar(serviceModel); //TODO don't know how to fake the saveAndFlush method with mockito for this test

        Assert.assertEquals(serviceModel.getMake(), serviceModelAfterSave.getMake());
        Assert.assertEquals(serviceModel.getModel(), serviceModel.getModel());
        Assert.assertEquals(serviceModel.getTravelledDistance(), serviceModel.getTravelledDistance());
        Assert.assertEquals(serviceModel.getParts(), serviceModelAfterSave.getParts());
    }

    @Test
    public void editCar_whenEditingWithValidValues_returnWithEditedValues() {
        CarServiceModel serviceModel = this.modelMapper.map(this.validCar, CarServiceModel.class);
        serviceModel.setTravelledDistance(CAR_TRAVELLED_DISTANCE_2);
        serviceModel.setModel(CAR_MODEL_2);
        serviceModel.setMake(CAR_MAKE_2);

        CarServiceModel serviceModelAfterEdit = this.carService.editCar(serviceModel);

        Assert.assertEquals(serviceModel.getId(), serviceModelAfterEdit.getId());
        Assert.assertEquals(serviceModel.getParts(), serviceModelAfterEdit.getParts());
        Assert.assertEquals(serviceModel.getModel(), serviceModelAfterEdit.getModel());
        Assert.assertEquals(serviceModel.getMake(), serviceModelAfterEdit.getMake());
        Assert.assertEquals(serviceModel.getTravelledDistance(), serviceModelAfterEdit.getTravelledDistance());
    }

    @Test
    public void deleteCar_whenDeletingCar_returnDeletedCar() {
        CarServiceModel serviceModel = this.carService.deleteCar(CAR_ID);

        Assert.assertEquals(serviceModel.getId(), CAR_ID);
        Assert.assertEquals(serviceModel.getMake(), CAR_MAKE);
        Assert.assertEquals(serviceModel.getModel(), CAR_MODEL);
        Assert.assertEquals(serviceModel.getParts(), null);
        Assert.assertEquals(serviceModel.getTravelledDistance(), CAR_TRAVELLED_DISTANCE);
    }

    @Test
    public void findCarById_whenSearchingWithValidId_findCar() {
        CarServiceModel serviceModel = this.carService.findCarById(CAR_ID);

        Assert.assertEquals(serviceModel.getId(), CAR_ID);
        Assert.assertEquals(serviceModel.getMake(), CAR_MAKE);
        Assert.assertEquals(serviceModel.getModel(), CAR_MODEL);
        Assert.assertEquals(serviceModel.getParts(), null);
        Assert.assertEquals(serviceModel.getTravelledDistance(), CAR_TRAVELLED_DISTANCE);
    }
}
