package com.assignment.tuum.controller;

import com.assignment.tuum.dtos.TransactionCreateDto;
import com.assignment.tuum.dtos.TransactionDto;
import com.assignment.tuum.dtos.TransactionListDto;
import com.assignment.tuum.rabbitMQ.producer.RabbitMQProducer;
import com.assignment.tuum.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
public class TransactionController {

    @Autowired
    private TransactionService service;

    @PostMapping(value = "/create_transaction")
//    @Operation(summary = "Save a department")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Department Successfully Saved",
//                    content = { @Content(mediaType = "application/json",
//                            schema = @Schema(implementation = Department.class)) }),
//            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
//                    content = @Content),
//            @ApiResponse(responseCode = "404", description = "Department not found",
//                    content = @Content) })
    public ResponseEntity<TransactionDto> createNewTransaction(@RequestBody TransactionCreateDto transactionDto)
    {
        log.debug("post request to create transaction ");
        TransactionDto savedTransaction = service.createTransaction(transactionDto);
        return  ResponseEntity
                .ok()
                .body(savedTransaction);

    }

    @GetMapping(value = "/transaction/account/{id}")
    public ResponseEntity<List<TransactionListDto>> getTransactionListByAccountId(@PathVariable(value = "id") Long id)
    {
        log.debug("Get request to fetch list of transactions of a user ");
       List<TransactionListDto> savedTransaction = service.getTransactions(id);
        return  ResponseEntity
                .ok()
                .body(savedTransaction);

    }
}
