package rw.auca.campus_lostand_found_management_system.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rw.auca.campus_lostand_found_management_system.domain.ItemCategory;
import rw.auca.campus_lostand_found_management_system.service.ItemCategoryService;

@RestController
@RequestMapping("/api/item-categories")
public class ItemCategoryController {

    private final ItemCategoryService itemCategoryService;

    public ItemCategoryController(ItemCategoryService itemCategoryService) {
        this.itemCategoryService = itemCategoryService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveItemCategory(
            @RequestBody ItemCategory itemCategory) {

        itemCategoryService.saveItemCategory(itemCategory);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Item category saved successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<List<ItemCategory>> getAllItemCategories() {
        return ResponseEntity.ok(
                itemCategoryService.getAllItemCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getItemCategoryById(@PathVariable UUID id) {

        var category = itemCategoryService.getItemCategoryById(id);

        if (category.isPresent()) {
            return ResponseEntity.ok(category.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The item category with that ID does not exist");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateItemCategory(
            @PathVariable UUID id,
            @RequestBody ItemCategory updatedCategory) {

        var category = itemCategoryService.updateItemCategory(id, updatedCategory);

        if (category.isPresent()) {
            return ResponseEntity.ok("Item category updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The item category with that ID does not exist");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteItemCategory(@PathVariable UUID id) {

        boolean deleted = itemCategoryService.deleteItemCategory(id);

        if (deleted) {
            return ResponseEntity.ok("Item category deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The item category with that ID does not exist");
        }
    }

    @GetMapping("/unused")
    public ResponseEntity<List<ItemCategory>> findUnusedItemCategories() {
        return ResponseEntity.ok(
                itemCategoryService.findUnusedItemCategories());
    }
}
