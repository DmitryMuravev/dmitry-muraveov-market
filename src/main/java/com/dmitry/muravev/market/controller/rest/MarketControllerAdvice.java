package com.dmitry.muravev.market.controller.rest;

import com.dmitry.muravev.market.dto.response.GenericMessageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestControllerAdvice
public class MarketControllerAdvice {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<GenericMessageResponse> handleResponseStatusException(ResponseStatusException rse) {
        log.error("Error occurs", rse);

        GenericMessageResponse errorMessageResponse = GenericMessageResponse.builder()
                .message(rse.getReason())
                .build();
        return ResponseEntity.status(rse.getStatus()).body(errorMessageResponse);
    }

}
