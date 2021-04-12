package com.elvin.latihandto.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class BookingDTO {

    @NotNull(message = "Harap isi nama!")
    @Column(name = "name")
    private String name;

    @NotNull
    @Email
    @Column(name = "email")
    private String email;

    @NotNull
    @Column(name = "msg")
    private String msg;

    @NotNull
    @Column(name = "product_id")
    private int productId;

    @NotNull
    @Column(name = "qty")
    private int qty;

    @Value("Pending")
    @Column(name = "status")
    private String status;
}
