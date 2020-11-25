package com.tistory.lte36.travolo2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
//이 파일은 Dispatcher Serlvet이 실행될 때 읽어들이는 파일이다.

@Configuration
@EnableWebMvc
//ComponentScan시 반드시 탐색 할 첫 번째 패키지를 basePackages로 등록 해주고, 중괄호 안에 여러 패키지를 등록시킬 수 있다.
@ComponentScan(basePackages = {"com.tistory.lte36.controller"})
public class WebMvcContextConfiguration extends WebMvcConfigurerAdapter {
	// /라는 문구가 url로 들어 올 경우 css, img, js를 구분할 수 없기 때문에 이런 파일들이 올경우 /css/, /img, /js/를 확인하라고 설정한다.
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("/css/").setCachePeriod(31556926);
        registry.addResourceHandler("/img/**").addResourceLocations("/img/").setCachePeriod(31556926);
        registry.addResourceHandler("/js/**").addResourceLocations("/js/").setCachePeriod(31556926);
    }
 
	//handler adapter에서 사용할 handler를 default로 설정한다.
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
   
    //addviewControllers는 특정 url에 대한 controller class를 작성하지 않고 매핑할 수 있도록 하는 메서드
    // /가 들어올 경우 main을 실행시킨다.
    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
    		System.out.println("addViewControllers가 호출됩니다. ");
        registry.addViewController("/").setViewName("main");
    }
    
    //main의 앞에 /WEB-INF/views/를 넣어주고, main의 뒤에 .jsp를 넣어주고 실행하라.
    @Bean
    public InternalResourceViewResolver getInternalResourceViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        return resolver;
    }
}
