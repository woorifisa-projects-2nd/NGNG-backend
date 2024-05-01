package com.ngng.api.global.security.jwt.custom;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

public class CustomHttpServletResponseWrapper extends HttpServletResponseWrapper {

    public CustomHttpServletResponseWrapper(HttpServletResponse response) {

        super(response);
    }

    @Override
    public void setHeader(String name, String value) {
        super.setHeader(name, value);
    }
}
