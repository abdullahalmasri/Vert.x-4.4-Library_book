/*
  https://vertx.io/docs/vertx-sql-client-templates/java/
  A SQL template consumes named parameters and thus takes (by default) a map as parameters sources instead of a tuple.

  A SQL template produces (by default) a RowSet<Row> like a client PreparedQuery. In fact the template is a thin wrapper for a PreparedQuery.

  When you need to perform an insert or update operation and you do not care of the result, you can use
  SqlTemplate.forUpdate

 By default templates produce Row as result type.
 You can provide a custom RowMapper to achieve row level mapping

 Created 30.06.2023 Document from Vert.x
 */

package com.example.starter.repository;

import com.example.starter.Constants.ModelConstant;
import com.example.starter.model.Library;
import io.vertx.core.Future;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.sqlclient.RowIterator;
import io.vertx.sqlclient.SqlConnection;
import io.vertx.sqlclient.templates.SqlTemplate;

import java.util.*;

/**
 * The type Library repository.
 */
public class LibraryRepository {
    private static final Logger log = LoggerFactory.getLogger(LibraryRepository.class);

    /**
     * Instantiates a new Library repository.
     */
    public LibraryRepository() {
    }

    /**
     * Select all library future.
     *
     * @param sqlConnection the sql connection
     * @param limit         the limit
     * @param offset        the offset
     * @return the future of List of Libraries
     */
    public Future<List<Library>> selectAllLibrary(SqlConnection sqlConnection, int limit, int offset) {
        return SqlTemplate.forQuery(sqlConnection, ModelConstant.SELECT_ALL_LIBRARIES)
                .mapTo(Library.class)
                .execute(Map.of("limit", limit, "offset", offset))
                .map(rowSet -> {
                    final List<Library> libraries = new ArrayList<>();
                    rowSet.forEach(libraries::add);
                    return libraries;
                }).onSuccess(success -> log.info("the Libraries are getting restored from the database successfully and it count[{" + success.size() + "}]"))
                .onFailure(failure -> log.info("the Libraries are getting restored from the database the reason [{}]", failure));
    }

    /**
     * Select library by id future.
     *
     * @param sqlConnection the sql connection
     * @param id            the id
     * @return the future Library with the given id
     */
    public Future<Library> selectLibraryById(SqlConnection sqlConnection, UUID id) {
        return SqlTemplate.forQuery(sqlConnection, ModelConstant.SELECT_LIBRARY_BY_ID)
                .mapTo(Library.class)
                .execute(Collections.singletonMap("id", id))
                .map(rowSet -> {
                    final RowIterator<Library> libraryRowIterator = rowSet.iterator();
                    if (libraryRowIterator.hasNext()) {
                        return libraryRowIterator.next();
                    } else {
                        throw new NoSuchElementException("there is no Library with this id");
                    }
                }).onSuccess(success -> log.info("the Library are restored successfully with id [{" + success.getLibraryId() + "}]"))
                .onFailure(failure -> log.info("the id provided are not found in db more info [{}]", failure));
    }

    /**
     * Insert library future.
     *
     * @param sqlConnection the sql connection
     * @param library       the library
     * @return the future inserted library into the database
     */
    public Future<Library> insertLibrary(SqlConnection sqlConnection, Library library) {
        if (library.getLibraryId() == null) {
            library.setLibraryId(UUID.randomUUID());
        }
        return SqlTemplate.forUpdate(sqlConnection, ModelConstant.INSERT_LIBRARY)
                .mapFrom(Library.class)
                .mapTo(Library.class)
                .execute(library)
                .map(rowSet -> {
                    final RowIterator<Library> libraryRowIterator = rowSet.iterator();
                    if (libraryRowIterator.hasNext()) {
                        return library;
                    } else {
                        throw new IllegalStateException("the Library aren't stored in Db");
                    }
                }).onSuccess(success -> log.info("the Library successfully stored in DataBase with id [{" + success.getLibraryId() + "}]"))
                .onFailure(failure -> log.info("[{}]", failure));
    }

    /**
     * Update library future.
     *
     * @param sqlConnection the sql connection
     * @param library       the library
     * @return the future to be executed update of library with given id
     */
    public Future<Library> updateLibrary(SqlConnection sqlConnection, Library library) {
        return SqlTemplate.forUpdate(sqlConnection, ModelConstant.UPDATE_LIBRARY)
                .mapFrom(Library.class)
                .execute(library)
                .flatMap(rowSet -> {
                    log.info("the row set is [{" + rowSet + "}]");
                    if (rowSet.rowCount() > 0) {
                        return Future.succeededFuture(library);
                    } else {
                        throw new NoSuchElementException("the library aren't found in DB");
                    }
                }).onSuccess(success -> log.info("the Library get updated with id [{" + success.getLibraryId() + "}]"))
                .onFailure(failure -> log.info("the Library can't be update for [{}]", failure));
    }

    /**
     * Delete library future.
     *
     * @param sqlConnection the sql connection
     * @param id            the id
     * @return the future deleted library with the given id
     */
    public Future<Void> deleteLibrary(SqlConnection sqlConnection, UUID id) {
        return SqlTemplate.forUpdate(sqlConnection, ModelConstant.DELETE_LIBRARY)
                .execute(Collections.singletonMap("id", id))
                .flatMap(rowSet -> {
                    if (rowSet.rowCount() > 0) {
                        return Future.succeededFuture();
                    } else {
                        throw new NoSuchElementException("No Library found with this id to Delete");
                    }
                });
    }


}
