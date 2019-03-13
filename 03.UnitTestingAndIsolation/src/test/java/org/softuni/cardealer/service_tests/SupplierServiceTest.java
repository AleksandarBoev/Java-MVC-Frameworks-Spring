package org.softuni.cardealer.service_tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.entities.Supplier;
import org.softuni.cardealer.domain.models.service.SupplierServiceModel;
import org.softuni.cardealer.repository.SupplierRepository;
import org.softuni.cardealer.service.SupplierServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class SupplierServiceTest extends BaseServiceTest {
    private static final String CORRECT_SUPPLIER_NAME = "Billa inc";
    private static final String CORRECT_SUPPLIER_NAME2 = "Kaufland inc";
    private static final boolean CORRECT_SUPPLIER_IS_IMPORTER = true;
    private static final boolean CORRECT_SUPPLIER_IS_IMPORTER_2 = false;

    private static final String NON_EXISTING_SUPPLIER_ID = "11111";

    private SupplierServiceImpl supplierService;
    private ModelMapper modelMapper;
    private Supplier correctSupplierInDb;
    @Autowired
    private SupplierRepository repository;

    private SupplierRepository fakeRepository;
    private SupplierServiceImpl supplierServiceWithFakeDb;

    public SupplierServiceTest() {
        this.modelMapper = new ModelMapper();
    }

    @Before
    public void init() {
        super.init();
        this.supplierService = new SupplierServiceImpl(super.supplierRepository, this.modelMapper);

        Supplier supplier = new Supplier();
        supplier.setName(CORRECT_SUPPLIER_NAME);
        supplier.setImporter(CORRECT_SUPPLIER_IS_IMPORTER);
        supplier = this.repository.saveAndFlush(supplier);
        this.correctSupplierInDb = supplier;

        this.fakeRepository = Mockito.mock(SupplierRepository.class);
        this.supplierServiceWithFakeDb = new SupplierServiceImpl(this.fakeRepository, this.modelMapper);
    }

    @Test
    public void saveSupplier_whenSavingCorrectSupplier_returnNonNullSupplierWithSameFieldValues() throws IllegalAccessException {
        //Arrange
        SupplierServiceModel serviceModel = new SupplierServiceModel();

        serviceModel.setName(CORRECT_SUPPLIER_NAME);
        serviceModel.setImporter(CORRECT_SUPPLIER_IS_IMPORTER);

        //Act
        serviceModel = this.supplierService.saveSupplier(serviceModel);

        //Assert
        Supplier supplierOutOfDb = this.repository.findById(serviceModel.getId()).orElse(null);

        Assert.assertNotNull(NOT_FOUND_IN_DATABASE_MESSAGE, supplierOutOfDb);
        super.getDifferences(supplierOutOfDb, serviceModel);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void saveSupplier_whenSavingSupplierWithNullName_throwException() {
//        Arrange
        SupplierServiceModel serviceModel = new SupplierServiceModel();
        serviceModel.setName(null);
        serviceModel.setImporter(CORRECT_SUPPLIER_IS_IMPORTER);

        serviceModel = this.supplierService.saveSupplier(serviceModel);

        //Act and Assert
        this.supplierService.saveSupplier(serviceModel);
    }

    @Test
    public void editSupplier_whenEditingSupplierWithCorrectValues_returnSupplierWithEditedFields() throws IllegalAccessException {
        //Arrange
        SupplierServiceModel serviceModel =
                this.modelMapper.map(this.correctSupplierInDb, SupplierServiceModel.class);

        serviceModel.setName(CORRECT_SUPPLIER_NAME2);
        serviceModel.setImporter(CORRECT_SUPPLIER_IS_IMPORTER_2);

        //Act
        serviceModel = this.supplierService.editSupplier(serviceModel);

        //Assert
        Supplier supplierAfterUpdate = this.repository.findById(serviceModel.getId()).orElse(null);

        Assert.assertNotNull(NOT_FOUND_IN_DATABASE_MESSAGE, supplierAfterUpdate);
        super.assertSameFieldValues(supplierAfterUpdate, serviceModel);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void editSupplier_whenChangingNameToNull_throwException() {
        //Arrange
        SupplierServiceModel serviceModel =
                this.modelMapper.map(this.correctSupplierInDb, SupplierServiceModel.class);

        serviceModel.setName(null);
        serviceModel.setImporter(CORRECT_SUPPLIER_IS_IMPORTER_2);

        //Act and Assert
        serviceModel = this.supplierService.editSupplier(serviceModel);
    }

    @Test
    public void deleteSupplier_whenDeletingExistingSupplier_notFindingTheSupplier() {
        //Act
        this.supplierService.deleteSupplier(this.correctSupplierInDb.getId());

        //Assert
        this.correctSupplierInDb = this.repository.findById(this.correctSupplierInDb.getId()).orElse(null);

        Assert.assertNull(this.correctSupplierInDb);
    }

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void deleteSupplier_whenDeletingNonExistingSupplier_throwExceptionFromDb() {
        this.supplierService.deleteSupplier(NON_EXISTING_SUPPLIER_ID);
    }

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void deletingSupplier_whenDeletingSupplierByGivingNullId_throwExceptionFromDb() {
        this.supplierService.deleteSupplier(null);
    }

    @Test
    public void findSupplierById_findExistingSupplier_getSupplier() throws IllegalAccessException {
        SupplierServiceModel serviceModel =
                this.supplierService.findSupplierById(this.correctSupplierInDb.getId());

        super.getDifferences(serviceModel, this.correctSupplierInDb);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findSupplierById_findNonExistingSupplier_throwExceptionFromModelmapper() {
        SupplierServiceModel serviceModel =
                this.supplierService.findSupplierById(NON_EXISTING_SUPPLIER_ID);
    }

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void findSupplierById_findByNullId_throwExceptionFromDb() {
        SupplierServiceModel serviceModel =
                this.supplierService.findSupplierById(null);
    }
}
