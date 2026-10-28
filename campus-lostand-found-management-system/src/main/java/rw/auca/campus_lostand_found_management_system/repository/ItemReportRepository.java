package rw.auca.campus_lostand_found_management_system.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import rw.auca.campus_lostand_found_management_system.domain.ItemReport;
import rw.auca.campus_lostand_found_management_system.domain.ReportStatus;

public interface ItemReportRepository
        extends JpaRepository<ItemReport, UUID> {

            List<ItemReport> findByStatusOrderByReportDateAsc(ReportStatus status);
}