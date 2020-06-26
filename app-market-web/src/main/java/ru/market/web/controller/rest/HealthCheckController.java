package ru.market.web.controller.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ru.market.dto.result.ResultDTO;
import ru.market.dto.result.ResultStatus;

@RestController
public class HealthCheckController {
    @RequestMapping(path = "/healthcheck", method = RequestMethod.GET)
    public ResultDTO healthCheck(){
        return new ResultDTO(ResultStatus.OK, "true");
    }
}
