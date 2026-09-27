package com.ramoshaba.devflow.mcp;

import com.ramoshaba.devflow.model.JiraIssue;
import com.ramoshaba.devflow.service.JiraIssueProvider;

import java.util.Map;
import java.util.Objects;

/**
 * Jira issue provider that converts raw Atlassian MCP data
 * into the JiraIssue model used by DevFlow.
 *
 * @author Itumeleng Ramoshaba
 */
public class AtlassianJiraIssueProvider implements JiraIssueProvider {

    private final AtlassianMcpGateway atlassianMcpGateway;

    /**
     * Creates a new AtlassianJiraIssueProvider.
     * @param atlassianMcpGateway gateway used to retrieve Jira issue
     * data from Atlassian MCP
     * @throws NullPointerException if the gateway is null
     */
    public AtlassianJiraIssueProvider(AtlassianMcpGateway atlassianMcpGateway) {

        this.atlassianMcpGateway = Objects.requireNonNull(atlassianMcpGateway,
                        "AtlassianMcpGateway must not be null"
                );
    }

    /**
     * Retrieves a Jira issue from the Atlassian MCP gateway
     * and converts the raw response into a JiraIssue object.
     *
     * @param issueKey Jira issue key, for example DEV-101
     * @return the mapped JiraIssue, or null if the issue
     * could not be retrieved
     */
    @Override
    public JiraIssue getIssue(String issueKey) {

        Map<String, Object> issueData = atlassianMcpGateway.getJiraIssue(issueKey);

        if (issueData == null) {
            return null;
        }

        String key = getString(issueData, "key");

        Map<String, Object> fields = getMap(issueData, "fields");

        String summary = getString(fields, "summary");

        String description = getString(fields, "description");

        Map<String, Object> statusData = getMap(fields, "status");

        String status = getString(statusData, "name");

        Map<String, Object> assigneeData = getMap(fields, "assignee");

        String assignee = getString(assigneeData, "displayName");

        return new JiraIssue(
                key,
                summary,
                description,
                status,
                assignee
        );
    }

    /**
     * Retrieves a value from a map and converts it to a String.
     *
     * @param map map containing the required value
     * @param key key used to locate the value
     * @return the value as a String, or null if the map
     * or value is null
     */
    private String getString(
            Map<String, Object> map,
            String key
    ) {

        if (map == null) {
            return null;
        }

        Object value = map.get(key);

        if (value == null) {
            return null;
        }

        return value.toString();
    }

    /**
     * Retrieves a nested map from a parent map.
     *
     * @param map parent map containing the nested map
     * @param key key used to locate the nested map
     * @return the nested map, or null if the value does not
     * exist or is not a map
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> getMap(
            Map<String, Object> map,
            String key
    ) {

        if (map == null) {
            return null;
        }

        Object value = map.get(key);

        if (value instanceof Map<?, ?>) {
            return (Map<String, Object>) value;
        }

        return null;
    }
}