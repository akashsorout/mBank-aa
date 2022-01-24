package in.co.bytehub.mbankaa.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.co.bytehub.mbankaa.security.exception.RoleNotExistException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.Optional;


@Service
public class RoleManagementService {

    private final ObjectMapper objectMapper;
    private final Resource resourceFile;
    private Role role;

    public RoleManagementService(ObjectMapper objectMapper, @Value("classpath:roles.json") Resource resourceFile) {
        this.objectMapper = objectMapper;
        this.resourceFile = resourceFile;
    }

    @PostConstruct
    public void readRoleConfig() throws IOException {
        role = objectMapper.readValue(resourceFile.getInputStream(), Role.class);
    }

    public Boolean isCurrentRoleSufficient(String currentRoleName, String requiredRole) {
        Optional<Role> currentRole = findRoleByName(role, currentRoleName);
        if (currentRole.isEmpty()) {
            throw new RoleNotExistException();
        } else {
            return findRoleByName(currentRole.get(), requiredRole).isPresent();
        }
    }

    private Optional<Role> findRoleByName(Role current, String roleName) {
        if (current.getName().equalsIgnoreCase(roleName))
            return Optional.of(current);
        else {
            List<Role> children = current.getChildren();
            if (children == null)
                return Optional.empty();
            else {
                Optional<Role> role = Optional.empty();
                for (Role child : children) {
                    role = findRoleByName(child, roleName);
                    if (role.isPresent()) {
                        break;
                    }
                }
                return role;
            }
        }
    }
}
