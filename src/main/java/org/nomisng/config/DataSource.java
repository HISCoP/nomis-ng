package org.nomisng.config;

import lombok.Data;

@Data
public class DataSource {
    private String username;
    private String password;
    private String url;
}