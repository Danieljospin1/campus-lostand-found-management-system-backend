
package rw.auca.campus_lostand_found_management_system.service;

import org.springframework.stereotype.Service;
import rw.auca.campus_lostand_found_management_system.domain.CampusMember;
import rw.auca.campus_lostand_found_management_system.repository.CampusMemberRepository;
import rw.auca.campus_lostand_found_management_system.repository.CustodianRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CampusMemberService {

    private final CampusMemberRepository campusMemberRepository;
    private final CustodianRepository custodianRepository;

    public CampusMemberService(CampusMemberRepository campusMemberRepository, CustodianRepository custodianRepository) {
        this.campusMemberRepository = campusMemberRepository;
        this.custodianRepository = custodianRepository;
    }

    public String saveCampusMember(CampusMember campusMember) {
        if (campusMemberRepository
                .existsByCampusId(campusMember.getCampusId())) {
            return "A campus member with that campus ID already exists";
        }

        campusMemberRepository.save(campusMember);
        return "Campus member saved successfully";
    }

    public List<CampusMember> findByLastName(String lastName) {
        return campusMemberRepository
                .findByLastNameIgnoreCaseOrderByFirstNameAsc(lastName);
    }

    public List<CampusMember> getAllCampusMembers() {
        return campusMemberRepository.findAll();
    }

    public Optional<CampusMember> getCampusMemberById(UUID id) {
        return campusMemberRepository.findById(id);
    }

    public Optional<CampusMember> updateCampusMember(
            UUID id, CampusMember updatedMember) {

        return campusMemberRepository.findById(id).flatMap(existingMember -> {
            String updatedCampusId = updatedMember.getCampusId();

            boolean campusIdUsedByAnotherMember = campusMemberRepository.existsByCampusId(updatedCampusId)
                    && !existingMember.getCampusId().equals(updatedCampusId);

            if (campusIdUsedByAnotherMember) {
                return Optional.empty();
            }

            existingMember.setFirstName(updatedMember.getFirstName());
            existingMember.setLastName(updatedMember.getLastName());
            existingMember.setCampusId(updatedCampusId);
            existingMember.setEmail(updatedMember.getEmail());
            existingMember.setPhone(updatedMember.getPhone());

            return Optional.of(campusMemberRepository.save(existingMember));
        });
    }

    public boolean deleteCampusMember(UUID id) {
        if (!campusMemberRepository.existsById(id)) {
            return false;
        }

        campusMemberRepository.deleteById(id);
        return true;
    }

    public Optional<List<CampusMember>> findMembersOfCustodian(UUID custodianId) {

        if (!custodianRepository.existsById(custodianId)) {
            return Optional.empty();
        }

        return Optional.of(
                campusMemberRepository.findMembersOfCustodian(custodianId));
    }
}
