package ru.market.client.rest.impl;

import com.fasterxml.jackson.core.type.TypeReference;

import ru.market.client.exception.RestClientException;
import ru.market.client.http.HttpConnection;
import ru.market.client.http.HttpResponse;
import ru.market.client.http.impl.HttpRequestImpl;
import ru.market.client.http.impl.HttpRequestWithBodyImpl;
import ru.market.client.rest.OperationRestClient;
import ru.market.client.url.UrlProvider;

import ru.market.dto.operation.OperationDTO;
import ru.market.dto.operation.OperationEnrollDebitDTO;
import ru.market.dto.operation.OperationFilterDTO;
import ru.market.dto.operation.OperationTransferDTO;

import java.util.Set;

public class OperationRestClientImpl extends AbstractRestClient implements OperationRestClient {
    private HttpConnection httpConnection;

    public OperationRestClientImpl(HttpConnection httpConnection, UrlProvider urlProvider) {
        super(urlProvider);
        this.httpConnection = httpConnection;
    }

    @Override
    public OperationDTO enrollment(OperationEnrollDebitDTO enrollDebitDTO) throws RestClientException {
        TypeReference<OperationDTO> typeReference = new TypeReference<OperationDTO>() {};

        HttpResponse<OperationDTO> httpResponse = httpConnection.put(
                new HttpRequestWithBodyImpl<>(createUrl("/operation/enrollment"), typeReference, enrollDebitDTO)
        );

        checkResponse(httpResponse);

        return httpResponse.getResponseBody();
    }

    @Override
    public OperationDTO debit(OperationEnrollDebitDTO enrollDebitDTO) throws RestClientException {
        TypeReference<OperationDTO> typeReference = new TypeReference<OperationDTO>() {};

        HttpResponse<OperationDTO> httpResponse = httpConnection.put(
                new HttpRequestWithBodyImpl<>(createUrl("/operation/debit"), typeReference, enrollDebitDTO)
        );

        checkResponse(httpResponse);

        return httpResponse.getResponseBody();
    }

    @Override
    public OperationDTO transfer(OperationTransferDTO transferDTO) throws RestClientException {
        TypeReference<OperationDTO> typeReference = new TypeReference<OperationDTO>() {};

        HttpResponse<OperationDTO> httpResponse = httpConnection.put(
                new HttpRequestWithBodyImpl<>(createUrl("/operation/transfer"), typeReference, transferDTO)
        );

        checkResponse(httpResponse);

        return httpResponse.getResponseBody();
    }

    @Override
    public Set<OperationDTO> getAllByMoneyAccountId(Long moneyAccountId) throws RestClientException {
        TypeReference<Set<OperationDTO>> typeReference = new TypeReference<Set<OperationDTO>>() {};

        HttpResponse<Set<OperationDTO>> httpResponse = httpConnection.get(
                new HttpRequestImpl<>(createUrl("/operation/money-account/" + moneyAccountId), typeReference)
        );

        checkResponse(httpResponse);

        return httpResponse.getResponseBody();
    }

    @Override
    public Set<OperationDTO> getAllByMoneyAccountIdAndFilter(Long moneyAccountId, OperationFilterDTO filterDTO)
            throws RestClientException {

        TypeReference<Set<OperationDTO>> typeReference = new TypeReference<Set<OperationDTO>>() {};

        HttpResponse<Set<OperationDTO>> httpResponse = httpConnection.post(
                new HttpRequestWithBodyImpl<>(
                        createUrl("/operation/money-account/" + moneyAccountId), typeReference, filterDTO
                )
        );

        checkResponse(httpResponse);

        return httpResponse.getResponseBody();
    }
}
