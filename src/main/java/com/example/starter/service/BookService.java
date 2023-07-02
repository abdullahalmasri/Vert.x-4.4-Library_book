package com.example.starter.service;

import com.example.starter.model.Book;
import com.example.starter.model.BookGetAllResponse;
import com.example.starter.model.BookGetByIdResponse;
import io.vertx.codegen.annotations.Nullable;
import io.vertx.core.Future;

import java.util.UUID;

/**
 * The interface Book service.
 */
public interface BookService {

  /**
   * Gets all books.
   *
   * @param p the p
   * @param l the l
   * @return the all books
   */
  Future<BookGetAllResponse> getAllBooks(String p, String l);

  /**
   * Gets book with id.
   *
   * @param id the id
   * @return the book with id
   */
  Future<BookGetByIdResponse> getBookWithId(UUID id);

  /**
   * Save book future.
   *
   * @param book the book
   * @return the future save new Book
   */
  Future<@Nullable BookGetByIdResponse> saveBook(Book book);

  /**
   * Update book future.
   *
   * @param book the book
   * @return the future will update book
   */
  Future<@Nullable BookGetByIdResponse> updateBook(Book book);

  /**
   * Update language of book future.
   *
   * @param book the book
   * @return the future update language in certain book
   */
  Future<@Nullable BookGetByIdResponse> updateLanguageOfBook(Book book);

  /**
   * Delete book future.
   *
   * @param id the id
   * @return the future will delete book with giving id
   */
  Future<Void> deleteBook(UUID id);

  /**
   * Count books future.
   *
   * @return the future
   */
  Future<Integer> countBooks();

}
