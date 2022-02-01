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
import org.nomisng.util.FlagOperatorType;
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
    private static final int STATUS = 1;
    private static final int FORM_LEVEL_FLAG = 2;
    private static final int STRING_FLAG_DATA_TYPE = 0;
    private static final int APPLICATION_CODESET_FLAG_DATA_TYPE = 1;
    private static final int INTEGER_FLAG_DATA_TYPE = 2;
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

    public void saveFormLevelFlag(Long memberId, Long flagId){

    }


    public void checkForAndSaveMemberFlag(Long householdId, Long householdMemberId, Object object, List<FormFlag> formFlags){
        formFlags.forEach(formFlag -> {
            int flagDataType = formFlag.getFlag().getDatatype();// 0 - string, 1 - application codeset, 2 - integer, 3 - form level flag
            int flagType = formFlag.getFlag().getType(); // 2 - form level flag, 3 is form data level flag
            String fieldName = formFlag.getFlag().getFieldName().trim();
            String operator = formFlag.getFlag().getOperator().trim();
            Boolean continuous = formFlag.getFlag().getContinuous();
            String formFlagFieldValue = formFlag.getFlag().getFieldValue().trim();
            JsonNode tree;
            String field = "";
            Integer fieldIntegerValue = 0;
            Integer formFlagFieldIntegerValue = 0;
            FlagOperatorType flagOperatorType = FlagOperatorType.from(operator);

            try {
                switch (flagType) {
                    //if form level flag
                    case FORM_LEVEL_FLAG:
                        this.saveMemberFlag(householdId, householdMemberId, formFlag.getFlagId());
                        break;
                    //form data level flag
                    default:
                        //if not application code set but is string
                        switch (flagDataType) {
                            case STRING_FLAG_DATA_TYPE:
                                tree = mapper.readTree(object.toString()).get(fieldName);
                                field = String.valueOf(tree).replaceAll("^\"+|\"+$", "");
                                if (formFlagFieldValue.equalsIgnoreCase(field)) {
                                    this.saveMemberFlag(householdId, householdMemberId, formFlag.getFlagId());
                                }
                                break;
                            //if application code set
                            case APPLICATION_CODESET_FLAG_DATA_TYPE:
                                tree = mapper.readTree(object.toString()).get(fieldName);
                                JsonNode jsonNode = tree.get("display");
                                field = String.valueOf(jsonNode).replaceAll("^\"+|\"+$", "");
                                if (formFlagFieldValue.equalsIgnoreCase(field)) {
                                    this.saveMemberFlag(householdId, householdMemberId, formFlag.getFlagId());
                                }
                                break;
                            // If integer
                            case INTEGER_FLAG_DATA_TYPE:
                                tree = mapper.readTree(object.toString()).get(fieldName);
                                //removing extra quotes
                                field = String.valueOf(tree).replaceAll("^\"+|\"+$", "");
                                fieldIntegerValue = Integer.valueOf(field);
                                formFlagFieldIntegerValue = Integer.valueOf(formFlagFieldValue);

                                switch (flagOperatorType) {
                                    case EQUAL_T0:
                                        if (formFlagFieldValue.equalsIgnoreCase(field)) {
                                            saveMemberFlag(householdId, householdMemberId, formFlag.getFlagId());
                                        } else if (continuous) {
                                            memberFlagRepository.findByHouseholdIdAndHouseholdMemberIdAndFlagId(householdId, householdMemberId, formFlag.getFlagId()).ifPresent(memberFlag -> {
                                                memberFlagRepository.delete(memberFlag);
                                            });
                                        }
                                        break;
                                    case GREATER_THAN:
                                        if (fieldIntegerValue > formFlagFieldIntegerValue) {
                                            saveMemberFlag(householdId, householdMemberId, formFlag.getFlagId());
                                        } else if (continuous) {
                                            memberFlagRepository.findByHouseholdIdAndHouseholdMemberIdAndFlagId(householdId, householdMemberId, formFlag.getFlagId()).ifPresent(memberFlag -> {
                                                memberFlagRepository.delete(memberFlag);
                                            });
                                        }
                                        break;
                                    case LESS_THAN:
                                        if (fieldIntegerValue < formFlagFieldIntegerValue) {
                                            saveMemberFlag(householdId, householdMemberId, formFlag.getFlagId());
                                        } else if (continuous) {
                                            findByHouseholdIdAndHouseMemberIdAndFlagIdAndDelete(householdId, householdMemberId, formFlag.getFlagId());
                                        }
                                        break;
                                    case GREATER_THAN_OR_EQUAL_TO:
                                        if (fieldIntegerValue >= formFlagFieldIntegerValue) {
                                            saveMemberFlag(householdId, householdMemberId, formFlag.getFlagId());
                                        } else if (continuous) {
                                            findByHouseholdIdAndHouseMemberIdAndFlagIdAndDelete(householdId, householdMemberId, formFlag.getFlagId());
                                        }
                                        break;
                                    case LESS_THAN_OR_EQUAL_TO:
                                        if (fieldIntegerValue <= formFlagFieldIntegerValue) {
                                            saveMemberFlag(householdId, householdMemberId, formFlag.getFlagId());
                                        } else if (continuous) {
                                            findByHouseholdIdAndHouseMemberIdAndFlagIdAndDelete(householdId, householdMemberId, formFlag.getFlagId());
                                        }
                                        break;
                            }
                        }
                    }

            }catch (Exception e){
                e.printStackTrace();
            }
        });

    }

    //Flag operation
    public List<Form> applyingFormsToBeneficiariesFlags(HouseholdMemberDTO householdMemberDTO, Form form, List<Form> forms){
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

    private void saveMemberFlag(Long householdId, Long householdMemberId, Long flagId){
        MemberFlag memberFlag = new MemberFlag();
        //set memberFlag attributes
        memberFlag.setFlagId(flagId);
        memberFlag.setHouseholdId(householdId);
        memberFlag.setHouseholdMemberId(householdMemberId);
        List<MemberFlag> memberFlags = memberFlagRepository.findAllByHouseholdIdAndHouseholdMemberId(householdMemberId, householdId);
        Flag flag = flagRepository.findByIdAndArchived(flagId, UN_ARCHIVED).get();
        //Check for opposites or similarities in flag field name & delete
        memberFlags.forEach(memberFlag1 -> {
            if(memberFlag1.getFlag().getFieldName().equalsIgnoreCase(flag.getFieldName()) &&
                    !memberFlag1.getFlag().getFieldValue().equalsIgnoreCase(flag.getFieldValue())) {
                memberFlagRepository.delete(memberFlag1);
                return;
            }
        });
        if (!memberFlagRepository.findByHouseholdIdAndHouseholdMemberIdAndFlagId(householdId, householdMemberId, flagId).isPresent()) {
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

    private void findByHouseholdIdAndHouseMemberIdAndFlagIdAndDelete(Long householdId, Long householdMemberId, Long flagId){
        memberFlagRepository.findByHouseholdIdAndHouseholdMemberIdAndFlagId(householdId, householdMemberId, flagId).ifPresent(memberFlag -> {
            memberFlagRepository.delete(memberFlag);
        });
    }
}
