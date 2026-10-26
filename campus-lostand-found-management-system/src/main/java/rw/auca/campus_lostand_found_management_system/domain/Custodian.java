
package rw.auca.campus_lostand_found_management_system.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "custodian")
public class Custodian {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String firstName;

    private String lastName;

    private String phone;

    @ManyToOne
    @JoinColumn(name = "help_desk_id", nullable = true)
    private HelpDesk helpDesk;

    @ManyToMany
    @JoinTable(
        name = "custodian_item_category",
        joinColumns = @JoinColumn(name = "custodian_id"),
        inverseJoinColumns = @JoinColumn(name = "item_category_id")
    )
    private List<ItemCategory> itemCategories = new ArrayList<>();

    public Custodian() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public HelpDesk getHelpDesk() {
        return helpDesk;
    }

    public void setHelpDesk(HelpDesk helpDesk) {
        this.helpDesk = helpDesk;
    }

    public List<ItemCategory> getItemCategories() {
        return itemCategories;
    }

    public void setItemCategories(List<ItemCategory> itemCategories) {
        this.itemCategories = itemCategories;
    }
}
