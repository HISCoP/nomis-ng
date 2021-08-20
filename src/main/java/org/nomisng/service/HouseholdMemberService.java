package org.nomisng.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.controller.apierror.EntityNotFoundException;
import org.nomisng.controller.apierror.RecordExistException;
import org.nomisng.domain.dto.HouseholdContactDTO;
import org.nomisng.domain.dto.HouseholdDTO;
import org.nomisng.domain.dto.HouseholdMemberDTO;
import org.nomisng.domain.entity.Household;
import org.nomisng.domain.entity.HouseholdContact;
import org.nomisng.domain.entity.HouseholdMember;
import org.nomisng.domain.mapper.HouseholdMapper;
import org.nomisng.domain.mapper.HouseholdMemberMapper;
import org.nomisng.repository.HouseholdMemberRepository;
import org.nomisng.repository.HouseholdRepository;
import org.nomisng.util.Constants;
import org.nomisng.util.JsonUtil;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

import static org.nomisng.util.Constants.ArchiveStatus.*;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class HouseholdMemberService {
    private final HouseholdMemberRepository householdMemberRepository;
    private final HouseholdMemberMapper householdMemberMapper;
    private final HouseholdMapper householdMapper;
    private ObjectMapper mapper = new ObjectMapper();
    private String firstName = "firstName";
    private String lastName = "lastName";



    public List<HouseholdMemberDTO> getAllHouseholdMembers() {
        return householdMemberMapper.toHouseholdDTOS(householdMemberRepository.findAllByArchived(UN_ARCHIVED));
    }

    public HouseholdMember save(HouseholdMemberDTO householdMemberDTO) {
        try {
            String details = mapper.writeValueAsString(householdMemberDTO.getDetails());
            firstName = getDetailsInfo(details, firstName);
            lastName = getDetailsInfo(details, lastName);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        Optional<HouseholdMember> optionalHouseholdMember = householdMemberRepository.findByHouseholdIdAndFirstNameAndLastNameAndArchived
                (householdMemberDTO.getHouseholdId(), firstName, lastName, UN_ARCHIVED);
        optionalHouseholdMember.ifPresent(householdMember -> {
            throw new RecordExistException(HouseholdMember.class, firstName + " " + lastName, "already exist in household");
        });
        HouseholdMember householdMember = householdMemberMapper.toHouseholdMember(householdMemberDTO);

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