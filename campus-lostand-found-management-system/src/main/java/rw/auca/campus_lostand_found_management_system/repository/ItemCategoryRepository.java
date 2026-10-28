package rw.auca.campus_lostand_found_management_system.repository;

import java.util.UUID;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rw.auca.campus_lostand_found_management_system.domain.ItemCategory;

public interface ItemCategoryRepository
        extends JpaRepository<ItemCategory, UUID> {

    @Query("SELECT ic FROM ItemCategory ic WHERE ic.custodians IS EMPTY")
    List<ItemCategory> findUnusedItemCategories();
}