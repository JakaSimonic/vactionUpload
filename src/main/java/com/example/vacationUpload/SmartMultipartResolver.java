package com.example.vacationUpload;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.support.StandardServletMultipartResolver;

public class SmartMultipartResolver extends StandardServletMultipartResolver {
	@Override
	public boolean isMultipart(HttpServletRequest request) {
		if (request.getHeader("X-Upload-File") != null ) {
			return false;
		}
		return super.isMultipart(request);
	}

}
