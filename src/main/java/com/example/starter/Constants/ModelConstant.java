package com.example.starter.Constants;

/**
 * The type Model constant.
 */
public class ModelConstant {

  // DataBase Constants

  /**
   * The constant HOST_CONFIG.
   * the constant HOST_CONFIG related to the host configuration
   */
  public static final String HOST_CONFIG = "datasource.host";


  /**
   * The Constant PORT_CONFIG
   * where its related to the port of your database
   */
  public static final String PORT_CONFIG = "datasource.port";


  /**
   * The constant DATABASE_CONFIG.
   * where its related to the database name that created in your machine
   */
  public static final String DATABASE_CONFIG = "datasource.database";


  /**
   * The constant USERNAME_CONFIG.
   * where its related to the username of database configuration
   */
  public static final String USERNAME_CONFIG = "datasource.user";


  /**
   * The constant PASSWORD_CONFIG.
   * where its related to the password of database configuration
   */
  public static final String PASSWORD_CONFIG = "datasource.password";

  // ----------------------------------------------------------------

  // SQL Statements for books

  /**
   * The SELECT_ALL_BOOKS statement
   * Select statement to return all book in table
   */
  public static final String SELECT_ALL_BOOKS = "SELECT * FROM books LIMIT #{limit} OFFSET #{offset}";


  /**
   * The constant SELECT_BOOK_BY_ID.
   */
  public static final String SELECT_BOOK_BY_ID = "SELECT * FROM books WHERE id = #{id}";


  /**
   * The constant INSERT_BOOKS.
   */
  public static final String INSERT_BOOKS = "INSERT INTO books (id,author, country, image_link, language, link, pages, title, year, library_id)\n" +
    "VALUES (#{id},#{author}, #{country}, #{image_link}, #{language}, #{link}, #{pages}, #{title}, #{year},#{library_id}) RETURNING id;";


  /**
   * The constant UPDATE_BOOK.
   */
  public static final String UPDATE_BOOK = "UPDATE books SET author = #{author}, country = #{country}, image_link = #{image_link}, " +
    "language = #{language}, link = #{link}, pages = #{pages}, title = #{title}, year = #{year},library_id=#{library_id} WHERE id = #{id}";


  /**
   * The constant UPDATE_BOOKS_LANGUAGE.
   */
  public static final String UPDATE_BOOKS_LANGUAGE = "UPDATE books SET language = #{language} WHERE id =#{id}";


  /**
   * The constant DELETE_BOOK.
   */
  public static final String DELETE_BOOK = "DELETE FROM books WHERE id = #{id}";


  /**
   * The constant COUNT_BOOK.
   */
  public static final String COUNT_BOOK = "SELECT COUNT(*) AS total FROM books";



  // ----------------------------------------------------------------

  // SQL Statements for library

  /**
   * The constant SELECT_ALL_LIBRARIES.
   */
  public static final String SELECT_ALL_LIBRARIES = "SELECT * FROM library LIMIT #{limit} OFFSET #{offset}";


  /**
   * The constant SELECT_LIBRARY_BY_ID.
   */
  public static final String SELECT_LIBRARY_BY_ID = "SELECT * FROM library WHERE id = #{id}";


  /**
   * The constant INSERT_LIBRARY.
   */
  public static final String INSERT_LIBRARY = "INSERT INTO library (id,name)\n" +
    "VALUES (#{id},#{name}) RETURNING id;";


  /**
   * The constant UPDATE_LIBRARY.
   */
  public static final String UPDATE_LIBRARY = "UPDATE library SET name = #{name} WHERE id = #{id}";


  /**
   * The constant DELETE_LIBRARY.
   */
  public static final String DELETE_LIBRARY = "DELETE FROM library WHERE id = #{id}";

}
