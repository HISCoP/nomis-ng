package org.nomisng.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.controller.apierror.EntityNotFoundException;
import org.nomisng.domain.dto.HouseholdContactDTO;
import org.nomisng.domain.dto.HouseholdDTO;
import org.nomisng.domain.dto.HouseholdMemberDTO;
import org.nomisng.domain.dto.VisitDTO;
import org.nomisng.domain.entity.Household;
import org.nomisng.domain.entity.HouseholdContact;
import org.nomisng.domain.entity.HouseholdMember;
import org.nomisng.domain.entity.Visit;
import org.nomisng.domain.mapper.HouseholdContactMapper;
import org.nomisng.domain.mapper.HouseholdMapper;
import org.nomisng.domain.mapper.HouseholdMemberMapper;
import org.nomisng.domain.mapper.VisitMapper;
import org.nomisng.repository.HouseholdContactRepository;
import org.nomisng.repository.HouseholdMemberRepository;
import org.nomisng.repository.HouseholdRepository;
import org.nomisng.repository.VisitRepository;
import org.nomisng.util.Constants;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class HouseholdService {
    private final HouseholdRepository householdRepository;
    private final HouseholdMemberRepository householdMemberRepository;
    private final HouseholdContactRepository householdContactRepository;
    private final HouseholdMapper householdMapper;
    private final HouseholdMemberMapper householdMemberMapper;
    private final HouseholdContactMapper householdContactMapper;
    //private Long organisationUnitId = 1L;

    public List<HouseholdDTO> getAllHouseholds() {
        return householdMapper.toHouseholdDTOS(householdRepository.findAllByArchived(Constants.ArchiveStatus.UN_ARCHIVED));
    }

    public Household save(HouseholdDTO householdDTO) {
        //TODO: check if household exist... by what criteria???

        return saveOrUpdateHousehold(null, householdDTO);
    }

    public HouseholdDTO getHouseholdById(Long id) {
        Household household = householdRepository.findByIdAndArchived(id, Constants.ArchiveStatus.UN_ARCHIVED)
                .orElseThrow(() -> new EntityNotFoundException(Household.class, "Id", id+""));
        List<HouseholdMember> householdMembers = household.getHouseholdMembers();
        List<HouseholdContact> householdContacts = household.getHouseholdContacts();

        List<HouseholdMemberDTO> householdMemberDTOS = householdMemberMapper.toHouseholdMemberDTOS(householdMembers);
        List<HouseholdContactDTO> householdContactDTOS = householdContactMapper.toHouseholdContactDTOS(householdContacts);

       return householdMapper.toHouseholdDTO(household, householdMemberDTOS, householdContactDTOS);
    }




    public Household update(Long id, HouseholdDTO householdDTO) {
        householdRepository.findByIdAndArchived(id, Constants.ArchiveStatus.UN_ARCHIVED)
                .orElseThrow(() -> new EntityNotFoundException(Household.class, "Id", id+""));

        return saveOrUpdateHousehold(id, householdDTO);
    }

    public void delete(Long id) {
        Household houseHold = householdRepository.findByIdAndArchived(id, Constants.ArchiveStatus.UN_ARCHIVED)
                .orElseThrow(() -> new EntityNotFoundException(Household.class, "Id", id+""));
        houseHold.setArchived(Constants.ArchiveStatus.ARCHIVED);
        householdRepository.save(houseHold);
    }

    private Household saveOrUpdateHousehold( Long id, HouseholdDTO householdDTO){
        List<HouseholdMember> updatedHouseholdMembers = new ArrayList<>();
        List<HouseholdContact> updatedHouseholdContacts = new ArrayList<>();

        Household household = householdMapper.toHousehold(householdDTO);
        //For updates and saving
        if(id != null){
            household.setId(id);
        }
        household = householdRepository.save(household);

        if(householdDTO.getHouseholdContactDTOS() != null) {
            List<HouseholdContact> householdContacts = householdContactMapper.toHouseholdContacts(householdDTO.getHouseholdContactDTOS());
            for (HouseholdContact householdContact : householdContacts) {
                householdContact.setHouseholdId(household.getId());
                updatedHouseholdContacts.add(householdContact);
            }
        }
        if(householdDTO.getHouseholdMemberDTO() != null) {
            HouseholdMember householdMember = householdMemberMapper.toHouseholdMember(householdDTO.getHouseholdMemberDTO());

            householdMember.setHouseholdId(household.getId());
            updatedHouseholdMembers.add(householdMember);

        }

        householdContactRepository.saveAll(updatedHouseholdContacts);
        householdMemberRepository.saveAll(updatedHouseholdMembers);
        return household;
    }
}