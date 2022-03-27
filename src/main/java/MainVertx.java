import io.vertx.core.DeploymentOptions;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

public class MainVertx {
    public static void main(String[] args) {
        VertxOptions vertxOptions = new VertxOptions(); //.setEventLoopPoolSize(3).setWorkerPoolSize(3);

        Vertx vertx = Vertx.vertx(vertxOptions);

        Verticle mainVertical = new MainVerticle();

        DeploymentOptions deploymentOptions = new DeploymentOptions();// .setWorker(true);
        vertx.deployVerticle(mainVertical, deploymentOptions);
    }
}
