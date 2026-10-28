package rw.auca.campus_lostand_found_management_system.repository;

import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rw.auca.campus_lostand_found_management_system.domain.HelpDesk;

public interface HelpDeskRepository extends JpaRepository<HelpDesk, UUID> {

    @Query(" SELECT h FROM HelpDesk h WHERE NOT EXISTS (SELECT c FROM Custodian c WHERE c.helpDesk = h) ORDER BY h.deskNumber ASC")
    List<HelpDesk> findFreeHelpDesks();
}