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
import com.example.starter.model.Book;
import io.vertx.core.Future;
import io.vertx.sqlclient.RowIterator;
import io.vertx.sqlclient.SqlConnection;
import io.vertx.sqlclient.templates.RowMapper;
import io.vertx.sqlclient.templates.SqlTemplate;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import java.util.*;

/**
 * The type Book repository.
 */
public class BookRepository {
  private static final Logger log = LoggerFactory.getLogger(BookRepository.class);

  /**
   * Instantiates a new Book repository.
   */
  public BookRepository() {
  }


  /**
   * Select all future.
   *
   * @param sqlConnection the sql connection
   * @param limit         the limit
   * @param offset        the offset
   * @return the future List of Books
   */
  public Future<List<Book>> selectAll(SqlConnection sqlConnection, int limit, int offset) {
    return SqlTemplate.forQuery(sqlConnection, ModelConstant.SELECT_ALL_BOOKS)
      .mapTo(Book.class)
      .execute(Map.of("limit", limit, "offset", offset))
      .map(rowSet -> {
        final List<Book> books = new ArrayList<>();
        rowSet.forEach(books::add);
        return books;
      }).onSuccess(success -> log.info("the books are restore from database successfully with Element size [{}]", (Throwable) success))
      .onFailure(failure -> log.error("the books are not restored from database successfully [{}]", failure));
  }

  /**
   * Select by id future.
   *
   * @param sqlConnection the sql connection
   * @param uuid          the uuid
   * @return the future book object with given id
   */
  public Future<Book> selectById(SqlConnection sqlConnection, UUID uuid) {
    return SqlTemplate.forQuery(sqlConnection, ModelConstant.SELECT_BOOK_BY_ID)
      .mapTo(Book.class)
      .execute(Collections.singletonMap("id", uuid))
      .map(rowSet -> {
        final RowIterator<Book> book = rowSet.iterator();
        if (book.hasNext()) {
          return book.next();
        } else {
          throw new NoSuchElementException("there is no book with this id");
        }
      }).onSuccess(success -> log.info(success+" returning [{}]"))
      .onFailure(failure -> log.info("the books are not restored from database successfully [{}]", failure));
  }

  /**
   * Insert book into db future.
   *
   * @param sqlConnection the sql connection
   * @param book          the book
   * @return the future inserted book into database
   */
  public Future<Book> insertBookIntoDb(SqlConnection sqlConnection, Book book) {
    if(book.getId()==null){
      book.setId(UUID.randomUUID());
    }
    return SqlTemplate.forUpdate(sqlConnection, ModelConstant.INSERT_BOOKS)
      .mapFrom(Book.class)
      .mapTo(Book.class)
      .execute(book)
      .map(rowSet -> {
        final RowIterator<Book> bookRowIterator = rowSet.iterator();
        if (bookRowIterator.hasNext()) {
//          book.setId(UUID.randomUUID());
          return book;
        } else {
          throw new IllegalStateException("can't insert book");
        }
      }).onSuccess(success -> log.info("the book has been stored in DB .. [{}]"+ success))
      .onFailure(failure -> log.info("the book are either missing requirement or wrong schema [{}]", failure));
  }

  /**
   * Update book future.
   *
   * @param sqlConnection the sql connection
   * @param book          the book
   * @return the future updated book
   */
  public Future<Book> updateBook(SqlConnection sqlConnection, Book book) {
    return SqlTemplate.forUpdate(sqlConnection, ModelConstant.UPDATE_BOOK)
      .mapFrom(Book.class)
      .execute(book)
      .flatMap(rowSet -> {
        if (rowSet.rowCount() > 0) {
          return Future.succeededFuture(book);
        } else {
          throw new NoSuchElementException("the book aren't found in DB");
        }
      }).onSuccess(success -> log.info("the book has been stored in DB .. [{}]" +success))
      .onFailure(failure -> log.info("[{}]", failure));

  }

  /**
   * Update language of book future.
   *
   * @param sqlConnection the sql connection
   * @param book          the book
   * @return the future update language of book
   */
  public Future<Book> updateLanguageOfBook(SqlConnection sqlConnection, Book book) {
    return SqlTemplate.forUpdate(sqlConnection, ModelConstant.UPDATE_BOOKS_LANGUAGE)
      .mapFrom(Book.class)
      .execute(book)
      .flatMap(rowSet -> {
        if (rowSet.rowCount() > 0) {
          return Future.succeededFuture(book);
        } else {
          throw new NoSuchElementException("the book aren't found in DB");
        }
      }).onSuccess(success -> log.info("the book has been stored in DB .. [{}]"+ success))
      .onFailure(failure -> log.info("the book are either missing requirement or wrong schema [{}]", failure));
  }

  /**
   * Delete future.
   *
   * @param connection the connection
   * @param id         the id
   * @return the future deleted book from the database
   */
  public Future<Void> delete(SqlConnection connection,
                             UUID id) {
    return SqlTemplate
      .forUpdate(connection, ModelConstant.DELETE_BOOK)
      .execute(Collections.singletonMap("id", id))
      .flatMap(rowSet -> {
        if (rowSet.rowCount() > 0) {
          return Future.succeededFuture();
        } else {
          throw new NoSuchElementException("No Book with id found");
        }
      });
  }


  /**
   * Count future.
   *
   * @param connection the connection
   * @return the future counted the books in our Database
   */
  public Future<Integer> count(SqlConnection connection) {
    final RowMapper<Integer> ROW_MAPPER = row -> row.getInteger("total");

    return SqlTemplate
      .forQuery(connection, ModelConstant.COUNT_BOOK)
      .mapTo(ROW_MAPPER)
      .execute(Collections.emptyMap())
      .map(rowSet -> rowSet.iterator().next())
      .onSuccess(success -> log.info("the count Books are successfully returned .. [{}]"+ success))
      .onFailure(failure -> log.info("can't count books reason below [{}]", failure));
  }
}
