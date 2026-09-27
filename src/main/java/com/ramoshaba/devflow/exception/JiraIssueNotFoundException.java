package com.ramoshaba.devflow.exception;

/**
 * Exception thrown when a requested Jira issue cannot be found.
 *
 * @author Itumeleng Ramoshaba
 */
public class JiraIssueNotFoundException extends RuntimeException {

    public JiraIssueNotFoundException(String issueKey)
    {
        super("Jira issue not found: " + issueKey);
    }
}