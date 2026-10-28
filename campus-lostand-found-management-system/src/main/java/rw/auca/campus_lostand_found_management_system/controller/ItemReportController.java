package rw.auca.campus_lostand_found_management_system.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rw.auca.campus_lostand_found_management_system.domain.ItemReport;
import rw.auca.campus_lostand_found_management_system.domain.ReportStatus;
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
        } else if (result.equals(
                "Custodian already handles an item report on that date")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
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

    @PatchMapping("/{id}/status")
    public ResponseEntity<String> updateItemReportStatus(
            @PathVariable UUID id,
            @RequestParam ReportStatus status) {

        String result = itemReportService.updateItemReportStatus(id, status);

        if (result.equals("The item report with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        } else if (result.equals("Item report status updated successfully")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteItemReport(@PathVariable UUID id) {

        String deleted = itemReportService.deleteItemReport(id);

        if (deleted.equals("The item report with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(deleted);
        } else if (deleted.equals("That item report has handovers, delete them first")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(deleted);
        } else {
            return ResponseEntity.ok(deleted);
        }
    }

    @GetMapping("/by-status")
    public ResponseEntity<?> findByStatus(
            @RequestParam ReportStatus status) {

        List<ItemReport> reports = itemReportService.findByStatus(status);

        return ResponseEntity.ok(reports);
    }

    @GetMapping("/between")
    public ResponseEntity<?> findBetweenDates(
            @RequestParam String start,
            @RequestParam String end) {

        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);

        if (startDate.isAfter(endDate)) {
            return ResponseEntity.badRequest()
                    .body("Start date must not be after end date");
        }

        List<ItemReport> reports = itemReportService.findBetweenDates(startDate, endDate);

        return ResponseEntity.ok(reports);
    }

    @GetMapping("/stats/by-status")
    public ResponseEntity<List<Object[]>> getItemReportStatsByStatus() {
        return ResponseEntity.ok(
                itemReportService.getItemReportStatsByStatus());
    }
}
