package com.Travel.Travel.infraestructure.helpers;

import com.Travel.Travel.infraestructure.DTOs.CurrencyDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Currency;

@Component
public class ApiCurrencyConnectorHelper {

    private final WebClient currencyWebClient;

    @Value(value = "${api.base-currency}")
    private String baseCurrency;
    private static final String FROM_CURRENCY_QUERY_PARAM = "?source={source}";
    private static final String TO_CURRENCY_QUERY_PARAM = "&currencies={currencies}";
  //  private static final String AMOUNT_CURRENCY_QUERY_PARAM = "&amount={amount}";
    private static final String CURRENCY_PATH = "/currency_data/live";


    public ApiCurrencyConnectorHelper(WebClient currencyWebClient) {
        this.currencyWebClient = currencyWebClient;
    }

    public CurrencyDTO getCurrency(Currency currency) {
        return      currencyWebClient.get().uri(uri -> uri.path(CURRENCY_PATH)
                                                          .query(FROM_CURRENCY_QUERY_PARAM)
                                                          .query(TO_CURRENCY_QUERY_PARAM)
                                                          .build(baseCurrency, currency.getCurrencyCode()))
                                            .retrieve()
                                            .bodyToMono(CurrencyDTO.class) //convierte un currency en DTO
                                            .block();
    }
}