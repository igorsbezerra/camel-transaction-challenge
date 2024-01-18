package dev.igor.cameltransaction.route;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import dev.igor.cameltransaction.dto.RefoundResponse;
import dev.igor.cameltransaction.error.ApplicationError;

@Component
public class RefoundPostTransactionRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:refound-transactions")
            .doTry()
                .log("chegou")
                .removeHeaders("CamelHttp*")
                .setHeader(Exchange.HTTP_METHOD, constant(HttpMethod.POST.toString()))
                .setHeader(Exchange.HTTP_PATH, simple("/transactions/${header.id}/devolution"))
                .to("{{transaction.url}}")
                .unmarshal().json(JsonLibrary.Jackson, RefoundResponse.class)
            .endDoTry()
            .doCatch(Exception.class)
                .process(new ApplicationError())
            .end()
        .end();
    }
}
