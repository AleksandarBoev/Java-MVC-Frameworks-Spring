package org.softuni.cardealer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.softuni.cardealer.domain.entities.Car;
import org.softuni.cardealer.domain.entities.Customer;
import org.softuni.cardealer.domain.entities.Part;
import org.softuni.cardealer.domain.entities.Supplier;
import org.softuni.cardealer.service_tests.BaseServiceTest;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class BaseServiceExtend extends BaseServiceTest {
    public BaseServiceExtend() {

    }

    @Test
    public void testStuff() {
        List<Supplier> supplierList = super.supplierRepository.findAll();
        List<Part> partList = super.partRepository.findAll();
        List<Car> carList = super.carRepository.findAll();
        List<Customer> customerList = super.customerRepository.findAll();
    }
}
