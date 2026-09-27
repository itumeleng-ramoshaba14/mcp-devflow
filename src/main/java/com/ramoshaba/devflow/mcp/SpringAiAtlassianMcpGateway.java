package com.ramoshaba.devflow.mcp;

import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.spec.McpSchema.CallToolRequest;
import io.modelcontextprotocol.spec.McpSchema.CallToolResult;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * MCP gateway implementation that uses Spring AI's
 * synchronous MCP client to communicate with Atlassian.
 *
 * @author Itumeleng Ramoshaba
 */
public class SpringAiAtlassianMcpGateway
        implements AtlassianMcpGateway {

    private final McpSyncClient mcpSyncClient;
    private final String cloudId;

    /**
     * Creates a new SpringAiAtlassianMcpGateway.
     *
     * @param mcpSyncClient synchronous MCP client used to
     *                      communicate with Atlassian
     * @param cloudId Atlassian Cloud ID identifying the Jira site
     * @throws NullPointerException if the MCP client or cloud ID is null
     * @throws IllegalArgumentException if the cloud ID is blank
     */
    public SpringAiAtlassianMcpGateway(
            McpSyncClient mcpSyncClient,
            String cloudId
    ) {

        this.mcpSyncClient =
                Objects.requireNonNull(
                        mcpSyncClient,
                        "McpSyncClient must not be null"
                );

        Objects.requireNonNull(
                cloudId,
                "Atlassian cloud ID must not be null"
        );

        if (cloudId.isBlank()) {
            throw new IllegalArgumentException(
                    "Atlassian cloud ID must not be blank"
            );
        }

        this.cloudId = cloudId.trim();
    }

    /**
     * Retrieves a Jira issue from Atlassian by calling
     * the getJiraIssue MCP tool.
     *
     * @param issueKey Jira issue key, for example DEV-101
     * @return structured Jira issue data returned by Atlassian,
     *         or null if the MCP tool reports an error
     * @throws IllegalArgumentException if the issue key is null or blank
     * @throws IllegalStateException if Atlassian does not return
     *                               structured issue data
     */
    @Override
    public Map<String, Object> getJiraIssue(String issueKey) {

        if (issueKey == null || issueKey.isBlank()) {
            throw new IllegalArgumentException(
                    "Jira issue key must not be blank"
            );
        }

        Map<String, Object> arguments = Map.of(
                "cloudId", cloudId,
                "issueIdOrKey", issueKey,
                "fields", List.of(
                        "summary",
                        "description",
                        "status",
                        "assignee"
                ),
                "responseContentFormat", "markdown"
        );

        CallToolRequest request =
                CallToolRequest
                        .builder("getJiraIssue")
                        .arguments(arguments)
                        .build();

        CallToolResult result =
                mcpSyncClient.callTool(request);

        if (result == null) {
            return null;
        }

        if (Boolean.TRUE.equals(result.isError())) {
            return null;
        }

        Object structuredContent =
                result.structuredContent();

        if (structuredContent == null) {
            throw new IllegalStateException(
                    "Atlassian MCP returned no structured Jira issue data"
            );
        }

        return convertToMap(structuredContent);
    }

    /**
     * Converts MCP structured content into a map that can
     * be processed by the DevFlow Jira provider.
     *
     * <p>The MCP SDK exposes structured content as Object because
     * MCP allows structured results to contain different JSON types.
     * DevFlow currently expects Jira issue data to be represented
     * as a JSON object.</p>
     *
     * @param structuredContent structured result returned by MCP
     * @return structured content converted to a String/Object map
     * @throws IllegalStateException if the structured content
     *                               is not a map
     */
    private Map<String, Object> convertToMap(
            Object structuredContent
    ) {

        if (!(structuredContent instanceof Map<?, ?> rawMap)) {
            throw new IllegalStateException(
                    "Atlassian MCP structured content is not a JSON object"
            );
        }

        Map<String, Object> convertedMap =
                new LinkedHashMap<>();

        for (Map.Entry<?, ?> entry : rawMap.entrySet()) {

            if (entry.getKey() instanceof String key) {
                convertedMap.put(
                        key,
                        entry.getValue()
                );
            }
        }

        return convertedMap;
    }
}