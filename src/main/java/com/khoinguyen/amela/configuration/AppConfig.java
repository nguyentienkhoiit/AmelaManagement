package com.khoinguyen.amela.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class AppConfig {
    @Value("${app.host}")
    public String HOST;
}
