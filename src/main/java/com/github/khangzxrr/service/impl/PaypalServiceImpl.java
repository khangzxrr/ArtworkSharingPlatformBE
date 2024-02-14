package com.github.khangzxrr.service.impl;

import com.github.khangzxrr.config.ApplicationProperties;
import com.github.khangzxrr.domain.Wallet;
import com.github.khangzxrr.domain.WalletTransaction;
import com.github.khangzxrr.domain.enumeration.WalletTransactionStatus;
import com.github.khangzxrr.domain.enumeration.WalletTransactionType;
import com.github.khangzxrr.repository.WalletRepository;
import com.github.khangzxrr.service.PaypalService;
import com.github.khangzxrr.service.WalletService;
import com.github.khangzxrr.service.dto.PaypalCaptureDTO;
import com.github.khangzxrr.service.dto.PaypalOrderDTO;
import com.github.khangzxrr.service.dto.PaypalTokenDTO;
import java.time.LocalDate;
import java.util.Base64;
import java.util.UUID;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@Transactional
public class PaypalServiceImpl implements PaypalService {

    private final ApplicationProperties.PaypalConfiguration paypalConfiguration;
    private static PaypalTokenDTO paypalTokenDTO;

    private final WalletService walletService;

    private final WalletRepository walletRepository;

    public PaypalServiceImpl(ApplicationProperties applicationProperties, WalletService walletService, WalletRepository walletRepository) {
        this.paypalConfiguration = applicationProperties.getPaypalConfiguration();
        this.walletService = walletService;
        this.walletRepository = walletRepository;
    }

    @Override
    public PaypalOrderDTO createDepositOrder() {
        if (paypalTokenDTO == null) {
            getAccessToken();
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        httpHeaders.add("Authorization", "Bearer " + paypalTokenDTO.getAccess_token());
        httpHeaders.add("PayPal-Request-Id", UUID.randomUUID().toString());

        HttpEntity<String> request = new HttpEntity<String>(
            "{\r\n" + //
            "    \"intent\": \"CAPTURE\",\r\n" + //
            "    \"purchase_units\": [\r\n" + //
            "        {\r\n" + //
            "            \"items\": [\r\n" + //
            "                {\r\n" + //
            "                    \"name\": \"Deposit wallet\",\r\n" + //
            "                    \"description\": \"Deposit wallet\",\r\n" + //
            "                    \"quantity\": \"1\",\r\n" + //
            "                    \"unit_amount\": {\r\n" + //
            "                        \"currency_code\": \"USD\",\r\n" + //
            "                        \"value\": \"100\"\r\n" + //
            "                    }\r\n" + //
            "                }\r\n" + //
            "            ],\r\n" + //
            "            \"amount\": {\r\n" + //
            "                \"currency_code\": \"USD\",\r\n" + //
            "                \"value\": \"100.00\",\r\n" + //
            "                \"breakdown\": {\r\n" + //
            "                    \"item_total\": {\r\n" + //
            "                        \"currency_code\": \"USD\",\r\n" + //
            "                        \"value\": \"100\"\r\n" + //
            "                    }\r\n" + //
            "                }\r\n" + //
            "            }\r\n" + //
            "        }\r\n" + //
            "    ],\r\n" + //
            "    \"application_context\": {\r\n" + //
            "        \"return_url\": \"" +
            paypalConfiguration.getVerifyUrl() +
            "\",\r\n" + //
            "        \"cancel_url\": \"https://example.com/cancel\"\r\n" + //
            "    }\r\n" + //
            "}",
            httpHeaders
        );

        RestTemplate restTemplate = new RestTemplate();

        PaypalOrderDTO response = restTemplate.postForObject(
            "https://api-m.sandbox.paypal.com/v2/checkout/orders",
            request,
            PaypalOrderDTO.class
        );

        // Optional<PaypalOrderLinkDTO> paypalOrderLinkDTO =  response.getLinks().stream().filter(l -> l.getRel().equals("approve")).findFirst();

        return response;
    }

    @Override
    public PaypalTokenDTO getAccessToken() {
        String combineClientIdAndSecret = paypalConfiguration.getClientId() + ":" + paypalConfiguration.getSecretKey();
        String encodedAuthorize = Base64.getEncoder().encodeToString(combineClientIdAndSecret.getBytes());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Basic " + encodedAuthorize);
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> request = new HttpEntity<String>("grant_type=client_credentials", httpHeaders);

        RestTemplate restTemplate = new RestTemplate();

        PaypalTokenDTO response = restTemplate.postForObject(
            "https://api-m.sandbox.paypal.com/v1/oauth2/token",
            request,
            PaypalTokenDTO.class
        );

        //store token
        paypalTokenDTO = response;

        return response;
    }

    @Override
    public PaypalCaptureDTO verifyPayment(String token) {
        if (paypalTokenDTO == null) {
            getAccessToken();
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        httpHeaders.add("Authorization", "Bearer " + paypalTokenDTO.getAccess_token());
        httpHeaders.add("PayPal-Request-Id", UUID.randomUUID().toString());

        HttpEntity<String> request = new HttpEntity<String>("", httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        PaypalCaptureDTO response = restTemplate.postForObject(
            "https://api-m.sandbox.paypal.com/v2/checkout/orders/" + token + "/capture",
            request,
            PaypalCaptureDTO.class
        );

        //doing nothing when status isnt COMPLETED
        if (!response.getStatus().equals("COMPLETED")) {
            return response;
        }

        Wallet wallet = walletService.getCurrentUserWallet();

        response
            .getPurchase_units()
            .stream()
            .map(pu -> pu.getPayments())
            .map(p -> p.getCaptures())
            .flatMap(c -> c.stream())
            .forEach(capture -> {
                if (!capture.getStatus().equals("COMPLETED")) {
                    return;
                }

                //temping ignore after .
                Long amount = (long) Double.parseDouble(capture.getAmount().getValue());

                WalletTransaction transaction = new WalletTransaction();
                transaction.setAmount(amount);
                transaction.setType(WalletTransactionType.DEPOSIT);
                transaction.setCreateAt(LocalDate.now());
                transaction.setStatus(WalletTransactionStatus.SUCCEED);

                wallet.addTransactions(transaction);
            });

        walletRepository.save(wallet);

        return response;
    }
}
