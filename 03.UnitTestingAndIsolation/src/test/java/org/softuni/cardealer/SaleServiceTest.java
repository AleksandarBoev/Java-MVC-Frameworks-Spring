package org.softuni.cardealer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.entities.CarSale;
import org.softuni.cardealer.domain.entities.PartSale;
import org.softuni.cardealer.domain.models.service.CarSaleServiceModel;
import org.softuni.cardealer.domain.models.service.PartSaleServiceModel;
import org.softuni.cardealer.repository.CarSaleRepository;
import org.softuni.cardealer.repository.PartSaleRepository;
import org.softuni.cardealer.service.SaleService;
import org.softuni.cardealer.service.SaleServiceImpl;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class SaleServiceTest {
    private static final String CAR_SALE_ID = "Car sale id";
    private static final Double CAR_SALE_DISCOUNT = 10D;

    private static final String PART_SALE_ID = "Part sale id";
    private static final Double PART_SALE_DISCOUNT = 15D;
    private static final Integer PART_SALE_QUANTITY = 200;

    private CarSaleRepository carSaleRepository;
    private PartSaleRepository partSaleRepository;

    private SaleService saleService;
    private ModelMapper modelMapper;

    private CarSale carSale;
    private PartSale partSale;

    public SaleServiceTest() {
        this.modelMapper = new ModelMapper();
    }

    @Before
    public void init() {
        this.carSale = new CarSale();
        carSale.setCar(null);
        carSale.setCustomer(null);
        carSale.setId(CAR_SALE_ID);
        carSale.setDiscount(CAR_SALE_DISCOUNT);

        this.partSale = new PartSale();
        partSale.setPart(null);
        partSale.setCustomer(null);
        partSale.setQuantity(PART_SALE_QUANTITY);
        partSale.setDiscount(PART_SALE_DISCOUNT);
        partSale.setId(PART_SALE_ID);

        this.carSaleRepository = Mockito.mock(CarSaleRepository.class);
        Mockito.when(this.carSaleRepository.saveAndFlush(this.carSale)).thenReturn(this.carSale);
        this.partSaleRepository = Mockito.mock(PartSaleRepository.class);
        Mockito.when(this.partSaleRepository.saveAndFlush(this.partSale)).thenReturn(this.partSale);

        this.saleService = new SaleServiceImpl(this.carSaleRepository, this.partSaleRepository, this.modelMapper);
    }

    @Test
    public void sellCar_whenSavingAValidCarSale_returnCarSale() {
        CarSaleServiceModel carSaleServiceModel =
                this.modelMapper.map(this.carSale, CarSaleServiceModel.class);

        CarSaleServiceModel carSaleServiceModelAfterSave=
                this.saleService.saleCar(carSaleServiceModel); //TODO same problem

        Assert.assertEquals(carSaleServiceModel.getId(), carSaleServiceModelAfterSave.getId());
        Assert.assertEquals(carSaleServiceModel.getCar(), carSaleServiceModelAfterSave.getCar());
        Assert.assertEquals(carSaleServiceModel.getCustomer(), carSaleServiceModelAfterSave.getCustomer());
        Assert.assertEquals(carSaleServiceModel.getDiscount(), carSaleServiceModelAfterSave.getDiscount());
    }

    @Test
    public void sellPart_whenSavingAValidPartSale_returnPartSale() {
        PartSaleServiceModel partSaleServiceModel =
                this.modelMapper.map(this.partSale, PartSaleServiceModel.class);

        PartSaleServiceModel partSaleServiceModelAfterSave=
                this.saleService.salePart(partSaleServiceModel); //TODO same problem

        Assert.assertEquals(partSaleServiceModel.getId(), partSaleServiceModelAfterSave.getId());
        Assert.assertEquals(partSaleServiceModel.getPart(), partSaleServiceModelAfterSave.getPart());
        Assert.assertEquals(partSaleServiceModel.getCustomer(), partSaleServiceModelAfterSave.getCustomer());
        Assert.assertEquals(partSaleServiceModel.getDiscount(), partSaleServiceModelAfterSave.getDiscount());
        Assert.assertEquals(partSaleServiceModel.getQuantity(), partSaleServiceModelAfterSave.getQuantity());
    }
}
