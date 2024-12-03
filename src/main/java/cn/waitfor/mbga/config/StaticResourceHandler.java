package cn.waitfor.mbga.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.EncodedResourceResolver;

@Configuration
public class StaticResourceHandler implements WebMvcConfigurer {

  @Value("${static-resource.handler}")
  private String resourceHandler;

  @Value("${static-resource.locations}")
  private String resourceLocations;

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry
            .addResourceHandler(resourceHandler)
            .addResourceLocations(resourceLocations)
            .setCachePeriod(60)
            .resourceChain(true)
            .addResolver(new EncodedResourceResolver());
  }
}
