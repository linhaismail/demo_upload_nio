package com.example.demo_upload;

import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatEmbeddedWebappClassLoader;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoUploadApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoUploadApplication.class, args);
	}

	// 大文件上传连接重置问题
	@Bean
	public TomcatServletWebServerFactory tomcatServlet() {
		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
		tomcat.addConnectorCustomizers((TomcatConnectorCustomizer) connector->{
			if (connector.getProtocolHandler() instanceof AbstractHttp11Protocol) {
				// -1 意思是不限大小
				((AbstractHttp11Protocol<?>)connector.getProtocolHandler()).setMaxSwallowSize(-1);
			}
		});
		return tomcat;
	}
}
