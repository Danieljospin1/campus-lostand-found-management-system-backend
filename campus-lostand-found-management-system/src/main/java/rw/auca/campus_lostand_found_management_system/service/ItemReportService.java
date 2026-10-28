
package rw.auca.campus_lostand_found_management_system.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import rw.auca.campus_lostand_found_management_system.domain.ItemReport;
import rw.auca.campus_lostand_found_management_system.domain.ReportStatus;
import rw.auca.campus_lostand_found_management_system.repository.CampusMemberRepository;
import rw.auca.campus_lostand_found_management_system.repository.CustodianRepository;
import rw.auca.campus_lostand_found_management_system.repository.HandoverRepository;
import rw.auca.campus_lostand_found_management_system.repository.ItemReportRepository;

@Service
public class ItemReportService {

    private final ItemReportRepository itemReportRepository;
    private final CampusMemberRepository campusMemberRepository;
    private final CustodianRepository custodianRepository;
    private final HandoverRepository handoverRepository;

    public ItemReportService(
            ItemReportRepository itemReportRepository,
            CampusMemberRepository campusMemberRepository,
            CustodianRepository custodianRepository,
            HandoverRepository handoverRepository) {
        this.itemReportRepository = itemReportRepository;
        this.campusMemberRepository = campusMemberRepository;
        this.custodianRepository = custodianRepository;
        this.handoverRepository = handoverRepository;
    }

    public String saveItemReport(ItemReport itemReport) {

        if (itemReport.getReportDate().isBefore(LocalDate.now())) {
            return "Item report date cannot be in the past";
        }
        if (itemReport.getCampusMember() == null
                || itemReport.getCampusMember().getId() == null
                || !campusMemberRepository.existsById(
                        itemReport.getCampusMember().getId())) {
            return "The specified campus member does not exist";
        }

        if (itemReport.getCustodian() != null) {
            var custodianId = itemReport.getCustodian().getId();
            var custodian = custodianRepository.findById(custodianId);
            if (custodian.isEmpty()) {
                return "The specified custodian does not exist";
            }

            boolean alreadyHasReport = itemReportRepository
                    .existsByCustodianAndReportDateAndStatusNot(
                            custodian.get(),
                            itemReport.getReportDate(),
                            ReportStatus.CLOSED);

            if (alreadyHasReport) {
                return "Custodian already handles an item report on that date";
            }

            itemReport.setCustodian(custodian.get());

        }

        itemReport.setStatus(ReportStatus.REPORTED);
        itemReportRepository.save(itemReport);

        return "Item report saved successfully";
    }

    public List<ItemReport> findByStatus(ReportStatus status) {
        return itemReportRepository.findByStatusOrderByReportDateAsc(status);
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

            return Optional.of(itemReportRepository.save(existingReport));
        });
    }

    public String updateItemReportStatus(UUID id, ReportStatus newStatus) {

        var optionalReport = itemReportRepository.findById(id);

        if (optionalReport.isEmpty()) {
            return "The item report with that id does not exist";
        }

        ItemReport report = optionalReport.get();
        ReportStatus currentStatus = report.getStatus();

        boolean allowed = (currentStatus == ReportStatus.REPORTED
                && (newStatus == ReportStatus.MATCHED
                        || newStatus == ReportStatus.CLOSED))
                ||
                (currentStatus == ReportStatus.MATCHED
                        && (newStatus == ReportStatus.RETURNED
                                || newStatus == ReportStatus.CLOSED));

        if (!allowed) {
            return "Cannot change status from "
                    + currentStatus + " to " + newStatus;
        }

        report.setStatus(newStatus);
        itemReportRepository.save(report);

        return "Item report status updated successfully";
    }

    public String deleteItemReport(UUID id) {
        var itemReportOptional = itemReportRepository.findById(id);

        if (itemReportOptional.isEmpty()) {
            return "The item report with that id does not exist";
        }

        var itemReport = itemReportOptional.get();

        if (handoverRepository.existsByItemReport(itemReport)) {
            return "That item report has handovers, delete them first";
        }

        itemReportRepository.delete(itemReport);

        return "Item report deleted successfully";

    }

    public List<ItemReport> findBetweenDates(LocalDate start, LocalDate end) {
        return itemReportRepository.findByReportDateBetweenOrderByReportDateAsc(start, end);
    }

    public List<Object[]> getItemReportStatsByStatus() {
        return itemReportRepository.countItemReportsByStatus();
    }
}
