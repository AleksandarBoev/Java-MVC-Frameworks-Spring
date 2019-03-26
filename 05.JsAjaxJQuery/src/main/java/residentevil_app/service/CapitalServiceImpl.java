package residentevil_app.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import residentevil_app.domain.models.service.CapitalServiceModel;
import residentevil_app.domain.models.view.CapitalAllViewModel;
import residentevil_app.repository.CapitalRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CapitalServiceImpl implements CapitalService {
    private CapitalRepository capitalRepository;
    private ModelMapper modelMapper;

    @Autowired
    public CapitalServiceImpl(CapitalRepository capitalRepository, ModelMapper modelMapper) {
        this.capitalRepository = capitalRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CapitalServiceModel> getAllCapitalsSortedByNameAsc() {
        return this.capitalRepository.getAllOrderedByNameAsc()
                .stream()
                .map(c -> this.modelMapper.map(c, CapitalServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public Set<CapitalServiceModel> getAllCapitalsByIds(Set<String> ids) {
        return this.capitalRepository.findAllByIdIn(ids)
                .stream()
                .map(c -> this.modelMapper.map(c, CapitalServiceModel.class))
                .collect(Collectors.toSet());
    }

    @Override
    public List<CapitalAllViewModel> getCapitalViewModels() {
        return this.capitalRepository.findAll()
                .stream()
                .map(c -> this.modelMapper.map(c, CapitalAllViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<CapitalAllViewModel> getCapitalViewModelsSortedByNameAsc() {
        return this.capitalRepository.getAllOrderedByNameAsc()
                .stream()
                .map(c -> this.modelMapper.map(c, CapitalAllViewModel.class))
                .collect(Collectors.toList());
    }
}
