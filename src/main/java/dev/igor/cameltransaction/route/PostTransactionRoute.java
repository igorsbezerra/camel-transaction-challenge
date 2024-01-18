package dev.igor.cameltransaction.route;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import dev.igor.cameltransaction.dto.TransactionRequest;
import dev.igor.cameltransaction.dto.TransactionResponse;
import dev.igor.cameltransaction.error.ApplicationError;

@Component
public class PostTransactionRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:post-transactions")
            .doTry()
                .removeHeaders("CamelHttp*")
                .setHeader(Exchange.HTTP_METHOD, constant(HttpMethod.POST.toString()))
                .setHeader(Exchange.HTTP_PATH, simple("/transactions"))
                .marshal().json(JsonLibrary.Jackson, TransactionRequest.class)
                .to("{{transaction.url}}")
                .unmarshal().json(JsonLibrary.Jackson, TransactionResponse.class)
            .endDoTry()
            .doCatch(Exception.class)
                .process(new ApplicationError())
            .end()
        .end();
    }
}
