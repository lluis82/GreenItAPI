package com.greenit.greenitapi.Entities.Caching;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Response {
    private List<?> body = new ArrayList<>();
    public Response(){}

    public Response setBody(List<?> o) {
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
        Response response = (Response) o;
        return body.equals(response.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(body);
    }
}
