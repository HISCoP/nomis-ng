package org.nomisng.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.controller.apierror.EntityNotFoundException;
import org.nomisng.controller.apierror.RecordExistException;
import org.nomisng.domain.dto.EncounterDTO;
import org.nomisng.domain.dto.HouseholdDTO;
import org.nomisng.domain.dto.HouseholdMemberDTO;
import org.nomisng.domain.entity.Encounter;
import org.nomisng.domain.entity.Household;
import org.nomisng.domain.entity.HouseholdMember;
import org.nomisng.domain.mapper.EncounterMapper;
import org.nomisng.domain.mapper.HouseholdMapper;
import org.nomisng.domain.mapper.HouseholdMemberMapper;
import org.nomisng.repository.HouseholdMemberRepository;
import org.nomisng.repository.HouseholdRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.nomisng.util.Constants.ArchiveStatus.*;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class HouseholdMemberService {
    private final HouseholdMemberRepository householdMemberRepository;
    private final HouseholdRepository householdRepository;
    private final HouseholdMemberMapper householdMemberMapper;
    private final HouseholdMapper householdMapper;
    private final EncounterMapper encounterMapper;
    private final EncounterService encounterService;
    private final UserService userService;
    private ObjectMapper mapper = new ObjectMapper();
    private String firstName = "firstName";
    private String lastName = "lastName";



    public Page<HouseholdMember> getAllHouseholdMembersPage(String search, Integer memberType, Pageable pageable) {
        Long currentCboProjectId = userService.getUserWithRoles().get().getCurrentCboProjectId();
        if(search == null || search.equalsIgnoreCase("*")) {
            if(memberType != null && memberType > 0){

            }
            return householdMemberRepository.findAllByCboProjectIdAndArchivedOrderByIdDesc(currentCboProjectId, UN_ARCHIVED, pageable);
        }
        search = "%"+search+"%";
        return householdMemberRepository.findAllByCboProjectIdAndArchivedAndSearchParameterOrderByIdDesc(search, currentCboProjectId, UN_ARCHIVED, pageable);
    }

    public List<HouseholdMemberDTO> getAllHouseholdMembersFromPage(Page<HouseholdMember> householdMembersPage) {
        return householdMemberMapper.toHouseholdDTOS(householdMembersPage.getContent());
    }

    public HouseholdMember save(HouseholdMemberDTO householdMemberDTO) {
        try {
            String details = mapper.writeValueAsString(householdMemberDTO.getDetails());
            firstName = getDetailsInfo(details, "firstName");
            lastName = getDetailsInfo(details, "lastName");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        Optional<HouseholdMember> optionalHouseholdMember = householdMemberRepository.findByHouseholdIdAndFirstNameAndLastNameAndArchived
                (householdMemberDTO.getHouseholdId(), firstName, lastName, UN_ARCHIVED);
        optionalHouseholdMember.ifPresent(householdMember -> {
            throw new RecordExistException(HouseholdMember.class, firstName + " " + lastName, "already exist in household");
        });
        Household household = householdRepository.findById(householdMemberDTO.getHouseholdId())
                .orElseThrow(()-> new EntityNotFoundException(Household.class, "id", "" +householdMemberDTO.getHouseholdId()));
        Long serialNumber = householdMemberRepository.findHouseholdMemberCountOfHousehold(household.getId()) + 1;
        householdMemberDTO.setUniqueId(household.getUniqueId()+household.getSerialNumber() + "/" +serialNumber);
        HouseholdMember householdMember = householdMemberMapper.toHouseholdMember(householdMemberDTO);

        Long currentCboProjectId = userService.getUserWithRoles().get().getCurrentCboProjectId();

        householdMember.setCboProjectId(currentCboProjectId);

        return householdMemberRepository.save(householdMember);
    }

    public HouseholdMemberDTO getHouseholdMemberById(Long id) {
        HouseholdMember householdMember = householdMemberRepository.findByIdAndArchived(id, UN_ARCHIVED)
                .orElseThrow(() -> new EntityNotFoundException(HouseholdMember.class, "Id", id+""));
       return householdMemberMapper.toHouseholdMemberDTO(householdMember);
    }

    public HouseholdDTO getHouseholdByHouseholdMemberId(Long id) {
        HouseholdMember householdMember = householdMemberRepository.findByIdAndArchived(id, UN_ARCHIVED)
                .orElseThrow(() -> new EntityNotFoundException(HouseholdMember.class, "Id", id+""));
        return householdMapper.toHouseholdDTO(householdMember.getHouseholdByHouseholdId());
    }

    public List<EncounterDTO> getEncountersByHouseholdMemberId(Long id) {
        HouseholdMember householdMember = householdMemberRepository.findByIdAndArchived(id, UN_ARCHIVED)
                .orElseThrow(() -> new EntityNotFoundException(HouseholdMember.class, "Id", id+""));
        List<Encounter> encounters = householdMember.getEncounterByHouseholdMemberId().stream()
                .map(encounter -> encounterService.addFirstNameAndLastNameAndFormNameToEncounter(encounter))
                .filter(encounter -> encounter.getArchived() != null && encounter.getArchived()== UN_ARCHIVED)// get all unarchived
                .sorted(Comparator.comparing(Encounter::getId).reversed()) // by id reversed/descending order
                .collect(Collectors.toList());
        return encounterMapper.toEncounterDTOS(encounters);
    }

    public HouseholdMember update(Long id, HouseholdMemberDTO householdMemberDTO) {
        householdMemberRepository.findByIdAndArchived(id, UN_ARCHIVED)
                .orElseThrow(() -> new EntityNotFoundException(HouseholdMember.class, "Id", id+""));
        HouseholdMember householdMember = householdMemberMapper.toHouseholdMember(householdMemberDTO);
        householdMember.setArchived(UN_ARCHIVED);
        return householdMemberRepository.save(householdMemberMapper.toHouseholdMember(householdMemberDTO));
    }

    public void delete(Long id) {
        HouseholdMember householdMember = householdMemberRepository.findByIdAndArchived(id, UN_ARCHIVED)
                .orElseThrow(() -> new EntityNotFoundException(HouseholdMember.class, "Id", id+""));
        householdMember.setArchived(ARCHIVED);
        householdMemberRepository.save(householdMember);
    }

    private String getDetailsInfo(String details, String field){
        try {
            JsonNode tree = mapper.readTree(details).get(field);
            if(!tree.isNull()) {
                //remove trailing quotes and return
                return String.valueOf(tree).replaceAll("^\"+|\"+$", "");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}