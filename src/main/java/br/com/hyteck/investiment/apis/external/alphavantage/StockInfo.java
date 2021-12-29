package br.com.hyteck.investiment.apis.external.alphavantage;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
@Data
@NoArgsConstructor
public class StockInfo {
    List<Map<String,String>> bestMatches;
}
