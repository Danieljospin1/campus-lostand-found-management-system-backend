package rw.auca.campus_lostand_found_management_system.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import rw.auca.campus_lostand_found_management_system.domain.CampusMember;

public interface CampusMemberRepository
        extends JpaRepository<CampusMember, UUID> {

    boolean existsByCampusId(String campusId);

    List<CampusMember> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);

    @Query("SELECT DISTINCT ir.campusMember FROM ItemReport ir WHERE ir.custodian.id = :custodianId")
    List<CampusMember> findMembersOfCustodian(
            @Param("custodianId") UUID custodianId);
}