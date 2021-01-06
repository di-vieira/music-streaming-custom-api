package br.com.backend.music.streaming.custom.api.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationCodeGrant;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.OAuth;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.TokenEndpoint;
import springfox.documentation.service.TokenRequestEndpoint;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	/**
	 * Client Id para acessar a API externa
	 */
	@Value("${security.oauth2.client.clientId}")
	private String clientId;
	
	/**
	 * Client Secret para acessar a API externa 
	 */
	@Value("${security.oauth2.client.clientSecret}")
	private String clientSecret;
	
	/**
	 * URI de autorização para o serviço de streaming
	 */
	@Value("${security.oauth2.client.userAuthorizationUri}")
	private String userAuthorizationUri;
	
	/**
	 * URI do token de autorização
	 */
	@Value("${security.oauth2.client.accessTokenUri}")
	private String accessTokenUri;
	
	/**
	 * Escopos de acesso para a API do serviço de streaming
	 */
	@Value("${security.oauth2.client.scope}")
	private String scope;
	
	/**
	 * Caractere separador do escopo
	 */
	private final String SCOPE_SEPARATOR = " ";
	
	/**
	 * Bean responsável por fazer a configuração do Swagger
	 * @return Docket
	 */
	@Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.any())              
          .paths(PathSelectors.any())                          
          .build().apiInfo(apiInfo())
          .useDefaultResponseMessages(false)
          .securitySchemes(Arrays.asList(securitySchema()))
          .securityContexts(Arrays.asList(securityContext()));                                       
    }
	
	/**
	 * Info da API no Swagger
	 * 
	 * @return ApiInfo
	 */
	private ApiInfo apiInfo() {
	    return new ApiInfo(
	      "API Customizada com funções adicionais para serviços de streaming", 
	      "API com funções adicionais para o Spotify", 
	      "Versão 0.0.1", 
	      "Terms of service", 
	      new Contact("Diego da Silva Vieira", "https://www.linkedin.com/in/diego-silva-vieira/", "diegodasilvavieira@gmail.com"), 
	      "License of API", "API license URL", Collections.emptyList());
	}
	
	/**
	 * Configura o schema de autenticação OAuth2 para que a interface do Swagger possa autenticar na API do Serviço de Streaming
	 * @return OAuth
	 */
	private OAuth securitySchema() {

		// Adiciona os escopos de acessos à API externa
        List<AuthorizationScope> authorizationScopeList = new ArrayList<AuthorizationScope>();
        String[] scopes = scope.split(SCOPE_SEPARATOR);
        for(int i=0; i < scopes.length; i++) {
        	authorizationScopeList.add(new AuthorizationScope(scopes[i], scopes[i]));
        }
        // Configura token e demais dados de acesso para a API externa
        List<GrantType> grantTypes = new ArrayList<GrantType>();
        TokenRequestEndpoint tokenRequestEndpoint = new TokenRequestEndpoint(userAuthorizationUri, clientId, clientSecret);
        TokenEndpoint tokenEndpoint = new TokenEndpoint(accessTokenUri, "token Spotify");
        GrantType creGrant = new AuthorizationCodeGrant(tokenRequestEndpoint, tokenEndpoint);

        grantTypes.add(creGrant);

        return new OAuth("oauth2schema", authorizationScopeList, grantTypes);
    }
	
	/**
	 * Método para configurar o contexto de segurança no swagger, ou seja, quais operações estarão dentro do contexto de 
	 * segurança na API
	 * @return SecurityContext
	 */
	private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.ant("/streaming/api/**"))
                .build();
    }
	
	/**
	 * Configura a autenticação padrão do contexto de segurança
	 * @return List<SecurityReference>
	 */
    private List<SecurityReference> defaultAuth() {
    	String[] scopes = scope.split(SCOPE_SEPARATOR);
        final AuthorizationScope[] authorizationScopes = new AuthorizationScope[scopes.length];
        for(int i=0; i < scopes.length; i++) {
        	authorizationScopes[i] = new AuthorizationScope(scopes[i], scopes[i]);
        }
        
        return Collections.singletonList(new SecurityReference("oauth2schema", authorizationScopes));
    }

    /**
     * Método que configura as informações de autenticação para a API
     * @return SecurityConfiguration
     */
    @Bean
    public SecurityConfiguration securityInfo() {
        return new SecurityConfiguration(clientId, clientSecret, null, "Login API Spotify", SCOPE_SEPARATOR, null, null);
    }
	
}
