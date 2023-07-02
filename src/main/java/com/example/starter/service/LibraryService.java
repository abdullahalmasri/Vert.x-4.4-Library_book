package com.example.starter.service;

import com.example.starter.model.Library;
import com.example.starter.model.LibraryGetAllResponse;
import com.example.starter.model.LibraryGetByIdResponse;
import io.vertx.codegen.annotations.Nullable;
import io.vertx.core.Future;

import java.util.UUID;

/**
 * The interface Library service.
 */
public interface LibraryService {
  /**
   * Gets all library.
   *
   * @param p the p
   * @param l the l
   * @return the all library
   */
  Future<LibraryGetAllResponse> getAllLibrary(String p, String l);

  /**
   * Gets library by id.
   *
   * @param id the id
   * @return the library by id
   */
  Future<LibraryGetByIdResponse> getLibraryById(UUID id);

  /**
   * Save library future.
   *
   * @param library the library
   * @return the future will save new Library
   */
  Future<@Nullable LibraryGetByIdResponse> saveLibrary(Library library);

  /**
   * Update library future.
   *
   * @param library the library
   * @return the future will update existing Library
   */
  Future<@Nullable LibraryGetByIdResponse> updateLibrary(Library library);

  /**
   * Delete library future.
   *
   * @param id the id
   * @return the future will delete Library with given Id
   */
  Future<Void> deleteLibrary(UUID id);

}
