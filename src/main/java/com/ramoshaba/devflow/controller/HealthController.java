package com.ramoshaba.devflow.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller that provides health-related endpoints for the MCP DevFlow application.
 * @author Itumeleng Ramoshaba
 */
@RestController
@RequestMapping("/api")
public class HealthController {

    /**
     * Checks whether the MCP DevFlow backend application is running.
     * @return a message confirming that the application is running
     */
    @GetMapping("/health")
    public String health() {
        return "MCP DevFlow is running";
    }
}