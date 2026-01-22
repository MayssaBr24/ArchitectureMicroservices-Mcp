package com.sante.appointment.config;

import com.sante.appointment.mcp.AppointmentMCPService;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class MCPConfig {

    @Bean
    public List<ToolCallback> toolCallbacks(AppointmentMCPService appointmentMCPService) {
        return List.of(ToolCallbacks.from(appointmentMCPService));
    }
}