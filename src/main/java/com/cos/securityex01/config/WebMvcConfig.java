package com.cos.securityex01.config;

import org.springframework.boot.web.servlet.view.MustacheViewResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // ioc 등록
public class WebMvcConfig implements WebMvcConfigurer{  

  @Override
  public void configureViewResolvers(ViewResolverRegistry registry) {
      // view 리졸버인 mustache를 재설정 한다.
      MustacheViewResolver resolver = new MustacheViewResolver();

      resolver.setCharset("UTF-8");
      resolver.setContentType("text/html;charset=UTF-8");
      resolver.setPrefix("classpath:/templates/");
      resolver.setSuffix(".html"); // .html 로 만든 파일을 mustache가 인식할 수 있도록 한다.

      registry.viewResolver(resolver);
  }
}
