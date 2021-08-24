package org.nomisng.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.controller.apierror.EntityNotFoundException;
import org.nomisng.domain.dto.*;
import org.nomisng.domain.entity.*;
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
    private final EncounterRepository encounterRepository;
    private final HouseholdMapper householdMapper;
    private final HouseholdMemberMapper householdMemberMapper;
    private final HouseholdAddressMapper householdAddressMapper;
    private static final int ACTIVE_HOUSEHOLD_ADDRESS = 1;
    private static final int INACTIVE_HOUSEHOLD_ADDRESS = 0;

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
        List<HouseholdAddress> householdAddresses = household.getHouseholdAddresses();

        List<HouseholdMemberDTO> householdMemberDTOS = householdMemberMapper.toHouseholdMemberDTOS(householdMembers);
        List<HouseholdAddressDTO> householdAddressDTOS = householdAddressMapper.toHouseholdContactDTOS(householdAddresses);
        return householdMapper.toHouseholdDTO(household, householdMemberDTOS, householdAddressDTOS);
    }

    //TODO: still in progress
    public List<Encounter> getEncounterByHouseholdId(Long id) {
        //Household household = householdRepository.findByIdAndArchived(id, UN_ARCHIVED)
                //.orElseThrow(() -> new EntityNotFoundException(Household.class, "Id", id+""));
        return encounterRepository.findAllHouseholdMemberAndArchived(id, UN_ARCHIVED, UN_ARCHIVED);
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
        List<HouseholdAddress> updatedHouseholdAddresses = new ArrayList<>();

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
            List<HouseholdAddress> householdAddresses = householdAddressMapper.toHouseholdContacts(householdDTO.getHouseholdAddressDTOS());
            for (HouseholdAddress householdAddress : householdAddresses) {
                if(householdAddress.getActive() == null){householdAddress.setActive(ACTIVE_HOUSEHOLD_ADDRESS);}//only one address at registration of household
                householdAddress.setHouseholdId(household.getId());
                updatedHouseholdAddresses.add(householdAddress);
            }
        }
        if(householdDTO.getHouseholdMemberDTO() != null) {
            HouseholdMember householdMember = householdMemberMapper.toHouseholdMember(householdDTO.getHouseholdMemberDTO());

            householdMember.setHouseholdId(household.getId());
            updatedHouseholdMembers.add(householdMember);
        }
        householdAddressRepository.saveAll(updatedHouseholdAddresses);
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

        List<HouseholdAddress> householdAddresses = household.getHouseholdAddresses().stream()
                .sorted(Comparator.comparingLong(HouseholdAddress::getId).reversed())//sort by id in descending/reversed order
                .collect(Collectors.toList());
        return householdAddressMapper.toHouseholdContactDTOS(householdAddresses);
    }

    public List<HouseholdAddressDTO> saveHouseholdAddress(Long id, HouseholdAddress householdAddress) {
        List<HouseholdAddress> householdAddresses = new ArrayList<>();
        for (HouseholdAddress householdAddress1 : householdAddressRepository.findAll()) {
            householdAddress1.setActive(INACTIVE_HOUSEHOLD_ADDRESS);
            householdAddresses.add(householdAddress1);
        }
        householdAddress.setActive(ACTIVE_HOUSEHOLD_ADDRESS);
        householdAddress.setHouseholdId(id);
        householdAddresses.add(householdAddress);

        householdAddresses = householdAddressRepository.saveAll(householdAddresses).stream()
                .sorted(Comparator.comparingLong(HouseholdAddress::getId).reversed())//sort by id in descending/reversed order
                .collect(Collectors.toList());

        return householdAddressMapper.toHouseholdContactDTOS(householdAddresses);
    }
}