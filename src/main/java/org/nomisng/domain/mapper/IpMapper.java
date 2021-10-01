package org.nomisng.domain.mapper;

import org.mapstruct.Mapper;
import org.nomisng.domain.dto.IpDTO;
import org.nomisng.domain.entity.Ip;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IpMapper {
    Ip toIp(IpDTO ipDTO);

    IpDTO toIpDTO(Ip ip);

    List<IpDTO> toIpDTOS(List<Ip> ips);

}
