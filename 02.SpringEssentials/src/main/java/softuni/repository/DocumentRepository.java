package softuni.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.domain.entities.Document;

@Repository
public interface DocumentRepository extends JpaRepository<Document, String> {
}
