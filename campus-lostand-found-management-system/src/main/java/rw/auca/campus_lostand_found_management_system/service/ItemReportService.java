
package rw.auca.campus_lostand_found_management_system.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import rw.auca.campus_lostand_found_management_system.domain.ItemReport;
import rw.auca.campus_lostand_found_management_system.domain.ReportStatus;
import rw.auca.campus_lostand_found_management_system.repository.CampusMemberRepository;
import rw.auca.campus_lostand_found_management_system.repository.CustodianRepository;
import rw.auca.campus_lostand_found_management_system.repository.ItemReportRepository;

@Service
public class ItemReportService {

    private final ItemReportRepository itemReportRepository;
    private final CampusMemberRepository campusMemberRepository;
    private final CustodianRepository custodianRepository;

    public ItemReportService(
            ItemReportRepository itemReportRepository,
            CampusMemberRepository campusMemberRepository,
            CustodianRepository custodianRepository) {
        this.itemReportRepository = itemReportRepository;
        this.campusMemberRepository = campusMemberRepository;
        this.custodianRepository = custodianRepository;
    }

    public String saveItemReport(ItemReport itemReport) {
        if (itemReport.getCampusMember() == null
                || itemReport.getCampusMember().getId() == null
                || !campusMemberRepository.existsById(
                        itemReport.getCampusMember().getId())) {
            return "The specified campus member does not exist";
        }

        if (itemReport.getCustodian() != null
                && (itemReport.getCustodian().getId() == null
                        || !custodianRepository.existsById(
                                itemReport.getCustodian().getId()))) {
            return "The specified custodian does not exist";
        }

        itemReport.setStatus(ReportStatus.REPORTED);
        itemReportRepository.save(itemReport);

        return "Item report saved successfully";
    }

    public List<ItemReport> getAllItemReports() {
        return itemReportRepository.findAll();
    }

    public Optional<ItemReport> getItemReportById(UUID id) {
        return itemReportRepository.findById(id);
    }

    public Optional<ItemReport> updateItemReport(
            UUID id, ItemReport updatedReport) {

        return itemReportRepository.findById(id).flatMap(existingReport -> {
            if (updatedReport.getCampusMember() == null
                    || updatedReport.getCampusMember().getId() == null
                    || !campusMemberRepository.existsById(
                            updatedReport.getCampusMember().getId())) {
                return Optional.empty();
            }

            if (updatedReport.getCustodian() != null
                    && (updatedReport.getCustodian().getId() == null
                            || !custodianRepository.existsById(
                                    updatedReport.getCustodian().getId()))) {
                return Optional.empty();
            }

            existingReport.setReportDate(updatedReport.getReportDate());
            existingReport.setItemName(updatedReport.getItemName());
            existingReport.setLocation(updatedReport.getLocation());
            existingReport.setCampusMember(updatedReport.getCampusMember());
            existingReport.setCustodian(updatedReport.getCustodian());

            // Status changes will be handled by the dedicated status endpoint.
            return Optional.of(itemReportRepository.save(existingReport));
        });
    }

    public boolean deleteItemReport(UUID id) {
        if (!itemReportRepository.existsById(id)) {
            return false;
        }

        itemReportRepository.deleteById(id);
        return true;
    }
}
