package com.example.starter;

import com.example.starter.SQL.PostSqlConn;
import com.example.starter.handler.*;
import com.example.starter.repository.BookRepository;
import com.example.starter.repository.LibraryRepository;
import com.example.starter.router.BookRouter;
import com.example.starter.router.HealthCheckRouter;
import com.example.starter.router.LibraryRouter;
import com.example.starter.router.MetricsRouter;
import com.example.starter.service.BookService;
import com.example.starter.service.BookServiceImp;
import com.example.starter.service.LibraryService;
import com.example.starter.service.LibraryServiceImp;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.pgclient.PgPool;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class apiVerticle extends AbstractVerticle {
  @Override
  public void start(Promise<Void> promise) throws Exception {
    final PgPool dbClient = PostSqlConn.buildDbClient(vertx);
    final LibraryRepository libraryRepository = new LibraryRepository();
    final LibraryService libraryService = new LibraryServiceImp(libraryRepository, dbClient);
    final LibraryHandler libraryHandler = new LibraryHandler(libraryService);
    final LibraryValidHandler libraryValidHandler = new LibraryValidHandler(vertx);
    final LibraryRouter libraryRouter = new LibraryRouter(vertx, libraryHandler, libraryValidHandler);
    final BookRepository bookRepository = new BookRepository();
    final BookService bookService = new BookServiceImp(bookRepository,dbClient);
    final BookHandler bookHandler = new BookHandler(bookService);
    final BookValidationHandler bookValidationHandler = new BookValidationHandler(vertx);
    final BookRouter bookRouter = new BookRouter(vertx, bookHandler, bookValidationHandler);

    final Router router = Router.router(vertx);
    ErrorHandler.buildHandler(router);
    HealthCheckRouter.setRouter(vertx, router, dbClient);
    MetricsRouter.setRouter(router);
    bookRouter.setRouter(router);
    libraryRouter.setRouter(router);
    buildHttpServer(vertx, promise, router);
  }


  /**
   * Run HTTP server on port 8888 with specified routes
   *
   * @param vertx   Vertx context
   * @param promise Callback
   * @param router  Router
   */
  private void buildHttpServer(Vertx vertx,
                               Promise<Void> promise,
                               Router router) {
    final int port = 8888;

    vertx.createHttpServer()
      .requestHandler(router)
      .listen(port, http -> {
        log.debug("Starting HTTP server");
        if (http.succeeded()) {
          promise.complete();
        } else {
          promise.fail(http.cause());
        }
      });
  }
}
