package org.nomisng.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.controller.apierror.EntityNotFoundException;
import org.nomisng.domain.dto.IpDTO;
import org.nomisng.domain.entity.Ip;
import org.nomisng.domain.mapper.IpMapper;
import org.nomisng.repository.IpRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class IpService {
    private final IpRepository ipRepository;
    private final IpMapper ipMapper;

    public List getAllIps() {
        return ipMapper.toIpDTOS(ipRepository.findAll());
    }

    public Ip save(IpDTO ipDTO) {
        return ipRepository.save(ipMapper.toIp(ipDTO));
    }

    public IpDTO getIp(Long id) {
        Ip ip = ipRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Ip.class, "Id", id+""));
       return ipMapper.toIpDTO(ip);
    }


    public Ip update(Long id, IpDTO ipDTO) {
        Ip ip = ipRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Ip.class, "Id", id+""));
        ipDTO.setId(ip.getId());
        return ipRepository.save(ipMapper.toIp(ipDTO));
    }

    public void delete(Long id) {

    }
}