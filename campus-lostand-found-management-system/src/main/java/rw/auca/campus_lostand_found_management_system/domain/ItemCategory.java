
package rw.auca.campus_lostand_found_management_system.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "item_category")
public class ItemCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "itemCategories")
    @JsonIgnore
    private List<Custodian> custodians = new ArrayList<>();

    public ItemCategory() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Custodian> getCustodians() {
        return custodians;
    }

    public void setCustodians(List<Custodian> custodians) {
        this.custodians = custodians;
    }
}
