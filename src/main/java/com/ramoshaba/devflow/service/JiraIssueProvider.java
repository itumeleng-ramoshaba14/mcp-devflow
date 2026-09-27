package com.ramoshaba.devflow.service;

import com.ramoshaba.devflow.model.JiraIssue;

/**
 * Provides Jira issue data to the application service.
 *
 * <p>The Atlassian MCP integration will implement this interface later.
 * Keeping the integration behind this boundary allows JiraService to be
 * unit tested without making real network calls.</p>
 *
 * @author Itumeleng Ramoshaba
 */
public interface JiraIssueProvider {

    JiraIssue getIssue(String issueKey);
}