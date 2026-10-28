
package rw.auca.campus_lostand_found_management_system.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import rw.auca.campus_lostand_found_management_system.domain.HelpDesk;
import rw.auca.campus_lostand_found_management_system.repository.HelpDeskRepository;

@Service
public class HelpDeskService {

    private final HelpDeskRepository helpDeskRepository;

    public HelpDeskService(HelpDeskRepository helpDeskRepository) {
        this.helpDeskRepository = helpDeskRepository;
    }

    public HelpDesk saveHelpDesk(HelpDesk helpDesk) {
        return helpDeskRepository.save(helpDesk);
    }

    public List<HelpDesk> getAllHelpDesks() {
        return helpDeskRepository.findAll();
    }

    public Optional<HelpDesk> getHelpDeskById(UUID id) {
        return helpDeskRepository.findById(id);
    }

    public Optional<HelpDesk> updateHelpDesk(UUID id, HelpDesk updatedHelpDesk) {
        return helpDeskRepository.findById(id).map(existingHelpDesk -> {
            existingHelpDesk.setDeskNumber(updatedHelpDesk.getDeskNumber());
            existingHelpDesk.setName(updatedHelpDesk.getName());

            return helpDeskRepository.save(existingHelpDesk);
        });
    }

    public boolean deleteHelpDesk(UUID id) {
        if (!helpDeskRepository.existsById(id)) {
            return false;
        }

        helpDeskRepository.deleteById(id);
        return true;
    }
}
