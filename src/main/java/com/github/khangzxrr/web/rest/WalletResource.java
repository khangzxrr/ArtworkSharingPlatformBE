package com.github.khangzxrr.web.rest;

import com.github.khangzxrr.repository.WalletRepository;
import com.github.khangzxrr.service.PaypalService;
import com.github.khangzxrr.service.WalletService;
import com.github.khangzxrr.service.dto.PaypalCaptureDTO;
import com.github.khangzxrr.service.dto.PaypalOrderDTO;
import java.net.URISyntaxException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing {@link com.github.khangzxrr.domain.Wallet}.
 */
@RestController
@RequestMapping("/api/wallets")
public class WalletResource {

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaypalService paypalService;

    public WalletResource(WalletService walletService, WalletRepository walletRepository, PaypalService paypalService) {
        this.paypalService = paypalService;
    }

    @PostMapping("orders/{orderId}/capture")
    public ResponseEntity<PaypalCaptureDTO> verifyPayment(@PathVariable String orderId) {
        PaypalCaptureDTO paypalCaptureDTO = paypalService.verifyPayment(orderId);

        return ResponseEntity.ok().body(paypalCaptureDTO);
    }

    /**
     * {@code POST  /wallets} : Create a new wallet.
     *
     * @param walletDTO the walletDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new walletDTO, or with status {@code 400 (Bad Request)} if the wallet has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("deposit")
    public ResponseEntity<PaypalOrderDTO> createWallet() {
        PaypalOrderDTO response = paypalService.createDepositOrder();

        return ResponseEntity.ok().body(response);
        // return ResponseEntity
        //     .created(new URI("/api/wallets/" + result.getId()))
        //     .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
        //     .body(result);
    }
    // /**
    //  * {@code PUT  /wallets/:id} : Updates an existing wallet.
    //  *
    //  * @param id the id of the walletDTO to save.
    //  * @param walletDTO the walletDTO to update.
    //  * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated walletDTO,
    //  * or with status {@code 400 (Bad Request)} if the walletDTO is not valid,
    //  * or with status {@code 500 (Internal Server Error)} if the walletDTO couldn't be updated.
    //  * @throws URISyntaxException if the Location URI syntax is incorrect.
    //  */
    // @PutMapping("/{id}")
    // public ResponseEntity<WalletDTO> updateWallet(
    //     @PathVariable(value = "id", required = false) final Long id,
    //     @RequestBody WalletDTO walletDTO
    // ) throws URISyntaxException {
    //     log.debug("REST request to update Wallet : {}, {}", id, walletDTO);
    //     if (walletDTO.getId() == null) {
    //         throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    //     }
    //     if (!Objects.equals(id, walletDTO.getId())) {
    //         throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    //     }

    //     if (!walletRepository.existsById(id)) {
    //         throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    //     }

    //     WalletDTO result = walletService.update(walletDTO);
    //     return ResponseEntity
    //         .ok()
    //         .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, walletDTO.getId().toString()))
    //         .body(result);
    // }

    // /**
    //  * {@code PATCH  /wallets/:id} : Partial updates given fields of an existing wallet, field will ignore if it is null
    //  *
    //  * @param id the id of the walletDTO to save.
    //  * @param walletDTO the walletDTO to update.
    //  * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated walletDTO,
    //  * or with status {@code 400 (Bad Request)} if the walletDTO is not valid,
    //  * or with status {@code 404 (Not Found)} if the walletDTO is not found,
    //  * or with status {@code 500 (Internal Server Error)} if the walletDTO couldn't be updated.
    //  * @throws URISyntaxException if the Location URI syntax is incorrect.
    //  */
    // @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    // public ResponseEntity<WalletDTO> partialUpdateWallet(
    //     @PathVariable(value = "id", required = false) final Long id,
    //     @RequestBody WalletDTO walletDTO
    // ) throws URISyntaxException {
    //     log.debug("REST request to partial update Wallet partially : {}, {}", id, walletDTO);
    //     if (walletDTO.getId() == null) {
    //         throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    //     }
    //     if (!Objects.equals(id, walletDTO.getId())) {
    //         throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    //     }

    //     if (!walletRepository.existsById(id)) {
    //         throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    //     }

    //     Optional<WalletDTO> result = walletService.partialUpdate(walletDTO);

    //     return ResponseUtil.wrapOrNotFound(
    //         result,
    //         HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, walletDTO.getId().toString())
    //     );
    // }

    // /**
    //  * {@code GET  /wallets} : get all the wallets.
    //  *
    //  * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of wallets in body.
    //  */
    // @GetMapping("")
    // public List<WalletDTO> getAllWallets() {
    //     log.debug("REST request to get all Wallets");
    //     return walletService.findAll();
    // }

    // /**
    //  * {@code GET  /wallets/:id} : get the "id" wallet.
    //  *
    //  * @param id the id of the walletDTO to retrieve.
    //  * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the walletDTO, or with status {@code 404 (Not Found)}.
    //  */
    // @GetMapping("/{id}")
    // public ResponseEntity<WalletDTO> getWallet(@PathVariable("id") Long id) {
    //     log.debug("REST request to get Wallet : {}", id);
    //     Optional<WalletDTO> walletDTO = walletService.findOne(id);
    //     return ResponseUtil.wrapOrNotFound(walletDTO);
    // }

    // /**
    //  * {@code DELETE  /wallets/:id} : delete the "id" wallet.
    //  *
    //  * @param id the id of the walletDTO to delete.
    //  * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
    //  */
    // @DeleteMapping("/{id}")
    // public ResponseEntity<Void> deleteWallet(@PathVariable("id") Long id) {
    //     log.debug("REST request to delete Wallet : {}", id);
    //     walletService.delete(id);
    //     return ResponseEntity
    //         .noContent()
    //         .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
    //         .build();
    // }
}
