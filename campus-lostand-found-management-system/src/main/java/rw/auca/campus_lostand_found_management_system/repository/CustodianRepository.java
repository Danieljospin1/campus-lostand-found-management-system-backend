package rw.auca.campus_lostand_found_management_system.repository;

import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import rw.auca.campus_lostand_found_management_system.domain.Custodian;

public interface CustodianRepository extends JpaRepository<Custodian, UUID> {

    @Query("SELECT DISTINCT c FROM Custodian c " +
            "JOIN c.itemCategories ic " +
            "WHERE LOWER(ic.name) = LOWER(:name)")
    List<Custodian> findByItemCategoryNameIgnoreCase(@Param("name") String name);

}