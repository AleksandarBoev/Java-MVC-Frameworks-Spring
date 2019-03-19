package residentevil_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import residentevil_app.domain.entities.Virus;

@Repository
public interface VirusRepository extends JpaRepository<Virus, String> {
}
