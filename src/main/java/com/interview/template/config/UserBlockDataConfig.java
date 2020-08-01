package com.interview.template.config;

import java.util.Set;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Configuration
@ConfigurationProperties(prefix = "block-data")
@Data
public class UserBlockDataConfig {
	 private Set<String> usernameBlackSet;
}