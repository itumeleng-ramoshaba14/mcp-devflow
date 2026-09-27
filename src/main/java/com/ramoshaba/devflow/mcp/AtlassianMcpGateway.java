package com.ramoshaba.devflow.mcp;

import java.util.Map;

/**
 * Defines the operations that DevFlow requires
 * from the Atlassian MCP integration.
 *
 * @author Itumeleng Ramoshaba
 */
public interface AtlassianMcpGateway {

    /**
     * Retrieves raw Jira issue data from Atlassian MCP.
     *
     * @param issueKey Jira issue key, for example DEV-101
     * @return raw Jira issue data returned by Atlassian MCP,
     * or null if the issue could not be retrieved
     */
    Map<String, Object> getJiraIssue(String issueKey);
}