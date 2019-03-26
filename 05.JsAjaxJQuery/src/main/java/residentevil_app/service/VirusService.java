package residentevil_app.service;

import residentevil_app.domain.models.service.VirusServiceModel;
import residentevil_app.domain.models.view.VirusShowAllViewModel;

import java.util.List;

public interface VirusService {
    VirusServiceModel saveVirus(VirusServiceModel virusServiceModel);

    List<VirusServiceModel> allVirusesOrderedByDateAsc();

    List<VirusShowAllViewModel> allVirusesViewModelsOrderedByDateAsc();

    VirusServiceModel findVirusById(String id);

    void editVirus(VirusServiceModel editedVirusServiceModel);

    void deleteVirus(String id);
}