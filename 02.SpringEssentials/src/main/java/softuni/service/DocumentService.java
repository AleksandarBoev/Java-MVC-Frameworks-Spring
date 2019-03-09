package softuni.service;

import softuni.domain.models.service.DocumentServiceModel;

import java.util.List;

public interface DocumentService extends BaseService<DocumentServiceModel> {
    List<DocumentServiceModel> getAll();

    DocumentServiceModel findById(String id);

    void deleteById(String id);
}
