package residentevil_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import residentevil_app.domain.entities.Capital;

import java.util.List;
import java.util.Set;

@Repository
public interface CapitalRepository extends JpaRepository<Capital, String> {
    @Query("" +
            "SELECT c FROM Capital c " +
            "ORDER BY c.name ASC")
    List<Capital> getAllOrderedByNameAsc();

    Set<Capital> findAllByIdIn(Set<String> names);
}
