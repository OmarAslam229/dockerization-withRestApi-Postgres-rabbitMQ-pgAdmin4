package com.assignment.tuum.controller;

import com.assignment.tuum.dtos.AccountDto;
import com.assignment.tuum.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@Slf4j
public class AccountController {

    @Autowired
    private AccountService service;

    @PostMapping(value = "/create_account")
//    @Operation(summary = "Save a department")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Department Successfully Saved",
//                    content = { @Content(mediaType = "application/json",
//                            schema = @Schema(implementation = Department.class)) }),
//            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
//                    content = @Content),
//            @ApiResponse(responseCode = "404", description = "Department not found",
//                    content = @Content) })
    public ResponseEntity<AccountDto> createNewAccount(@RequestBody AccountDto accountDto)
    {
        log.debug("post request to create account ");
        AccountDto savedAccount = service.createAccount(accountDto);
        return  ResponseEntity
                .ok()
                .body(savedAccount);

    }

    @GetMapping(value = "/account/{id}")
    public ResponseEntity<AccountDto> getAccount(@PathVariable(value = "id") Long id)
    {
        log.debug("Get request to fetch account details");
        AccountDto savedAccount = service.getAccountDetails(id);
        return  ResponseEntity
                .ok()
                .body(savedAccount);

    }
}
