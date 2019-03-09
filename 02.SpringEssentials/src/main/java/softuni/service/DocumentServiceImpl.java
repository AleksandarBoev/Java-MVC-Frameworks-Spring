package softuni.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.domain.entities.Document;
import softuni.domain.models.service.DocumentServiceModel;
import softuni.repository.DocumentRepository;

import javax.validation.Validator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentServiceImpl extends BaseServiceImpl implements DocumentService {
    private DocumentRepository documentRepository;

    @Autowired
    public DocumentServiceImpl(ModelMapper modelMapper, Validator validator, DocumentRepository documentRepository) {
        super(modelMapper, validator);
        this.documentRepository = documentRepository;
    }

    @Override
    public DocumentServiceModel register(DocumentServiceModel documentServiceModel) {
        //TODO validations
        Document document =
                this.documentRepository.save(super.getModelMapper().map(documentServiceModel, Document.class));
        documentServiceModel.setId(document.getId());
        return null; //TODO not finished
    }

    @Override
    public List<DocumentServiceModel> getAll() {
        return this.documentRepository.findAll()
                .stream()
                .map(d -> super.getModelMapper().map(d, DocumentServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public DocumentServiceModel findById(String id) {
        //TODO if nothing is found, null will be returned and modelmapper breaks program when trying to map from null
        Document document = this.documentRepository.findById(id).orElse(null);
        DocumentServiceModel documentServiceModel = super.getModelMapper().map(document, DocumentServiceModel.class);
        return documentServiceModel;
    }

    @Override
    public void deleteById(String id) {
        this.documentRepository.deleteById(id);
    }
}
