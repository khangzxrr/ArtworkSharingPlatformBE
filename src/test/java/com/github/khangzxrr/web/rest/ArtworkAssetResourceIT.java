package com.github.khangzxrr.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.github.khangzxrr.IntegrationTest;
import com.github.khangzxrr.domain.ArtworkAsset;
import com.github.khangzxrr.repository.ArtworkAssetRepository;
import com.github.khangzxrr.service.dto.ArtworkAssetDTO;
import com.github.khangzxrr.service.mapper.ArtworkAssetMapper;
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
 * Integration tests for the {@link ArtworkAssetResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ArtworkAssetResourceIT {

    private static final String ENTITY_API_URL = "/api/artwork-assets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ArtworkAssetRepository artworkAssetRepository;

    @Autowired
    private ArtworkAssetMapper artworkAssetMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArtworkAssetMockMvc;

    private ArtworkAsset artworkAsset;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArtworkAsset createEntity(EntityManager em) {
        ArtworkAsset artworkAsset = new ArtworkAsset();
        return artworkAsset;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArtworkAsset createUpdatedEntity(EntityManager em) {
        ArtworkAsset artworkAsset = new ArtworkAsset();
        return artworkAsset;
    }

    @BeforeEach
    public void initTest() {
        artworkAsset = createEntity(em);
    }

    @Test
    @Transactional
    void createArtworkAsset() throws Exception {
        int databaseSizeBeforeCreate = artworkAssetRepository.findAll().size();
        // Create the ArtworkAsset
        ArtworkAssetDTO artworkAssetDTO = artworkAssetMapper.toDto(artworkAsset);
        restArtworkAssetMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(artworkAssetDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ArtworkAsset in the database
        List<ArtworkAsset> artworkAssetList = artworkAssetRepository.findAll();
        assertThat(artworkAssetList).hasSize(databaseSizeBeforeCreate + 1);
        ArtworkAsset testArtworkAsset = artworkAssetList.get(artworkAssetList.size() - 1);
    }

    @Test
    @Transactional
    void createArtworkAssetWithExistingId() throws Exception {
        // Create the ArtworkAsset with an existing ID
        artworkAsset.setId(1L);
        ArtworkAssetDTO artworkAssetDTO = artworkAssetMapper.toDto(artworkAsset);

        int databaseSizeBeforeCreate = artworkAssetRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restArtworkAssetMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(artworkAssetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArtworkAsset in the database
        List<ArtworkAsset> artworkAssetList = artworkAssetRepository.findAll();
        assertThat(artworkAssetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllArtworkAssets() throws Exception {
        // Initialize the database
        artworkAssetRepository.saveAndFlush(artworkAsset);

        // Get all the artworkAssetList
        restArtworkAssetMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(artworkAsset.getId().intValue())));
    }

    @Test
    @Transactional
    void getArtworkAsset() throws Exception {
        // Initialize the database
        artworkAssetRepository.saveAndFlush(artworkAsset);

        // Get the artworkAsset
        restArtworkAssetMockMvc
            .perform(get(ENTITY_API_URL_ID, artworkAsset.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(artworkAsset.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingArtworkAsset() throws Exception {
        // Get the artworkAsset
        restArtworkAssetMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingArtworkAsset() throws Exception {
        // Initialize the database
        artworkAssetRepository.saveAndFlush(artworkAsset);

        int databaseSizeBeforeUpdate = artworkAssetRepository.findAll().size();

        // Update the artworkAsset
        ArtworkAsset updatedArtworkAsset = artworkAssetRepository.findById(artworkAsset.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedArtworkAsset are not directly saved in db
        em.detach(updatedArtworkAsset);
        ArtworkAssetDTO artworkAssetDTO = artworkAssetMapper.toDto(updatedArtworkAsset);

        restArtworkAssetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, artworkAssetDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(artworkAssetDTO))
            )
            .andExpect(status().isOk());

        // Validate the ArtworkAsset in the database
        List<ArtworkAsset> artworkAssetList = artworkAssetRepository.findAll();
        assertThat(artworkAssetList).hasSize(databaseSizeBeforeUpdate);
        ArtworkAsset testArtworkAsset = artworkAssetList.get(artworkAssetList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingArtworkAsset() throws Exception {
        int databaseSizeBeforeUpdate = artworkAssetRepository.findAll().size();
        artworkAsset.setId(longCount.incrementAndGet());

        // Create the ArtworkAsset
        ArtworkAssetDTO artworkAssetDTO = artworkAssetMapper.toDto(artworkAsset);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArtworkAssetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, artworkAssetDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(artworkAssetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArtworkAsset in the database
        List<ArtworkAsset> artworkAssetList = artworkAssetRepository.findAll();
        assertThat(artworkAssetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchArtworkAsset() throws Exception {
        int databaseSizeBeforeUpdate = artworkAssetRepository.findAll().size();
        artworkAsset.setId(longCount.incrementAndGet());

        // Create the ArtworkAsset
        ArtworkAssetDTO artworkAssetDTO = artworkAssetMapper.toDto(artworkAsset);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtworkAssetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(artworkAssetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArtworkAsset in the database
        List<ArtworkAsset> artworkAssetList = artworkAssetRepository.findAll();
        assertThat(artworkAssetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamArtworkAsset() throws Exception {
        int databaseSizeBeforeUpdate = artworkAssetRepository.findAll().size();
        artworkAsset.setId(longCount.incrementAndGet());

        // Create the ArtworkAsset
        ArtworkAssetDTO artworkAssetDTO = artworkAssetMapper.toDto(artworkAsset);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtworkAssetMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(artworkAssetDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ArtworkAsset in the database
        List<ArtworkAsset> artworkAssetList = artworkAssetRepository.findAll();
        assertThat(artworkAssetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateArtworkAssetWithPatch() throws Exception {
        // Initialize the database
        artworkAssetRepository.saveAndFlush(artworkAsset);

        int databaseSizeBeforeUpdate = artworkAssetRepository.findAll().size();

        // Update the artworkAsset using partial update
        ArtworkAsset partialUpdatedArtworkAsset = new ArtworkAsset();
        partialUpdatedArtworkAsset.setId(artworkAsset.getId());

        restArtworkAssetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArtworkAsset.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArtworkAsset))
            )
            .andExpect(status().isOk());

        // Validate the ArtworkAsset in the database
        List<ArtworkAsset> artworkAssetList = artworkAssetRepository.findAll();
        assertThat(artworkAssetList).hasSize(databaseSizeBeforeUpdate);
        ArtworkAsset testArtworkAsset = artworkAssetList.get(artworkAssetList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateArtworkAssetWithPatch() throws Exception {
        // Initialize the database
        artworkAssetRepository.saveAndFlush(artworkAsset);

        int databaseSizeBeforeUpdate = artworkAssetRepository.findAll().size();

        // Update the artworkAsset using partial update
        ArtworkAsset partialUpdatedArtworkAsset = new ArtworkAsset();
        partialUpdatedArtworkAsset.setId(artworkAsset.getId());

        restArtworkAssetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArtworkAsset.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArtworkAsset))
            )
            .andExpect(status().isOk());

        // Validate the ArtworkAsset in the database
        List<ArtworkAsset> artworkAssetList = artworkAssetRepository.findAll();
        assertThat(artworkAssetList).hasSize(databaseSizeBeforeUpdate);
        ArtworkAsset testArtworkAsset = artworkAssetList.get(artworkAssetList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingArtworkAsset() throws Exception {
        int databaseSizeBeforeUpdate = artworkAssetRepository.findAll().size();
        artworkAsset.setId(longCount.incrementAndGet());

        // Create the ArtworkAsset
        ArtworkAssetDTO artworkAssetDTO = artworkAssetMapper.toDto(artworkAsset);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArtworkAssetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, artworkAssetDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(artworkAssetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArtworkAsset in the database
        List<ArtworkAsset> artworkAssetList = artworkAssetRepository.findAll();
        assertThat(artworkAssetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchArtworkAsset() throws Exception {
        int databaseSizeBeforeUpdate = artworkAssetRepository.findAll().size();
        artworkAsset.setId(longCount.incrementAndGet());

        // Create the ArtworkAsset
        ArtworkAssetDTO artworkAssetDTO = artworkAssetMapper.toDto(artworkAsset);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtworkAssetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(artworkAssetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArtworkAsset in the database
        List<ArtworkAsset> artworkAssetList = artworkAssetRepository.findAll();
        assertThat(artworkAssetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamArtworkAsset() throws Exception {
        int databaseSizeBeforeUpdate = artworkAssetRepository.findAll().size();
        artworkAsset.setId(longCount.incrementAndGet());

        // Create the ArtworkAsset
        ArtworkAssetDTO artworkAssetDTO = artworkAssetMapper.toDto(artworkAsset);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtworkAssetMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(artworkAssetDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ArtworkAsset in the database
        List<ArtworkAsset> artworkAssetList = artworkAssetRepository.findAll();
        assertThat(artworkAssetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteArtworkAsset() throws Exception {
        // Initialize the database
        artworkAssetRepository.saveAndFlush(artworkAsset);

        int databaseSizeBeforeDelete = artworkAssetRepository.findAll().size();

        // Delete the artworkAsset
        restArtworkAssetMockMvc
            .perform(delete(ENTITY_API_URL_ID, artworkAsset.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ArtworkAsset> artworkAssetList = artworkAssetRepository.findAll();
        assertThat(artworkAssetList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
