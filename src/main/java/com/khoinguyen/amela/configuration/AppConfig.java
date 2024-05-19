package com.khoinguyen.amela.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class AppConfig {
    @Value("${app.host}")
    public String HOST;

    @Value("${app.version}")
    public String version;

    @Value("${app.description}")
    public String description;
}
