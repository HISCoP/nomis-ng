package org.nomisng.config;


import lombok.Data;

import java.util.Map;

@Data
public class DatabaseProperties {
    private Map<String, DataSource> spring;
}