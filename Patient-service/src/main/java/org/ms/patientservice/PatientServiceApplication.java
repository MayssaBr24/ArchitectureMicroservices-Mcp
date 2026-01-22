package org.ms.patientservice;

import org.ms.patientservice.mcp.PatientMCPService;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.List;

@EnableFeignClients
@SpringBootApplication
public class PatientServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatientServiceApplication.class, args);
    }

    @Bean
    public List<ToolCallback> toolCallbacks(PatientMCPService patientMCPService) {
        return List.of(ToolCallbacks.from(patientMCPService));
    }
}
