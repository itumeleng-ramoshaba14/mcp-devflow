package com.ramoshaba.devflow.model;

/**
 * Represents the Jira issue information used by MCP DevFlow.
 *
 * <p>This model belongs to the DevFlow application and is intentionally
 * independent of Atlassian MCP response formats. The MCP integration layer
 * will later map Jira data into this model.</p>
 *
 * @param key Jira issue key
 * @param summary short issue title
 * @param description issue description
 * @param status current Jira workflow status
 * @param assignee display name of the assigned user
 *
 * @author Itumeleng Ramoshaba
 */
public record JiraIssue(
        String key,
        String summary,
        String description,
        String status,
        String assignee
) {
}