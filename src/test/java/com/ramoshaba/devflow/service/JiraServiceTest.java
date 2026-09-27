package com.ramoshaba.devflow.service;

import com.ramoshaba.devflow.exception.JiraIssueNotFoundException;
import com.ramoshaba.devflow.model.JiraIssue;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

public class JiraServiceTest {

    @Test
    void getIssueNormalizesKeyAndReturnsIssue() {

        JiraIssue expectedIssue = new JiraIssue("DEV-101", "Connect DevFlow to Jira",
                "Retrieve a Jira issue through MCP",
                "To Do",
                "Test User"
        );

        AtomicReference<String> requestedKey = new AtomicReference<>();

        JiraIssueProvider provider = issueKey -> {
            requestedKey.set(issueKey);
            return expectedIssue;
        };

        JiraService jiraService = new JiraService(provider);

        JiraIssue actualIssue = jiraService.getIssue("  dev-101  ");

        assertEquals("DEV-101", requestedKey.get());
        assertEquals(expectedIssue, actualIssue);
    }

    @Test
    void getIssueRejectsBlankKey() {

        JiraIssueProvider provider = issueKey -> null;

        JiraService jiraService = new JiraService(provider);

        assertThrows(IllegalArgumentException.class, () -> jiraService.getIssue("   ")
        );
    }

    @Test
    void getIssueRejectsNullKey() {

        JiraIssueProvider provider = issueKey -> null;

        JiraService jiraService = new JiraService(provider);

        assertThrows(IllegalArgumentException.class, () -> jiraService.getIssue(null));
    }

    @Test
    void getIssueThrowsNotFoundWhenProviderReturnsNull() {

        JiraIssueProvider provider = issueKey -> null;

        JiraService jiraService = new JiraService(provider);

        JiraIssueNotFoundException exception =
                assertThrows(JiraIssueNotFoundException.class, () -> jiraService.getIssue("DEV-999"));

        assertEquals("Jira issue not found: DEV-999", exception.getMessage());
    }

    @Test
    void constructorRejectsNullProvider() {

        assertThrows(NullPointerException.class, () -> new JiraService(null));
    }
}