package in.co.bytehub.mbankaa.security;


import in.co.bytehub.mbankaa.security.exception.AuthorizationFailedException;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AuthorizationInterceptor implements HandlerInterceptor {

    private RoleManagementService roleManagementService;

    public AuthorizationInterceptor(RoleManagementService roleManagementService) {
        this.roleManagementService = roleManagementService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod) {
            RequiredRole requiredRole = ((HandlerMethod) handler).getMethodAnnotation(RequiredRole.class);
            if (requiredRole == null)
                return true;
            else {
                String roleFromToken = (String) request.getAttribute("role");
                if (roleManagementService.isCurrentRoleSufficient(roleFromToken, requiredRole.value())) {
                    return true;
                } else {
                    throw new AuthorizationFailedException();
                }
            }
        }
        return true;
    }
}
