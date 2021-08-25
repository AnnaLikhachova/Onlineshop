package com.likhachova.web.security;

import com.likhachova.model.Product;
import com.likhachova.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Map;

@AllArgsConstructor
@Getter
@Setter
public class Session {
    private long sessionId;
    private User user;
    private Map<Product,Integer> cart;
    private LocalDate expireDate;

    public Session(LocalDate expireDate, User user) {
        this.expireDate = expireDate;
        this.user = user;
    }
}
