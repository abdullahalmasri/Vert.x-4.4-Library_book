package com.example.starter.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;


public class Book implements Serializable {

    private static final long serialVersionUID = 10430432030329403L;
    @JsonProperty(value = "id")
    private UUID id;
    @JsonProperty(value = "author")
    private String author;
    @JsonProperty(value = "country")
    private String country;
    @JsonProperty(value = "title")
    private String title;
    @JsonProperty(value = "language")
    private String language;
    @JsonProperty(value = "link")
    private String link;
    @JsonProperty(value = "pages")
    private int pages;
    @JsonProperty(value = "image_link")
    private String imageUrl;
    @JsonProperty(value = "year")
    private Date date;
    @JsonProperty(value = "library_id")
    private UUID libraryId;

    public Book() {
    }

    public Book(UUID id, String author, String country, String title, String language, String link, int pages, String imageUrl, Date date, UUID libraryId) {
        this.id = id;
        this.author = author;
        this.country = country;
        this.title = title;
        this.language = language;
        this.link = link;
        this.pages = pages;
        this.imageUrl = imageUrl;
        this.date = date;
        this.libraryId = libraryId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public UUID getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(UUID libraryId) {
        this.libraryId = libraryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return getPages() == book.getPages() && getId().equals(book.getId()) && getAuthor().equals(book.getAuthor()) && getCountry().equals(book.getCountry()) && getTitle().equals(book.getTitle()) && getLanguage().equals(book.getLanguage()) && getLink().equals(book.getLink()) && getImageUrl().equals(book.getImageUrl()) && getDate().equals(book.getDate()) && getLibraryId().equals(book.getLibraryId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAuthor(), getCountry(), getTitle(), getLanguage(), getLink(), getPages(), getImageUrl(), getDate(), getLibraryId());
    }

    @Override
    public String toString() {
        return "book{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", country='" + country + '\'' +
                ", title='" + title + '\'' +
                ", language='" + language + '\'' +
                ", link='" + link + '\'' +
                ", pages=" + pages +
                ", imageUrl='" + imageUrl + '\'' +
                ", date=" + date +
                ", libraryId=" + libraryId +
                '}';
    }
}
