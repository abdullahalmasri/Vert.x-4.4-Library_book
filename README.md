= Starter

image:https://img.shields.io/badge/vert.x-4.4.3-purple.svg[link="https://vertx.io"]

This application was generated using http://start.vertx.io

== Building


To package your application:
```
./mvnw clean package
```

To run your application:
```
./mvnw clean compile exec:java
```

== Help

* https://vertx.io/docs/[Vert.x Documentation]
* https://stackoverflow.com/questions/tagged/vert.x?sort=newest&pageSize=15[Vert.x Stack Overflow]
* https://groups.google.com/forum/?fromgroups#!forum/vertx[Vert.x User Group]
* https://discord.gg/6ry7aqPWXy[Vert.x Discord]
* https://gitter.im/eclipse-vertx/vertx-users[Vert.x Gitter]


Intellij IDE
go to Edit configurations 
select Application in the build and run past this ``io.vertx.core.Launcher`` and in program arguments ``run com.example.starter.MainVerticle``
and enjoy

