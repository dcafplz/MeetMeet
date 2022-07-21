package meetmeet.config;

<<<<<<< HEAD
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
=======
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

>>>>>>> b50974767ac1091f9942cab0f9b200c704793d6f
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2  
public class SwaggerConfig {
<<<<<<< HEAD
	/*	1. Docket(DocumentationType.SWAGGER_2)
	 * 	- swagger 설정 필수 핵심 bean
	 * 	- swagger 버전에 따른 차이점
	 * 
	 * 	2. .ignoreParameterTypes(Aplication.class)
	 * 	- @ApiIgnore HttpSession session 처럼 특정 parameter를 사용자에게 은닉 가능하게 하는 설정
	 * 	- parameter 정보 선별해서 문서에 기록시에는 필수
	 * 
	 * 	3. apiInfo(swaggerInfo())
	 * 	- swaggerInfo() 사용자 정의 메소드가 parameter
	 * 	- 제목이나 설명 등 header 부분을 별도의 메소드로 개발해서 호출해서 적용
	 * 
	 * 	4. apis()
	 * 	- 컨트롤러가 존재하는 package 등록
	 * 	- 서비스 하고자 하는 서비스들(기능, 방식, parameter, 반환값, 오류메시지) 소개. 즉, 필수
	 * 	- 여러개 package 있으면 여러줄 쓸 수 있음. apis().apis() ...
	 * 
	 * 	5. useDefaultResponseMessages(false)
	 * 	- true
	 * 		- 하단 코드 설정과 무관한 상태 정보도 서비스
	 * 	- false
	 * 		- 코드상에 다음처럼 설정한 응답상태 정보만 doc에 서비스
	 * 
			  @ApiResponses({
		          @ApiResponse(code = 200, message = "OK !!"), ...})
	 */
	
    @Bean	// 스프링 빈으로 등록 의미
    public Docket swaggerApi() {
    	
        return new Docket(DocumentationType.SWAGGER_2).ignoredParameterTypes(ApiIgnore.class)
=======
	
    @Bean
    public Docket swaggerApi() {
    	
        return new Docket(DocumentationType.SWAGGER_2)
        		.ignoredParameterTypes(ApiIgnore.class)
>>>>>>> b50974767ac1091f9942cab0f9b200c704793d6f
        		.apiInfo(swaggerInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("meetmeet.controller"))
                .build()
                .useDefaultResponseMessages(false); 
    }
    

    private ApiInfo swaggerInfo() {
<<<<<<< HEAD
        return new ApiInfoBuilder().title("API Doc 입니다")
                .description("Swagger Doc 학습을 위한 기본 문서 작성중 ")
                .license("license : playdata").licenseUrl("http://www.google.com")
=======
        return new ApiInfoBuilder().title("신동혁 스웨거")
                .description("웹프로젝트 밋밋 Swagger Doc ")
                .license("license : playdata").licenseUrl("https://shindonghyeo.github.io/java/Swagger/")
>>>>>>> b50974767ac1091f9942cab0f9b200c704793d6f
                .version("1").build();
    }
    
}
