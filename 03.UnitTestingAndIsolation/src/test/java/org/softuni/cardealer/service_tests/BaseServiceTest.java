package org.softuni.cardealer.service_tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.entities.Car;
import org.softuni.cardealer.domain.entities.Customer;
import org.softuni.cardealer.domain.entities.Part;
import org.softuni.cardealer.domain.entities.Supplier;
import org.softuni.cardealer.repository.*;
import org.softuni.constants.CarConstants;
import org.softuni.constants.CustomerConstants;
import org.softuni.constants.PartConstants;
import org.softuni.constants.SupplierConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public abstract class BaseServiceTest {
    protected static final String FIELD_VALUES_MISMATCH_MESSAGE = "Fields [%s] do not match!";
    protected static final String NOT_FOUND_IN_DATABASE_MESSAGE = "Entity not found in database!";

    @Autowired
    protected CarRepository carRepository;
    @Autowired
    protected CarSaleRepository carSaleRepository;
    @Autowired
    protected CustomerRepository customerRepository;
    @Autowired
    protected PartRepository partRepository;
    @Autowired
    protected PartSaleRepository partSaleRepository;

    @Autowired
    protected SupplierRepository supplierRepository; //breaking OOP principles for the tests might be ok?

    protected ModelMapper modelMapper;

    @Before
    public void init() {
        this.modelMapper = new ModelMapper();

        Supplier supplier1 = new Supplier();
        supplier1.setName(SupplierConstants.SUPPLIER_CORRECT_NAME_1);
        supplier1.setImporter(SupplierConstants.SUPPLIER_CORRECT_IS_IMPORTER_1);
        this.supplierRepository.saveAndFlush(supplier1);


        Supplier supplier2 = new Supplier();
        supplier2.setName(SupplierConstants.SUPPLIER_CORRECT_NAME_2);
        supplier2.setImporter(SupplierConstants.SUPPLIER_CORRECT_IS_IMPORTER_2);
        this.supplierRepository.saveAndFlush(supplier2);

        Part part1 = new Part();
        part1.setName(PartConstants.PART_CORRECT_NAME_1);
        part1.setPrice(PartConstants.PART_CORRECT_PRICE_1);
        part1.setSupplier(supplier1);
        this.partRepository.saveAndFlush(part1);

        Part part2 = new Part();
        part2.setName(PartConstants.PART_CORRECT_NAME_2);
        part2.setPrice(PartConstants.PART_CORRECT_PRICE_2);
        part2.setSupplier(supplier2);
        this.partRepository.saveAndFlush(part2);

        Car car1 = new Car();
        List<Part> partsList1 = new ArrayList<>(2);
        partsList1.add(part1);
        partsList1.add(part2);
        car1.setParts(partsList1);
        car1.setTravelledDistance(CarConstants.CAR_CORRECT_TRAVELLED_DISTANCE_1);
        car1.setMake(CarConstants.CAR_CORRECT_MAKE_1);
        car1.setModel(CarConstants.CAR_CORRECT_MODEL_1);
        this.carRepository.saveAndFlush(car1);

        Car car2 = new Car();
        List<Part> partsList2 = new ArrayList<>(1);
        partsList2.add(part1);
        car2.setParts(partsList2);
        car2.setTravelledDistance(CarConstants.CAR_CORRECT_TRAVELLED_DISTANCE_2);
        car2.setMake(CarConstants.CAR_CORRECT_MAKE_2);
        car2.setModel(CarConstants.CAR_CORRECT_MODEL_2);
        this.carRepository.saveAndFlush(car2);

        Customer customer1 = new Customer();
        customer1.setName(CustomerConstants.CUSTOMER_CORRECT_NAME_1);
        customer1.setBirthDate(CustomerConstants.CUSTOMER_CORRECT_BIRTH_DATE_1);
        customer1.setYoungDriver(CustomerConstants.CUSTOMER_CORRECT_IS_YOUNG_DRIVER1);
        this.customerRepository.saveAndFlush(customer1);

        Customer customer2 = new Customer();
        customer2.setName(CustomerConstants.CUSTOMER_CORRECT_NAME_2);
        customer2.setBirthDate(CustomerConstants.CUSTOMER_CORRECT_BIRTH_DATE_2);
        customer2.setYoungDriver(CustomerConstants.CUSTOMER_CORRECT_IS_YOUNG_DRIVER2);
        this.customerRepository.saveAndFlush(customer2);
    }

    protected List<String> getDifferences(Object object1, Object object2) throws IllegalAccessException {
        List<String> result = new ArrayList<>();

        Class class1 = object1.getClass();
        Class class2 = object2.getClass();

        Field[] object1Fields = Arrays.stream(class1.getDeclaredFields())
                .sorted((f1, f2) -> f1.getName().compareTo(f2.getName()))
                .toArray(n -> new Field[n]);

        Field[] object2Fields = Arrays.stream(class2.getDeclaredFields())
                .sorted((f1, f2) -> f1.getName().compareTo(f2.getName()))
                .toArray(n -> new Field[n]);

        for (int i = 0; i < object1Fields.length; i++) {

            object1Fields[i].setAccessible(true);
            object2Fields[i].setAccessible(true);

            if (!object1Fields[i].get(object1).equals(object2Fields[i].get(object2))) {
                object1Fields[i].setAccessible(false);
                object2Fields[i].setAccessible(false);

                result.add(object1Fields[i].getName());
            }

            object1Fields[i].setAccessible(false);
            object2Fields[i].setAccessible(false);
        }

        return result;
    }

    protected void assertSameFieldValues(Object object1, Object object2) throws IllegalAccessException {
        List<String> differences = this.getDifferences(object1, object2);
        Assert.assertTrue(String.format(FIELD_VALUES_MISMATCH_MESSAGE, String.join(", ", differences)), differences.isEmpty());
    }

    protected List<String> getDifferences(Object object1, Object object2, Set<String> excludedFields) throws IllegalAccessException {
        List<String> result = new ArrayList<>();

        Class class1 = object1.getClass();
        Class class2 = object2.getClass();

        Field[] object1Fields = Arrays.stream(class1.getDeclaredFields())
                .sorted((f1, f2) -> f1.getName().compareTo(f2.getName()))
                .toArray(n -> new Field[n]);

        Field[] object2Fields = Arrays.stream(class2.getDeclaredFields())
                .sorted((f1, f2) -> f1.getName().compareTo(f2.getName()))
                .toArray(n -> new Field[n]);

        for (int i = 0; i < object1Fields.length; i++) {
            if (excludedFields.contains(object1Fields[i].getName()))
                continue;

            object1Fields[i].setAccessible(true);
            object2Fields[i].setAccessible(true);

            if (!object1Fields[i].get(object1).equals(object2Fields[i].get(object2))) {
                object1Fields[i].setAccessible(false);
                object2Fields[i].setAccessible(false);

                result.add(object1Fields[i].getName());
            }

            object1Fields[i].setAccessible(false);
            object2Fields[i].setAccessible(false);
        }

        return result;
    }

    protected void assertSameFieldValues(Object object1, Object object2, Set<String> excludedFields) throws IllegalAccessException {
        List<String> differences = this.getDifferences(object1, object2, excludedFields);
        Assert.assertTrue(String.format(FIELD_VALUES_MISMATCH_MESSAGE, String.join(", ", differences)), differences.isEmpty());
    }
}
