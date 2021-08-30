package org.nomisng.controller;

import lombok.RequiredArgsConstructor;
import org.nomisng.domain.dto.IpDTO;
import org.nomisng.domain.entity.Ip;
import org.nomisng.service.IpService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/ips")
@RequiredArgsConstructor
public class IpController {
    private final IpService ipService;

    @GetMapping
    public ResponseEntity<List<IpDTO>> getAllIps() {
        return ResponseEntity.ok(ipService.getAllIps());
    }

    @GetMapping("/{id}")
    public ResponseEntity<IpDTO> getIp(@PathVariable Long id) {
        return ResponseEntity.ok(ipService.getIp(id));
    }

    @PostMapping
    public ResponseEntity<Ip> save(@RequestBody IpDTO ipDTO) {
        return ResponseEntity.ok(ipService.save(ipDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ip> updateIp(@RequestBody IpDTO ipDTO, @PathVariable Long id) {
        return ResponseEntity.ok(ipService.update(id, ipDTO));
    }
}
