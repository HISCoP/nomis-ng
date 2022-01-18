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
import org.nomisng.domain.entity.Flag;
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
                //TODO: complete
            }
            return householdMemberRepository.findAllByCboProjectIdAndArchivedOrderByIdDesc(currentCboProjectId, UN_ARCHIVED, pageable);
        }
        search = "%"+search+"%";
        return householdMemberRepository.findAllByCboProjectIdAndArchivedAndSearchParameterOrderByIdDesc(search, currentCboProjectId, UN_ARCHIVED, pageable);
    }

    public List<HouseholdMemberDTO> getAllHouseholdMembersFromPage(Page<HouseholdMember> householdMembersPage) {
        List<HouseholdMember> householdMembers = householdMembersPage.getContent().stream()
                .map(householdMember -> addMemberFlag(householdMember))
                .collect(Collectors.toList());
        return householdMemberMapper.toHouseholdDTOS(householdMembers);
    }

    public HouseholdMember addMemberFlag(HouseholdMember householdMember){
        List<Flag> flags = new ArrayList<>();

        householdMember.getMemberFlagsById().forEach(memberFlag -> {
            flags.add(memberFlag.getFlag());
        });
        householdMember.setFlags(flags);
        return householdMember;
    }

    public HouseholdMember save(HouseholdMemberDTO householdMemberDTO) {
        try {
            String details = mapper.writeValueAsString(householdMemberDTO.getDetails());
            firstName = this.getValueFromJsonField(details, "firstName");
            lastName = this.getValueFromJsonField(details, "lastName");
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

        //Getting the uniqueId without the serial number
        Long serialNumber = householdMemberRepository.findHouseholdMemberCountOfHousehold(household.getId());
        //int index = household.getUniqueId().lastIndexOf("/");
        //String householdUniqueId = household.getUniqueId().substring(0, index);

        //adding new serial number
        householdMemberDTO.setUniqueId(household.getUniqueId() + "/" +serialNumber+1);
        HouseholdMember householdMember = householdMemberMapper.toHouseholdMember(householdMemberDTO);

        Long currentCboProjectId = userService.getUserWithRoles().get().getCurrentCboProjectId();
        householdMember.setCboProjectId(currentCboProjectId);

        return householdMemberRepository.save(householdMember);
    }

    public HouseholdMemberDTO getHouseholdMemberById(Long id) {
        HouseholdMember householdMember = this.addMemberFlag(householdMemberRepository.findByIdAndArchived(id, UN_ARCHIVED)
                .orElseThrow(() -> new EntityNotFoundException(HouseholdMember.class, "Id", id+"")));

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
        householdMember.setId(id);
        return householdMemberRepository.save(householdMember);
    }

    public void delete(Long id) {
        HouseholdMember householdMember = householdMemberRepository.findByIdAndArchived(id, UN_ARCHIVED)
                .orElseThrow(() -> new EntityNotFoundException(HouseholdMember.class, "Id", id+""));
        householdMember.setArchived(ARCHIVED);
        householdMemberRepository.save(householdMember);
    }

    private String getValueFromJsonField(String details, String field){
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