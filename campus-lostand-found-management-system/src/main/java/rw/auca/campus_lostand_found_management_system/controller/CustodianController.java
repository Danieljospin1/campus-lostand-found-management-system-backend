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

import rw.auca.campus_lostand_found_management_system.domain.Custodian;
import rw.auca.campus_lostand_found_management_system.service.CustodianService;

@RestController
@RequestMapping("/api/custodians")
public class CustodianController {

    private final CustodianService custodianService;

    public CustodianController(CustodianService custodianService) {
        this.custodianService = custodianService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveCustodian(
            @RequestBody Custodian custodian) {

        String result = custodianService.saveCustodian(custodian);

        if (result.equals("Custodian saved successfully")) {
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Custodian>> getAllCustodians() {
        return ResponseEntity.ok(custodianService.getAllCustodians());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustodianById(@PathVariable UUID id) {

        var custodian = custodianService.getCustodianById(id);

        if (custodian.isPresent()) {
            return ResponseEntity.ok(custodian.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The custodian with that ID does not exist");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCustodian(
            @PathVariable UUID id,
            @RequestBody Custodian updatedCustodian) {

        var custodian = custodianService.updateCustodian(id, updatedCustodian);

        if (custodian.isPresent()) {
            return ResponseEntity.ok("Custodian updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The custodian with that ID does not exist, or a related help desk or category is invalid");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCustodian(@PathVariable UUID id) {

        boolean deleted = custodianService.deleteCustodian(id);

        if (deleted) {
            return ResponseEntity.ok("Custodian deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The custodian with that ID does not exist");
        }
    }
}
