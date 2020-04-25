package ru.market.dto.result;

public class ResultDTO {
    private ResultStatus status;
    private String description;

    public ResultDTO() {
    }

    public ResultDTO(ResultStatus status, String description) {
        this.status = status;
        this.description = description;
    }

    public ResultStatus getStatus() {
        return status;
    }

    public void setStatus(ResultStatus status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
