package com.ramoshaba.devflow.service;

import com.ramoshaba.devflow.exception.JiraIssueNotFoundException;
import com.ramoshaba.devflow.model.JiraIssue;

import java.util.Locale;
import java.util.Objects;

/**
 * Service responsible for Jira-related application logic.
 *
 * <p>The service does not communicate directly with Jira or MCP.
 * Instead, it depends on JiraIssueProvider, which allows the service
 * to be unit tested without making real Jira network calls.</p>
 *
 * @author Itumeleng Ramoshaba
 */
public class JiraService {

    private final JiraIssueProvider jiraIssueProvider;

    /**
     * Creates a JiraService.
     *
     * @param jiraIssueProvider provider used to retrieve Jira issues
     */
    public JiraService(JiraIssueProvider jiraIssueProvider) {

        this.jiraIssueProvider = Objects.requireNonNull(jiraIssueProvider,
                        "JiraIssueProvider must not be null");
    }

    /**
     * Retrieves a Jira issue using its issue key.
     *
     * <p>The issue key is validated, trimmed and converted
     * to uppercase before being sent to the provider.</p>
     *
     * Example:
     *
     * <pre>
     * " dev-101 " -> "DEV-101"
     * </pre>
     *
     * @param issueKey Jira issue key, for example DEV-101
     * @return the matching JiraIssue
     * @throws IllegalArgumentException if the issue key is null or blank
     * @throws JiraIssueNotFoundException if the issue cannot be found
     */
    public JiraIssue getIssue(String issueKey) {

        if (issueKey == null || issueKey.isBlank()) {
            throw new IllegalArgumentException("Jira issue key must not be blank");
        }

        String normalizedIssueKey = issueKey.trim().toUpperCase(Locale.ROOT);

        JiraIssue jiraIssue = jiraIssueProvider.getIssue(normalizedIssueKey);

        if (jiraIssue == null) {
            throw new JiraIssueNotFoundException(normalizedIssueKey);
        }

        return jiraIssue;
    }
}