package com.greenit.greenitapi.Entities.Caching;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Request {
    private List<?> body = new ArrayList<>();
    public Request(){
    }

    public Request setBody(List<?> o) {
        this.body = o;
        return this;
    }

    public List<?> getBody(){
        return this.body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return body.equals(request.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(body);
    }
}
