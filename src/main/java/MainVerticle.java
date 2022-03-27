import io.vertx.core.*;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;

public class MainVerticle extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        System.out.println("Calling from " + Thread.currentThread().getName());

        Router router = Router.router(vertx);

        router.get("/test").handler(ctx -> {
            vertx.executeBlocking(
                promise -> {
                    System.out.println("Work executed on " + Thread.currentThread().getName());
                    // Call some blocking API that takes a significant amount of time to return
                    try {
                        Thread.sleep(60000);
                        promise.complete("OKOKKOK");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                },
                    //https://vertx.io/docs/vertx-core/java/#blocking_code
                false,

                res -> {
                    System.out.println("The result is:  '" + res.result() + "' received on " + Thread.currentThread().getName());
                    HttpServerResponse response = ctx.response();
                    response.putHeader("content-type", "text/plain");

                    // Write to the response and end it
                    response.end("Hello World from Vert.x-Web!");
                }
            );
        });

        router.get("/test2").handler(ctx -> {
            HttpServerResponse response = ctx.response();
            response.putHeader("content-type", "text/plain");

            // Write to the response and end it
            response.end("Hello World from Vert.x-Web!!!!");
        });

        HttpServerOptions httpServerOptions = new HttpServerOptions().setLogActivity(true);

        HttpServer server = vertx.createHttpServer(httpServerOptions).requestHandler(router);

        // Now bind the server:
        server.listen(8080, res -> {
            if (res.succeeded()) {
                startPromise.complete();
                System.out.println("HTTP server started on port 8080");
            } else {
                startPromise.fail(res.cause());
            }
        });
    }
}
