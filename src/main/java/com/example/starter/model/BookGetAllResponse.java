package com.example.starter.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class BookGetAllResponse implements Serializable {
    private static final long serialVersionUID = 345436434634346L;

    @JsonProperty(value = "total")
    private final int total;

    @JsonProperty(value = "limit")
    private final int limit;

    @JsonProperty(value = "page")
    private final int page;

    @JsonProperty(value = "books")
    private final List<BookGetByIdResponse> books;

    public BookGetAllResponse(int total,
                              int limit,
                              int page,
                              List<BookGetByIdResponse> books) {
        this.total = total;
        this.limit = limit;
        this.page = page;
        this.books = books;
    }

    public int getTotal() {
        return total;
    }

    public int getLimit() {
        return limit;
    }

    public int getPage() {
        return page;
    }

    public List<BookGetByIdResponse> getBooks() {
        return books;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookGetAllResponse)) return false;
        BookGetAllResponse that = (BookGetAllResponse) o;
        return getTotal() == that.getTotal() && getLimit() == that.getLimit() && getPage() == that.getPage() && getBooks().equals(that.getBooks());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTotal(), getLimit(), getPage(), getBooks());
    }

    @Override
    public String toString() {
        return "BookGetAllResponse{" +
                "total=" + total +
                ", limit=" + limit +
                ", page=" + page +
                ", books=" + books +
                '}';
    }
}
