package com.github.khangzxrr.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.github.khangzxrr.IntegrationTest;
import com.github.khangzxrr.domain.ArtworkComplain;
import com.github.khangzxrr.domain.enumeration.ComplainStatus;
import com.github.khangzxrr.repository.ArtworkComplainRepository;
import com.github.khangzxrr.service.dto.ArtworkComplainDTO;
import com.github.khangzxrr.service.mapper.ArtworkComplainMapper;
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
 * Integration tests for the {@link ArtworkComplainResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ArtworkComplainResourceIT {

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final ComplainStatus DEFAULT_STATUS = ComplainStatus.POSTED;
    private static final ComplainStatus UPDATED_STATUS = ComplainStatus.HIDED;

    private static final String ENTITY_API_URL = "/api/artwork-complains";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ArtworkComplainRepository artworkComplainRepository;

    @Autowired
    private ArtworkComplainMapper artworkComplainMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArtworkComplainMockMvc;

    private ArtworkComplain artworkComplain;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArtworkComplain createEntity(EntityManager em) {
        ArtworkComplain artworkComplain = new ArtworkComplain().content(DEFAULT_CONTENT).status(DEFAULT_STATUS);
        return artworkComplain;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArtworkComplain createUpdatedEntity(EntityManager em) {
        ArtworkComplain artworkComplain = new ArtworkComplain().content(UPDATED_CONTENT).status(UPDATED_STATUS);
        return artworkComplain;
    }

    @BeforeEach
    public void initTest() {
        artworkComplain = createEntity(em);
    }

    @Test
    @Transactional
    void createArtworkComplain() throws Exception {
        int databaseSizeBeforeCreate = artworkComplainRepository.findAll().size();
        // Create the ArtworkComplain
        ArtworkComplainDTO artworkComplainDTO = artworkComplainMapper.toDto(artworkComplain);
        restArtworkComplainMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(artworkComplainDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ArtworkComplain in the database
        List<ArtworkComplain> artworkComplainList = artworkComplainRepository.findAll();
        assertThat(artworkComplainList).hasSize(databaseSizeBeforeCreate + 1);
        ArtworkComplain testArtworkComplain = artworkComplainList.get(artworkComplainList.size() - 1);
        assertThat(testArtworkComplain.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testArtworkComplain.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createArtworkComplainWithExistingId() throws Exception {
        // Create the ArtworkComplain with an existing ID
        artworkComplain.setId(1L);
        ArtworkComplainDTO artworkComplainDTO = artworkComplainMapper.toDto(artworkComplain);

        int databaseSizeBeforeCreate = artworkComplainRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restArtworkComplainMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(artworkComplainDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArtworkComplain in the database
        List<ArtworkComplain> artworkComplainList = artworkComplainRepository.findAll();
        assertThat(artworkComplainList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllArtworkComplains() throws Exception {
        // Initialize the database
        artworkComplainRepository.saveAndFlush(artworkComplain);

        // Get all the artworkComplainList
        restArtworkComplainMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(artworkComplain.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    void getArtworkComplain() throws Exception {
        // Initialize the database
        artworkComplainRepository.saveAndFlush(artworkComplain);

        // Get the artworkComplain
        restArtworkComplainMockMvc
            .perform(get(ENTITY_API_URL_ID, artworkComplain.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(artworkComplain.getId().intValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingArtworkComplain() throws Exception {
        // Get the artworkComplain
        restArtworkComplainMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingArtworkComplain() throws Exception {
        // Initialize the database
        artworkComplainRepository.saveAndFlush(artworkComplain);

        int databaseSizeBeforeUpdate = artworkComplainRepository.findAll().size();

        // Update the artworkComplain
        ArtworkComplain updatedArtworkComplain = artworkComplainRepository.findById(artworkComplain.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedArtworkComplain are not directly saved in db
        em.detach(updatedArtworkComplain);
        updatedArtworkComplain.content(UPDATED_CONTENT).status(UPDATED_STATUS);
        ArtworkComplainDTO artworkComplainDTO = artworkComplainMapper.toDto(updatedArtworkComplain);

        restArtworkComplainMockMvc
            .perform(
                put(ENTITY_API_URL_ID, artworkComplainDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(artworkComplainDTO))
            )
            .andExpect(status().isOk());

        // Validate the ArtworkComplain in the database
        List<ArtworkComplain> artworkComplainList = artworkComplainRepository.findAll();
        assertThat(artworkComplainList).hasSize(databaseSizeBeforeUpdate);
        ArtworkComplain testArtworkComplain = artworkComplainList.get(artworkComplainList.size() - 1);
        assertThat(testArtworkComplain.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testArtworkComplain.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingArtworkComplain() throws Exception {
        int databaseSizeBeforeUpdate = artworkComplainRepository.findAll().size();
        artworkComplain.setId(longCount.incrementAndGet());

        // Create the ArtworkComplain
        ArtworkComplainDTO artworkComplainDTO = artworkComplainMapper.toDto(artworkComplain);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArtworkComplainMockMvc
            .perform(
                put(ENTITY_API_URL_ID, artworkComplainDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(artworkComplainDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArtworkComplain in the database
        List<ArtworkComplain> artworkComplainList = artworkComplainRepository.findAll();
        assertThat(artworkComplainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchArtworkComplain() throws Exception {
        int databaseSizeBeforeUpdate = artworkComplainRepository.findAll().size();
        artworkComplain.setId(longCount.incrementAndGet());

        // Create the ArtworkComplain
        ArtworkComplainDTO artworkComplainDTO = artworkComplainMapper.toDto(artworkComplain);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtworkComplainMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(artworkComplainDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArtworkComplain in the database
        List<ArtworkComplain> artworkComplainList = artworkComplainRepository.findAll();
        assertThat(artworkComplainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamArtworkComplain() throws Exception {
        int databaseSizeBeforeUpdate = artworkComplainRepository.findAll().size();
        artworkComplain.setId(longCount.incrementAndGet());

        // Create the ArtworkComplain
        ArtworkComplainDTO artworkComplainDTO = artworkComplainMapper.toDto(artworkComplain);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtworkComplainMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(artworkComplainDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ArtworkComplain in the database
        List<ArtworkComplain> artworkComplainList = artworkComplainRepository.findAll();
        assertThat(artworkComplainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateArtworkComplainWithPatch() throws Exception {
        // Initialize the database
        artworkComplainRepository.saveAndFlush(artworkComplain);

        int databaseSizeBeforeUpdate = artworkComplainRepository.findAll().size();

        // Update the artworkComplain using partial update
        ArtworkComplain partialUpdatedArtworkComplain = new ArtworkComplain();
        partialUpdatedArtworkComplain.setId(artworkComplain.getId());

        partialUpdatedArtworkComplain.status(UPDATED_STATUS);

        restArtworkComplainMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArtworkComplain.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArtworkComplain))
            )
            .andExpect(status().isOk());

        // Validate the ArtworkComplain in the database
        List<ArtworkComplain> artworkComplainList = artworkComplainRepository.findAll();
        assertThat(artworkComplainList).hasSize(databaseSizeBeforeUpdate);
        ArtworkComplain testArtworkComplain = artworkComplainList.get(artworkComplainList.size() - 1);
        assertThat(testArtworkComplain.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testArtworkComplain.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateArtworkComplainWithPatch() throws Exception {
        // Initialize the database
        artworkComplainRepository.saveAndFlush(artworkComplain);

        int databaseSizeBeforeUpdate = artworkComplainRepository.findAll().size();

        // Update the artworkComplain using partial update
        ArtworkComplain partialUpdatedArtworkComplain = new ArtworkComplain();
        partialUpdatedArtworkComplain.setId(artworkComplain.getId());

        partialUpdatedArtworkComplain.content(UPDATED_CONTENT).status(UPDATED_STATUS);

        restArtworkComplainMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArtworkComplain.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArtworkComplain))
            )
            .andExpect(status().isOk());

        // Validate the ArtworkComplain in the database
        List<ArtworkComplain> artworkComplainList = artworkComplainRepository.findAll();
        assertThat(artworkComplainList).hasSize(databaseSizeBeforeUpdate);
        ArtworkComplain testArtworkComplain = artworkComplainList.get(artworkComplainList.size() - 1);
        assertThat(testArtworkComplain.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testArtworkComplain.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingArtworkComplain() throws Exception {
        int databaseSizeBeforeUpdate = artworkComplainRepository.findAll().size();
        artworkComplain.setId(longCount.incrementAndGet());

        // Create the ArtworkComplain
        ArtworkComplainDTO artworkComplainDTO = artworkComplainMapper.toDto(artworkComplain);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArtworkComplainMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, artworkComplainDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(artworkComplainDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArtworkComplain in the database
        List<ArtworkComplain> artworkComplainList = artworkComplainRepository.findAll();
        assertThat(artworkComplainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchArtworkComplain() throws Exception {
        int databaseSizeBeforeUpdate = artworkComplainRepository.findAll().size();
        artworkComplain.setId(longCount.incrementAndGet());

        // Create the ArtworkComplain
        ArtworkComplainDTO artworkComplainDTO = artworkComplainMapper.toDto(artworkComplain);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtworkComplainMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(artworkComplainDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArtworkComplain in the database
        List<ArtworkComplain> artworkComplainList = artworkComplainRepository.findAll();
        assertThat(artworkComplainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamArtworkComplain() throws Exception {
        int databaseSizeBeforeUpdate = artworkComplainRepository.findAll().size();
        artworkComplain.setId(longCount.incrementAndGet());

        // Create the ArtworkComplain
        ArtworkComplainDTO artworkComplainDTO = artworkComplainMapper.toDto(artworkComplain);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtworkComplainMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(artworkComplainDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ArtworkComplain in the database
        List<ArtworkComplain> artworkComplainList = artworkComplainRepository.findAll();
        assertThat(artworkComplainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteArtworkComplain() throws Exception {
        // Initialize the database
        artworkComplainRepository.saveAndFlush(artworkComplain);

        int databaseSizeBeforeDelete = artworkComplainRepository.findAll().size();

        // Delete the artworkComplain
        restArtworkComplainMockMvc
            .perform(delete(ENTITY_API_URL_ID, artworkComplain.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ArtworkComplain> artworkComplainList = artworkComplainRepository.findAll();
        assertThat(artworkComplainList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
