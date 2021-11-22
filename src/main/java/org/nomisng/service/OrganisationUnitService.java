package org.nomisng.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
/*import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;*/
import org.nomisng.controller.apierror.EntityNotFoundException;
import org.nomisng.controller.apierror.RecordExistException;
import org.nomisng.domain.dto.OrganisationUnitDTO;
import org.nomisng.domain.entity.HouseholdMigration;
import org.nomisng.domain.entity.OrganisationUnit;
import org.nomisng.domain.entity.OrganisationUnitHierarchy;
import org.nomisng.domain.mapper.OrganisationUnitMapper;
import org.nomisng.repository.HouseholdMigrationRepository;
import org.nomisng.repository.OrganisationUnitHierarchyRepository;
import org.nomisng.repository.OrganisationUnitRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.nomisng.util.Constants.ArchiveStatus.ARCHIVED;
import static org.nomisng.util.Constants.ArchiveStatus.UN_ARCHIVED;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class OrganisationUnitService {
    private static final Long FIRST_ORG_LEVEL = 1L;
    public static final long WARD_LEVEL = 4L;
    private final OrganisationUnitRepository organisationUnitRepository;
    private final HouseholdMigrationRepository householdMigrationRepository;
    private final OrganisationUnitMapper organisationUnitMapper;
    private final OrganisationUnitHierarchyRepository organisationUnitHierarchyRepository;

    public OrganisationUnit save(OrganisationUnitDTO organisationUnitDTO) {
        Optional<OrganisationUnit> organizationOptional = organisationUnitRepository.findByNameAndParentOrganisationUnitIdAndArchived(organisationUnitDTO.getName(), organisationUnitDTO.getId(), UN_ARCHIVED);
        if(organizationOptional.isPresent())throw new RecordExistException(OrganisationUnit.class, "Name", organisationUnitDTO.getName() +"");
        final OrganisationUnit organisationUnit = organisationUnitMapper.toOrganisationUnit(organisationUnitDTO);

        OrganisationUnit organisationUnit1 = organisationUnitRepository.save(organisationUnit);
        Long level = organisationUnit1.getOrganisationUnitLevelId();
        List<OrganisationUnitHierarchy> organisationUnitHierarchies = new ArrayList<>();
        OrganisationUnit returnOrgUnit = organisationUnit1;

        Long parent_org_unit_id = 1L;
        while(parent_org_unit_id > 0){
            parent_org_unit_id = organisationUnit1.getParentOrganisationUnitId();
            organisationUnitHierarchies.add(new OrganisationUnitHierarchy(null, returnOrgUnit.getId(), organisationUnit1.getParentOrganisationUnitId(),
                    level, null, null, null));

            Optional<OrganisationUnit> organisationUnitOptional = organisationUnitRepository.findById(organisationUnit1.getParentOrganisationUnitId());
            if(organisationUnitOptional.isPresent()){
                organisationUnit1 = organisationUnitOptional.get();
            }
            --parent_org_unit_id;
        }
        organisationUnitHierarchyRepository.saveAll(organisationUnitHierarchies);
        return returnOrgUnit;
    }

    //TODO: work on this
    public OrganisationUnit update(Long id, OrganisationUnitDTO organisationUnitDTO) {
        Optional<OrganisationUnit> organizationOptional = organisationUnitRepository.findByIdAndArchived(id, UN_ARCHIVED);
        if(!organizationOptional.isPresent())throw new EntityNotFoundException(OrganisationUnit.class, "Id", id +"");
        final OrganisationUnit organisationUnit = organisationUnitMapper.toOrganisationUnit(organisationUnitDTO);

        organisationUnit.setId(id);
        return organisationUnitRepository.save(organisationUnit);
    }

    public Integer delete(Long id) {
        Optional<OrganisationUnit> organizationOptional = organisationUnitRepository.findByIdAndArchived(id, UN_ARCHIVED);
        if (!organizationOptional.isPresent())throw new EntityNotFoundException(OrganisationUnit.class, "Id", id +"");
        organizationOptional.get().setArchived(ARCHIVED);
        return organizationOptional.get().getArchived();
    }

    public OrganisationUnit getOrganizationUnit(Long id){
        Optional<OrganisationUnit> organizationOptional = organisationUnitRepository.findByIdAndArchived(id, UN_ARCHIVED);
        if (!organizationOptional.isPresent())throw new EntityNotFoundException(OrganisationUnit.class, "Id", id +"");
        return organizationOptional.get();
    }

    public List<OrganisationUnit> getOrganisationUnitByParentOrganisationUnitId(Long id) {
        return  organisationUnitRepository.findAllOrganisationUnitByParentOrganisationUnitIdAndArchived(id, UN_ARCHIVED);
    }

    public List<OrganisationUnit> getAllOrganizationUnit() {
        return organisationUnitRepository.findAllByArchivedOrderByIdAsc(UN_ARCHIVED);
    }

    public List<OrganisationUnit> getOrganisationUnitByParentOrganisationUnitIdAndOrganisationUnitLevelId(Long parentOrgUnitId, Long orgUnitLevelId) {
        OrganisationUnit parentOrganisationUnit = organisationUnitRepository.findByIdAndArchived(parentOrgUnitId, UN_ARCHIVED).orElseThrow(
                () -> new EntityNotFoundException(OrganisationUnit.class, "Parent OrganisationUnit", "invalid"));

        List<OrganisationUnit> organisationUnits = new ArrayList<>();
        organisationUnitRepository.findAllByParentOrganisationUnitIdAndOrganisationUnitLevelId(parentOrgUnitId, orgUnitLevelId).forEach(organisationUnit -> {
            organisationUnit.setParentOrganisationUnitName(parentOrganisationUnit.getName());
            organisationUnits.add(organisationUnit);
        });
        return organisationUnits;
    }

    public Page<OrganisationUnit> getOrganisationUnitByOrganisationUnitLevelId(Long organisationUnitLevelId, String orgUnitName, Pageable pageable) {
        if(orgUnitName != null && !orgUnitName.equalsIgnoreCase("*")){
            orgUnitName = "%"+orgUnitName+"%";
            return organisationUnitRepository.findAllByOrganisationByLevelAndName(organisationUnitLevelId, orgUnitName, pageable);
        }
        return organisationUnitRepository.findAllByOrganisationUnitLevelId(organisationUnitLevelId, pageable);
    }

    public List<OrganisationUnit> getOrganisationUnitByOrganisationUnitLevelIdPageContent(Page<OrganisationUnit> page) {
        List<OrganisationUnit> organisationUnits = new ArrayList<>();
        page.getContent().forEach(organisationUnit -> {
            Long orgUnitId = organisationUnit.getParentOrganisationUnitId();
            /*for(int i=0; i<2; i++) {
                Optional<OrganisationUnit> optionalOrganisationUnit = organisationUnitRepository.findByIdAndArchived(orgUnitId, UNARCHIVED);
                if(optionalOrganisationUnit.isPresent()){
                    if(organisationUnit.getParentOrganisationUnitName() == null) {
                        organisationUnit.setParentOrganisationUnitName(optionalOrganisationUnit.get().getName());
                    }else if(organisationUnit.getParentParentOrganisationUnitName() == null) {
                        organisationUnit.setParentParentOrganisationUnitName(optionalOrganisationUnit.get().getName());
                    }
                    orgUnitId = optionalOrganisationUnit.get().getParentOrganisationUnitId();
                }
            }*/
            organisationUnits.add(findOrganisationUnits(organisationUnit, orgUnitId));
        });
        return organisationUnits;
    }


        public Page<OrganisationUnitHierarchy> getOrganisationUnitHierarchies(Long parent_org_unit_id, Long org_unit_level_id, Pageable pageable){
        return organisationUnitHierarchyRepository.findAllByParentOrganisationUnitIdAndOrganisationUnitLevelId(parent_org_unit_id, org_unit_level_id, pageable);
    }

    public List<OrganisationUnitDTO> getOrganisationUnitSubsetByParentOrganisationUnitIdAndOrganisationUnitLevelId(Page<OrganisationUnitHierarchy> organisationUnitHierarchies) {
        List<OrganisationUnitDTO> organisationUnitDTOS = new ArrayList<>();
        organisationUnitHierarchies.forEach(organisationUnitHierarchy -> {
            OrganisationUnit organisationUnit = organisationUnitHierarchy.getOrganisationUnitByOrganisationUnitId();
            Long orgUnitId = organisationUnit.getParentOrganisationUnitId();

            if(organisationUnit.getOrganisationUnitLevelId() == WARD_LEVEL){
                organisationUnit.setHouseholdMaxCount(householdMigrationRepository.findByWardId(organisationUnit.getId()));
            }
            organisationUnitDTOS.add(organisationUnitMapper.toOrganisationUnitDTO(findOrganisationUnits(organisationUnit, orgUnitId)));
        });
        return organisationUnitDTOS;
    }

    public Page<OrganisationUnit> getAllOrganisationUnitByOrganisationUnitLevelId(Long organisationUnitLevelId, String orgUnitName, Pageable pageable) {
        if(orgUnitName != null && !orgUnitName.equalsIgnoreCase("*")){
            orgUnitName = "%"+orgUnitName;
            return organisationUnitRepository.findAllByOrganisationByLevelAndName(organisationUnitLevelId, orgUnitName, pageable);
        }

        return organisationUnitRepository.findAllByOrganisationUnitLevelId(organisationUnitLevelId, pageable);
    }

    /*public List getAll(){
        List orgList = new ArrayList();
        try {
            orgList = this.readDataFromExcelFile("C:\\Users\\Dell\\Documents\\PALLADIUM WORKS\\PALLADIUM WORKS\\IP_Facilities.xlsx");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return orgList;
    }

    public List<OrganisationUnitDTO> readDataFromExcelFile(String excelFilePath) throws IOException {

        List<OrganisationUnitExtraction> organisationUnitExtractions = new ArrayList<OrganisationUnitExtraction>();
        List<OrganisationUnitDTO> organisationUnitDTOS = new ArrayList<>();


        FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
        try {

            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

            Sheet firstSheet = workbook.getSheetAt(0);

            Iterator<Row> iterator = firstSheet.iterator();
            while (iterator.hasNext()) {
                Row nextRow = iterator.next();
                Iterator<Cell> cellIterator = nextRow.cellIterator();
                OrganisationUnitExtraction organisationUnitExtraction = new OrganisationUnitExtraction();
                OrganisationUnitDTO organisationUnitDTO = new OrganisationUnitDTO();

                while (cellIterator.hasNext()) {
                    Cell nextCell = cellIterator.next();
                    int columnIndex = nextCell.getColumnIndex();
                    String parentOrganisationUnitName = "";
                    switch (columnIndex) {
                        case 0:
                            organisationUnitExtraction.setOrganisationUnitName(String.valueOf(nextCell).trim());
                            //System.out.println(getCellValue(nextCell));
                            break;
                        case 1:
                            parentOrganisationUnitName = String.valueOf(nextCell).trim();
                            organisationUnitExtraction.setParentOrganisationUnitName(parentOrganisationUnitName);
                            //System.out.println(getCellValue(nextCell));
                            break;
                        case 2:
                            String parentParentOrganisationUnitName = String.valueOf(nextCell).trim();
                            organisationUnitExtraction.setParentParentOrganisationUnitName(parentParentOrganisationUnitName);
                            organisationUnitExtraction.setDescription("Facility in "+organisationUnitExtraction.getParentOrganisationUnitName());
                            Long id = organisationUnitRepository.findByOrganisationDetails(organisationUnitExtraction.getParentOrganisationUnitName(), parentParentOrganisationUnitName);
                            organisationUnitExtraction.setParentOrganisationUnitId(id);

                            organisationUnitDTO.setName(organisationUnitExtraction.getOrganisationUnitName());
                            organisationUnitDTO.setDescription(organisationUnitExtraction.getDescription());
                            organisationUnitDTO.setOrganisationUnitLevelId(4L);
                            organisationUnitDTO.setParentOrganisationUnitId(organisationUnitExtraction.getParentOrganisationUnitId());
                            save(organisationUnitDTO);
                            break;
                    }
                }
                organisationUnitDTOS.add(organisationUnitDTO);
                organisationUnitExtractions.add(organisationUnitExtraction);
            }
            inputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return organisationUnitDTOS;
    }

    private Object getCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case NUMERIC:
                return cell.getNumericCellValue();
        }
        return null;
    }*/

    private OrganisationUnit findOrganisationUnits(OrganisationUnit organisationUnit, Long orgUnitId){
        for(int i=0; i<2; i++) {
            Optional<OrganisationUnit> optionalOrganisationUnit = organisationUnitRepository.findByIdAndArchived(orgUnitId, UN_ARCHIVED);
            if(optionalOrganisationUnit.isPresent()){
                if(organisationUnit.getParentOrganisationUnitName() == null) {
                    organisationUnit.setParentOrganisationUnitName(optionalOrganisationUnit.get().getName());
                }else if(organisationUnit.getParentParentOrganisationUnitName() == null) {
                    organisationUnit.setParentParentOrganisationUnitName(optionalOrganisationUnit.get().getName());
                }
                orgUnitId = optionalOrganisationUnit.get().getParentOrganisationUnitId();
            }
        }
        return organisationUnit;
    }
}
