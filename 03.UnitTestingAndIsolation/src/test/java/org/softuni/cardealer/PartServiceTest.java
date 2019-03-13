package org.softuni.cardealer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.entities.Part;
import org.softuni.cardealer.domain.models.service.PartServiceModel;
import org.softuni.cardealer.repository.PartRepository;
import org.softuni.cardealer.service.PartService;
import org.softuni.cardealer.service.PartServiceImpl;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Optional;

@RunWith(SpringRunner.class)
public class PartServiceTest {
    private static final String PART_ID1 = "222";
    private static final String PART_NAME1 = "Wheel";
    private static final BigDecimal PART_PRICE1 = new BigDecimal("120.90");

    private static final String PART_ID2 = "333";
    private static final String PART_NAME2 = "Tire";
    private static final BigDecimal PART_PRICE2 = new BigDecimal("87.50");

    private PartRepository partRepository;
    private PartService partService;
    private ModelMapper modelMapper;

    public PartServiceTest() {
        this.modelMapper = new ModelMapper();
    }

    @Before
    public void init() {
        this.partRepository = Mockito.mock(PartRepository.class);
        Part part = new Part();
        part.setId(PART_ID1);
        part.setName(PART_NAME1);
        part.setSupplier(null);
        part.setPrice(PART_PRICE1);

        Mockito.when(this.partRepository.saveAndFlush(part)).thenReturn(part);
        Mockito.when(this.partRepository.findById(PART_ID1)).thenReturn(Optional.of(part));

        this.partService = new PartServiceImpl(this.partRepository, this.modelMapper);
    }

    @Test
    public void savePart_whenSavingValidPart_successfulSave() {
        PartServiceModel serviceModel = new PartServiceModel();
        serviceModel.setName(PART_NAME2);
        serviceModel.setPrice(PART_PRICE1);
        serviceModel.setSupplier(null);

        PartServiceModel serviceModelAfterSave = this.partService.savePart(serviceModel);

        Assert.assertEquals(serviceModel.getName(), serviceModelAfterSave.getName());
        Assert.assertEquals(serviceModel.getPrice(), serviceModelAfterSave.getPrice());
        Assert.assertEquals(serviceModel.getSupplier(), serviceModelAfterSave.getSupplier());
    }

    @Test
    public void editPart_whenEditingPartWithValidInfo_successfulEdit() {
        Part part = this.partRepository.findById(PART_ID1).orElse(null);
        PartServiceModel serviceModel =
                this.modelMapper.map(part, PartServiceModel.class);

        serviceModel.setPrice(PART_PRICE2);
        serviceModel.setName(PART_NAME2);

        PartServiceModel serviceModelAfterEdit = this.partService.editPart(serviceModel);

        Assert.assertEquals(serviceModel.getName(), serviceModelAfterEdit.getName());
        Assert.assertEquals(serviceModel.getPrice(), serviceModelAfterEdit.getPrice());
        Assert.assertEquals(serviceModel.getSupplier(), serviceModelAfterEdit.getSupplier());
    }

    @Test
    public void deletePart_whenDeletingPart_noExceptions() {
        PartServiceModel partServiceModel = new PartServiceModel();
        this.partService.deletePart(PART_ID1);
    }

    @Test
    public void findPartById_whenSearchingForAPartById_findId() {
        PartServiceModel partServiceModel = this.partService.findPartById(PART_ID1);

        Assert.assertEquals(partServiceModel.getId(), PART_ID1);
        Assert.assertEquals(partServiceModel.getName(), PART_NAME1);
        Assert.assertEquals(partServiceModel.getSupplier(), null);
        Assert.assertEquals(partServiceModel.getPrice(), PART_PRICE1);
    }
}
