package rw.auca.campus_lostand_found_management_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.auca.campus_lostand_found_management_system.domain.Handover;

import java.util.UUID;

public interface HandoverRepository
        extends JpaRepository<Handover, UUID> {
}
