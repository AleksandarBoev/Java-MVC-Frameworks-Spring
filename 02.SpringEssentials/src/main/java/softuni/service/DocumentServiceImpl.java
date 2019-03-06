package softuni.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.domain.entities.Document;
import softuni.domain.models.service.DocumentServiceModel;
import softuni.repository.DocumentRepository;

import javax.validation.Validator;

@Service
public class DocumentServiceImpl extends BaseServiceImpl implements DocumentService {
    private DocumentRepository documentRepository;

    @Autowired
    public DocumentServiceImpl(ModelMapper modelMapper, Validator validator, DocumentRepository documentRepository) {
        super(modelMapper, validator);
        this.documentRepository = documentRepository;
    }

    @Override
    public boolean save(DocumentServiceModel documentServiceModel) {
        //TODO validations
        Document document =
                this.documentRepository.save(super.getModelMapper().map(documentServiceModel, Document.class));
        documentServiceModel.setId(document.getId());
        return true;
    }
}
