package com.example.starter.router;

import com.example.starter.handler.BookHandler;
import com.example.starter.handler.BookValidationHandler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.LoggerFormat;
import io.vertx.ext.web.handler.LoggerHandler;

/**
 * The type Book router.
 */
public class BookRouter {
    private final Vertx vertx;
    private final BookHandler bookHandler;
    private final BookValidationHandler bookValidationHandler;

    /**
     * Instantiates a new Book router.
     *
     * @param vertx                 the vertx
     * @param bookHandler           the book handler
     * @param bookValidationHandler the book validation handler
     */
    public BookRouter(Vertx vertx, BookHandler bookHandler, BookValidationHandler bookValidationHandler) {
        this.vertx = vertx;
        this.bookHandler = bookHandler;
        this.bookValidationHandler = bookValidationHandler;
    }


    /**
     * Set books API routes
     *
     * @param router Router
     */
    public void setRouter(Router router) {
        router.mountSubRouter("/api/v1", buildBookRouter());
    }

    private Router buildBookRouter() {
        final Router bookRouter = Router.router(vertx);

        bookRouter.route("/books*").handler(LoggerHandler.create());
        bookRouter.get("/books").handler(LoggerHandler.create(LoggerFormat.DEFAULT)).handler(bookValidationHandler.readAll()).handler(bookHandler::readAll);
        bookRouter.get("/books/:id").handler(LoggerHandler.create(LoggerFormat.DEFAULT)).handler(bookHandler::readOne);
        bookRouter.post("/books").consumes("application/json").handler(BodyHandler.create()).handler(bookHandler::create);
        bookRouter.put("/books/:id").consumes("application/json").handler(BodyHandler.create()).handler(bookHandler::update);

        //TODO handle patch Book Language API
        //    bookRouter.patch("/books/:id/language").consumes("application/json").handler(BodyHandler.create()).handler(bookHandler::update);
        bookRouter.delete("/books/:id").handler(bookHandler::delete);

        return bookRouter;
    }
}
