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

import rw.auca.campus_lostand_found_management_system.domain.HelpDesk;
import rw.auca.campus_lostand_found_management_system.service.HelpDeskService;

@RestController
@RequestMapping("/api/help-desks")
public class HelpDeskController {

    private final HelpDeskService helpDeskService;

    public HelpDeskController(HelpDeskService helpDeskService) {
        this.helpDeskService = helpDeskService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveHelpDesk(@RequestBody HelpDesk helpDesk) {

        helpDeskService.saveHelpDesk(helpDesk);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Help desk saved successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<List<HelpDesk>> getAllHelpDesks() {
        return ResponseEntity.ok(helpDeskService.getAllHelpDesks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getHelpDeskById(@PathVariable UUID id) {

        var helpDesk = helpDeskService.getHelpDeskById(id);

        if (helpDesk.isPresent()) {
            return ResponseEntity.ok(helpDesk.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The help desk with that ID does not exist");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateHelpDesk(
            @PathVariable UUID id,
            @RequestBody HelpDesk updatedHelpDesk) {

        var helpDesk = helpDeskService.updateHelpDesk(id, updatedHelpDesk);

        if (helpDesk.isPresent()) {
            return ResponseEntity.ok("Help desk updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The help desk with that ID does not exist");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteHelpDesk(@PathVariable UUID id) {

        boolean deleted = helpDeskService.deleteHelpDesk(id);

        if (deleted) {
            return ResponseEntity.ok("Help desk deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The help desk with that ID does not exist");
        }
    }
}
