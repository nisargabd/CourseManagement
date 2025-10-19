package com.sanketika.course_backend.utils;

import lombok.Data;

@Data
public class ApiEnvelope<T> {
    private Params params = new Params();
    private String id;
    private String ver;
    private String ts;
    private String responseCode;
    private ApiResult<T> result;

}
