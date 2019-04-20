package norvina.config;

import norvina.web.interceptors.FaviconInterceptor;
import norvina.web.interceptors.TitleInterceptor;
import norvina.web.interceptors.UsernameInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApplicationWebMvcConfiguration implements WebMvcConfigurer {

    private final TitleInterceptor titleInterceptor;
    private final FaviconInterceptor faviconInterceptor;
    private final UsernameInterceptor usernameInterceptor;

    @Autowired
    public ApplicationWebMvcConfiguration(TitleInterceptor titleInterceptor, FaviconInterceptor faviconInterceptor, UsernameInterceptor usernameInterceptor) {
        this.titleInterceptor = titleInterceptor;
        this.faviconInterceptor = faviconInterceptor;
        this.usernameInterceptor = usernameInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.titleInterceptor);
        registry.addInterceptor(this.faviconInterceptor);
        registry.addInterceptor(this.usernameInterceptor);
    }
}