package com.example.vacationUpload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.util.unit.DataSize;
import javax.servlet.MultipartConfigElement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.MultipartFilter;
@SpringBootApplication
public class VacationUploadApplication {

	@Value( "${file.save.location}" )
	private String saveLocation;

	public static void main(String[] args) {
		SpringApplication.run(VacationUploadApplication.class, args);
	}

	@Bean
	public MultipartResolver multipartResolver() {
		return new SmartMultipartResolver();
	}
	// @Bean
	// MultipartConfigElement multipartConfigElement() {
	// 	System.out.println("save location" + saveLocation);
	// 	MultipartConfigFactory factory = new MultipartConfigFactory();
	// 	factory.setMaxFileSize(DataSize.ofMegabytes(500));
	// 	factory.setMaxRequestSize(DataSize.ofMegabytes(500));
	// 	factory.setFileSizeThreshold(DataSize.ofMegabytes(5));
	// 	factory.setLocation(saveLocation);
	// 	return factory.createMultipartConfig();
	// }

	// @Bean
	// //(name = "multipartResolver")
	// public CommonsMultipartResolver createMultipartResolver() {

	// 	final CommonsMultipartResolver cmr = new PJCommonsMultipartResolver();
	// 	cmr.setMaxUploadSize(DataSize.ofMegabytes(500).toBytes());
	// 	cmr.setDefaultEncoding("UTF-8");
	// 	cmr.getFileUpload().setProgressListener((long pBytesRead, long pContentLength, int pItems) -> {
	// 		System.out.println(" Uploaded " + (pBytesRead * 100 / pContentLength));
	// 	});

	// 	return cmr;
	// }

	// @Bean
	// @Order(0)
	// public MultipartFilter multipartFilter() {
	// 	MultipartFilter multipartFilter = new MultipartFilter();
	// 	multipartFilter.setMultipartResolverBeanName("multipartResolver");
	// 	return multipartFilter;
	// }
}
