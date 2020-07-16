package ru.market.client.rest.impl;

import com.fasterxml.jackson.core.type.TypeReference;

import ru.market.client.exception.RestClientException;
import ru.market.client.http.HttpConnection;
import ru.market.client.http.HttpResponse;
import ru.market.client.http.impl.HttpRequestImpl;
import ru.market.client.http.impl.HttpRequestWithBodyImpl;
import ru.market.client.rest.CostLimitRestClient;
import ru.market.client.url.UrlProvider;

import ru.market.dto.limit.CostLimitDTO;
import ru.market.dto.limit.CostLimitInfoDTO;
import ru.market.dto.limit.CostLimitNoIdDTO;

import java.util.Set;

public class CostLimitRestClientImpl extends AbstractRestClient implements CostLimitRestClient {
    private HttpConnection httpConnection;

    public CostLimitRestClientImpl(HttpConnection httpConnection, UrlProvider urlProvider) {
        super(urlProvider);
        this.httpConnection = httpConnection;
    }

    @Override
    public CostLimitDTO create(CostLimitNoIdDTO costLimitNoIdDTO) throws RestClientException {
        TypeReference<CostLimitDTO> typeReference = new TypeReference<CostLimitDTO>() {};

        HttpResponse<CostLimitDTO> httpResponse = httpConnection.put(
                new HttpRequestWithBodyImpl<>(createUrl("/cost-limit"), costLimitNoIdDTO), typeReference
        );

        checkResponse(httpResponse);

        return httpResponse.getResponseBody();
    }

    @Override
    public Set<CostLimitDTO> getAll() throws RestClientException {
        TypeReference<Set<CostLimitDTO>> typeReference = new TypeReference<Set<CostLimitDTO>>() {};

        HttpResponse<Set<CostLimitDTO>> httpResponse = httpConnection.get(
                new HttpRequestImpl(createUrl("/cost-limit")), typeReference
        );

        checkResponse(httpResponse);

        return httpResponse.getResponseBody();
    }

    @Override
    public CostLimitInfoDTO getCostLimitInfoById(Long id, String dateStr) throws RestClientException {
        TypeReference<CostLimitInfoDTO> typeReference = new TypeReference<CostLimitInfoDTO>() {};

        String path = String.format("/cost-limit/info/%d/%s", id, dateStr);

        HttpResponse<CostLimitInfoDTO> httpResponse = httpConnection.get(
                new HttpRequestImpl(createUrl(path)), typeReference
        );

        checkResponse(httpResponse);

        return httpResponse.getResponseBody();
    }

    @Override
    public void deleteById(Long id, Boolean rollbackOperations) throws RestClientException {
        TypeReference<Void> typeReference = new TypeReference<Void>() {};

        String path = String.format("/cost-limit/%d?rollbackOperations=%s", id, String.valueOf(rollbackOperations));

        HttpResponse<Void> httpResponse = httpConnection.delete(
                new HttpRequestImpl(createUrl(path)), typeReference
        );

        checkResponse(httpResponse);
    }
}
