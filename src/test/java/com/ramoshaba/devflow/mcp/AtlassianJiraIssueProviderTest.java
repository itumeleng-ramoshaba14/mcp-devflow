package com.ramoshaba.devflow.mcp;

import com.ramoshaba.devflow.model.JiraIssue;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class AtlassianJiraIssueProviderTest {

    /**
     * Tests that raw Jira data returned by the MCP gateway
     * is correctly converted into a JiraIssue object.
     */
    @Test
    void getIssueMapsMcpDataToJiraIssue() {

        Map<String, Object> rawIssue = Map.of(
                "key", "DEV-101",
                "fields", Map.of(
                        "summary", "Connect DevFlow to Jira",
                        "description", "Retrieve Jira issue using MCP",
                        "status", Map.of(
                                "name", "To Do"
                        ),
                        "assignee", Map.of(
                                "displayName", "Itumeleng"
                        )
                )
        );

        AtlassianMcpGateway gateway = issueKey -> rawIssue;

        AtlassianJiraIssueProvider provider = new AtlassianJiraIssueProvider(gateway);

        JiraIssue jiraIssue = provider.getIssue("DEV-101");

        assertNotNull(jiraIssue);

        assertEquals("DEV-101", jiraIssue.key());

        assertEquals("Connect DevFlow to Jira", jiraIssue.summary());

        assertEquals("Retrieve Jira issue using MCP", jiraIssue.description());

        assertEquals("To Do", jiraIssue.status());

        assertEquals("Itumeleng", jiraIssue.assignee());
    }

    /**
     * Tests that the provider returns null when the
     * MCP gateway does not return a Jira issue.
     */
    @Test
    void getIssueReturnsNullWhenGatewayReturnsNull() {

        AtlassianMcpGateway gateway = issueKey -> null;

        AtlassianJiraIssueProvider provider = new AtlassianJiraIssueProvider(gateway);

        JiraIssue jiraIssue = provider.getIssue("DEV-999");

        assertNull(jiraIssue);
    }

    /**
     * Tests that the provider cannot be created
     * without an Atlassian MCP gateway.
     */
    @Test
    void constructorRejectsNullGateway() {

        assertThrows(NullPointerException.class, () -> new AtlassianJiraIssueProvider(null));
    }
}