package org.softuni.cardealer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.entities.Customer;
import org.softuni.cardealer.domain.models.service.CustomerServiceModel;
import org.softuni.cardealer.repository.CustomerRepository;
import org.softuni.cardealer.service.CustomerService;
import org.softuni.cardealer.service.CustomerServiceImpl;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Optional;

@RunWith(SpringRunner.class)
public class CustomerServiceTest {
    private static final String CUSTOMER_ID = "Customer id";
    private static final String CUSTOMER_NAME = "Customer name";
    private static final LocalDate CUSTOMER_BIRTH_DATE = LocalDate.parse("1995-01-15");
    private static final boolean CUSTOMER_IS_YOUNG_DRIVER = false;

    private static final String CUSTOMER_NAME_2 = "Customer name 2";
    private static final LocalDate CUSTOMER_BIRTH_DATE_2 = LocalDate.parse("1996-02-16");
    private static final boolean CUSTOMER_IS_YOUNG_DRIVER_2 = true;

    private CustomerService customerService;
    private CustomerRepository customerRepository;
    private ModelMapper modelMapper;
    private Customer validCustomer;

    public CustomerServiceTest() {
        this.modelMapper = new ModelMapper();
    }

    @Before
    public void init() {
        this.customerRepository = Mockito.mock(CustomerRepository.class);

        this.validCustomer = new Customer();
        this.validCustomer.setId(CUSTOMER_ID);
        this.validCustomer.setYoungDriver(CUSTOMER_IS_YOUNG_DRIVER);
        this.validCustomer.setName(CUSTOMER_NAME);
        this.validCustomer.setBirthDate(CUSTOMER_BIRTH_DATE);

        Mockito.when(this.customerRepository.saveAndFlush(this.validCustomer)).thenReturn(this.validCustomer);
        Mockito.when(this.customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.of(this.validCustomer));

        this.customerService = new CustomerServiceImpl(this.customerRepository, this.modelMapper);
    }

    @Test
    public void saveCustomer_whenSavingCustomer_returnCustomer() {
        CustomerServiceModel serviceModel = this.modelMapper.map(this.validCustomer, CustomerServiceModel.class);

        this.customerService.saveCustomer(serviceModel); //TODO same problem like in CarServiceTest
    }

    @Test
    public void editCustomer_whenEditingCustomerWithValidValues_returnCustomerWithEditedValues() {
        CustomerServiceModel serviceModel = this.modelMapper.map(this.validCustomer, CustomerServiceModel.class);
        serviceModel.setName(CUSTOMER_NAME_2);
        serviceModel.setBirthDate(CUSTOMER_BIRTH_DATE_2);
        serviceModel.setYoungDriver(CUSTOMER_IS_YOUNG_DRIVER_2);

        CustomerServiceModel serviceModelAfterEdit = this.customerService.editCustomer(serviceModel);

        Assert.assertEquals(serviceModel.getId(), serviceModelAfterEdit.getId());
        Assert.assertEquals(serviceModel.getName(), serviceModelAfterEdit.getName());
        Assert.assertEquals(serviceModel.getBirthDate(), serviceModelAfterEdit.getBirthDate());
        Assert.assertEquals(serviceModel.isYoungDriver(), serviceModelAfterEdit.isYoungDriver());
    }

    @Test
    public void deleteCustomer_whenDeletingExistingCustomer_returnDeletedCustomer() {
        CustomerServiceModel serviceModel = this.customerService.deleteCustomer(CUSTOMER_ID);

        Assert.assertEquals(serviceModel.getId(), CUSTOMER_ID);
        Assert.assertEquals(serviceModel.getName(), CUSTOMER_NAME);
        Assert.assertEquals(serviceModel.getBirthDate(), CUSTOMER_BIRTH_DATE);
        Assert.assertEquals(serviceModel.isYoungDriver(), CUSTOMER_IS_YOUNG_DRIVER);
    }

    @Test
    public void findCustomerById_whenSearchingForAnExistingCustomer_findCustomer() {
        CustomerServiceModel serviceModel = this.customerService.findCustomerById(CUSTOMER_ID);

        Assert.assertEquals(serviceModel.getId(), CUSTOMER_ID);
        Assert.assertEquals(serviceModel.getName(), CUSTOMER_NAME);
        Assert.assertEquals(serviceModel.getBirthDate(), CUSTOMER_BIRTH_DATE);
        Assert.assertEquals(serviceModel.isYoungDriver(), CUSTOMER_IS_YOUNG_DRIVER);
    }
}
