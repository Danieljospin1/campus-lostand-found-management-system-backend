package rw.auca.campus_lostand_found_management_system.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rw.auca.campus_lostand_found_management_system.domain.Custodian;
import rw.auca.campus_lostand_found_management_system.domain.ItemReport;
import rw.auca.campus_lostand_found_management_system.domain.ReportStatus;

public interface ItemReportRepository
        extends JpaRepository<ItemReport, UUID> {

    boolean existsByCustodianAndReportDateAndStatusNot(Custodian custodian, LocalDate reportDate, ReportStatus status);

    List<ItemReport> findByStatusOrderByReportDateAsc(ReportStatus status);

    List<ItemReport> findByReportDateBetweenOrderByReportDateAsc(LocalDate start, LocalDate end);

    @Query("SELECT ir.status, COUNT(ir) FROM ItemReport ir GROUP BY ir.status")
    List<Object[]> countItemReportsByStatus();
}