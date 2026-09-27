package com.ramoshaba.devflow.controller;

import com.ramoshaba.devflow.model.JiraIssue;
import com.ramoshaba.devflow.service.JiraIssueProvider;
import com.ramoshaba.devflow.service.JiraService;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class JiraControllerTest {

    /**
     * Tests that the Jira endpoint returns the Jira issue
     * provided by JiraService.
     *
     * @throws Exception if the HTTP request cannot be executed
     */
    @Test
    void getIssueReturnsJiraIssue() throws Exception {

        JiraIssue expectedIssue = new JiraIssue(
                "DEV-101",
                "Connect DevFlow to Jira",
                "Retrieve Jira issue using MCP",
                "To Do",
                "Itumeleng"
        );

        JiraIssueProvider provider = issueKey -> expectedIssue;

        JiraService jiraService = new JiraService(provider);

        JiraController jiraController = new JiraController(jiraService);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(jiraController).build();

        mockMvc.perform(get("/api/jira/issues/DEV-101"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.key").value("DEV-101"))
                .andExpect(jsonPath("$.summary").value("Connect DevFlow to Jira"))
                .andExpect(jsonPath("$.description").value("Retrieve Jira issue using MCP"))
                .andExpect(jsonPath("$.status").value("To Do"))
                .andExpect(jsonPath("$.assignee").value("Itumeleng"));
    }

    /**
     * Tests that the issue key received through the HTTP
     * endpoint is normalized by JiraService before being
     * sent to the Jira issue provider.
     *
     * @throws Exception if the HTTP request cannot be executed
     */
    @Test
    void getIssueNormalizesIssueKey() throws Exception {

        JiraIssueProvider provider =
                issueKey -> new JiraIssue(
                        issueKey,
                        "Test issue",
                        "Test description",
                        "To Do",
                        "Itumeleng"
                );

        JiraService jiraService = new JiraService(provider);

        JiraController jiraController = new JiraController(jiraService);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(jiraController).build();

        mockMvc.perform(get("/api/jira/issues/dev-101"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.key").value("DEV-101"));
    }

    /**
     * Tests that JiraController cannot be created without
     * a JiraService dependency.
     */
    @Test
    void constructorRejectsNullJiraService() {

        org.junit.jupiter.api.Assertions.assertThrows(NullPointerException.class,
                () -> new JiraController(null));
    }
}