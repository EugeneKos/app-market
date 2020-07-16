package ru.market.client.rest.impl;

import com.fasterxml.jackson.core.type.TypeReference;

import ru.market.client.exception.RestClientException;
import ru.market.client.http.HttpConnection;
import ru.market.client.http.HttpResponse;
import ru.market.client.http.impl.HttpRequestImpl;
import ru.market.client.http.impl.HttpRequestWithBodyImpl;
import ru.market.client.rest.CostRestClient;
import ru.market.client.url.UrlProvider;

import ru.market.dto.cost.CostDTO;
import ru.market.dto.cost.CostNoIdDTO;

import java.util.Set;

public class CostRestClientImpl extends AbstractRestClient implements CostRestClient {
    private HttpConnection httpConnection;

    public CostRestClientImpl(HttpConnection httpConnection, UrlProvider urlProvider) {
        super(urlProvider);
        this.httpConnection = httpConnection;
    }

    @Override
    public CostDTO create(CostNoIdDTO costNoIdDTO) throws RestClientException {
        TypeReference<CostDTO> typeReference = new TypeReference<CostDTO>() {};

        HttpResponse<CostDTO> httpResponse = httpConnection.put(
                new HttpRequestWithBodyImpl<>(createUrl("/cost"), costNoIdDTO), typeReference
        );

        checkResponse(httpResponse);

        return httpResponse.getResponseBody();
    }

    @Override
    public CostDTO update(CostDTO costDTO) throws RestClientException {
        TypeReference<CostDTO> typeReference = new TypeReference<CostDTO>() {};

        HttpResponse<CostDTO> httpResponse = httpConnection.post(
                new HttpRequestWithBodyImpl<>(createUrl("/cost"), costDTO), typeReference
        );

        checkResponse(httpResponse);

        return httpResponse.getResponseBody();
    }

    @Override
    public Set<CostDTO> getAllByCostLimitIdAndDate(Long costLimitId, String dateStr) throws RestClientException {
        TypeReference<Set<CostDTO>> typeReference = new TypeReference<Set<CostDTO>>() {};

        String path = String.format("/cost/%d/%s", costLimitId, dateStr);

        HttpResponse<Set<CostDTO>> httpResponse = httpConnection.get(new HttpRequestImpl(createUrl(path)), typeReference);

        checkResponse(httpResponse);

        return httpResponse.getResponseBody();
    }

    @Override
    public void deleteById(Long id) throws RestClientException {
        TypeReference<Void> typeReference = new TypeReference<Void>() {};

        HttpResponse<Void> httpResponse = httpConnection.delete(
                new HttpRequestImpl(createUrl("/cost/" + id)), typeReference
        );

        checkResponse(httpResponse);
    }
}
