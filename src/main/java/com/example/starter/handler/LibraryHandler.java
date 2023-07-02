package com.example.starter.handler;

import com.example.starter.Utils.ResponseUtils;
import com.example.starter.model.Library;
import com.example.starter.model.LibraryGetAllResponse;
import com.example.starter.model.LibraryGetByIdResponse;
import com.example.starter.service.LibraryService;
import io.vertx.core.Future;
import io.vertx.ext.web.RoutingContext;

import java.util.UUID;

/**
 * The type Library handler.
 */
public class LibraryHandler {
  private static final String ID_PARAMETER = "id";
  private static final String PAGE_PARAMETER = "page";
  private static final String LIMIT_PARAMETER = "limit";

  private final LibraryService libraryService;

  /**
   * Instantiates a new Library handler.
   *
   * @param libraryService the library service
   */
  public LibraryHandler(LibraryService libraryService) {
    this.libraryService = libraryService;
  }


  /**
   * Read all future.
   *
   * @param rc the rc
   * @return the future will be returned to library service to get all Libraries
   */
  public Future<LibraryGetAllResponse> readAll(RoutingContext rc) {
    final String page = rc.queryParams().get(PAGE_PARAMETER);
    final String limit = rc.queryParams().get(LIMIT_PARAMETER);

    return libraryService.getAllLibrary(page, limit)
      .onSuccess(success -> ResponseUtils.buildOkResponse(rc, success))
      .onFailure(throwable -> ResponseUtils.buildErrorResponse(rc, throwable));
  }

  /**
   * Read one future.
   *
   * @param rc the rc
   * @return the future will be returned to library service to get certain Library
   */
  public Future<LibraryGetByIdResponse> readOne(RoutingContext rc) {
    final String id = rc.pathParam(ID_PARAMETER);

    return libraryService.getLibraryById(UUID.fromString(id))
      .onSuccess(success -> ResponseUtils.buildOkResponse(rc, success))
      .onFailure(throwable -> ResponseUtils.buildErrorResponse(rc, throwable));
  }

  /**
   * Create future.
   *
   * @param rc the rc
   * @return the future will return to Library service to create new instance of library
   */
  public Future<LibraryGetByIdResponse> create(RoutingContext rc) {
    final Library lib = rc.body().asJsonObject().mapTo(Library.class);

    return libraryService.saveLibrary(lib)
      .onSuccess(success -> ResponseUtils.buildCreatedResponse(rc, success))
      .onFailure(throwable -> ResponseUtils.buildErrorResponse(rc, throwable));
  }

  /**
   * Update future.
   *
   * @param rc the rc
   * @return the future will call Library service to update certain Library
   */
  public Future<LibraryGetByIdResponse> update(RoutingContext rc) {
    final String id = rc.pathParam(ID_PARAMETER);
    final Library library = rc.body().asJsonObject().mapTo(Library.class);
    library.setLibraryId(UUID.fromString(id));
    return libraryService.updateLibrary(library)
      .onSuccess(success -> ResponseUtils.buildOkResponse(rc, success))
      .onFailure(throwable -> ResponseUtils.buildErrorResponse(rc, throwable));
  }


  /**
   * Delete future.
   *
   * @param rc the rc
   * @return the future will call Library service to delete Library with given id
   */
  public Future<Void> delete(RoutingContext rc) {
    final String id = rc.pathParam(ID_PARAMETER);

    return libraryService.deleteLibrary(UUID.fromString(id))
      .onSuccess(success -> ResponseUtils.buildNoContentResponse(rc))
      .onFailure(throwable -> ResponseUtils.buildErrorResponse(rc, throwable));
  }

}
