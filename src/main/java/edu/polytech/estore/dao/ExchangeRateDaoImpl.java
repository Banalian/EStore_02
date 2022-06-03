package edu.polytech.estore.dao;

import edu.polytech.estore.model.exchangerate.WsExchangeRateResult;

import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

@Stateless
public class ExchangeRateDaoImpl implements ExchangeRateDAO {

    private static final String URI = "https://api.apilayer.com/exchangerates_data";

    private static final String API_KEY = "62xscrcaBx9AlFTW9S9g4PQrOZg2g75i";


    @Override
    public WsExchangeRateResult getConvertion(String currency, Double amount) {
        Client client = ClientBuilder.newClient();

        return client.target(URI)
                .path("convert")
                .queryParam("from", "EUR")
                .queryParam("to", currency)
                .queryParam("amount", amount)
                .request(MediaType.APPLICATION_JSON)
                .header("apikey", API_KEY)
                .get(WsExchangeRateResult.class);
    }


    @Override
    public double getConvertionRate(String from, String to){
        Client client = ClientBuilder.newClient();

        return client.target(URI)
                .path("convert")
                .queryParam("from", from)
                .queryParam("to", to)
                .queryParam("amount", 1)
                .request(MediaType.APPLICATION_JSON)
                .header("apikey", API_KEY)
                .get(WsExchangeRateResult.class).getInfo().getRate();
    }

}

