package com.github.khangzxrr.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.github.khangzxrr.IntegrationTest;
import com.github.khangzxrr.domain.RequestAttachment;
import com.github.khangzxrr.repository.RequestAttachmentRepository;
import com.github.khangzxrr.service.dto.RequestAttachmentDTO;
import com.github.khangzxrr.service.mapper.RequestAttachmentMapper;
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
 * Integration tests for the {@link RequestAttachmentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RequestAttachmentResourceIT {

    private static final String ENTITY_API_URL = "/api/request-attachments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RequestAttachmentRepository requestAttachmentRepository;

    @Autowired
    private RequestAttachmentMapper requestAttachmentMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRequestAttachmentMockMvc;

    private RequestAttachment requestAttachment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RequestAttachment createEntity(EntityManager em) {
        RequestAttachment requestAttachment = new RequestAttachment();
        return requestAttachment;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RequestAttachment createUpdatedEntity(EntityManager em) {
        RequestAttachment requestAttachment = new RequestAttachment();
        return requestAttachment;
    }

    @BeforeEach
    public void initTest() {
        requestAttachment = createEntity(em);
    }

    @Test
    @Transactional
    void createRequestAttachment() throws Exception {
        int databaseSizeBeforeCreate = requestAttachmentRepository.findAll().size();
        // Create the RequestAttachment
        RequestAttachmentDTO requestAttachmentDTO = requestAttachmentMapper.toDto(requestAttachment);
        restRequestAttachmentMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requestAttachmentDTO))
            )
            .andExpect(status().isCreated());

        // Validate the RequestAttachment in the database
        List<RequestAttachment> requestAttachmentList = requestAttachmentRepository.findAll();
        assertThat(requestAttachmentList).hasSize(databaseSizeBeforeCreate + 1);
        RequestAttachment testRequestAttachment = requestAttachmentList.get(requestAttachmentList.size() - 1);
    }

    @Test
    @Transactional
    void createRequestAttachmentWithExistingId() throws Exception {
        // Create the RequestAttachment with an existing ID
        requestAttachment.setId(1L);
        RequestAttachmentDTO requestAttachmentDTO = requestAttachmentMapper.toDto(requestAttachment);

        int databaseSizeBeforeCreate = requestAttachmentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRequestAttachmentMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requestAttachmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestAttachment in the database
        List<RequestAttachment> requestAttachmentList = requestAttachmentRepository.findAll();
        assertThat(requestAttachmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRequestAttachments() throws Exception {
        // Initialize the database
        requestAttachmentRepository.saveAndFlush(requestAttachment);

        // Get all the requestAttachmentList
        restRequestAttachmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(requestAttachment.getId().intValue())));
    }

    @Test
    @Transactional
    void getRequestAttachment() throws Exception {
        // Initialize the database
        requestAttachmentRepository.saveAndFlush(requestAttachment);

        // Get the requestAttachment
        restRequestAttachmentMockMvc
            .perform(get(ENTITY_API_URL_ID, requestAttachment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(requestAttachment.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingRequestAttachment() throws Exception {
        // Get the requestAttachment
        restRequestAttachmentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRequestAttachment() throws Exception {
        // Initialize the database
        requestAttachmentRepository.saveAndFlush(requestAttachment);

        int databaseSizeBeforeUpdate = requestAttachmentRepository.findAll().size();

        // Update the requestAttachment
        RequestAttachment updatedRequestAttachment = requestAttachmentRepository.findById(requestAttachment.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRequestAttachment are not directly saved in db
        em.detach(updatedRequestAttachment);
        RequestAttachmentDTO requestAttachmentDTO = requestAttachmentMapper.toDto(updatedRequestAttachment);

        restRequestAttachmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, requestAttachmentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requestAttachmentDTO))
            )
            .andExpect(status().isOk());

        // Validate the RequestAttachment in the database
        List<RequestAttachment> requestAttachmentList = requestAttachmentRepository.findAll();
        assertThat(requestAttachmentList).hasSize(databaseSizeBeforeUpdate);
        RequestAttachment testRequestAttachment = requestAttachmentList.get(requestAttachmentList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingRequestAttachment() throws Exception {
        int databaseSizeBeforeUpdate = requestAttachmentRepository.findAll().size();
        requestAttachment.setId(longCount.incrementAndGet());

        // Create the RequestAttachment
        RequestAttachmentDTO requestAttachmentDTO = requestAttachmentMapper.toDto(requestAttachment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequestAttachmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, requestAttachmentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requestAttachmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestAttachment in the database
        List<RequestAttachment> requestAttachmentList = requestAttachmentRepository.findAll();
        assertThat(requestAttachmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRequestAttachment() throws Exception {
        int databaseSizeBeforeUpdate = requestAttachmentRepository.findAll().size();
        requestAttachment.setId(longCount.incrementAndGet());

        // Create the RequestAttachment
        RequestAttachmentDTO requestAttachmentDTO = requestAttachmentMapper.toDto(requestAttachment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestAttachmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requestAttachmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestAttachment in the database
        List<RequestAttachment> requestAttachmentList = requestAttachmentRepository.findAll();
        assertThat(requestAttachmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRequestAttachment() throws Exception {
        int databaseSizeBeforeUpdate = requestAttachmentRepository.findAll().size();
        requestAttachment.setId(longCount.incrementAndGet());

        // Create the RequestAttachment
        RequestAttachmentDTO requestAttachmentDTO = requestAttachmentMapper.toDto(requestAttachment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestAttachmentMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requestAttachmentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RequestAttachment in the database
        List<RequestAttachment> requestAttachmentList = requestAttachmentRepository.findAll();
        assertThat(requestAttachmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRequestAttachmentWithPatch() throws Exception {
        // Initialize the database
        requestAttachmentRepository.saveAndFlush(requestAttachment);

        int databaseSizeBeforeUpdate = requestAttachmentRepository.findAll().size();

        // Update the requestAttachment using partial update
        RequestAttachment partialUpdatedRequestAttachment = new RequestAttachment();
        partialUpdatedRequestAttachment.setId(requestAttachment.getId());

        restRequestAttachmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRequestAttachment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRequestAttachment))
            )
            .andExpect(status().isOk());

        // Validate the RequestAttachment in the database
        List<RequestAttachment> requestAttachmentList = requestAttachmentRepository.findAll();
        assertThat(requestAttachmentList).hasSize(databaseSizeBeforeUpdate);
        RequestAttachment testRequestAttachment = requestAttachmentList.get(requestAttachmentList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateRequestAttachmentWithPatch() throws Exception {
        // Initialize the database
        requestAttachmentRepository.saveAndFlush(requestAttachment);

        int databaseSizeBeforeUpdate = requestAttachmentRepository.findAll().size();

        // Update the requestAttachment using partial update
        RequestAttachment partialUpdatedRequestAttachment = new RequestAttachment();
        partialUpdatedRequestAttachment.setId(requestAttachment.getId());

        restRequestAttachmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRequestAttachment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRequestAttachment))
            )
            .andExpect(status().isOk());

        // Validate the RequestAttachment in the database
        List<RequestAttachment> requestAttachmentList = requestAttachmentRepository.findAll();
        assertThat(requestAttachmentList).hasSize(databaseSizeBeforeUpdate);
        RequestAttachment testRequestAttachment = requestAttachmentList.get(requestAttachmentList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingRequestAttachment() throws Exception {
        int databaseSizeBeforeUpdate = requestAttachmentRepository.findAll().size();
        requestAttachment.setId(longCount.incrementAndGet());

        // Create the RequestAttachment
        RequestAttachmentDTO requestAttachmentDTO = requestAttachmentMapper.toDto(requestAttachment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequestAttachmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, requestAttachmentDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(requestAttachmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestAttachment in the database
        List<RequestAttachment> requestAttachmentList = requestAttachmentRepository.findAll();
        assertThat(requestAttachmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRequestAttachment() throws Exception {
        int databaseSizeBeforeUpdate = requestAttachmentRepository.findAll().size();
        requestAttachment.setId(longCount.incrementAndGet());

        // Create the RequestAttachment
        RequestAttachmentDTO requestAttachmentDTO = requestAttachmentMapper.toDto(requestAttachment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestAttachmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(requestAttachmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RequestAttachment in the database
        List<RequestAttachment> requestAttachmentList = requestAttachmentRepository.findAll();
        assertThat(requestAttachmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRequestAttachment() throws Exception {
        int databaseSizeBeforeUpdate = requestAttachmentRepository.findAll().size();
        requestAttachment.setId(longCount.incrementAndGet());

        // Create the RequestAttachment
        RequestAttachmentDTO requestAttachmentDTO = requestAttachmentMapper.toDto(requestAttachment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestAttachmentMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(requestAttachmentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RequestAttachment in the database
        List<RequestAttachment> requestAttachmentList = requestAttachmentRepository.findAll();
        assertThat(requestAttachmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRequestAttachment() throws Exception {
        // Initialize the database
        requestAttachmentRepository.saveAndFlush(requestAttachment);

        int databaseSizeBeforeDelete = requestAttachmentRepository.findAll().size();

        // Delete the requestAttachment
        restRequestAttachmentMockMvc
            .perform(delete(ENTITY_API_URL_ID, requestAttachment.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RequestAttachment> requestAttachmentList = requestAttachmentRepository.findAll();
        assertThat(requestAttachmentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
