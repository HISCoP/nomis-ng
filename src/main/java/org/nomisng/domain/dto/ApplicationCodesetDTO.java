package org.nomisng.domain.dto;


import lombok.Data;

@Data
public class ApplicationCodesetDTO {
    private Long id;

    private String codesetGroup;

    private String language;

    private String display;

    private String version;

    private String code;

}
