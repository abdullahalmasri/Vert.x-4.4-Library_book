package com.example.starter.handler;

import io.vertx.core.Vertx;
import io.vertx.ext.web.validation.RequestPredicate;
import io.vertx.ext.web.validation.ValidationHandler;
import io.vertx.ext.web.validation.builder.Bodies;
import io.vertx.ext.web.validation.builder.ParameterProcessorFactory;
import io.vertx.ext.web.validation.builder.Parameters;
import io.vertx.json.schema.SchemaParser;
import io.vertx.json.schema.SchemaRouter;
import io.vertx.json.schema.SchemaRouterOptions;
import io.vertx.json.schema.common.dsl.ObjectSchemaBuilder;

import static io.vertx.json.schema.common.dsl.Keywords.maxLength;
import static io.vertx.json.schema.common.dsl.Keywords.minLength;
import static io.vertx.json.schema.common.dsl.Schemas.*;

public class LibraryValidHandler {
  private final Vertx vertx;

  public LibraryValidHandler(Vertx vertx) {
    this.vertx = vertx;
  }
  public ValidationHandler readAll() {
    final SchemaParser schemaParser = buildSchemaParser();

    return ValidationHandler
      .builder(schemaParser)
      .queryParameter(buildPageQueryParameter())
      .queryParameter(buildLimitQueryParameter())
      .build();
  }


  public ValidationHandler readOne() {
    final SchemaParser schemaParser = buildSchemaParser();

    return ValidationHandler
      .builder(schemaParser)
//      .pathParameter(buildIdPathParameter())
      .build();
  }

  public ValidationHandler create() {
    final SchemaParser schemaParser = buildSchemaParser();
    final ObjectSchemaBuilder schemaBuilder = buildBodySchemaBuilder();

    return ValidationHandler
      .builder(schemaParser)
      .predicate(RequestPredicate.BODY_REQUIRED)
      .body(Bodies.json(schemaBuilder))
      .build();
  }

  public ValidationHandler update() {
    final SchemaParser schemaParser = buildSchemaParser();
    final ObjectSchemaBuilder schemaBuilder = buildBodySchemaBuilder();

    return ValidationHandler
      .builder(schemaParser)
      .predicate(RequestPredicate.BODY_REQUIRED)
      .body(Bodies.json(schemaBuilder))
//      .pathParameter(buildIdPathParameter())
      .build();
  }

  public ValidationHandler delete() {
    final SchemaParser schemaParser = buildSchemaParser();

    return ValidationHandler
      .builder(schemaParser)
//      .pathParameter(buildIdPathParameter())
      .build();
  }

  private SchemaParser buildSchemaParser() {
    return SchemaParser.createDraft7SchemaParser(SchemaRouter.create(vertx, new SchemaRouterOptions()));
  }

  private ObjectSchemaBuilder buildBodySchemaBuilder() {
    return objectSchema()
      .requiredProperty("name", stringSchema().with(minLength(1)).with(maxLength(255)));
  }

//  private ParameterProcessorFactory buildIdPathParameter() {
//    return Parameters.param("id", objectSchema());
//  }

  private ParameterProcessorFactory buildPageQueryParameter() {
    return Parameters.optionalParam("page", intSchema());
  }

  private ParameterProcessorFactory buildLimitQueryParameter() {
    return Parameters.optionalParam("limit", intSchema());
  }
}
