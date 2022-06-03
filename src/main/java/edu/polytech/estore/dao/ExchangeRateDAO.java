package edu.polytech.estore.dao;

import edu.polytech.estore.model.exchangerate.WsExchangeRateResult;

public interface ExchangeRateDAO {
    WsExchangeRateResult getConvertion(String currency, Double amount);

    double getConvertionRate(String from, String to);
}
