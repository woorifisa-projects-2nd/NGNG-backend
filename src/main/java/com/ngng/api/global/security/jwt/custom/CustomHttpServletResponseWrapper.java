package com.ngng.api.global.security.jwt.custom;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.util.Collection;

public class CustomHttpServletResponseWrapper extends HttpServletResponseWrapper {

    private Collection<String> customHeaders;

    public CustomHttpServletResponseWrapper(HttpServletResponse response) {
//    public CustomHttpServletResponseWrapper(HttpServletResponse response, Collection<String> customHeaders) {

        super(response);
//        this.customHeaders = customHeaders;
    }

    @Override
    public void setHeader(String name, String value) {
        super.setHeader(name, value);
    }
}
