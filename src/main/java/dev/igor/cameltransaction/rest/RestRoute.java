package dev.igor.cameltransaction.rest;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import dev.igor.cameltransaction.dto.RefoundResponse;
import dev.igor.cameltransaction.dto.TransactionRequest;
import dev.igor.cameltransaction.dto.TransactionResponse;

@Component
public class RestRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        restConfiguration()
            .apiContextRouteId("swagger")
            .component("servlet")
            .contextPath("")
            .apiContextPath("/swagger")
            .apiProperty("api.title", "Swagger Service User")
            .apiProperty("api.description", "Swagger Service User")
            .apiProperty("api.version", "Swagger Service User")
            .apiProperty("host", "localhost")
            .apiProperty("port", "8080")
            .apiProperty("schemes", "http")
            .bindingMode(RestBindingMode.auto);

        rest("/transactions")
            .post()
                .id("rest-transaction-create")
                .description("Method responsible to create transaction")
                .bindingMode(RestBindingMode.auto)

                .type(TransactionRequest.class)
                .produces(MediaType.APPLICATION_JSON_VALUE)
                .outType(TransactionResponse.class)

                .responseMessage()
                    .code(HttpStatus.CREATED.value())
                    .message("CREATED")
                .endResponseMessage()
            .to("direct:post-transactions")
            .post("/{id}/devolution")
                .id("rest-transaction-refound")
                .description("Method responsible to refound transaction")
                .bindingMode(RestBindingMode.auto)

                .produces(MediaType.APPLICATION_JSON_VALUE)
                .outType(RefoundResponse.class)
                
                .responseMessage()
                    .code(HttpStatus.OK.value())
                    .message("OK")
                .endResponseMessage()
            .to("direct:refound-transactions");
    }
}
