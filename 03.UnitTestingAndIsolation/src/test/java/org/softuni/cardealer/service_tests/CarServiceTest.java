package org.softuni.cardealer.service_tests;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.entities.Car;
import org.softuni.cardealer.domain.entities.Part;
import org.softuni.cardealer.domain.models.service.CarServiceModel;
import org.softuni.cardealer.repository.CarRepository;
import org.softuni.cardealer.repository.PartRepository;
import org.softuni.cardealer.service.CarServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CarServiceTest extends BaseServiceTest {
    private static final Long VALID_DISTANCE = 100L;
    private static final String VALID_MAKE = "Mitsubishi";
    private static final String VALID_MODEL = "Lancer";

    private static final Long VALID_DISTANCE2 = 999L;
    private static final String VALID_MAKE2 = "BMW";
    private static final String VALID_MODEL2 = "Wagon";


    private CarRepository carRepository;
    private ModelMapper modelMapper;
    private CarServiceImpl carService;
    private Car correctCarInDb;
    @Autowired
    private PartRepository partRepository;

    public CarServiceTest() {
        this.modelMapper = new ModelMapper();
    }

    @Before
    public void init() {
        this.carRepository = Mockito.mock(CarRepository.class);
        this.carService = new CarServiceImpl(this.carRepository, this.modelMapper);

        Part mockedPart1 = Mockito.mock(Part.class);
        Part mockedPart2 = Mockito.mock(Part.class);
        Part mockedPart3 = Mockito.mock(Part.class);
        List<Part> partList = this.getThreeMockedParts();
        //TODO cant save mocked things into fake db:
        //org.springframework.dao.InvalidDataAccessApiUsageException:
        // Unknown entity: org.softuni.cardealer.domain.entities.Part$MockitoMock$170913260;
        // nested exception is java.lang.IllegalArgumentException:
        // Unknown entity: org.softuni.cardealer.domain.entities.Part$MockitoMock$170913260
        this.partRepository.saveAll(partList);

        this.correctCarInDb = new Car();
        this.correctCarInDb.setMake(VALID_MAKE);
        this.correctCarInDb.setModel(VALID_MODEL);
        this.correctCarInDb.setTravelledDistance(VALID_DISTANCE);
        this.correctCarInDb.setParts(partList);

        //TODO
        // org.springframework.dao.InvalidDataAccessApiUsageException: org.hibernate.TransientObjectException:
        // object references an unsaved transient instance - save the transient instance before flushing:
        //https://stackoverflow.com/questions/2302802/object-references-an-unsaved-transient-instance-save-the-transient-instance-be
        this.carRepository.saveAndFlush(this.correctCarInDb);
    }

    @Test
    public void saveCar_whenSavingCorrectCar_expectSuccess() throws IllegalAccessException {
        Car car = new Car();
        List<Part> parts = this.getThreeMockedParts();
        car.setMake(VALID_MAKE2);
        car.setModel(VALID_MODEL2);
        car.setTravelledDistance(VALID_DISTANCE2);
        car.setParts(parts);

        CarServiceModel serviceModel = this.modelMapper.map(car, CarServiceModel.class);

        serviceModel = this.carService.saveCar(serviceModel);

        Car savedInDb = this.carRepository.findById(serviceModel.getId()).orElse(null);

        super.assertSameFieldValues(savedInDb, car);
    }

    private List<Part> getThreeMockedParts() {
        Part mockedPart1 = Mockito.mock(Part.class);
        Part mockedPart2 = Mockito.mock(Part.class);
        Part mockedPart3 = Mockito.mock(Part.class);

        List<Part> parts = new ArrayList<>();
        parts.add(mockedPart1);
        parts.add(mockedPart2);
        parts.add(mockedPart3);

        return parts;
    }
}
