package in.co.bytehub.mbankaa.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.co.bytehub.mbankaa.security.AuthenticationFilter;
import in.co.bytehub.mbankaa.security.AuthorizationInterceptor;
import in.co.bytehub.mbankaa.security.RoleManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.stream.Collectors;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${allow-url}")
    private List<String> allowedUrls;

    @Autowired
    private RoleManagementService roleManagementService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthorizationInterceptor(roleManagementService))
                .excludePathPatterns(allowedUrls.stream().map(url -> url + "/*").collect(Collectors.toList()));
    }


    @Bean
    public FilterRegistrationBean<AuthenticationFilter> filterRegistrationBean(@Value("${jwt.sign.key}") String jwtKey, ObjectMapper objectMapper) {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(jwtKey, objectMapper, allowedUrls);
        FilterRegistrationBean<AuthenticationFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(authenticationFilter);
        filterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return filterRegistrationBean;
    }
}
