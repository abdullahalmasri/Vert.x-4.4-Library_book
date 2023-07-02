package com.example.starter.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class Library implements Serializable {

    private static final long serialVersionUID = -423483278953723L;


    @JsonProperty(value = "id")
    @Column(name = "id")
    private UUID libraryId;

    @JsonProperty(value = "name")
    @Column(name = "name")
    private String libraryName;

    public Library() {
    }

    public Library(UUID libraryId, String libraryName) {
        this.libraryId = libraryId;
        this.libraryName = libraryName;
    }

    public UUID getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(UUID libraryId) {
        this.libraryId = libraryId;
    }

    public String getLibraryName() {
        return libraryName;
    }

    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Library)) return false;
        Library library = (Library) o;
        return getLibraryId().equals(library.getLibraryId()) && getLibraryName().equals(library.getLibraryName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLibraryId(), getLibraryName());
    }

    @Override
    public String toString() {
        return "library{" +
                "libraryId=" + libraryId +
                ", libraryName='" + libraryName + '\'' +
                '}';
    }
}
