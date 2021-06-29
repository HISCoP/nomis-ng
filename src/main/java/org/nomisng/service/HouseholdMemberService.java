package org.nomisng.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.controller.apierror.EntityNotFoundException;
import org.nomisng.domain.dto.HouseholdContactDTO;
import org.nomisng.domain.dto.HouseholdDTO;
import org.nomisng.domain.dto.HouseholdMemberDTO;
import org.nomisng.domain.entity.Household;
import org.nomisng.domain.entity.HouseholdContact;
import org.nomisng.domain.entity.HouseholdMember;
import org.nomisng.domain.mapper.HouseholdMemberMapper;
import org.nomisng.repository.HouseholdMemberRepository;
import org.nomisng.repository.HouseholdRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class HouseholdMemberService {
    private final HouseholdRepository householdRepository;
    private final HouseholdMemberRepository householdMemberRepository;
    private final HouseholdMemberMapper householdMemberMapper;
    private final HouseholdService householdService;

    public List<HouseholdMemberDTO> getAllHouseholdMembers() {
        return householdMemberMapper.toHouseholdDTOS(householdMemberRepository.findAll());
    }

    public HouseholdMember save(HouseholdMemberDTO householdMemberDTO) {
        householdRepository.findById(householdMemberDTO.getHouseholdId())
                .orElseThrow(() -> new EntityNotFoundException(Household.class, "Id", householdMemberDTO.getHouseholdId()+""));
        HouseholdMember householdMember = householdMemberMapper.toHouseholdMember(householdMemberDTO);

        return householdMemberRepository.save(householdMember);
    }

    public HouseholdMemberDTO getHouseholdMemberById(Long id) {
        HouseholdMember householdMember = householdMemberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(HouseholdMember.class, "Id", id+""));
       return householdMemberMapper.toHouseholdMemberDTO(householdMember);
    }

    public HouseholdDTO getHouseholdByHouseholdMemberId(Long id) {
        HouseholdMember householdMember = householdMemberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(HouseholdMember.class, "Id", id+""));
        return householdService.getHouseholdById(householdMember.getHouseholdId());
    }

    public HouseholdMember update(Long id, HouseholdMemberDTO householdMemberDTO) {
        householdMemberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(HouseholdMember.class, "Id", id+""));
        return householdMemberRepository.save(householdMemberMapper.toHouseholdMember(householdMemberDTO));
    }

    public Integer delete(Long id) {
        return null;
    }
}