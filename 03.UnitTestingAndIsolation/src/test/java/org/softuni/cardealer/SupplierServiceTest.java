package org.softuni.cardealer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.entities.Supplier;
import org.softuni.cardealer.domain.models.service.SupplierServiceModel;
import org.softuni.cardealer.repository.SupplierRepository;
import org.softuni.cardealer.service.SupplierService;
import org.softuni.cardealer.service.SupplierServiceImpl;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
public class SupplierServiceTest {
    private static final String CORRECT_SUPPLIER_NAME_1 = "Billa inc";
    private static final boolean CORRECT_SUPPLIER_IS_IMPORTER_1 = false;

    private static final String CORRECT_SUPPLIER_NAME_2 = "Kaufland inc";
    private static final boolean CORRECT_SUPPLIER_IS_IMPORTER_2 = true;

    private static final String SUPPLIER_ID = "123";

    private SupplierService supplierService;
    SupplierRepository fakeSupplierRepository;
    private ModelMapper modelMapper;

    public SupplierServiceTest() {
        this.modelMapper = new ModelMapper();
    }

    @Before
    public void init() {
        Supplier correctSupplier = new Supplier();
        correctSupplier.setId(SUPPLIER_ID);
        correctSupplier.setName(CORRECT_SUPPLIER_NAME_1);
        correctSupplier.setImporter(CORRECT_SUPPLIER_IS_IMPORTER_1);

        this.fakeSupplierRepository = Mockito.mock(SupplierRepository.class);
        //When searching with this id, return this supplier
        Mockito.when(this.fakeSupplierRepository.findById(SUPPLIER_ID)).thenReturn(Optional.of(correctSupplier));
        //When saving a supplier, return the same supplier
        Mockito.when(this.fakeSupplierRepository.saveAndFlush(correctSupplier)).thenReturn(correctSupplier);

        this.supplierService = new SupplierServiceImpl(this.fakeSupplierRepository, this.modelMapper);
    }

    @Test
    public void saveSupplier_whenAddingCorrectSupplier_successfulAdd() {
        SupplierServiceModel serviceModel = new SupplierServiceModel();
        serviceModel.setName(CORRECT_SUPPLIER_NAME_1);
        serviceModel.setImporter(CORRECT_SUPPLIER_IS_IMPORTER_1);

        SupplierServiceModel serviceModelOutOfDb = this.supplierService.saveSupplier(serviceModel);

        Assert.assertEquals(serviceModel.getName(), serviceModelOutOfDb.getName());
        Assert.assertEquals(serviceModel.isImporter(), serviceModelOutOfDb.isImporter());
    }

    @Test
    public void editSupplier_whenEditingSupplierWithCorrectValues_successfulEdit() {
        Supplier supplier = this.fakeSupplierRepository.findById(SUPPLIER_ID).orElse(null);
        SupplierServiceModel serviceModel = this.modelMapper.map(supplier, SupplierServiceModel.class);
        serviceModel.setName(CORRECT_SUPPLIER_NAME_2);
        serviceModel.setImporter(CORRECT_SUPPLIER_IS_IMPORTER_2);

        SupplierServiceModel serviceModelOutOfDb = this.supplierService.editSupplier(serviceModel);

        Assert.assertEquals(serviceModel.getId(), serviceModelOutOfDb.getId());
        Assert.assertEquals(serviceModel.getName(), serviceModelOutOfDb.getName());
        Assert.assertEquals(serviceModel.isImporter(), serviceModelOutOfDb.isImporter());
    }

    @Test
    public void deleteSupplier_whenDeletingSupplier_successfulDelete() {
        Supplier supplier = this.fakeSupplierRepository.findById(SUPPLIER_ID).orElse(null);
        SupplierServiceModel serviceModel = this.modelMapper.map(supplier, SupplierServiceModel.class);

        serviceModel = this.supplierService.deleteSupplier(SUPPLIER_ID);

        Assert.assertEquals(serviceModel.getId(), SUPPLIER_ID); //when deleting a supplier, he does not loose his id.
        Assert.assertEquals(serviceModel.getName(), CORRECT_SUPPLIER_NAME_1);
        Assert.assertEquals(serviceModel.isImporter(), CORRECT_SUPPLIER_IS_IMPORTER_1);
    }

    @Test
    public void findSupplierById_whenSearchingById_successfulFind() {
        SupplierServiceModel serviceModel = this.supplierService.findSupplierById(SUPPLIER_ID);

        Assert.assertEquals(serviceModel.getId(), SUPPLIER_ID); //when deleting a supplier, he does not loose his id.
        Assert.assertEquals(serviceModel.getName(), CORRECT_SUPPLIER_NAME_1);
        Assert.assertEquals(serviceModel.isImporter(), CORRECT_SUPPLIER_IS_IMPORTER_1);
    }
}
