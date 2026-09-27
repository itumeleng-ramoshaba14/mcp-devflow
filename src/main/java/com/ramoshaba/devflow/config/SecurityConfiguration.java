package com.ramoshaba.devflow.config;

import org.springaicommunity.mcp.security.client.sync.config.McpClientOAuth2Configurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration for the Atlassian MCP integration.
 *
 * <p>This configuration enables OAuth support required for
 * communication with the Atlassian Rovo MCP server.</p>
 *
 * <p>The configuration is only activated when the
 * "atlassian" Spring profile is active.</p>
 *
 * @author Itumeleng Ramoshaba
 */
@Configuration
@EnableWebSecurity
@Profile("atlassian")
public class SecurityConfiguration {

    /**
     * Configures the Spring Security filter chain used for
     * Atlassian MCP OAuth authentication.
     *
     * <p>Application endpoints are currently permitted because
     * DevFlow is still in development. The MCP OAuth configuration
     * handles authentication between DevFlow and the Atlassian
     * MCP server.</p>
     *
     * @param http Spring Security HTTP configuration
     * @return configured SecurityFilterChain
     * @throws Exception if the security configuration cannot be built
     */
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        return http
                .authorizeHttpRequests(
                        auth -> auth.anyRequest().permitAll()
                )
                .with(
                        McpClientOAuth2Configurer.mcpClientOAuth2(),
                        Customizer.withDefaults()
                )
                .csrf(CsrfConfigurer::disable)
                .build();
    }
}