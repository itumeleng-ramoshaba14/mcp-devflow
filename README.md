# MCP DevFlow

MCP DevFlow is a Java and Spring Boot application designed to demonstrate how the Model Context Protocol (MCP) can be used to connect AI-powered developer tools with enterprise platforms such as Jira and Confluence.

The project is being built as a practical learning and portfolio project, with the goal of demonstrating modern Java development, Spring Boot, MCP integration, AI-assisted workflows, REST API development, testing, security, and professional software engineering practices.

---

## Project Overview

Software developers often switch between multiple tools during their daily work, including:

- Jira for tasks and issue tracking
- Confluence for documentation
- Source control platforms
- IDEs
- AI assistants
- Internal developer tools

MCP DevFlow aims to reduce this fragmentation by providing a single backend application capable of connecting an AI assistant to developer tools through the Model Context Protocol.

The long-term goal is to allow a developer to perform tasks such as:

- Retrieve Jira issues
- Search project documentation
- Summarize development work
- Retrieve sprint-related information
- Assist with developer workflows
- Generate structured technical summaries
- Perform approved actions through MCP tools

The initial implementation focuses on Atlassian Jira and Confluence through Atlassian Rovo MCP.

---

## Why This Project

The project was created to provide hands-on experience with:

- Model Context Protocol
- Enterprise Java development
- Spring Boot
- REST APIs
- AI integration
- Local Large Language Models
- Atlassian APIs and MCP tools
- Authentication and authorization
- Automated testing
- Application logging
- Exception handling
- Git and GitHub workflows
- Agile sprint-based development

It is intended to demonstrate how AI can be integrated into existing enterprise developer workflows without tightly coupling the application directly to individual systems.

---

## Architecture

The planned high-level architecture is:

```text
Developer
    |
    v
MCP DevFlow
Spring Boot Application
    |
    +-------------------------+
    |                         |
    v                         v
Local AI Layer           MCP Client Layer
Ollama                   Spring AI
                              |
                              v
                     Atlassian Rovo MCP
                              |
                    +---------+---------+
                    |                   |
                    v                   v
                  Jira             Confluence
