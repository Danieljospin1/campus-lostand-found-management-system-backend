package rw.auca.campus_lostand_found_management_system.service;

import org.springframework.stereotype.Service;
import rw.auca.campus_lostand_found_management_system.domain.Handover;
import rw.auca.campus_lostand_found_management_system.domain.ReportStatus;
import rw.auca.campus_lostand_found_management_system.repository.HandoverRepository;
import rw.auca.campus_lostand_found_management_system.repository.ItemReportRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class HandoverService {

    private final HandoverRepository handoverRepository;
    private final ItemReportRepository itemReportRepository;

    public HandoverService(HandoverRepository handoverRepository, ItemReportRepository itemReportRepository) {
        this.handoverRepository = handoverRepository;
        this.itemReportRepository = itemReportRepository;
    }

    public String saveHandover(Handover handover, UUID itemReportId) {
        var itemReportOptional = itemReportRepository.findById(itemReportId);

        if (itemReportOptional.isEmpty()) {
            return "The item report with that id does not exist";
        }

        var itemReport = itemReportOptional.get();

        if (itemReport.getStatus() != ReportStatus.RETURNED) {
            return "Handovers are only for RETURNED item reports";
        }

        handover.setItemReport(itemReport);
        handover.setHandoverDate(LocalDate.now());

        handoverRepository.save(handover);

        return "Handover saved successfully";
    }

    public List<Handover> getHandoversByCampusMember(String campusId) {
        return handoverRepository.findByCampusMemberCampusId(campusId);
    }

}
