package com.assignment.tuum.service;

import com.assignment.tuum.dtos.*;
import com.assignment.tuum.model.Balance;
import com.assignment.tuum.model.enums.Currency;
import com.assignment.tuum.model.enums.Direction;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindContext;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost:";
    public static RestTemplate restTemplate;

    @BeforeAll
    public static void init()
    {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void setBaseUrl()
    {
        baseUrl = baseUrl.concat(port+"").concat("/api");
    }

    @Test
    @Sql(statements = "INSERT INTO account(id, country, customer_id) VALUES (1, 'Pakistan', 'Omar');", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO balance(id, available_balance, currency, account_id)VALUES (1, 2, 'EUR', 1),(2, 100, 'USD', 1)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM balance WHERE account_id = 1",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(statements = "DELETE FROM transaction WHERE account_id = 1",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(statements = "DELETE FROM account WHERE id = 1",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Create new transaction Success")
    public void testCreateNewTransaction()
    {

        TransactionCreateDto dto = TransactionCreateDto.builder()
                .account_id(1L)
                .currency(Currency.EUR)
                .amount(98)
                .direction(Direction.IN)
                .description("Test transaction")
                .build();

        String URL  = baseUrl.concat("/create_transaction");
        TransactionDto response =   restTemplate.postForObject(URL,dto, TransactionDto.class);

        assertEquals(100 , response.getBalanceAfterTransaction());

    }

    @Test
    @DisplayName("Create new transaction failure by invalid balance")
    public void testCreateNewTransactionByWrongAmount_failure()
    {

        TransactionCreateDto dto = TransactionCreateDto.builder()
                .account_id(1L)
                .currency(Currency.EUR)
                .amount(0)
                .direction(Direction.IN)
                .description("Test transaction")
                .build();

        String URL  = baseUrl.concat("/create_transaction");
        Exception exception = assertThrows(HttpClientErrorException.BadRequest.class,
                () ->  restTemplate.postForObject(URL,dto, TransactionDto.class));

        String expectedMessage = "Invalid transaction amount :";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @Sql(statements = "DELETE FROM balance WHERE account_id = 1",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM account WHERE id = 1",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO account(id, country, customer_id) VALUES (1, 'Pakistan', 'Omar');", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO balance(id, available_balance, currency, account_id)VALUES (1, 20, 'EUR', 1)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM balance WHERE account_id = 1",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(statements = "DELETE FROM account WHERE id = 1",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Create new transaction failure by Insufficient funds")
    public void testCreateNewTransactionByInsufficientFunds_failure()
    {

        TransactionCreateDto dto = TransactionCreateDto.builder()
                .account_id(1L)
                .currency(Currency.EUR)
                .amount(21)
                .direction(Direction.OUT)
                .description("Test transaction")
                .build();

        String URL  = baseUrl.concat("/create_transaction");
        Exception exception = assertThrows(HttpClientErrorException.BadRequest.class,
                () ->   restTemplate.postForObject(URL,dto, TransactionDto.class) );

        String expectedMessage = "InSufficient funds ";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Create new transaction failure by Missing Description")
    public void testCreateNewTransactionByMissingDescription_failure()
    {

        TransactionCreateDto dto = TransactionCreateDto.builder()
                .account_id(1L)
                .currency(Currency.EUR)
                .amount(21)
                .direction(Direction.OUT)
                .description("")
                .build();

        String URL  = baseUrl.concat("/create_transaction");
        Exception exception = assertThrows(HttpClientErrorException.BadRequest.class,
                () ->  restTemplate.postForObject(URL,dto, TransactionDto.class) );

        String expectedMessage = "Description cannot be null ";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Create new transaction failure by Missing Account_Id or no balance found for this account")
    public void testCreateNewTransactionByMissingAccountId_failure()
    {
        Long ID = 3L;

        TransactionCreateDto dto = TransactionCreateDto.builder()
                .account_id(ID)
                .currency(Currency.EUR)
                .amount(21)
                .direction(Direction.IN)
                .description("Test description")
                .build();

        String URL  = baseUrl.concat("/create_transaction");

       Exception exception = assertThrows(HttpClientErrorException.NotFound.class,
                () ->  restTemplate.postForObject(URL,dto, TransactionDto.class) );

        String expectedMessage = "Can't find account or balance for account With account_Id #" + ID;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @Sql(statements = "INSERT INTO account(id, country, customer_id) VALUES (1, 'Pakistan', 'Omar');", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO balance(id, available_balance, currency, account_id)VALUES (1, 1000, 'EUR', 1),(2, 100, 'USD', 1),(3, 100, 'SEK', 1);", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO transaction(id, amount, currency, description, direction, account_id)\n" +
            "\tVALUES(1, 1000, 'EUR','test description','IN', 1),(2, 500, 'EUR','test description','OUT', 1),(3, 757, 'SEK','test description SEK 3','IN', 1);", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM balance WHERE account_id = 1",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(statements = "DELETE FROM transaction WHERE account_id = 1",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(statements = "DELETE FROM account WHERE id = 1",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("get account by ID Success")
    public void testGetTransactionByAccountId()
    {
        final Long ID = 1L;
        String URL  = baseUrl.concat("/transaction/account/{id}");

        List<TransactionListDto> response =   (restTemplate.getForObject(URL,List.class,ID));

        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(3 , response.size())
        );
    }

    @Test
    @DisplayName("get transaction by account_id Failure")
    public void testGetTransactionByAccountId_Failure()
    {
        final Long ID = 5L;
        String URL  = baseUrl.concat("/transaction/account/{id}");

        Exception exception =   assertThrows(HttpClientErrorException.NotFound.class,
                () ->   restTemplate.getForObject(URL, List.class, ID));

        String expectedMessage = "Can't find transactions with account_id : " + ID;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
