package com.elvin.latihandto.response;

import lombok.Data;

@Data
public class ImportResponse {
    private String msg;

    public ImportResponse(String msg) {
        this.msg = msg;
    }
}
