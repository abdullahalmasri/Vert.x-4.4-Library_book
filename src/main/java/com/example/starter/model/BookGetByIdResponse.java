package com.example.starter.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class BookGetByIdResponse implements Serializable {

    private static final long serialVersionUID = 7621071075786169611L;

    @JsonProperty(value = "id")
    private final UUID id;

    @JsonProperty(value = "author")
    private final String author;

    @JsonProperty(value = "country")
    private final String country;

    @JsonProperty(value = "image_link")
    private final String imageLink;

    @JsonProperty(value = "language")
    private final String language;

    @JsonProperty(value = "link")
    private final String link;

    @JsonProperty(value = "pages")
    private final Integer pages;

    @JsonProperty(value = "title")
    private final String title;

    @JsonProperty(value = "year")
    private final Date year;

    @JsonProperty(value = "library_id")
    private final UUID libraryId;

    public BookGetByIdResponse(Book book) {
        this.id = book.getId();
        this.author = book.getAuthor();
        this.country = book.getCountry();
        this.imageLink = book.getImageUrl();
        this.language = book.getLanguage();
        this.link = book.getLink();
        this.pages = book.getPages();
        this.title = book.getTitle();
        this.year = book.getDate();
        this.libraryId = book.getLibraryId();
    }

    public UUID getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getCountry() {
        return country;
    }

    public String getImageLink() {
        return imageLink;
    }

    public String getLanguage() {
        return language;
    }

    public String getLink() {
        return link;
    }

    public Integer getPages() {
        return pages;
    }

    public String getTitle() {
        return title;
    }

    public Date getYear() {
        return year;
    }

    public UUID getLibraryId() {
        return libraryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookGetByIdResponse)) return false;
        BookGetByIdResponse that = (BookGetByIdResponse) o;
        return getId().equals(that.getId()) && getAuthor().equals(that.getAuthor()) && getCountry().equals(that.getCountry()) && getImageLink().equals(that.getImageLink()) && getLanguage().equals(that.getLanguage()) && getLink().equals(that.getLink()) && getPages().equals(that.getPages()) && getTitle().equals(that.getTitle()) && getYear().equals(that.getYear()) && getLibraryId().equals(that.getLibraryId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAuthor(), getCountry(), getImageLink(), getLanguage(), getLink(), getPages(), getTitle(), getYear(), getLibraryId());
    }

    @Override
    public String toString() {
        return "BookGetByIdResponse{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", country='" + country + '\'' +
                ", imageLink='" + imageLink + '\'' +
                ", language='" + language + '\'' +
                ", link='" + link + '\'' +
                ", pages=" + pages +
                ", title='" + title + '\'' +
                ", year=" + year +
                ", libraryId=" + libraryId +
                '}';
    }
}
