package com.github.khangzxrr.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.github.khangzxrr.IntegrationTest;
import com.github.khangzxrr.domain.RequestProgress;
import com.github.khangzxrr.domain.WalletTransaction;
import com.github.khangzxrr.domain.enumeration.RequestProgressStatus;
import com.github.khangzxrr.domain.enumeration.RequestProgressType;
import com.github.khangzxrr.repository.RequestProgressRepository;
import com.github.khangzxrr.service.dto.RequestProgressDTO;
import com.github.khangzxrr.service.mapper.RequestProgressMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link RequestProgressResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RequestProgressResourceIT {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final RequestProgressType DEFAULT_TYPE = RequestProgressType.FIRST_PAYMENT;
    private static final RequestProgressType UPDATED_TYPE = RequestProgressType.SECOND_PAYMENT;

    private static final RequestProgressStatus DEFAULT_STATUS = RequestProgressStatus.SUCCEED;
    private static final RequestProgressStatus UPDATED_STATUS = RequestProgressStatus.FAILED;

    private static final String ENTITY_API_URL = "/api/request-progresses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RequestProgressRepository requestProgressRepository;

    @Autowired
    private RequestProgressMapper requestProgressMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRequestProgressMockMvc;

    private RequestProgress requestProgress;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RequestProgress createEntity(EntityManager em) {
        RequestProgress requestProgress = new RequestProgress()
            .date(DEFAULT_DATE)
            .description(DEFAULT_DESCRIPTION)
            .type(DEFAULT_TYPE)
            .status(DEFAULT_STATUS);
        // Add required entity
        WalletTransaction walletTransaction;
        if (TestUtil.findAll(em, WalletTransaction.class).isEmpty()) {
            walletTransaction = WalletTransactionResourceIT.createEntity(em);
            em.persist(walletTransaction);
            em.flush();
        } else {
            walletTransaction = TestUtil.findAll(em, WalletTransaction.class).get(0);
        }
        requestProgress.setTransaction(walletTransaction);
        return requestProgress;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RequestProgress createUpdatedEntity(EntityManager em) {
        RequestProgress requestProgress = new RequestProgress()
            .date(UPDATED_DATE)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
            .status(UPDATED_STATUS);
        // Add required entity
        WalletTransaction walletTransaction;
        if (TestUtil.findAll(em, WalletTransaction.class).isEmpty()) {
            walletTransaction = WalletTransactionResourceIT.createUpdatedEntity(em);
            em.persist(walletTransaction);
            em.flush();
        } else {
            walletTransaction = TestUtil.findAll(em, WalletTransaction.class).get(0);
        }
        requestProgress.setTransaction(walletTransaction);
        return requestProgress;
    }

    @BeforeEach
    public void initTest() {
        requestProgress = createEntity(em);
    }

    @Test
    @Transactional
    void createRequestProgress() throws Exception {
        int databaseSizeBeforeCreate = requestProgressRepository.findAll().size();
        // Create the RequestProgress
        RequestProgressDTO requestProgressDTO = requestProgressMapper.toDto(requestProgress);
        restRequestProgressMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requestProgressDTO))
            )
            .andExpect(status().isCreated());

        // Validate the RequestProgress in the database
        List<RequestProgress> requestProgressList = requestProgressRepository.findAll();
        assertThat(requestProgressList).hasSize(databaseSizeBeforeCreate + 1);
        RequestProgress testRequestProgress = requestProgressList.get(requestProgressList.size() - 1);
        assertThat(testRequestProgress.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testRequestProgress.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRequestProgress.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testRequestProgress.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createRequestProgressWithExistingId() throws Exception {
        // Create the RequestProgress with an existing ID
        requestProgress.setId(1L);
        RequestProgressDTO requestProgressDTO = requestProgressMapper.toDto(requestProgress);

        int databaseSizeBeforeCreate = requestProgressRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRequestProgressMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requestProgressDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestProgress in the database
        List<RequestProgress> requestProgressList = requestProgressRepository.findAll();
        assertThat(requestProgressList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRequestProgresses() throws Exception {
        // Initialize the database
        requestProgressRepository.saveAndFlush(requestProgress);

        // Get all the requestProgressList
        restRequestProgressMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requestProgress.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    void getRequestProgress() throws Exception {
        // Initialize the database
        requestProgressRepository.saveAndFlush(requestProgress);

        // Get the requestProgress
        restRequestProgressMockMvc
            .perform(get(ENTITY_API_URL_ID, requestProgress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(requestProgress.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingRequestProgress() throws Exception {
        // Get the requestProgress
        restRequestProgressMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRequestProgress() throws Exception {
        // Initialize the database
        requestProgressRepository.saveAndFlush(requestProgress);

        int databaseSizeBeforeUpdate = requestProgressRepository.findAll().size();

        // Update the requestProgress
        RequestProgress updatedRequestProgress = requestProgressRepository.findById(requestProgress.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRequestProgress are not directly saved in db
        em.detach(updatedRequestProgress);
        updatedRequestProgress.date(UPDATED_DATE).description(UPDATED_DESCRIPTION).type(UPDATED_TYPE).status(UPDATED_STATUS);
        RequestProgressDTO requestProgressDTO = requestProgressMapper.toDto(updatedRequestProgress);

        restRequestProgressMockMvc
            .perform(
                put(ENTITY_API_URL_ID, requestProgressDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requestProgressDTO))
            )
            .andExpect(status().isOk());

        // Validate the RequestProgress in the database
        List<RequestProgress> requestProgressList = requestProgressRepository.findAll();
        assertThat(requestProgressList).hasSize(databaseSizeBeforeUpdate);
        RequestProgress testRequestProgress = requestProgressList.get(requestProgressList.size() - 1);
        assertThat(testRequestProgress.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testRequestProgress.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRequestProgress.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testRequestProgress.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingRequestProgress() throws Exception {
        int databaseSizeBeforeUpdate = requestProgressRepository.findAll().size();
        requestProgress.setId(longCount.incrementAndGet());

        // Create the RequestProgress
        RequestProgressDTO requestProgressDTO = requestProgressMapper.toDto(requestProgress);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequestProgressMockMvc
            .perform(
                put(ENTITY_API_URL_ID, requestProgressDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requestProgressDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestProgress in the database
        List<RequestProgress> requestProgressList = requestProgressRepository.findAll();
        assertThat(requestProgressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRequestProgress() throws Exception {
        int databaseSizeBeforeUpdate = requestProgressRepository.findAll().size();
        requestProgress.setId(longCount.incrementAndGet());

        // Create the RequestProgress
        RequestProgressDTO requestProgressDTO = requestProgressMapper.toDto(requestProgress);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestProgressMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requestProgressDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestProgress in the database
        List<RequestProgress> requestProgressList = requestProgressRepository.findAll();
        assertThat(requestProgressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRequestProgress() throws Exception {
        int databaseSizeBeforeUpdate = requestProgressRepository.findAll().size();
        requestProgress.setId(longCount.incrementAndGet());

        // Create the RequestProgress
        RequestProgressDTO requestProgressDTO = requestProgressMapper.toDto(requestProgress);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestProgressMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requestProgressDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RequestProgress in the database
        List<RequestProgress> requestProgressList = requestProgressRepository.findAll();
        assertThat(requestProgressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRequestProgressWithPatch() throws Exception {
        // Initialize the database
        requestProgressRepository.saveAndFlush(requestProgress);

        int databaseSizeBeforeUpdate = requestProgressRepository.findAll().size();

        // Update the requestProgress using partial update
        RequestProgress partialUpdatedRequestProgress = new RequestProgress();
        partialUpdatedRequestProgress.setId(requestProgress.getId());

        partialUpdatedRequestProgress.date(UPDATED_DATE).type(UPDATED_TYPE);

        restRequestProgressMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRequestProgress.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRequestProgress))
            )
            .andExpect(status().isOk());

        // Validate the RequestProgress in the database
        List<RequestProgress> requestProgressList = requestProgressRepository.findAll();
        assertThat(requestProgressList).hasSize(databaseSizeBeforeUpdate);
        RequestProgress testRequestProgress = requestProgressList.get(requestProgressList.size() - 1);
        assertThat(testRequestProgress.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testRequestProgress.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRequestProgress.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testRequestProgress.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateRequestProgressWithPatch() throws Exception {
        // Initialize the database
        requestProgressRepository.saveAndFlush(requestProgress);

        int databaseSizeBeforeUpdate = requestProgressRepository.findAll().size();

        // Update the requestProgress using partial update
        RequestProgress partialUpdatedRequestProgress = new RequestProgress();
        partialUpdatedRequestProgress.setId(requestProgress.getId());

        partialUpdatedRequestProgress.date(UPDATED_DATE).description(UPDATED_DESCRIPTION).type(UPDATED_TYPE).status(UPDATED_STATUS);

        restRequestProgressMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRequestProgress.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRequestProgress))
            )
            .andExpect(status().isOk());

        // Validate the RequestProgress in the database
        List<RequestProgress> requestProgressList = requestProgressRepository.findAll();
        assertThat(requestProgressList).hasSize(databaseSizeBeforeUpdate);
        RequestProgress testRequestProgress = requestProgressList.get(requestProgressList.size() - 1);
        assertThat(testRequestProgress.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testRequestProgress.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRequestProgress.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testRequestProgress.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingRequestProgress() throws Exception {
        int databaseSizeBeforeUpdate = requestProgressRepository.findAll().size();
        requestProgress.setId(longCount.incrementAndGet());

        // Create the RequestProgress
        RequestProgressDTO requestProgressDTO = requestProgressMapper.toDto(requestProgress);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequestProgressMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, requestProgressDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(requestProgressDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestProgress in the database
        List<RequestProgress> requestProgressList = requestProgressRepository.findAll();
        assertThat(requestProgressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRequestProgress() throws Exception {
        int databaseSizeBeforeUpdate = requestProgressRepository.findAll().size();
        requestProgress.setId(longCount.incrementAndGet());

        // Create the RequestProgress
        RequestProgressDTO requestProgressDTO = requestProgressMapper.toDto(requestProgress);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestProgressMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(requestProgressDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestProgress in the database
        List<RequestProgress> requestProgressList = requestProgressRepository.findAll();
        assertThat(requestProgressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRequestProgress() throws Exception {
        int databaseSizeBeforeUpdate = requestProgressRepository.findAll().size();
        requestProgress.setId(longCount.incrementAndGet());

        // Create the RequestProgress
        RequestProgressDTO requestProgressDTO = requestProgressMapper.toDto(requestProgress);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestProgressMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(requestProgressDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RequestProgress in the database
        List<RequestProgress> requestProgressList = requestProgressRepository.findAll();
        assertThat(requestProgressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRequestProgress() throws Exception {
        // Initialize the database
        requestProgressRepository.saveAndFlush(requestProgress);

        int databaseSizeBeforeDelete = requestProgressRepository.findAll().size();

        // Delete the requestProgress
        restRequestProgressMockMvc
            .perform(delete(ENTITY_API_URL_ID, requestProgress.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RequestProgress> requestProgressList = requestProgressRepository.findAll();
        assertThat(requestProgressList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
