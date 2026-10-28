
package rw.auca.campus_lostand_found_management_system.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import rw.auca.campus_lostand_found_management_system.domain.ItemCategory;
import rw.auca.campus_lostand_found_management_system.repository.ItemCategoryRepository;

@Service
public class ItemCategoryService {

    private final ItemCategoryRepository itemCategoryRepository;

    public ItemCategoryService(ItemCategoryRepository itemCategoryRepository) {
        this.itemCategoryRepository = itemCategoryRepository;
    }

    public ItemCategory saveItemCategory(ItemCategory itemCategory) {
        return itemCategoryRepository.save(itemCategory);
    }

    public List<ItemCategory> getAllItemCategories() {
        return itemCategoryRepository.findAll();
    }

    public Optional<ItemCategory> getItemCategoryById(UUID id) {
        return itemCategoryRepository.findById(id);
    }

    public Optional<ItemCategory> updateItemCategory(
            UUID id, ItemCategory updatedCategory) {

        return itemCategoryRepository.findById(id).map(existingCategory -> {
            existingCategory.setName(updatedCategory.getName());

            return itemCategoryRepository.save(existingCategory);
        });
    }

    public boolean deleteItemCategory(UUID id) {
        if (!itemCategoryRepository.existsById(id)) {
            return false;
        }

        itemCategoryRepository.deleteById(id);
        return true;
    }
}
