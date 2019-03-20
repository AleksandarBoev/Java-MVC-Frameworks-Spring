package residentevil_app.service;

import residentevil_app.domain.models.service.VirusServiceModel;

import java.util.List;

public interface VirusService {
    VirusServiceModel saveVirus(VirusServiceModel virusServiceModel);

    List<VirusServiceModel> allVirusesOrderedByDateAsc();

    VirusServiceModel findVirusById(String id);

    void editVirus(VirusServiceModel editedVirusServiceModel);
}
