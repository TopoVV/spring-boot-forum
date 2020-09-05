package com.topov.forum.controller;


import com.topov.forum.dto.response.ApiResponse;
import com.topov.forum.dto.result.OperationResult;
import com.topov.forum.service.account.AccountServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
public class AccountConfirmationController {
    private final AccountServiceImpl accountConfirmationService;

    @Autowired
    public AccountConfirmationController(AccountServiceImpl accountConfirmationService) {
        this.accountConfirmationService = accountConfirmationService;
    }

    @GetMapping(value = "/registration/{token}")
    public ResponseEntity<ApiResponse> confirmAccountGet(@PathVariable String token) {
        log.debug("Handling (GET) account confirmation request");
        final OperationResult result = accountConfirmationService.confirmAccount(token);
        return result.createResponseEntity();
    }
}
