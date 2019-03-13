package resident_evil.domain.entities;

import javax.persistence.Id;

public class BaseEntity {
    private Integer id;

    @Id
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
