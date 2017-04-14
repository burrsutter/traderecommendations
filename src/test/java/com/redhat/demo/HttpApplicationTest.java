package com.redhat.demo;

import io.vertx.core.Vertx;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.ext.web.client.WebClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.redhat.demo.http.TradeRecommendations;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(VertxUnitRunner.class)
public class HttpApplicationTest {

    private Vertx vertx;
    private WebClient client;

    @Before
    public void before(TestContext context) {
        vertx = Vertx.vertx();
        vertx.exceptionHandler(context.exceptionHandler());
        vertx.deployVerticle(TradeRecommendations.class.getName(), context.asyncAssertSuccess());
        client = WebClient.create(vertx);
    }

    @After
    public void after(TestContext context) {
        vertx.close(context.asyncAssertSuccess());
    }

    @Test
    public void callRecommendationTest(TestContext context) {
        // Send a request and get a response
        Async async = context.async();
        client.get(8080, "localhost", "/")
            .send(resp -> {
                assertThat(resp.succeeded()).isTrue();
                assertThat(resp.result().statusCode()).isEqualTo(200);
                String order = resp.result().bodyAsJsonObject().getString("order");
                assertThat(order).startsWith("BUY");
                async.complete();
            });
    }

}
