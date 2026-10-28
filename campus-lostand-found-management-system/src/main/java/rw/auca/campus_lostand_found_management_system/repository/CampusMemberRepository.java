package rw.auca.campus_lostand_found_management_system.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import rw.auca.campus_lostand_found_management_system.domain.CampusMember;

public interface CampusMemberRepository
        extends JpaRepository<CampusMember, UUID> {

    boolean existsByCampusId(String campusId);
}