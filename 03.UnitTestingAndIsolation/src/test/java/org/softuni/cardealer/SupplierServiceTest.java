package org.softuni.cardealer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.entities.Supplier;
import org.softuni.cardealer.domain.models.service.SupplierServiceModel;
import org.softuni.cardealer.repository.SupplierRepository;
import org.softuni.cardealer.service.SupplierService;
import org.softuni.cardealer.service.SupplierServiceImpl;
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
public class SupplierServiceTest {
    /*
    Thoughts on testing the database:
    1) Maybe I should use the repository to extract entities, save and so on, because I know it works for sure.
    And then see if the service does the same.
    2) Setting an id to an entity and saving it to the db does not mean that the id will be switched with
    the generated one. It stays. Could come in useful - not entirely true. Works if I use the repository
    to do the save, but does not work with using the service. Need to test it (hehe)
    3) Saving an entity updates its id if it does not have one!
    4) Test out if the state of the fake db is the same for every test. Meaning if I change something,
    in the first method, will those changes mess up the other methods, which rely on the db.
    Maybe it shouldn't, because the @Before method is made before every test. Test it out for
    a collection also.
    Conclusion: changes in test methods to the values do not stay after end of method.
     */
    private static final String SUPPLIER_ID_MISMATCH = "Supplier ids not not the same!";
    private static final String SUPPLIER_NAMES_MISMATCH = "Supplier names do NOT match!";
    private static final String SUPPLIER_IS_IMPORTER_MISMATCH = "Suppliers 'isImporter' do NOT match!";

    private static final String SUPPLIER_IDS_MISMATCH_AFTER_UPDATE = "Supplier ids do not match after update!";
    private static final String SUPPLIER_ID = "12345";

    private SupplierServiceImpl supplierService;
    private ModelMapper modelMapper;
    @Autowired
    private SupplierRepository repository;

    private List<String> someStuff;

    public SupplierServiceTest() {

    }

    @Before
    public void init() {
        this.modelMapper = new ModelMapper();
        this.supplierService = new SupplierServiceImpl(this.repository, this.modelMapper);

        this.repository.saveAndFlush(new Supplier()); //TODO see if changes made to fake db in tests stay after the tests end.

        this.someStuff = new ArrayList<>();
        this.someStuff.add("Aleksandar");
        this.someStuff.add("Pesho");
        this.someStuff.add("Gosho");
        this.someStuff.add("Tosho");
        this.someStuff.add("Sasho");
    }

    @Test
    public void saveSupplier_whenSavingCorrectSupplier_returnSameSupplier() {
        SupplierServiceModel testSupplier = new SupplierServiceModel();

        testSupplier.setId(SUPPLIER_ID);
        testSupplier.setName("Bila supplier INC");
        testSupplier.setImporter(true);

        this.supplierService.saveSupplier(testSupplier);

        Supplier outOfDb = this.repository.findAll().get(0);

//        Assert.assertTrue(SUPPLIER_ID_MISMATCH, compareIds(outOfDb, testSupplier)); //false
        Assert.assertEquals(SUPPLIER_NAMES_MISMATCH, testSupplier.getName(), outOfDb.getName()); //or should I stick with this
        Assert.assertEquals(SUPPLIER_IS_IMPORTER_MISMATCH, testSupplier.isImporter(), outOfDb.isImporter());
    }

    @Test
    public void editSupplier_whenEditingSupplierWithCorrectValues_returnSupplierWithEditedFields() {
        Supplier supplier = new Supplier();
        supplier.setName("Aleksandar");
        supplier.setImporter(false);

        this.repository.save(supplier);

        SupplierServiceModel supplierServiceModel =
                this.modelMapper.map(supplier, SupplierServiceModel.class);

        supplierServiceModel.setId(supplier.getId());
        supplierServiceModel.setName("Pesho");
        supplier.setImporter(true);

        this.supplierService.editSupplier(supplierServiceModel);

        Supplier supplierAfterUpdate = this.repository.findById(supplier.getId()).orElse(null);

        Assert.assertEquals(supplier.getId(), supplierAfterUpdate.getId());
        Assert.assertTrue(this.compareNames(supplierAfterUpdate, supplierServiceModel));
        Assert.assertTrue(this.compareIsImporter(supplierAfterUpdate, supplierServiceModel));
    }

    @Test
    public void deleteSupplier_deletingExistingSupplier_notFindingTheSupplier() {
        Supplier supplier = new Supplier();
        supplier.setName("Aleksandar");
        supplier.setImporter(true);

        this.repository.saveAndFlush(supplier);
        this.supplierService.deleteSupplier(supplier.getId());
        supplier = this.repository.findById(supplier.getId()).orElse(null);

        Assert.assertNull(supplier);
    }

    @Test
    public void findSupplierById_findSupplier_getSupplier() { //TODO need to add things in the @Before
    }

    @Test
    public void findSupplierById_findSupplier_dontGetSupplier() {
        this.repository.deleteAll();
        //TODO search with an Id I have given in
    }

    @Test
    public void createdList_addFirstElement() {
        this.someStuff.add(0, "Not aleksandar");
    }

    @Test
    public void createdList_whenDeletingAll_noneStay() {
        this.someStuff.clear();
    }

    @Test
    public void createdList_checkFirstElement_shouldBeAleksandar() {
        Assert.assertEquals("Aleksandar", this.someStuff.get(0));
    }

    private boolean compareNames(Supplier supplierEntity, SupplierServiceModel supplierServiceModel) {
        return supplierEntity.getName().equals(supplierServiceModel.getName());
    }

    private boolean compareIds(Supplier supplierEntity, SupplierServiceModel supplierServiceModel) {
        return supplierEntity.getId().equals(supplierServiceModel.getId());
    }

    private boolean compareIsImporter(Supplier supplierEntity, SupplierServiceModel supplierServiceModel) {
        return supplierEntity.isImporter() == supplierServiceModel.isImporter();
    }

    private boolean compareEntityAndServiceModel(Supplier supplierEntity, SupplierServiceModel supplierServiceModel) {
        return this.compareIds(supplierEntity, supplierServiceModel)
                && this.compareNames(supplierEntity, supplierServiceModel)
                && this.compareIsImporter(supplierEntity, supplierServiceModel);
    }
}
