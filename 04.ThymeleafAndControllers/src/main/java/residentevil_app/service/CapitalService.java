package residentevil_app.service;

import residentevil_app.domain.models.service.CapitalServiceModel;

import java.util.List;
import java.util.Set;

public interface CapitalService {
    List<CapitalServiceModel> getAllCapitalsSortedByNameAsc();

    Set<CapitalServiceModel> getAllCapitalsByIds(Set<String> ids);
}
