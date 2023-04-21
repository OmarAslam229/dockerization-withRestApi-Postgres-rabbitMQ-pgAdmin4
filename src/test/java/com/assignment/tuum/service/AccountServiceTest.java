package com.assignment.tuum.service;

import com.assignment.tuum.dtos.CreateAccountDto;
import com.assignment.tuum.dtos.ReturnAccountDto;
import com.assignment.tuum.exceptions.UserNotFoundException;
import com.assignment.tuum.model.Balance;
import com.assignment.tuum.model.enums.Currency;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

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
    @DisplayName("Create new account Success")
    public void testCreateNewAccount()
    {
        List<Balance> balanceList = new ArrayList<Balance>(){{
            add(Balance.builder().currency(Currency.EUR).availableBalance(1000L).build());
            add(Balance.builder().currency(Currency.USD).availableBalance(1500L).build());
            add(Balance.builder().currency(Currency.SEK).availableBalance(125L).build());
        }};

        CreateAccountDto dto = CreateAccountDto.builder()
                .customerId("Omar")
                .country("pakistan")
                .balances(balanceList)
                .build();

        String URL  = baseUrl.concat("/create_account");
        ReturnAccountDto response =   restTemplate.postForObject(URL,dto, ReturnAccountDto.class);

        assertEquals("Omar" , response.getCustomerId());
        assertEquals(3 , response.getBalances().size());
        assertEquals(Currency.EUR,response.getBalances().get(0).getCurrency());
        assertEquals(Currency.USD,response.getBalances().get(1).getCurrency());
        assertEquals(Currency.SEK,response.getBalances().get(2).getCurrency());

        assertEquals(1000L,response.getBalances().get(0).getAvailableBalance());
    }

    @Test
    @Sql(statements = "INSERT INTO account(id, country, customer_id) VALUES (1, 'Pakistan', 'Omar');", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO balance(id, available_balance, currency, account_id)VALUES (1, 1000, 'EUR', 1),(2, 100, 'USD', 1),(3, 100, 'SEK', 1);", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM balance WHERE account_id = 1",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(statements = "DELETE FROM account WHERE id = 1",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("get account by ID Success")
    public void testGetAccountById()
    {
        final Long ID = 1L;
        String URL  = baseUrl.concat("/account/{id}");
        ReturnAccountDto response =   restTemplate.getForObject(URL,ReturnAccountDto.class,ID);

        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals("Omar" , response.getCustomerId()),
                () -> assertEquals(3 , response.getBalances().size()),
                () -> assertEquals(Currency.EUR,response.getBalances().get(0).getCurrency()),
                () -> assertEquals(Currency.USD,response.getBalances().get(1).getCurrency()),
                () -> assertEquals(Currency.SEK,response.getBalances().get(2).getCurrency()),
                () -> assertEquals(1000L,response.getBalances().get(0).getAvailableBalance())
        );
    }

    @Test
    @DisplayName("get account by ID Failure")
    public void testGetAccountById_Failure()
    {
        final Long ID = 5L;
        String URL  = baseUrl.concat("/account/{id}");

        Exception exception =   assertThrows(HttpClientErrorException.NotFound.class,
                () ->  restTemplate.getForObject(URL, ReturnAccountDto.class, ID) );

        String expectedMessage = "Account With Id #" + ID + " not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
