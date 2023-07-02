package com.example.starter.service;

import com.example.starter.model.Book;
import com.example.starter.model.BookGetAllResponse;
import com.example.starter.model.BookGetByIdResponse;
import com.example.starter.repository.BookRepository;
import io.vertx.codegen.annotations.Nullable;
import io.vertx.core.Future;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.pgclient.PgPool;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * The type Book service imp.
 */
public class BookServiceImp implements BookService {
    private static final Logger log = LoggerFactory.getLogger(BookServiceImp.class);

    private final BookRepository bookRepository;
    private final PgPool dbClient;

    /**
     * Instantiates a new Book service imp.
     *
     * @param bookRepository the book repository
     * @param dbClient       the db client
     */
    public BookServiceImp(BookRepository bookRepository, PgPool dbClient) {
        this.bookRepository = bookRepository;
        this.dbClient = dbClient;
    }

    @Override
    public Future<BookGetAllResponse> getAllBooks(String p, String l) {
        final int page = Integer.valueOf(p);
        final int limit = Integer.valueOf(l);
        final int offset = (page - 1) * limit;
        return dbClient.withConnection(sqlConnection -> {
            return bookRepository.count(sqlConnection)
                    .flatMap(total ->
                            bookRepository.selectAll(sqlConnection, limit, offset)
                                    .map(result -> {
                                        final List<BookGetByIdResponse> books = result.stream()
                                                .map(BookGetByIdResponse::new)
                                                .collect(Collectors.toList());

                                        return new BookGetAllResponse(total, limit, offset, books);

                                    })
                    );
        }).onSuccess(success -> log.info("the result is " + success))
                .onFailure(failure -> log.info(failure.getMessage()));
    }

    @Override
    public Future<BookGetByIdResponse> getBookWithId(UUID id) {
        return dbClient.withConnection(sqlConnection ->
                bookRepository.selectById(sqlConnection, id).map(BookGetByIdResponse::new)
        ).onSuccess(success -> log.info("the book is restored with id " + success.getId()))
                .onFailure(failure -> log.info(failure.getMessage()));
    }

    @Override
    public Future<@Nullable BookGetByIdResponse> saveBook(Book book) {
        return dbClient.withTransaction(
                connection -> {
                    return bookRepository.insertBookIntoDb(connection, book)
                            .map(BookGetByIdResponse::new);
                })
                .onSuccess(success -> log.info("Create one book " + success))
                .onFailure(throwable -> log.info(throwable.getMessage()));
    }

    @Override
    public Future<@Nullable BookGetByIdResponse> updateBook(Book book) {
        return dbClient.withTransaction(sqlConnection -> {
            return bookRepository.updateBook(sqlConnection, book).map(BookGetByIdResponse::new);
        }).onSuccess(success -> log.info("Update book " + success))
                .onFailure(throwable -> log.info(throwable.getMessage()));
    }

    @Override
    public Future<@Nullable BookGetByIdResponse> updateLanguageOfBook(Book book) {
        return dbClient.withTransaction(sqlConnection -> {
            return bookRepository.updateBook(sqlConnection, book).map(BookGetByIdResponse::new);
        }).onSuccess(success -> log.info("Update book " + success))
                .onFailure(throwable -> log.info(throwable.getMessage()));
    }

    @Override
    public Future<Void> deleteBook(UUID id) {
        return dbClient.withConnection(sqlConnection -> {
            return bookRepository.delete(sqlConnection, id);
        });
    }

    @Override
    public Future<Integer> countBooks() {
        return dbClient.withTransaction(sqlConnection -> {
            return bookRepository.count(sqlConnection);
        });
    }
}
