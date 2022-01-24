package in.co.bytehub.mbankaa.controller;

import in.co.bytehub.mbankaa.security.RoleManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class WelcomeController {

    @Autowired
    private RoleManagementService roleManagementService;

    @GetMapping("/welcome")
    public Map<String, Object> greet(@RequestParam String currentRole, @RequestParam String requiredRole) {

        return Map.of("message",
                roleManagementService.isCurrentRoleSufficient(currentRole, requiredRole)
        );

    }
}
