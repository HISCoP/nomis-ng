package org.nomisng.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.controller.apierror.EntityNotFoundException;
import org.nomisng.controller.apierror.RecordExistException;
import org.nomisng.domain.dto.FlagDTO;
import org.nomisng.domain.dto.HouseholdMemberDTO;
import org.nomisng.domain.entity.*;
import org.nomisng.domain.mapper.FlagMapper;
import org.nomisng.repository.FlagRepository;
import org.nomisng.repository.FormFlagRepository;
import org.nomisng.repository.MemberFlagRepository;
import org.nomisng.util.OperatorType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.nomisng.util.Constants.ArchiveStatus.UN_ARCHIVED;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class FlagService {
    public static final int STATUS = 1;
    private final FlagRepository flagRepository;
    private final FlagMapper flagMapper;
    private final MemberFlagRepository memberFlagRepository;
    private final FormFlagRepository formFlagRepository;
    private final ObjectMapper mapper;

    public Flag save(FlagDTO flagDTO) {
        Optional<Flag> optionalFlag = flagRepository.findByNameAndFieldNameAndFieldValueAndArchived(flagDTO.getName(),
                flagDTO.getFieldName(), flagDTO.getFieldValue(), UN_ARCHIVED);
        if (optionalFlag.isPresent()) throw new RecordExistException(Flag.class, "Name", flagDTO.getName() + " with " + flagDTO.getFieldValue());
        return flagRepository.save(flagMapper.toFlag(flagDTO));
    }


    public Flag update(Long id, FlagDTO flagDTO) {
        flagRepository.findByIdAndArchived(id, UN_ARCHIVED).orElseThrow(() -> new EntityNotFoundException(Flag.class, "Id", id + ""));

        flagDTO.setId(id);
        return flagRepository.save(flagMapper.toFlag(flagDTO));
    }


    public void checkForAndSaveMemberFlag(Long memberId, Object object, List<FormFlag> formFlags){
        formFlags.forEach(formFlag -> {
            int flagDataType = formFlag.getFlag().getDatatype();// 0 - string, 1 - application codeset, 2 - integer
            int type = formFlag.getFlag().getType(); // 2 - form level flag, 3 is form data level flag
            String fieldName = formFlag.getFlag().getFieldName().trim();
            String operator = formFlag.getFlag().getOperator().trim();
            Boolean continuous = formFlag.getFlag().getContinuous();
            String formFlagFieldValue = formFlag.getFlag().getFieldValue().replaceAll("\\s", "").trim();
            JsonNode tree;
            String field = "";
            Integer fieldIntegerValue = 0;
            Integer formFlagFieldIntegerValue = 0;
            OperatorType operatorType = OperatorType.from(operator);

            try {
                //if form level flag
                if(type == 2){
                    this.saveMemberFlag(memberId, formFlag.getFlagId());
                }
                //form data level flag
                else {

                    //if not application code set but is string
                    if (flagDataType == 0) {
                        tree = mapper.readTree(object.toString()).get(fieldName);
                        field = String.valueOf(tree).replaceAll("^\"+|\"+$", "");
                        if (formFlagFieldValue.equalsIgnoreCase(field)) {
                            this.saveMemberFlag(memberId, formFlag.getFlagId());
                        }

                        //if application code set
                    } else if (flagDataType == 1) {
                        tree = mapper.readTree(object.toString()).get(fieldName);
                        JsonNode jsonNode = tree.get("display");
                        field = String.valueOf(jsonNode).replaceAll("^\"+|\"+$", "");
                        if (formFlagFieldValue.equalsIgnoreCase(field)) {
                            this.saveMemberFlag(memberId, formFlag.getFlagId());
                        }
                    }// If integer
                    else if (flagDataType == 2) {
                        tree = mapper.readTree(object.toString()).get(fieldName);
                        //removing extra quotes
                        field = String.valueOf(tree).replaceAll("^\"+|\"+$", "");
                        fieldIntegerValue = Integer.valueOf(field);
                        formFlagFieldIntegerValue = Integer.valueOf(formFlagFieldValue);

                        if (operator.equalsIgnoreCase("equal_to")) {
                            if (formFlagFieldValue.equalsIgnoreCase(field)) {
                                saveMemberFlag(memberId, formFlag.getFlagId());
                            } else if (continuous) {
                                memberFlagRepository.findByMemberIdAndFlagId(memberId, formFlag.getFlagId()).ifPresent(memberFlag -> {
                                    memberFlagRepository.delete(memberFlag);
                                });
                            }
                        } else if (operator.equalsIgnoreCase("greater_than")) {
                            if (fieldIntegerValue > formFlagFieldIntegerValue) {
                                saveMemberFlag(memberId, formFlag.getFlagId());
                            } else if (continuous) {
                                memberFlagRepository.findByMemberIdAndFlagId(memberId, formFlag.getFlagId()).ifPresent(memberFlag -> {
                                    memberFlagRepository.delete(memberFlag);
                                });
                            }
                        } else if (operator.equalsIgnoreCase("less_than")) {
                            if (fieldIntegerValue < formFlagFieldIntegerValue) {
                                saveMemberFlag(memberId, formFlag.getFlagId());
                            } else if (continuous) {
                                findByMemberIdAndFlagIdAndDelete(memberId, formFlag.getFlagId());
                            }
                        } else if (operator.equalsIgnoreCase("greater_than_or_equal_to")) {
                            if (fieldIntegerValue >= formFlagFieldIntegerValue) {
                                saveMemberFlag(memberId, formFlag.getFlagId());
                            } else if (continuous) {
                                findByMemberIdAndFlagIdAndDelete(memberId, formFlag.getFlagId());
                            }
                        } else if (operator.equalsIgnoreCase("less_than_or_equal_to")) {
                            if (fieldIntegerValue <= formFlagFieldIntegerValue) {
                                saveMemberFlag(memberId, formFlag.getFlagId());
                            } else if (continuous) {
                                findByMemberIdAndFlagIdAndDelete(memberId, formFlag.getFlagId());
                            }
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        });

    }

    //Flag operation
    public List<Form> setAndGetFormListForFlagOperation(HouseholdMemberDTO householdMemberDTO, Form form, List<Form> forms){
        //Get forms flags are applied to
        List<FormFlag> formFlags = formFlagRepository.findByFormCodeAndStatusAndArchived(form.getCode(), STATUS, UN_ARCHIVED);
        List<Flag> memberFlags = new ArrayList<>();

        //check if formFlag is empty
        if(formFlags.isEmpty()){
            forms.add(form);
            return forms;
        } else {
            //check for member flag
            formFlags.forEach(formFlag -> {
                householdMemberDTO.getFlags().forEach(flag -> {
                    if(formFlag.getFlagId() == flag.getId()){
                        //Temporary solution to age and recency testing
                        memberFlags.add(flag);
                    }
                });
            });
            if(memberFlags.size() == formFlags.size()){
                forms.add(form);
                return forms;
            }
        }
        return forms;
    }

    private void saveMemberFlag(Long memberId, Long flagId){
        MemberFlag memberFlag = new MemberFlag();
        //set memberFlag attributes
        memberFlag.setFlagId(flagId);
        memberFlag.setMemberId(memberId);
        List<MemberFlag> memberFlags = memberFlagRepository.findAllByMemberId(memberId);
        Flag flag = flagRepository.findByIdAndArchived(flagId, UN_ARCHIVED).get();
        //Check for opposites or similarities in flag field name & delete
        memberFlags.forEach(memberFlag1 -> {
            if(memberFlag1.getFlag().getFieldName().equalsIgnoreCase(flag.getFieldName()) &&
                    !memberFlag1.getFlag().getFieldValue().equalsIgnoreCase(flag.getFieldValue())) {
                memberFlagRepository.delete(memberFlag1);
                return;
            }
        });
        if (!memberFlagRepository.findByMemberIdAndFlagId(memberId, flagId).isPresent()) {
            memberFlagRepository.save(memberFlag);
            return;
        }
    }

    Integer getAge(Object object){
        try {
            final JsonNode tree = mapper.readTree(object.toString()).get("dob");
            String dob = String.valueOf(tree).replaceAll("^\"+|\"+$", "");

            if (dob != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                //convert String to LocalDate
                LocalDate localDate = LocalDate.parse(dob, formatter);
                Period period = Period.between(localDate, LocalDate.now());
                return period.getYears();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private void findByMemberIdAndFlagIdAndDelete(Long memberId, Long flagId){
        memberFlagRepository.findByMemberIdAndFlagId(memberId, flagId).ifPresent(memberFlag -> {
            memberFlagRepository.delete(memberFlag);
        });
    }
}
