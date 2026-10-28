package rw.auca.campus_lostand_found_management_system.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import rw.auca.campus_lostand_found_management_system.domain.ItemCategory;

public interface ItemCategoryRepository
        extends JpaRepository<ItemCategory, UUID> {
}