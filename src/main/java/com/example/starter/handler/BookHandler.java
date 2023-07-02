package com.example.starter.handler;

import com.example.starter.Utils.ResponseUtils;
import com.example.starter.model.Book;
import com.example.starter.model.BookGetAllResponse;
import com.example.starter.model.BookGetByIdResponse;
import com.example.starter.service.BookService;
import io.vertx.core.Future;
import io.vertx.ext.web.RoutingContext;

import java.util.UUID;

public class BookHandler {
    private static final String ID_PARAMETER = "id";
    private static final String PAGE_PARAMETER = "page";
    private static final String LIMIT_PARAMETER = "limit";

    private final BookService bookService;

    public BookHandler(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Read all books
     * It should return 200 OK in case of success
     * It should return 400 Bad Request, 404 Not Found or 500 Internal Server Error in case of failure
     *
     * @param rc Routing context
     * @return BookGetAllResponse
     */
    public Future<BookGetAllResponse> readAll(RoutingContext rc) {
        final String page = rc.queryParams().get(PAGE_PARAMETER);
        final String limit = rc.queryParams().get(LIMIT_PARAMETER);

        return bookService.getAllBooks(page, limit)
                .onSuccess(success -> ResponseUtils.buildOkResponse(rc, success))
                .onFailure(throwable -> ResponseUtils.buildErrorResponse(rc, throwable));
    }

    /**
     * Read one book
     * It should return 200 OK in case of success
     * It should return 400 Bad Request, 404 Not Found or 500 Internal Server Error in case of failure
     *
     * @param rc Routing context
     * @return BookGetByIdResponse
     */
    public Future<BookGetByIdResponse> readOne(RoutingContext rc) {
        final String id = rc.pathParam(ID_PARAMETER);

        return bookService.getBookWithId(UUID.fromString(id))
                .onSuccess(success -> ResponseUtils.buildOkResponse(rc, success))
                .onFailure(throwable -> ResponseUtils.buildErrorResponse(rc, throwable));
    }

    /**
     * Create one book
     * It should return 201 Created in case of success
     * It should return 400 Bad Request, 404 Not Found or 500 Internal Server Error in case of failure
     *
     * @param rc Routing context
     * @return BookGetByIdResponse
     */
    public Future<BookGetByIdResponse> create(RoutingContext rc) {
        final Book book = rc.body().asJsonObject().mapTo(Book.class);

        return bookService.saveBook(book)
                .onSuccess(success -> ResponseUtils.buildCreatedResponse(rc, success))
                .onFailure(throwable -> ResponseUtils.buildErrorResponse(rc, throwable));
    }

    /**
     * Update one book
     * It should return 200 OK in case of success
     * It should return 400 Bad Request, 404 Not Found or 500 Internal Server Error in case of failure
     *
     * @param rc Routing context
     * @return BookGetByIdResponse
     */
    public Future<BookGetByIdResponse> update(RoutingContext rc) {
        final String id = rc.pathParam(ID_PARAMETER);
        final Book book = rc.getBodyAsJson().mapTo(Book.class);
        book.setId(UUID.fromString(id));
        return bookService.updateBook(book)
                .onSuccess(success -> ResponseUtils.buildOkResponse(rc, success))
                .onFailure(throwable -> ResponseUtils.buildErrorResponse(rc, throwable));
    }

    /**
     * Delete one book
     * It should return 204 No Content in case of success
     * It should return 400 Bad Request, 404 Not Found or 500 Internal Server Error in case of failure
     *
     * @param rc Routing context
     * @return BookGetByIdResponse
     */
    public Future<Void> delete(RoutingContext rc) {
        final String id = rc.pathParam(ID_PARAMETER);

        return bookService.deleteBook(UUID.fromString(id))
                .onSuccess(success -> ResponseUtils.buildNoContentResponse(rc))
                .onFailure(throwable -> ResponseUtils.buildErrorResponse(rc, throwable));
    }
}
