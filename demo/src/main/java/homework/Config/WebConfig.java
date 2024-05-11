package homework.Config;

import homework.Utils.RoleAuthFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    /**
     * 配置并注册一个角色认证过滤器。
     *
     * @return FilterRegistrationBean<RoleAuthFilter> 返回配置好的过滤器注册对象，它包含了角色认证过滤器的配置信息。
     */
    @Bean
    public FilterRegistrationBean<RoleAuthFilter> roleAuthFilterRegistration() {
        // 创建一个过滤器注册bean实例
        FilterRegistrationBean<RoleAuthFilter> registrationBean = new FilterRegistrationBean<>();
        // 实例化并配置角色认证过滤器
        RoleAuthFilter filter = new RoleAuthFilter();
        registrationBean.setFilter(filter);
        // 将过滤器应用到所有请求路径
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}
