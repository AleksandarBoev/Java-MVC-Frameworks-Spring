package org.softuni.cardealer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.entities.Supplier;
import org.softuni.cardealer.repository.SupplierRepository;
import org.softuni.cardealer.service.SupplierService;
import org.softuni.cardealer.service.SupplierServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class TestingSomething {
    private SupplierService supplierService;
    private String supplierId;
    @Autowired
    private SupplierRepository supplierRepository;

    @Before
    public void init() {
        this.supplierService = new SupplierServiceImpl(this.supplierRepository, new ModelMapper());
        Supplier supplier = new Supplier();
        supplier.setName("Aleksandar");
        supplier.setImporter(true);
        supplier = this.supplierRepository.saveAndFlush(supplier);
        this.supplierId = supplier.getId();
    }

    @Test
    public void testStuff() {
        this.supplierService.deleteSupplier(supplierId);
    }
}
