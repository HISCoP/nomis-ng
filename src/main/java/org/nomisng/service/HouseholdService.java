package org.nomisng.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.controller.apierror.EntityNotFoundException;
import org.nomisng.domain.dto.*;
import org.nomisng.domain.entity.*;
import org.nomisng.domain.mapper.EncounterMapper;
import org.nomisng.domain.mapper.HouseholdAddressMapper;
import org.nomisng.domain.mapper.HouseholdMapper;
import org.nomisng.domain.mapper.HouseholdMemberMapper;
import org.nomisng.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import static org.nomisng.util.Constants.ArchiveStatus.*;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class HouseholdService {
    private final HouseholdRepository householdRepository;
    private final HouseholdMemberRepository householdMemberRepository;
    private final HouseholdAddressRepository householdAddressRepository;
    private final EncounterMapper encounterMapper;
    private final HouseholdMapper householdMapper;
    private final HouseholdMemberMapper householdMemberMapper;
    private final HouseholdAddressMapper householdAddressMapper;
    private static final int ACTIVE_HOUSEHOLD_ADDRESS = 1;
    private static final int INACTIVE_HOUSEHOLD_ADDRESS = 0;
    private final EncounterService encounterService;

    private Long organisationUnitId = 1L;

    public List<HouseholdDTO> getAllHouseholds() {
        return householdMapper.toHouseholdDTOS(householdRepository.findAllByArchivedOrderByIdDesc(UN_ARCHIVED));
    }

    public Household save(HouseholdDTO householdDTO) {
        //TODO: check if household exist... by what criteria???
        if(householdDTO.getHouseholdMemberDTO().getHouseholdMemberType() == null){householdDTO.getHouseholdMemberDTO().setHouseholdMemberType(0);}
        return saveOrUpdateHousehold(null, householdDTO);
    }

    public HouseholdDTO getHouseholdById(Long id) {
        Household household = householdRepository.findByIdAndArchived(id, UN_ARCHIVED)
                .orElseThrow(() -> new EntityNotFoundException(Household.class, "Id", id+""));
        List<HouseholdMember> householdMembers = household.getHouseholdMembers().stream()
                .sorted(Comparator.comparingInt(HouseholdMember::getHouseholdMemberType))
                .collect(Collectors.toList());
        List<HouseholdMigration> householdMigrations = household.getHouseholdMigrations();

        List<HouseholdMemberDTO> householdMemberDTOS = householdMemberMapper.toHouseholdMemberDTOS(householdMembers);
        List<HouseholdAddressDTO> householdAddressDTOS = householdAddressMapper.toHouseholdContactDTOS(householdMigrations);
        return householdMapper.toHouseholdDTO(household, householdMemberDTOS, householdAddressDTOS);
    }

    public List<EncounterDTO> getEncounterByHouseholdId(Long householdId) {
        Household household = householdRepository.findByIdAndArchived(householdId, UN_ARCHIVED)
                .orElseThrow(() -> new EntityNotFoundException(Household.class, "householdId", householdId+""));

        List<Encounter> encounters = household.getEncountersById().stream()
                .filter(encounter ->encounter.getArchived()!= null && encounter.getArchived()== UN_ARCHIVED)
                .map(encounter -> encounterService.addFirstNameAndLastNameAndFormNameToEncounter(encounter))
                .sorted(Comparator.comparing(Encounter::getId).reversed())
                .collect(Collectors.toList());

        return encounterMapper.toEncounterDTOS(encounters);
    }

    public Household update(Long id, HouseholdDTO householdDTO) {
        householdRepository.findByIdAndArchived(id, UN_ARCHIVED)
                .orElseThrow(() -> new EntityNotFoundException(Household.class, "Id", id+""));
        return saveOrUpdateHousehold(id, householdDTO);
    }

    public void delete(Long id) {
        Household houseHold = householdRepository.findByIdAndArchived(id, UN_ARCHIVED)
                .orElseThrow(() -> new EntityNotFoundException(Household.class, "Id", id+""));
        houseHold.setArchived(ARCHIVED);
        householdRepository.save(houseHold);
    }

    private Household saveOrUpdateHousehold( Long id, HouseholdDTO householdDTO){
        List<HouseholdMember> updatedHouseholdMembers = new ArrayList<>();
        List<HouseholdMigration> updatedHouseholdMigrations = new ArrayList<>();

        Household household = householdMapper.toHousehold(householdDTO);
        //For updates and saving
        if(id != null){
            household.setId(id);
        }
        household.setArchived(UN_ARCHIVED);
        household.setCboId(organisationUnitId);
        household.setStatus(1);
        household = householdRepository.save(household);

        if(householdDTO.getHouseholdAddressDTOS() != null) {
            List<HouseholdMigration> householdMigrations = householdAddressMapper.toHouseholdContacts(householdDTO.getHouseholdAddressDTOS());
            for (HouseholdMigration householdMigration : householdMigrations) {
                if(householdMigration.getActive() == null){
                    householdMigration.setActive(ACTIVE_HOUSEHOLD_ADDRESS);}//only one address at registration of household
                householdMigration.setHouseholdId(household.getId());
                updatedHouseholdMigrations.add(householdMigration);
            }
        }
        if(householdDTO.getHouseholdMemberDTO() != null) {
            HouseholdMember householdMember = householdMemberMapper.toHouseholdMember(householdDTO.getHouseholdMemberDTO());

            householdMember.setHouseholdId(household.getId());
            updatedHouseholdMembers.add(householdMember);
        }
        householdAddressRepository.saveAll(updatedHouseholdMigrations);
        householdMemberRepository.saveAll(updatedHouseholdMembers);
        return household;
    }

    public List<HouseholdMemberDTO> getHouseholdMembersByHouseholdId(Long id) {
        Household household = householdRepository.findByIdAndArchived(id, UN_ARCHIVED)
                .orElseThrow(() -> new EntityNotFoundException(Household.class, "Id", id+""));

        List<HouseholdMember> householdMembers = household.getHouseholdMembers().stream()
                .sorted(Comparator.comparingInt(HouseholdMember::getHouseholdMemberType))//sort by memberType
                .collect(Collectors.toList());
        return householdMemberMapper.toHouseholdMemberDTOS(householdMembers);
    }

    public List<HouseholdAddressDTO> getHouseholdAddressesByHouseholdId(Long id) {
        Household household = householdRepository.findByIdAndArchived(id, UN_ARCHIVED)
                .orElseThrow(() -> new EntityNotFoundException(Household.class, "Id", id+""));

        List<HouseholdMigration> householdMigrations = household.getHouseholdMigrations().stream()
                .sorted(Comparator.comparingLong(HouseholdMigration::getId).reversed())//sort by id in descending/reversed order
                .collect(Collectors.toList());
        return householdAddressMapper.toHouseholdContactDTOS(householdMigrations);
    }

    public List<HouseholdAddressDTO> saveHouseholdAddress(Long id, HouseholdMigration householdMigration) {
        List<HouseholdMigration> householdMigrations = new ArrayList<>();
        for (HouseholdMigration householdMigration1 : householdAddressRepository.findAll()) {
            householdMigration1.setActive(INACTIVE_HOUSEHOLD_ADDRESS);
            householdMigrations.add(householdMigration1);
        }
        householdMigration.setActive(ACTIVE_HOUSEHOLD_ADDRESS);
        householdMigration.setHouseholdId(id);
        householdMigrations.add(householdMigration);

        householdMigrations = householdAddressRepository.saveAll(householdMigrations).stream()
                .sorted(Comparator.comparingLong(HouseholdMigration::getId).reversed())//sort by id in descending/reversed order
                .collect(Collectors.toList());

        return householdAddressMapper.toHouseholdContactDTOS(householdMigrations);
    }
}