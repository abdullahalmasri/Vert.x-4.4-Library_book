package com.example.starter.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class LibraryGetAllResponse implements Serializable {

  private static final long serialVersionUID = -8964658883487451260L;

  @JsonProperty(value = "total")
  private final int total;

  @JsonProperty(value = "limit")
  private final int limit;

  @JsonProperty(value = "page")
  private final int page;

  @JsonProperty(value = "library")
  private final List<LibraryGetByIdResponse> libraries;

  public LibraryGetAllResponse(int total,
                            int limit,
                            int page,
                            List<LibraryGetByIdResponse> libraries) {
    this.total = total;
    this.limit = limit;
    this.page=page;
    this.libraries = libraries;
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

  public List<LibraryGetByIdResponse> getLibraries() {
    return libraries;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof LibraryGetAllResponse)) return false;
    LibraryGetAllResponse that = (LibraryGetAllResponse) o;
    return getTotal() == that.getTotal() && getLimit() == that.getLimit() && getPage() == that.getPage() && getLibraries().equals(that.getLibraries());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getTotal(), getLimit(), getPage(), getLibraries());
  }

  @Override
  public String toString() {
    return "LibraryGetAllResponse{" +
      "total=" + total +
      ", limit=" + limit +
      ", page=" + page +
      ", libraries=" + libraries +
      '}';
  }
}
