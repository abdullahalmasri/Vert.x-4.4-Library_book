package com.example.starter.router;

import com.example.starter.handler.LibraryHandler;
import com.example.starter.handler.LibraryValidHandler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.LoggerFormat;
import io.vertx.ext.web.handler.LoggerHandler;

/**
 * The type Library router.
 */
public class LibraryRouter {

  private final Vertx vertx;
  private final LibraryHandler libraryHandler;
  private final LibraryValidHandler libraryValidHandler;

  /**
   * Instantiates a new Library router.
   *
   * @param vertx               the vertx
   * @param libraryHandler      the library handler
   * @param libraryValidHandler the library valid handler
   */
  public LibraryRouter(Vertx vertx, LibraryHandler libraryHandler, LibraryValidHandler libraryValidHandler) {
    this.vertx = vertx;
    this.libraryHandler = libraryHandler;
    this.libraryValidHandler = libraryValidHandler;
  }

  /**
   * Sets router.
   *
   * @param router the router
   */
  public void setRouter(Router router) {
    router.mountSubRouter("/api/v1", buildLibraryRouter());
  }

  private Router buildLibraryRouter() {
    final Router LibraryRouter = Router.router(vertx);

    LibraryRouter.route("/libraries*").handler(LoggerHandler.create());
    LibraryRouter.get("/libraries").handler(LoggerHandler.create(LoggerFormat.DEFAULT)).handler(libraryValidHandler.readAll()).handler(libraryHandler::readAll);
    LibraryRouter.get("/libraries/:id").handler(LoggerHandler.create(LoggerFormat.DEFAULT)).handler(libraryValidHandler.readOne()).handler(libraryHandler::readOne);
    LibraryRouter.post("/saveLib").consumes("application/json").handler(BodyHandler.create()).handler(libraryHandler::create);
    LibraryRouter.put("/libraries/:id").consumes("application/json").handler(BodyHandler.create()).handler(libraryHandler::update);
    LibraryRouter.delete("/libraries/:id").handler(LoggerHandler.create(LoggerFormat.DEFAULT)).handler(libraryValidHandler.delete()).handler(libraryHandler::delete);

    return LibraryRouter;
  }
}
