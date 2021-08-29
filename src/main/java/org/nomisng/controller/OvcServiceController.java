package org.nomisng.controller;


import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.nomisng.domain.dto.OvcServiceDTO;
import org.nomisng.domain.entity.Domain;
import org.nomisng.domain.entity.OvcService;
import org.nomisng.service.OvcServiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ovc-services")
public class OvcServiceController {
    private final OvcServiceService ovcServiceService;

    @PostMapping
    public ResponseEntity<OvcService> save(@RequestBody OvcServiceDTO ovcServiceDTO) {
        return ResponseEntity.ok(ovcServiceService.save(ovcServiceDTO));
    }

    @PutMapping("/{id}")
    public OvcService update(@PathVariable Long id, @RequestBody OvcServiceDTO ovcServiceDTO) {
        return ovcServiceService.update(id, ovcServiceDTO);
    }

    @GetMapping("{id}/domain")
    public ResponseEntity<Domain> getDomainByOvcServiceId(@PathVariable Long id) {
        return ResponseEntity.ok(ovcServiceService.getDomainByOvcServiceId(id));
    }

    /*@GetMapping("{id}")
    public ResponseEntity<OvcServiceDTO> getOvcServiceByIdOrServiceType(@PathVariable Long id) {
        return ResponseEntity.ok(ovcServiceService.getOvcServiceById(id));
    }*/

    @GetMapping("{serviceType}")
    public ResponseEntity<List<OvcServiceDTO>> getAllOvcServices(@PathVariable(value = "serviceType", required = false) Integer serviceType) {
        if(serviceType != 0){
            return ResponseEntity.ok(ovcServiceService.getOvcServiceByServiceType(serviceType));
        }
        return ResponseEntity.ok(ovcServiceService.getAllOvcServices());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        ovcServiceService.delete(id);
    }

}
