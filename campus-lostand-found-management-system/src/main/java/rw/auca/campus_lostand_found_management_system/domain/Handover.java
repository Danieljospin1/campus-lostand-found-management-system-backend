package rw.auca.campus_lostand_found_management_system.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "handover")
public class Handover {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String proofType;

    private String remarks;

    private int itemsReturned;

    private LocalDate handoverDate;

    @ManyToOne
    @JoinColumn(name = "item_report_id")
    private ItemReport itemReport;

    public Handover() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getProofType() {
        return proofType;
    }

    public void setProofType(String proofType) {
        this.proofType = proofType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getItemsReturned() {
        return itemsReturned;
    }

    public void setItemsReturned(int itemsReturned) {
        this.itemsReturned = itemsReturned;
    }

    public LocalDate getHandoverDate() {
        return handoverDate;
    }

    public void setHandoverDate(LocalDate handoverDate) {
        this.handoverDate = handoverDate;
    }

    public ItemReport getItemReport() {
        return itemReport;
    }

    public void setItemReport(ItemReport itemReport) {
        this.itemReport = itemReport;
    }
}
