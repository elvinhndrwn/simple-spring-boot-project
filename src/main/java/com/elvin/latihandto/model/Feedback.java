package com.elvin.latihandto.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class Feedback {

    @NotNull(message = "Email harap diisi!")
    private String name;

    @NotNull(message = "Email harap diisi!")
    @Email
    private String email;

    @NotNull(message = "Email harap diisi!")
    @Min(10)
    private String msg;
}
