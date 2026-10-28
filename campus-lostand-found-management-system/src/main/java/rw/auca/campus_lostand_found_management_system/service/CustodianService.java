
package rw.auca.campus_lostand_found_management_system.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import rw.auca.campus_lostand_found_management_system.domain.Custodian;
import rw.auca.campus_lostand_found_management_system.domain.ItemCategory;
import rw.auca.campus_lostand_found_management_system.repository.CustodianRepository;
import rw.auca.campus_lostand_found_management_system.repository.HelpDeskRepository;
import rw.auca.campus_lostand_found_management_system.repository.ItemCategoryRepository;

@Service
public class CustodianService {

    private final CustodianRepository custodianRepository;
    private final HelpDeskRepository helpDeskRepository;
    private final ItemCategoryRepository itemCategoryRepository;

    public CustodianService(
            CustodianRepository custodianRepository,
            HelpDeskRepository helpDeskRepository,
            ItemCategoryRepository itemCategoryRepository) {
        this.custodianRepository = custodianRepository;
        this.helpDeskRepository = helpDeskRepository;
        this.itemCategoryRepository = itemCategoryRepository;
    }

    public String saveCustodian(Custodian custodian) {
        if (custodian.getHelpDesk() != null) {
            UUID helpDeskId = custodian.getHelpDesk().getId();

            if (helpDeskId == null ||
                    !helpDeskRepository.existsById(helpDeskId)) {
                return "The specified help desk does not exist";
            }
        }

        if (custodian.getItemCategories() != null) {
            for (ItemCategory category : custodian.getItemCategories()) {
                if (category.getId() == null ||
                        !itemCategoryRepository.existsById(category.getId())) {
                    return "One or more specified item categories do not exist";
                }
            }
        }

        custodianRepository.save(custodian);
        return "Custodian saved successfully";
    }

    public List<Custodian> getAllCustodians() {
        return custodianRepository.findAll();
    }

    public Optional<Custodian> getCustodianById(UUID id) {
        return custodianRepository.findById(id);
    }

    public Optional<Custodian> updateCustodian(
            UUID id, Custodian updatedCustodian) {

        return custodianRepository.findById(id).flatMap(existingCustodian -> {
            if (updatedCustodian.getHelpDesk() != null) {
                UUID helpDeskId = updatedCustodian.getHelpDesk().getId();

                if (helpDeskId == null ||
                        !helpDeskRepository.existsById(helpDeskId)) {
                    return Optional.empty();
                }
            }

            if (updatedCustodian.getItemCategories() != null) {
                for (ItemCategory category : updatedCustodian.getItemCategories()) {
                    if (category.getId() == null ||
                            !itemCategoryRepository.existsById(category.getId())) {
                        return Optional.empty();
                    }
                }
            }

            existingCustodian.setFirstName(updatedCustodian.getFirstName());
            existingCustodian.setLastName(updatedCustodian.getLastName());
            existingCustodian.setPhone(updatedCustodian.getPhone());
            existingCustodian.setHelpDesk(updatedCustodian.getHelpDesk());
            existingCustodian.setItemCategories(
                    updatedCustodian.getItemCategories() == null
                            ? existingCustodian.getItemCategories()
                            : updatedCustodian.getItemCategories());

            return Optional.of(custodianRepository.save(existingCustodian));
        });
    }

    public boolean deleteCustodian(UUID id) {
        if (!custodianRepository.existsById(id)) {
            return false;
        }

        custodianRepository.deleteById(id);
        return true;
    }

    public List<Custodian> findByItemCategoryName(String name) {
        return custodianRepository.findByItemCategoryNameIgnoreCase(name);
    }

    public String addItemCategory(UUID custodianId, UUID itemCategoryId) {

        var custodianOptional = custodianRepository.findById(custodianId);

        if (custodianOptional.isEmpty()) {
            return "The custodian with that id does not exist";
        }

        var categoryOptional = itemCategoryRepository.findById(itemCategoryId);

        if (categoryOptional.isEmpty()) {
            return "That item category does not exist";
        }

        var custodian = custodianOptional.get();
        var category = categoryOptional.get();

        if (custodian.getItemCategories().contains(category)) {
            return "The custodian already has that item category";
        }

        custodian.getItemCategories().add(category);
        custodianRepository.save(custodian);

        return "Item category added to the custodian";
    }
}
