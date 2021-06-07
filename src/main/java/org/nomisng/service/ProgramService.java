package org.nomisng.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.controller.apierror.EntityNotFoundException;
import org.nomisng.domain.dto.ProgramDTO;
import org.nomisng.domain.entity.Form;
import org.nomisng.domain.entity.Program;
import org.nomisng.domain.mapper.ProgramMapper;
import org.nomisng.repository.ProgramRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ProgramService {
    public static final int ARCHIVED = 1;
    private final ProgramRepository programRepository;
    private final ProgramMapper programMapper;
    private static final int UN_ARCHIVED = 0;

    public Program save(ProgramDTO programDTO) {

        final Program program = programMapper.toProgramDTO(programDTO);
        if(program.getCode() == null) {
            program.setCode(UUID.randomUUID().toString());
        }
        if(program.getArchived() == null) {
            program.setArchived(UN_ARCHIVED);
        }
        return this.programRepository.save(program);
    }

    public List<Program> getAllPrograms(){
        return programRepository.findAllByArchivedIsNotOrderByIdDesc(ARCHIVED);
    }

    public List<Form> getFormByProgramId(Long programId){
        Optional<Program> programOptional = programRepository.findById(programId);
        if(!programOptional.isPresent() || programOptional.get().getArchived() == ARCHIVED) throw new EntityNotFoundException(Program.class, "Program Id", programId + "");
        List<Form> forms = programOptional.get().getFormByProgramCode().stream()
                .filter(form ->form.getArchived()!= null && form.getArchived()== UN_ARCHIVED)
                .sorted(Comparator.comparing(Form::getId).reversed())
                .collect(Collectors.toList());
        return forms;
    }

    public Integer delete(Long id) {
        Program program = programRepository.findByIdAndArchived(id, UN_ARCHIVED).orElseThrow(
                () -> new EntityNotFoundException(Program.class, "Program Id", id + ""));
        program.setArchived(ARCHIVED);
        programRepository.save(program);
        return program.getArchived();
    }

    public Program update(Long id, ProgramDTO programDTO) {
        Optional<Program> programOptional = programRepository.findById(id);
        if(!programOptional.isPresent())throw new EntityNotFoundException(Program.class, "Id", id +"");
        if(programDTO.getArchived() == null){
            programDTO.setArchived(UN_ARCHIVED);
        }
        final Program program = this.programMapper.toProgramDTO(programDTO);
        program.setId(id);
        return programRepository.save(program);
    }
}
