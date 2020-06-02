package ru.market.dto.operation;

import ru.market.dto.result.ResultDTO;
import ru.market.dto.result.ResultStatus;

public class OperationResultDTO extends ResultDTO {
    private Long operationId;

    public OperationResultDTO() {
    }

    public OperationResultDTO(ResultStatus status, String description) {
        super(status, description);
    }

    public OperationResultDTO(ResultStatus status, String description, Long operationId) {
        super(status, description);
        this.operationId = operationId;
    }

    public Long getOperationId() {
        return operationId;
    }

    public void setOperationId(Long operationId) {
        this.operationId = operationId;
    }
}
