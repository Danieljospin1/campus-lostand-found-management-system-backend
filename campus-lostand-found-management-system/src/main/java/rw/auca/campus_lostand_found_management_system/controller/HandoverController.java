package rw.auca.campus_lostand_found_management_system.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.auca.campus_lostand_found_management_system.domain.Handover;
import rw.auca.campus_lostand_found_management_system.service.HandoverService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/handovers")
public class HandoverController {

    private final HandoverService handoverService;

    public HandoverController(HandoverService handoverService) {
        this.handoverService = handoverService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveHandover(
            @RequestParam UUID itemReportId,
            @RequestBody Handover handover) {

        String result = handoverService.saveHandover(handover, itemReportId);

        if (result.equals("The item report with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        } else if (result.equals("Handovers are only for RETURNED item reports")) {
            return ResponseEntity.badRequest().body(result);
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        }
    }

    @GetMapping("/campus-member/{campusId}")
    public ResponseEntity<List<Handover>> getHandoversByCampusMember(
            @PathVariable String campusId) {

        List<Handover> handovers = handoverService.getHandoversByCampusMember(campusId);

        return ResponseEntity.ok(handovers);
    }

}