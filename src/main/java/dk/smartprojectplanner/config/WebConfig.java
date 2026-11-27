package dk.smartprojectplanner.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configures request handling for the web application
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Registers the login interceptor and defines which paths are public
     *
     * @param registry the registry used to configure interceptors
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/",
                        "/login",
                        "/register",
                        "/registerForm",
                        "/style.css",
                        "/images/**",
                        "/error"
                );
    }
}
