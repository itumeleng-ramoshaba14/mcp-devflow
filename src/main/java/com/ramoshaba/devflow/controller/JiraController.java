package com.ramoshaba.devflow.controller;

import com.ramoshaba.devflow.model.JiraIssue;
import com.ramoshaba.devflow.service.JiraService;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * REST controller for Jira-related operations.
 *
 * <p>The controller receives HTTP requests and delegates
 * Jira-related business logic to JiraService.</p>
 *
 * @author Itumeleng Ramoshaba
 */
@RestController
@RequestMapping("/api/jira/issues")
@Profile("atlassian")
public class JiraController {

    private final JiraService jiraService;

    /**
     * Creates a new JiraController.
     *
     * @param jiraService service used to retrieve Jira issues
     * @throws NullPointerException if JiraService is null
     */
    public JiraController(JiraService jiraService) {
        this.jiraService = Objects.requireNonNull(jiraService, "JiraService must not be null");
    }

    /**
     * Retrieves a Jira issue using its issue key.
     *
     * <p>For example, a request to
     * {@code /api/jira/issues/DEV-101}
     * retrieves the Jira issue with key DEV-101.</p>
     *
     * @param issueKey Jira issue key, for example DEV-101
     * @return JiraIssue matching the supplied issue key
     */
    @GetMapping("/{issueKey}")
    public JiraIssue getIssue(@PathVariable String issueKey) {
        return jiraService.getIssue(issueKey);
    }
}