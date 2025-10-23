package com.sanketika.course_backend.utils;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiEnvelope<T> {
    private Params params = new Params();
    private String id;
    private String ver;
    private String ts;
    private String responseCode;
    private ApiResult<T> result;

}
