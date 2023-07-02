package com.example.starter.service;

import com.example.starter.model.Library;
import com.example.starter.model.LibraryGetAllResponse;
import com.example.starter.model.LibraryGetByIdResponse;
import com.example.starter.repository.LibraryRepository;
import io.vertx.codegen.annotations.Nullable;
import io.vertx.core.Future;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.pgclient.PgPool;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * The type Library service imp.
 */
public class LibraryServiceImp implements LibraryService {

    private static final Logger log = LoggerFactory.getLogger(LibraryServiceImp.class);

    private final LibraryRepository libraryRepository;
    private final PgPool dbClient;

    /**
     * Instantiates a new Library service imp.
     *
     * @param libraryRepository the library repository
     * @param dbClient          the db client
     */
    public LibraryServiceImp(LibraryRepository libraryRepository, PgPool dbClient) {
        this.libraryRepository = libraryRepository;
        this.dbClient = dbClient;
    }

    @Override
    public Future<LibraryGetAllResponse> getAllLibrary(String p, String l) {
        final int page = Integer.parseInt(p);
        final int limit = Integer.parseInt(l);
        final int offset = (page - 1) * limit;
        return dbClient.withConnection(sqlConnection -> {
            return libraryRepository.selectAllLibrary(sqlConnection, limit, offset)
                    .map(result -> {
                        final List<LibraryGetByIdResponse> libraries = result.stream()
                                .map(LibraryGetByIdResponse::new)
                                .collect(Collectors.toList());
                        return new LibraryGetAllResponse(libraries.size(), limit, offset, libraries);
                    }).onSuccess(success -> log.info("the result is " + success))
                    .onFailure(failure -> log.info(failure.getMessage()));
        });
    }

    @Override
    public Future<LibraryGetByIdResponse> getLibraryById(UUID id) {
        return dbClient.withConnection(sqlConnection ->
                libraryRepository.selectLibraryById(sqlConnection, id).map(LibraryGetByIdResponse::new)
        ).onSuccess(success -> log.info("the Library is restored with id " + success.getLibraryId()))
                .onFailure(failure -> log.info(failure.getMessage()));
    }

    @Override
    public Future<@Nullable LibraryGetByIdResponse> saveLibrary(Library library) {

        return dbClient.withConnection(sqlConnection -> {
            return libraryRepository.insertLibrary(sqlConnection, library)
                    .map(LibraryGetByIdResponse::new);
        })
                .onSuccess(success -> System.out.println("Create one Library " + success))
                .onFailure(throwable -> System.out.println(throwable.getMessage()));
    }

    @Override
    public Future<@Nullable LibraryGetByIdResponse> updateLibrary(Library library) {
        return dbClient.withConnection(sqlConnection -> {
            return libraryRepository.updateLibrary(sqlConnection, library)
                    .map(LibraryGetByIdResponse::new);
        }).onSuccess(success -> log.info("Update Library " + success))
                .onFailure(throwable -> log.info(throwable.getMessage()));
    }

    @Override
    public Future<Void> deleteLibrary(UUID id) {
        return dbClient.withConnection(sqlConnection -> {
            return libraryRepository.deleteLibrary(sqlConnection, id);
        });
    }
}
