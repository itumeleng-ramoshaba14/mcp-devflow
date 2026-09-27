package com.ramoshaba.devflow.config;

import com.ramoshaba.devflow.mcp.AtlassianJiraIssueProvider;
import com.ramoshaba.devflow.mcp.AtlassianMcpGateway;
import com.ramoshaba.devflow.mcp.SpringAiAtlassianMcpGateway;
import com.ramoshaba.devflow.service.JiraIssueProvider;
import com.ramoshaba.devflow.service.JiraService;

import io.modelcontextprotocol.client.McpSyncClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

/**
 * Spring configuration for the Atlassian MCP integration.
 *
 * <p>This configuration is only activated when the
 * "atlassian" Spring profile is enabled.</p>
 *
 * @author Itumeleng Ramoshaba
 */
@Configuration
@Profile("atlassian")
public class AtlassianMcpConfiguration {

    /**
     * Creates the Atlassian MCP gateway used by DevFlow.
     *
     * <p>Spring AI automatically creates McpSyncClient instances
     * for the MCP connections configured in
     * application-atlassian.properties.</p>
     *
     * @param mcpSyncClients MCP clients created by Spring AI
     * @param cloudId Atlassian Cloud ID for the Jira site
     * @return configured AtlassianMcpGateway
     * @throws IllegalStateException if no MCP client is available
     */
    @Bean
    public AtlassianMcpGateway atlassianMcpGateway(
            List<McpSyncClient> mcpSyncClients,
            @Value("${devflow.atlassian.cloud-id}") String cloudId
    ) {

        if (mcpSyncClients.isEmpty()) {
            throw new IllegalStateException("No Atlassian MCP client is available");
        }

        McpSyncClient mcpSyncClient = mcpSyncClients.get(0);

        return new SpringAiAtlassianMcpGateway(mcpSyncClient, cloudId);
    }

    /**
     * Creates the JiraIssueProvider responsible for converting
     * Atlassian MCP data into DevFlow JiraIssue objects.
     *
     * @param atlassianMcpGateway Atlassian MCP gateway
     * @return Jira issue provider
     */
    @Bean
    public JiraIssueProvider jiraIssueProvider(AtlassianMcpGateway atlassianMcpGateway) {

        return new AtlassianJiraIssueProvider(atlassianMcpGateway);
    }

    /**
     * Creates the JiraService used by the application.
     *
     * @param jiraIssueProvider provider used to retrieve Jira issues
     * @return configured JiraService
     */
    @Bean
    public JiraService jiraService(JiraIssueProvider jiraIssueProvider) {

        return new JiraService(jiraIssueProvider);
    }
}