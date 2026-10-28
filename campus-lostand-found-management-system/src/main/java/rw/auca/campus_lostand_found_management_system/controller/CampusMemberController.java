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

import rw.auca.campus_lostand_found_management_system.domain.CampusMember;
import rw.auca.campus_lostand_found_management_system.service.CampusMemberService;

@RestController
@RequestMapping("/api/campus-members")
public class CampusMemberController {

    private final CampusMemberService campusMemberService;

    public CampusMemberController(CampusMemberService campusMemberService) {
        this.campusMemberService = campusMemberService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveCampusMember(
            @RequestBody CampusMember campusMember) {

        String result = campusMemberService.saveCampusMember(campusMember);

        if (result.equals("Campus member saved successfully")) {
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<CampusMember>> getAllCampusMembers() {
        return ResponseEntity.ok(
                campusMemberService.getAllCampusMembers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCampusMemberById(@PathVariable UUID id) {

        var member = campusMemberService.getCampusMemberById(id);

        if (member.isPresent()) {
            return ResponseEntity.ok(member.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The campus member with that ID does not exist");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCampusMember(
            @PathVariable UUID id,
            @RequestBody CampusMember updatedMember) {

        var member = campusMemberService.updateCampusMember(id, updatedMember);

        if (member.isPresent()) {
            return ResponseEntity.ok("Campus member updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The campus member with that ID does not exist, or the campus ID is already used");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCampusMember(@PathVariable UUID id) {

        boolean deleted = campusMemberService.deleteCampusMember(id);

        if (deleted) {
            return ResponseEntity.ok("Campus member deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The campus member with that ID does not exist");
        }
    }
}
