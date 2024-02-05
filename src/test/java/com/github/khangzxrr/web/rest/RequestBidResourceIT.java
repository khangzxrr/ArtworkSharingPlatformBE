package com.github.khangzxrr.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.github.khangzxrr.IntegrationTest;
import com.github.khangzxrr.domain.RequestBid;
import com.github.khangzxrr.domain.enumeration.RequestBidStatus;
import com.github.khangzxrr.repository.RequestBidRepository;
import com.github.khangzxrr.service.dto.RequestBidDTO;
import com.github.khangzxrr.service.mapper.RequestBidMapper;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link RequestBidResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RequestBidResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final Integer DEFAULT_DEADLINE = 1;
    private static final Integer UPDATED_DEADLINE = 2;

    private static final RequestBidStatus DEFAULT_STATUS = RequestBidStatus.BIDED;
    private static final RequestBidStatus UPDATED_STATUS = RequestBidStatus.SELECTED_BID;

    private static final String ENTITY_API_URL = "/api/request-bids";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RequestBidRepository requestBidRepository;

    @Autowired
    private RequestBidMapper requestBidMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRequestBidMockMvc;

    private RequestBid requestBid;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RequestBid createEntity(EntityManager em) {
        RequestBid requestBid = new RequestBid()
            .description(DEFAULT_DESCRIPTION)
            .price(DEFAULT_PRICE)
            .deadline(DEFAULT_DEADLINE)
            .status(DEFAULT_STATUS);
        return requestBid;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RequestBid createUpdatedEntity(EntityManager em) {
        RequestBid requestBid = new RequestBid()
            .description(UPDATED_DESCRIPTION)
            .price(UPDATED_PRICE)
            .deadline(UPDATED_DEADLINE)
            .status(UPDATED_STATUS);
        return requestBid;
    }

    @BeforeEach
    public void initTest() {
        requestBid = createEntity(em);
    }

    @Test
    @Transactional
    void createRequestBid() throws Exception {
        int databaseSizeBeforeCreate = requestBidRepository.findAll().size();
        // Create the RequestBid
        RequestBidDTO requestBidDTO = requestBidMapper.toDto(requestBid);
        restRequestBidMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requestBidDTO)))
            .andExpect(status().isCreated());

        // Validate the RequestBid in the database
        List<RequestBid> requestBidList = requestBidRepository.findAll();
        assertThat(requestBidList).hasSize(databaseSizeBeforeCreate + 1);
        RequestBid testRequestBid = requestBidList.get(requestBidList.size() - 1);
        assertThat(testRequestBid.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRequestBid.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testRequestBid.getDeadline()).isEqualTo(DEFAULT_DEADLINE);
        assertThat(testRequestBid.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createRequestBidWithExistingId() throws Exception {
        // Create the RequestBid with an existing ID
        requestBid.setId(1L);
        RequestBidDTO requestBidDTO = requestBidMapper.toDto(requestBid);

        int databaseSizeBeforeCreate = requestBidRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRequestBidMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requestBidDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RequestBid in the database
        List<RequestBid> requestBidList = requestBidRepository.findAll();
        assertThat(requestBidList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRequestBids() throws Exception {
        // Initialize the database
        requestBidRepository.saveAndFlush(requestBid);

        // Get all the requestBidList
        restRequestBidMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requestBid.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].deadline").value(hasItem(DEFAULT_DEADLINE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    void getRequestBid() throws Exception {
        // Initialize the database
        requestBidRepository.saveAndFlush(requestBid);

        // Get the requestBid
        restRequestBidMockMvc
            .perform(get(ENTITY_API_URL_ID, requestBid.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(requestBid.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.deadline").value(DEFAULT_DEADLINE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingRequestBid() throws Exception {
        // Get the requestBid
        restRequestBidMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRequestBid() throws Exception {
        // Initialize the database
        requestBidRepository.saveAndFlush(requestBid);

        int databaseSizeBeforeUpdate = requestBidRepository.findAll().size();

        // Update the requestBid
        RequestBid updatedRequestBid = requestBidRepository.findById(requestBid.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRequestBid are not directly saved in db
        em.detach(updatedRequestBid);
        updatedRequestBid.description(UPDATED_DESCRIPTION).price(UPDATED_PRICE).deadline(UPDATED_DEADLINE).status(UPDATED_STATUS);
        RequestBidDTO requestBidDTO = requestBidMapper.toDto(updatedRequestBid);

        restRequestBidMockMvc
            .perform(
                put(ENTITY_API_URL_ID, requestBidDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requestBidDTO))
            )
            .andExpect(status().isOk());

        // Validate the RequestBid in the database
        List<RequestBid> requestBidList = requestBidRepository.findAll();
        assertThat(requestBidList).hasSize(databaseSizeBeforeUpdate);
        RequestBid testRequestBid = requestBidList.get(requestBidList.size() - 1);
        assertThat(testRequestBid.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRequestBid.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testRequestBid.getDeadline()).isEqualTo(UPDATED_DEADLINE);
        assertThat(testRequestBid.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingRequestBid() throws Exception {
        int databaseSizeBeforeUpdate = requestBidRepository.findAll().size();
        requestBid.setId(longCount.incrementAndGet());

        // Create the RequestBid
        RequestBidDTO requestBidDTO = requestBidMapper.toDto(requestBid);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequestBidMockMvc
            .perform(
                put(ENTITY_API_URL_ID, requestBidDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requestBidDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestBid in the database
        List<RequestBid> requestBidList = requestBidRepository.findAll();
        assertThat(requestBidList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRequestBid() throws Exception {
        int databaseSizeBeforeUpdate = requestBidRepository.findAll().size();
        requestBid.setId(longCount.incrementAndGet());

        // Create the RequestBid
        RequestBidDTO requestBidDTO = requestBidMapper.toDto(requestBid);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestBidMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requestBidDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestBid in the database
        List<RequestBid> requestBidList = requestBidRepository.findAll();
        assertThat(requestBidList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRequestBid() throws Exception {
        int databaseSizeBeforeUpdate = requestBidRepository.findAll().size();
        requestBid.setId(longCount.incrementAndGet());

        // Create the RequestBid
        RequestBidDTO requestBidDTO = requestBidMapper.toDto(requestBid);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestBidMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requestBidDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RequestBid in the database
        List<RequestBid> requestBidList = requestBidRepository.findAll();
        assertThat(requestBidList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRequestBidWithPatch() throws Exception {
        // Initialize the database
        requestBidRepository.saveAndFlush(requestBid);

        int databaseSizeBeforeUpdate = requestBidRepository.findAll().size();

        // Update the requestBid using partial update
        RequestBid partialUpdatedRequestBid = new RequestBid();
        partialUpdatedRequestBid.setId(requestBid.getId());

        partialUpdatedRequestBid.description(UPDATED_DESCRIPTION).deadline(UPDATED_DEADLINE).status(UPDATED_STATUS);

        restRequestBidMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRequestBid.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRequestBid))
            )
            .andExpect(status().isOk());

        // Validate the RequestBid in the database
        List<RequestBid> requestBidList = requestBidRepository.findAll();
        assertThat(requestBidList).hasSize(databaseSizeBeforeUpdate);
        RequestBid testRequestBid = requestBidList.get(requestBidList.size() - 1);
        assertThat(testRequestBid.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRequestBid.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testRequestBid.getDeadline()).isEqualTo(UPDATED_DEADLINE);
        assertThat(testRequestBid.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateRequestBidWithPatch() throws Exception {
        // Initialize the database
        requestBidRepository.saveAndFlush(requestBid);

        int databaseSizeBeforeUpdate = requestBidRepository.findAll().size();

        // Update the requestBid using partial update
        RequestBid partialUpdatedRequestBid = new RequestBid();
        partialUpdatedRequestBid.setId(requestBid.getId());

        partialUpdatedRequestBid.description(UPDATED_DESCRIPTION).price(UPDATED_PRICE).deadline(UPDATED_DEADLINE).status(UPDATED_STATUS);

        restRequestBidMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRequestBid.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRequestBid))
            )
            .andExpect(status().isOk());

        // Validate the RequestBid in the database
        List<RequestBid> requestBidList = requestBidRepository.findAll();
        assertThat(requestBidList).hasSize(databaseSizeBeforeUpdate);
        RequestBid testRequestBid = requestBidList.get(requestBidList.size() - 1);
        assertThat(testRequestBid.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRequestBid.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testRequestBid.getDeadline()).isEqualTo(UPDATED_DEADLINE);
        assertThat(testRequestBid.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingRequestBid() throws Exception {
        int databaseSizeBeforeUpdate = requestBidRepository.findAll().size();
        requestBid.setId(longCount.incrementAndGet());

        // Create the RequestBid
        RequestBidDTO requestBidDTO = requestBidMapper.toDto(requestBid);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequestBidMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, requestBidDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(requestBidDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestBid in the database
        List<RequestBid> requestBidList = requestBidRepository.findAll();
        assertThat(requestBidList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRequestBid() throws Exception {
        int databaseSizeBeforeUpdate = requestBidRepository.findAll().size();
        requestBid.setId(longCount.incrementAndGet());

        // Create the RequestBid
        RequestBidDTO requestBidDTO = requestBidMapper.toDto(requestBid);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestBidMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(requestBidDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestBid in the database
        List<RequestBid> requestBidList = requestBidRepository.findAll();
        assertThat(requestBidList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRequestBid() throws Exception {
        int databaseSizeBeforeUpdate = requestBidRepository.findAll().size();
        requestBid.setId(longCount.incrementAndGet());

        // Create the RequestBid
        RequestBidDTO requestBidDTO = requestBidMapper.toDto(requestBid);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestBidMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(requestBidDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RequestBid in the database
        List<RequestBid> requestBidList = requestBidRepository.findAll();
        assertThat(requestBidList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRequestBid() throws Exception {
        // Initialize the database
        requestBidRepository.saveAndFlush(requestBid);

        int databaseSizeBeforeDelete = requestBidRepository.findAll().size();

        // Delete the requestBid
        restRequestBidMockMvc
            .perform(delete(ENTITY_API_URL_ID, requestBid.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RequestBid> requestBidList = requestBidRepository.findAll();
        assertThat(requestBidList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
