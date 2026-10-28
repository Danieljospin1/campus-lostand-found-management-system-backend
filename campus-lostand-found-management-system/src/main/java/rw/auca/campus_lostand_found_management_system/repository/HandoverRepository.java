package rw.auca.campus_lostand_found_management_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import rw.auca.campus_lostand_found_management_system.domain.Handover;

import java.util.UUID;
import java.util.List;

public interface HandoverRepository extends JpaRepository<Handover, UUID> {
    @Query("SELECT h FROM Handover h WHERE h.itemReport.campusMember.campusId = :campusId ORDER BY h.handoverDate DESC")
    List<Handover> findByCampusMemberCampusId(@Param("campusId") String campusId);
}
