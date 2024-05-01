package com.ngng.api.product.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;


    public class CustomMultipartFile implements MultipartFile {

        private final byte[] bytes;
        String name;
        String originalFilename;
        String contentType;
        boolean isEmpty;
        long size;

        InputStream inputStream;

        public CustomMultipartFile(byte[] bytes, String name, String originalFilename, String contentType,
                                   long size) {
            this.bytes = bytes;
            this.name = name;
            this.originalFilename = originalFilename;
            this.contentType = contentType;
            this.size = size;
            this.isEmpty = false;
            this.inputStream= new ByteArrayInputStream(bytes);
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getOriginalFilename() {
            return originalFilename;
        }

        @Override
        public String getContentType() {
            return contentType;
        }

        @Override
        public boolean isEmpty() {
            return isEmpty;
        }

        @Override
        public long getSize() {
            return size;
        }

        @Override
        public byte[] getBytes() throws IOException {
            return bytes;
        }

        @Override
        public InputStream getInputStream() {
            return this.inputStream;
        }

        @Override
        public void transferTo(File dest) throws IllegalStateException {
        }

    }
