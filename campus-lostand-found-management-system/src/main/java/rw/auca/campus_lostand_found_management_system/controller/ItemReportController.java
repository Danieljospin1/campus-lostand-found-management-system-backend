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

import rw.auca.campus_lostand_found_management_system.domain.ItemReport;
import rw.auca.campus_lostand_found_management_system.service.ItemReportService;

@RestController
@RequestMapping("/api/item-reports")
public class ItemReportController {

    private final ItemReportService itemReportService;

    public ItemReportController(ItemReportService itemReportService) {
        this.itemReportService = itemReportService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveItemReport(
            @RequestBody ItemReport itemReport) {

        String result = itemReportService.saveItemReport(itemReport);

        if (result.equals("Item report saved successfully")) {
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<ItemReport>> getAllItemReports() {
        return ResponseEntity.ok(itemReportService.getAllItemReports());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getItemReportById(@PathVariable UUID id) {

        var report = itemReportService.getItemReportById(id);

        if (report.isPresent()) {
            return ResponseEntity.ok(report.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The item report with that ID does not exist");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateItemReport(
            @PathVariable UUID id,
            @RequestBody ItemReport updatedReport) {

        var report = itemReportService.updateItemReport(id, updatedReport);

        if (report.isPresent()) {
            return ResponseEntity.ok("Item report updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The item report with that ID does not exist, or a referenced member or custodian is invalid");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteItemReport(@PathVariable UUID id) {

        boolean deleted = itemReportService.deleteItemReport(id);

        if (deleted) {
            return ResponseEntity.ok("Item report deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The item report with that ID does not exist");
        }
    }
}
