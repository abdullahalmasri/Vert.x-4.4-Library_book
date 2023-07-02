package com.example.starter.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class LibraryGetByIdResponse implements Serializable {

    private static final long serialVersionUID = 77743243726736L;

    @JsonProperty(value = "id")
    @Column(name = "id")
    private final UUID libraryId;

    @JsonProperty(value = "name")
    @Column(name = "name")
    private final String libraryName;

    public LibraryGetByIdResponse(Library library) {
        this.libraryId = library.getLibraryId();
        this.libraryName = library.getLibraryName();
    }

    public UUID getLibraryId() {
        return libraryId;
    }

    public String getLibraryName() {
        return libraryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LibraryGetByIdResponse)) return false;
        LibraryGetByIdResponse that = (LibraryGetByIdResponse) o;
        return getLibraryId().equals(that.getLibraryId()) && getLibraryName().equals(that.getLibraryName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLibraryId(), getLibraryName());
    }

    @Override
    public String toString() {
        return "LibraryGetByIdResponse{" +
                "libraryId=" + libraryId +
                ", libraryName='" + libraryName + '\'' +
                '}';
    }
}
