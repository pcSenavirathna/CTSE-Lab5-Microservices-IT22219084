package com.sliit.api_gateway;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GatewayController {

    @GetMapping("/")
    public Map<String, Object> home() {
        return Map.of(
                "service", "api-gateway",
                "status", "UP",
                "routes", new String[]{"/items/**", "/orders/**", "/payments/**"}
        );
    }
}
