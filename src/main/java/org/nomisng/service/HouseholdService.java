package org.nomisng.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.controller.apierror.EntityNotFoundException;
import org.nomisng.controller.apierror.IllegalTypeException;
import org.nomisng.domain.dto.*;
import org.nomisng.domain.entity.*;
import org.nomisng.domain.mapper.EncounterMapper;
import org.nomisng.domain.mapper.HouseholdMigrationMapper;
import org.nomisng.domain.mapper.HouseholdMapper;
import org.nomisng.domain.mapper.HouseholdMemberMapper;
import org.nomisng.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import static org.nomisng.util.Constants.ArchiveStatus.*;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class HouseholdService {
    public static final int HOUSEHOLD_MEMBER_TYPE = 1;
    private final HouseholdRepository householdRepository;
    private final HouseholdMemberRepository householdMemberRepository;
    private final HouseholdMigrationRepository householdMigrationRepository;
    private final EncounterMapper encounterMapper;
    private final CboProjectRepository cboProjectRepository;
    private final EncounterRepository encounterRepository;
    private final HouseholdMapper householdMapper;
    private final HouseholdMemberMapper householdMemberMapper;
    private final HouseholdMigrationMapper householdMigrationMapper;
    private static final int ACTIVE_HOUSEHOLD_ADDRESS = 1;
    private static final int INACTIVE_HOUSEHOLD_ADDRESS = 0;
    private final EncounterService encounterService;
    private final UserService userService;
    private final static String HH_ASSESSMENT_FORM_CODE = "5f451d7d-213c-4478-b700-69a262667b89";


    public List<HouseholdDTO> getAllHouseholds() {
        return householdMapper.toHouseholdDTOS(householdRepository.findByCboProjectIdAndArchivedOrderByIdDesc(getCurrentCboProjectId(), UN_ARCHIVED));
    }

    public Household save(HouseholdDTO householdDTO) {
        //TODO: check if household exist... by what criteria???
        return saveOrUpdateHousehold(null, true, householdDTO);
    }

    public HouseholdDTO getHouseholdById(Long id) {
        Household household = getHousehold(id);
        List<HouseholdMember> householdMembers = household.getHouseholdMembers().stream()
                .sorted(Comparator.comparingInt(HouseholdMember::getHouseholdMemberType)) //start with caregiver
                .collect(Collectors.toList());
        List<HouseholdMigration> householdMigrations = household.getHouseholdMigrations();
        List<HouseholdMemberDTO> householdMemberDTOS = householdMemberMapper.toHouseholdMemberDTOS(householdMembers);
        List<HouseholdMigrationDTO> householdMigrationDTOS = householdMigrationMapper.toHouseholdMigrationDTOS(householdMigrations);

        Long householdMemberId = householdMemberDTOS.stream().findFirst().get().getId();
        HouseholdDTO householdDTO =  householdMapper.toHouseholdDTO(household, householdMemberDTOS, householdMigrationDTOS);

        Optional<Encounter> optionalEncounter = encounterRepository.findTopByFormCodeAndHouseholdMemberIdAndArchivedOrderByIdAsc(HH_ASSESSMENT_FORM_CODE, householdMemberId, UN_ARCHIVED);
        optionalEncounter.ifPresent(encounter -> {
            householdDTO.setAssessment(encounter.getFormData().stream().findFirst().get().getData());
        });
        return householdDTO;
    }

    public List<EncounterDTO> getEncounterByHouseholdId(Long householdId) {
        Household household = getHousehold(householdId);
        List<Encounter> encounters = household.getEncountersById().stream()
                .filter(encounter -> encounter.getArchived()!= null && encounter.getArchived()== UN_ARCHIVED)
                .map(encounter -> encounterService.addFirstNameAndLastNameAndFormNameToEncounter(encounter))
                .sorted(Comparator.comparing(Encounter::getId).reversed())
                .collect(Collectors.toList());
        return encounterMapper.toEncounterDTOS(encounters);
    }

    public Household update(Long id, HouseholdDTO householdDTO) {
        getHousehold(id);
        return saveOrUpdateHousehold(id, false, householdDTO);
    }

    public void delete(Long id) {
        Household houseHold = getHousehold(id);
        houseHold.setArchived(ARCHIVED);
        householdRepository.save(houseHold);
    }

    private Household saveOrUpdateHousehold(Long id, Boolean firstTime, HouseholdDTO householdDTO){
        List<FormData> formDataList = new ArrayList<>();
        Long currentCboProjectId = getCurrentCboProjectId();

        Household household = householdMapper.toHousehold(householdDTO);
        //For updates and saving
        if(id != null){
            household.setId(id);
        }

        household.setArchived(UN_ARCHIVED);
        household.setCboProjectId(currentCboProjectId);
        household.setStatus(1);
        household.setCboProjectId(currentCboProjectId);

        //save household
        household = householdRepository.save(household);

        HouseholdMember householdMember = null;

        if(householdDTO.getHouseholdMigrationDTOS() != null) {
            if(firstTime && householdDTO.getHouseholdMigrationDTOS().size() > 1){
                throw new IllegalTypeException(HouseholdMigration.class, "HouseholdMigration", "should not be > 1 for registration");
            }

            List<HouseholdMigration> householdMigrations = householdMigrationMapper.toHouseholdMigration(householdDTO.getHouseholdMigrationDTOS());
            for (HouseholdMigration householdMigration : householdMigrations) {
                if(householdMigration.getActive() == null){
                    householdMigration.setActive(ACTIVE_HOUSEHOLD_ADDRESS);}//only one address at registration of household
                householdMigration.setHouseholdId(household.getId());
                householdMigrationRepository.save(householdMigration);
            }
        }
        if(householdDTO.getHouseholdMemberDTO() != null){
            householdMember = householdMemberMapper.toHouseholdMember(householdDTO.getHouseholdMemberDTO());
            householdMember.setCboProjectId(currentCboProjectId);
            householdMember.setHouseholdId(household.getId());
            householdMember.setHouseholdMemberType(HOUSEHOLD_MEMBER_TYPE);

            householdMember = householdMemberRepository.save(householdMember);
        }
        if(firstTime) {
            //Encounter for assessment
            EncounterDTO encounterDTO = new EncounterDTO();
            encounterDTO.setDateEncounter(LocalDateTime.now());
            encounterDTO.setFormCode(HH_ASSESSMENT_FORM_CODE);
            encounterDTO.setHouseholdMemberId(householdMember.getId());
            encounterDTO.setHouseholdId(household.getId());
            encounterDTO.setCboProjectId(currentCboProjectId);

            //FormData for assessment
            FormData formData = new FormData();
            formData.setData(householdDTO.getAssessment());
            formData.setArchived(UN_ARCHIVED);
            formDataList.add(formData);

            //set the formData in encounter
            encounterDTO.setFormData(formDataList);
            //save encounter
            encounterService.save(encounterDTO);
        }
        return household;
    }

    public List<HouseholdMemberDTO> getHouseholdMembersByHouseholdId(Long id) {
        Household household = getHousehold(id);

        List<HouseholdMember> householdMembers = household.getHouseholdMembers().stream()
                .sorted(Comparator.comparingInt(HouseholdMember::getHouseholdMemberType))//sort by memberType
                .collect(Collectors.toList());
        return householdMemberMapper.toHouseholdMemberDTOS(householdMembers);
    }

    public List<HouseholdMigrationDTO> getHouseholdAddressesByHouseholdId(Long id) {
        Household household = getHousehold(id);
        List<HouseholdMigration> householdMigrations = household.getHouseholdMigrations().stream()
                .sorted(Comparator.comparingLong(HouseholdMigration::getId).reversed())//sort by id in descending/reversed order
                .collect(Collectors.toList());
        return householdMigrationMapper.toHouseholdMigrationDTOS(householdMigrations);
    }

    public List<HouseholdMigrationDTO> saveHouseholdMigration(Long id, HouseholdMigration householdMigration) {
        getHousehold(id);
        //Deactivating the old address
        List<HouseholdMigration> householdMigrations = householdMigrationRepository.findAllByHouseholdIdAndActive(id, ACTIVE_HOUSEHOLD_ADDRESS).stream()
                .map(householdMigration1 -> {householdMigration1.setActive(INACTIVE_HOUSEHOLD_ADDRESS); return householdMigration1;})
                .collect(Collectors.toList());

        householdMigration.setActive(ACTIVE_HOUSEHOLD_ADDRESS);
        householdMigration.setHouseholdId(id);
        householdMigration.setCboProjectId(userService.getUserWithRoles().get().getCurrentCboProjectId());
        householdMigrations.add(householdMigration);

        householdMigrations = householdMigrationRepository.saveAll(householdMigrations).stream()
                .sorted(Comparator.comparingLong(HouseholdMigration::getId).reversed())//sort by id in descending/reversed order
                .collect(Collectors.toList());

        return householdMigrationMapper.toHouseholdMigrationDTOS(householdMigrations);
    }

    private Long getCurrentCboProjectId(){
        Long currentCboId = userService.getUserWithRoles().get().getCurrentCboProjectId();
        return currentCboId;
    }

    private Household getHousehold(Long id){
        return householdRepository.findByIdAndCboProjectIdAndArchived(id, getCurrentCboProjectId(), UN_ARCHIVED)
                .orElseThrow(() -> new EntityNotFoundException(Household.class, "Id", id+""));
    }

    public Long getMaxHouseholdIdByOrganisation(Long id) {
        return householdMigrationRepository.findByWardId(id);
    }
}