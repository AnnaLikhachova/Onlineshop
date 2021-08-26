package com.likhachova.web.security;

import com.likhachova.model.Product;
import com.likhachova.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;


@AllArgsConstructor
@Getter
@Setter
public class Session {
    private String sessionId;
    private User user;
    private Map<Product,Integer> cart;
    private LocalDate expireDate;

    public Session(LocalDate expireDate, User user) {
        this.expireDate = expireDate;
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return Objects.equals(sessionId, session.sessionId) &&
                Objects.equals(user, session.user) &&
                Objects.equals(cart, session.cart) &&
                Objects.equals(expireDate, session.expireDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId, user, cart, expireDate);
    }
}
