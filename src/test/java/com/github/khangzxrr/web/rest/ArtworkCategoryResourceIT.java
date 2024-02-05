package com.github.khangzxrr.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.github.khangzxrr.IntegrationTest;
import com.github.khangzxrr.domain.ArtworkCategory;
import com.github.khangzxrr.repository.ArtworkCategoryRepository;
import com.github.khangzxrr.service.dto.ArtworkCategoryDTO;
import com.github.khangzxrr.service.mapper.ArtworkCategoryMapper;
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
 * Integration tests for the {@link ArtworkCategoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ArtworkCategoryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/artwork-categories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ArtworkCategoryRepository artworkCategoryRepository;

    @Autowired
    private ArtworkCategoryMapper artworkCategoryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArtworkCategoryMockMvc;

    private ArtworkCategory artworkCategory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArtworkCategory createEntity(EntityManager em) {
        ArtworkCategory artworkCategory = new ArtworkCategory().name(DEFAULT_NAME);
        return artworkCategory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArtworkCategory createUpdatedEntity(EntityManager em) {
        ArtworkCategory artworkCategory = new ArtworkCategory().name(UPDATED_NAME);
        return artworkCategory;
    }

    @BeforeEach
    public void initTest() {
        artworkCategory = createEntity(em);
    }

    @Test
    @Transactional
    void createArtworkCategory() throws Exception {
        int databaseSizeBeforeCreate = artworkCategoryRepository.findAll().size();
        // Create the ArtworkCategory
        ArtworkCategoryDTO artworkCategoryDTO = artworkCategoryMapper.toDto(artworkCategory);
        restArtworkCategoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(artworkCategoryDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ArtworkCategory in the database
        List<ArtworkCategory> artworkCategoryList = artworkCategoryRepository.findAll();
        assertThat(artworkCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        ArtworkCategory testArtworkCategory = artworkCategoryList.get(artworkCategoryList.size() - 1);
        assertThat(testArtworkCategory.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createArtworkCategoryWithExistingId() throws Exception {
        // Create the ArtworkCategory with an existing ID
        artworkCategory.setId(1L);
        ArtworkCategoryDTO artworkCategoryDTO = artworkCategoryMapper.toDto(artworkCategory);

        int databaseSizeBeforeCreate = artworkCategoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restArtworkCategoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(artworkCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArtworkCategory in the database
        List<ArtworkCategory> artworkCategoryList = artworkCategoryRepository.findAll();
        assertThat(artworkCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllArtworkCategories() throws Exception {
        // Initialize the database
        artworkCategoryRepository.saveAndFlush(artworkCategory);

        // Get all the artworkCategoryList
        restArtworkCategoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(artworkCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getArtworkCategory() throws Exception {
        // Initialize the database
        artworkCategoryRepository.saveAndFlush(artworkCategory);

        // Get the artworkCategory
        restArtworkCategoryMockMvc
            .perform(get(ENTITY_API_URL_ID, artworkCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(artworkCategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingArtworkCategory() throws Exception {
        // Get the artworkCategory
        restArtworkCategoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingArtworkCategory() throws Exception {
        // Initialize the database
        artworkCategoryRepository.saveAndFlush(artworkCategory);

        int databaseSizeBeforeUpdate = artworkCategoryRepository.findAll().size();

        // Update the artworkCategory
        ArtworkCategory updatedArtworkCategory = artworkCategoryRepository.findById(artworkCategory.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedArtworkCategory are not directly saved in db
        em.detach(updatedArtworkCategory);
        updatedArtworkCategory.name(UPDATED_NAME);
        ArtworkCategoryDTO artworkCategoryDTO = artworkCategoryMapper.toDto(updatedArtworkCategory);

        restArtworkCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, artworkCategoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(artworkCategoryDTO))
            )
            .andExpect(status().isOk());

        // Validate the ArtworkCategory in the database
        List<ArtworkCategory> artworkCategoryList = artworkCategoryRepository.findAll();
        assertThat(artworkCategoryList).hasSize(databaseSizeBeforeUpdate);
        ArtworkCategory testArtworkCategory = artworkCategoryList.get(artworkCategoryList.size() - 1);
        assertThat(testArtworkCategory.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingArtworkCategory() throws Exception {
        int databaseSizeBeforeUpdate = artworkCategoryRepository.findAll().size();
        artworkCategory.setId(longCount.incrementAndGet());

        // Create the ArtworkCategory
        ArtworkCategoryDTO artworkCategoryDTO = artworkCategoryMapper.toDto(artworkCategory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArtworkCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, artworkCategoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(artworkCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArtworkCategory in the database
        List<ArtworkCategory> artworkCategoryList = artworkCategoryRepository.findAll();
        assertThat(artworkCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchArtworkCategory() throws Exception {
        int databaseSizeBeforeUpdate = artworkCategoryRepository.findAll().size();
        artworkCategory.setId(longCount.incrementAndGet());

        // Create the ArtworkCategory
        ArtworkCategoryDTO artworkCategoryDTO = artworkCategoryMapper.toDto(artworkCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtworkCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(artworkCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArtworkCategory in the database
        List<ArtworkCategory> artworkCategoryList = artworkCategoryRepository.findAll();
        assertThat(artworkCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamArtworkCategory() throws Exception {
        int databaseSizeBeforeUpdate = artworkCategoryRepository.findAll().size();
        artworkCategory.setId(longCount.incrementAndGet());

        // Create the ArtworkCategory
        ArtworkCategoryDTO artworkCategoryDTO = artworkCategoryMapper.toDto(artworkCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtworkCategoryMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(artworkCategoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ArtworkCategory in the database
        List<ArtworkCategory> artworkCategoryList = artworkCategoryRepository.findAll();
        assertThat(artworkCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateArtworkCategoryWithPatch() throws Exception {
        // Initialize the database
        artworkCategoryRepository.saveAndFlush(artworkCategory);

        int databaseSizeBeforeUpdate = artworkCategoryRepository.findAll().size();

        // Update the artworkCategory using partial update
        ArtworkCategory partialUpdatedArtworkCategory = new ArtworkCategory();
        partialUpdatedArtworkCategory.setId(artworkCategory.getId());

        restArtworkCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArtworkCategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArtworkCategory))
            )
            .andExpect(status().isOk());

        // Validate the ArtworkCategory in the database
        List<ArtworkCategory> artworkCategoryList = artworkCategoryRepository.findAll();
        assertThat(artworkCategoryList).hasSize(databaseSizeBeforeUpdate);
        ArtworkCategory testArtworkCategory = artworkCategoryList.get(artworkCategoryList.size() - 1);
        assertThat(testArtworkCategory.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateArtworkCategoryWithPatch() throws Exception {
        // Initialize the database
        artworkCategoryRepository.saveAndFlush(artworkCategory);

        int databaseSizeBeforeUpdate = artworkCategoryRepository.findAll().size();

        // Update the artworkCategory using partial update
        ArtworkCategory partialUpdatedArtworkCategory = new ArtworkCategory();
        partialUpdatedArtworkCategory.setId(artworkCategory.getId());

        partialUpdatedArtworkCategory.name(UPDATED_NAME);

        restArtworkCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArtworkCategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArtworkCategory))
            )
            .andExpect(status().isOk());

        // Validate the ArtworkCategory in the database
        List<ArtworkCategory> artworkCategoryList = artworkCategoryRepository.findAll();
        assertThat(artworkCategoryList).hasSize(databaseSizeBeforeUpdate);
        ArtworkCategory testArtworkCategory = artworkCategoryList.get(artworkCategoryList.size() - 1);
        assertThat(testArtworkCategory.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingArtworkCategory() throws Exception {
        int databaseSizeBeforeUpdate = artworkCategoryRepository.findAll().size();
        artworkCategory.setId(longCount.incrementAndGet());

        // Create the ArtworkCategory
        ArtworkCategoryDTO artworkCategoryDTO = artworkCategoryMapper.toDto(artworkCategory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArtworkCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, artworkCategoryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(artworkCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArtworkCategory in the database
        List<ArtworkCategory> artworkCategoryList = artworkCategoryRepository.findAll();
        assertThat(artworkCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchArtworkCategory() throws Exception {
        int databaseSizeBeforeUpdate = artworkCategoryRepository.findAll().size();
        artworkCategory.setId(longCount.incrementAndGet());

        // Create the ArtworkCategory
        ArtworkCategoryDTO artworkCategoryDTO = artworkCategoryMapper.toDto(artworkCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtworkCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(artworkCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArtworkCategory in the database
        List<ArtworkCategory> artworkCategoryList = artworkCategoryRepository.findAll();
        assertThat(artworkCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamArtworkCategory() throws Exception {
        int databaseSizeBeforeUpdate = artworkCategoryRepository.findAll().size();
        artworkCategory.setId(longCount.incrementAndGet());

        // Create the ArtworkCategory
        ArtworkCategoryDTO artworkCategoryDTO = artworkCategoryMapper.toDto(artworkCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtworkCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(artworkCategoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ArtworkCategory in the database
        List<ArtworkCategory> artworkCategoryList = artworkCategoryRepository.findAll();
        assertThat(artworkCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteArtworkCategory() throws Exception {
        // Initialize the database
        artworkCategoryRepository.saveAndFlush(artworkCategory);

        int databaseSizeBeforeDelete = artworkCategoryRepository.findAll().size();

        // Delete the artworkCategory
        restArtworkCategoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, artworkCategory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ArtworkCategory> artworkCategoryList = artworkCategoryRepository.findAll();
        assertThat(artworkCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
