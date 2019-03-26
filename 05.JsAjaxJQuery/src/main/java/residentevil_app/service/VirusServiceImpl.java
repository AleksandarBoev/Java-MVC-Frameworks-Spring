package residentevil_app.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import residentevil_app.domain.entities.Capital;
import residentevil_app.domain.entities.Virus;
import residentevil_app.domain.models.service.VirusServiceModel;
import residentevil_app.domain.models.view.VirusShowAllViewModel;
import residentevil_app.repository.VirusRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class VirusServiceImpl implements VirusService {
    private VirusRepository virusRepository;
    private ModelMapper modelMapper;

    @Autowired
    public VirusServiceImpl(VirusRepository virusRepository, ModelMapper modelMapper) {
        this.virusRepository = virusRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public VirusServiceModel saveVirus(VirusServiceModel virusServiceModel) {
        Virus virus = this.modelMapper.map(virusServiceModel, Virus.class);
        Set<Capital> capitals = virusServiceModel.getCapitals()
                .stream()
                .map(c -> this.modelMapper.map(c, Capital.class))
                .collect(Collectors.toSet());

        virus.setCapitals(new HashSet<>());
        virus = this.virusRepository.saveAndFlush(virus); //TODO detatched entity exception
        /*
        This problem occurs if I try to set the capitals before the saveAndFlush method.
        org.hibernate.PersistentObjectException: detached entity passed to persist:
        residentevil_app.domain.entities.Capital
         */
        virus.setCapitals(capitals);
        this.virusRepository.saveAndFlush(virus);
        virusServiceModel.setId(virus.getId()); //setting the id of a pointer parameter will affect the pointer. But this is more testable (i think)
        return virusServiceModel;
    }

    @Override
    public List<VirusServiceModel> allVirusesOrderedByDateAsc() {
        return this.virusRepository.findAllByOrderByReleasedOnAsc()
                .stream()
                .map(v -> this.modelMapper.map(v, VirusServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<VirusShowAllViewModel> allVirusesViewModelsOrderedByDateAsc() {
        return this.virusRepository.findAllByOrderByReleasedOnAsc()
                .stream()
                .map(v -> this.modelMapper.map(v, VirusShowAllViewModel.class))
                .collect(Collectors.toList());
    }


    @Override
    public VirusServiceModel findVirusById(String id) {
        Virus virus = this.virusRepository.findById(id).orElse(null);
        return this.modelMapper.map(virus, VirusServiceModel.class);
    }

    @Override
    public void editVirus(VirusServiceModel editedVirusServiceModel) {
        Virus virus = this.modelMapper.map(editedVirusServiceModel, Virus.class);
        Set<Capital> capitals = editedVirusServiceModel.getCapitals()
                .stream()
                .map(c -> this.modelMapper.map(c, Capital.class))
                .collect(Collectors.toSet());
        virus.setCapitals(capitals);

        virus = this.virusRepository.saveAndFlush(virus);
    }

    @Override
    public void deleteVirus(String id) {
        if (!this.virusRepository.existsById(id))
            return;

        this.virusRepository.deleteById(id);
    }
}
