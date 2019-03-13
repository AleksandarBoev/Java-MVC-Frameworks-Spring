package org.softuni.cardealer.service_tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.softuni.cardealer.domain.entities.Part;
import org.softuni.cardealer.domain.models.service.PartServiceModel;
import org.softuni.cardealer.service.PartService;
import org.softuni.cardealer.service.PartServiceImpl;
import org.softuni.constants.PartConstants;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PartServiceTest extends BaseServiceTest {
    private PartService partService;

    @Before
    public void init() {
        super.init();
        this.partService = new PartServiceImpl(super.partRepository, super.modelMapper);

//        partService.savePart(partServiceModel);
//        partService.deletePart(String id);
//        partService.editPart(partServiceModel);
//        partService.findPartById(String id);
    }

    @Test
    public void savePart_whenSavingPartWithCorrectValues_goesIntoDatabase() throws IllegalAccessException {
        Part initialSavedPart = new Part();
        initialSavedPart.setName(PartConstants.PART_CORRECT_NAME_1);
        initialSavedPart.setPrice(PartConstants.PART_CORRECT_PRICE_1);
        initialSavedPart.setSupplier(super.supplierRepository.findAll().get(0));

        PartServiceModel partServiceModel = super.modelMapper.map(initialSavedPart, PartServiceModel.class);
        partServiceModel = this.partService.savePart(partServiceModel);

        Set<String> excludedFields = new HashSet<>();
        excludedFields.add("id");

        Assert.assertNotNull(partServiceModel.getId());

        Part partOutOfDb = super.partRepository.findById(partServiceModel.getId()).orElse(null);
        Assert.assertNotNull(partOutOfDb);
        super.assertSameFieldValues(initialSavedPart, partOutOfDb, excludedFields);
        //TODO java.lang.AssertionError: Fields [supplier] do not match!
    }

}
