package com.github.khangzxrr.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.github.khangzxrr.IntegrationTest;
import com.github.khangzxrr.domain.ArtworkLike;
import com.github.khangzxrr.repository.ArtworkLikeRepository;
import com.github.khangzxrr.service.dto.ArtworkLikeDTO;
import com.github.khangzxrr.service.mapper.ArtworkLikeMapper;
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
 * Integration tests for the {@link ArtworkLikeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ArtworkLikeResourceIT {

    private static final LocalDate DEFAULT_CREATE_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_AT = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/artwork-likes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ArtworkLikeRepository artworkLikeRepository;

    @Autowired
    private ArtworkLikeMapper artworkLikeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArtworkLikeMockMvc;

    private ArtworkLike artworkLike;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArtworkLike createEntity(EntityManager em) {
        ArtworkLike artworkLike = new ArtworkLike().createAt(DEFAULT_CREATE_AT);
        return artworkLike;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArtworkLike createUpdatedEntity(EntityManager em) {
        ArtworkLike artworkLike = new ArtworkLike().createAt(UPDATED_CREATE_AT);
        return artworkLike;
    }

    @BeforeEach
    public void initTest() {
        artworkLike = createEntity(em);
    }

    @Test
    @Transactional
    void createArtworkLike() throws Exception {
        int databaseSizeBeforeCreate = artworkLikeRepository.findAll().size();
        // Create the ArtworkLike
        ArtworkLikeDTO artworkLikeDTO = artworkLikeMapper.toDto(artworkLike);
        restArtworkLikeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(artworkLikeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ArtworkLike in the database
        List<ArtworkLike> artworkLikeList = artworkLikeRepository.findAll();
        assertThat(artworkLikeList).hasSize(databaseSizeBeforeCreate + 1);
        ArtworkLike testArtworkLike = artworkLikeList.get(artworkLikeList.size() - 1);
        assertThat(testArtworkLike.getCreateAt()).isEqualTo(DEFAULT_CREATE_AT);
    }

    @Test
    @Transactional
    void createArtworkLikeWithExistingId() throws Exception {
        // Create the ArtworkLike with an existing ID
        artworkLike.setId(1L);
        ArtworkLikeDTO artworkLikeDTO = artworkLikeMapper.toDto(artworkLike);

        int databaseSizeBeforeCreate = artworkLikeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restArtworkLikeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(artworkLikeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArtworkLike in the database
        List<ArtworkLike> artworkLikeList = artworkLikeRepository.findAll();
        assertThat(artworkLikeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllArtworkLikes() throws Exception {
        // Initialize the database
        artworkLikeRepository.saveAndFlush(artworkLike);

        // Get all the artworkLikeList
        restArtworkLikeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(artworkLike.getId().intValue())))
            .andExpect(jsonPath("$.[*].createAt").value(hasItem(DEFAULT_CREATE_AT.toString())));
    }

    @Test
    @Transactional
    void getArtworkLike() throws Exception {
        // Initialize the database
        artworkLikeRepository.saveAndFlush(artworkLike);

        // Get the artworkLike
        restArtworkLikeMockMvc
            .perform(get(ENTITY_API_URL_ID, artworkLike.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(artworkLike.getId().intValue()))
            .andExpect(jsonPath("$.createAt").value(DEFAULT_CREATE_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingArtworkLike() throws Exception {
        // Get the artworkLike
        restArtworkLikeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingArtworkLike() throws Exception {
        // Initialize the database
        artworkLikeRepository.saveAndFlush(artworkLike);

        int databaseSizeBeforeUpdate = artworkLikeRepository.findAll().size();

        // Update the artworkLike
        ArtworkLike updatedArtworkLike = artworkLikeRepository.findById(artworkLike.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedArtworkLike are not directly saved in db
        em.detach(updatedArtworkLike);
        updatedArtworkLike.createAt(UPDATED_CREATE_AT);
        ArtworkLikeDTO artworkLikeDTO = artworkLikeMapper.toDto(updatedArtworkLike);

        restArtworkLikeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, artworkLikeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(artworkLikeDTO))
            )
            .andExpect(status().isOk());

        // Validate the ArtworkLike in the database
        List<ArtworkLike> artworkLikeList = artworkLikeRepository.findAll();
        assertThat(artworkLikeList).hasSize(databaseSizeBeforeUpdate);
        ArtworkLike testArtworkLike = artworkLikeList.get(artworkLikeList.size() - 1);
        assertThat(testArtworkLike.getCreateAt()).isEqualTo(UPDATED_CREATE_AT);
    }

    @Test
    @Transactional
    void putNonExistingArtworkLike() throws Exception {
        int databaseSizeBeforeUpdate = artworkLikeRepository.findAll().size();
        artworkLike.setId(longCount.incrementAndGet());

        // Create the ArtworkLike
        ArtworkLikeDTO artworkLikeDTO = artworkLikeMapper.toDto(artworkLike);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArtworkLikeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, artworkLikeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(artworkLikeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArtworkLike in the database
        List<ArtworkLike> artworkLikeList = artworkLikeRepository.findAll();
        assertThat(artworkLikeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchArtworkLike() throws Exception {
        int databaseSizeBeforeUpdate = artworkLikeRepository.findAll().size();
        artworkLike.setId(longCount.incrementAndGet());

        // Create the ArtworkLike
        ArtworkLikeDTO artworkLikeDTO = artworkLikeMapper.toDto(artworkLike);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtworkLikeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(artworkLikeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArtworkLike in the database
        List<ArtworkLike> artworkLikeList = artworkLikeRepository.findAll();
        assertThat(artworkLikeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamArtworkLike() throws Exception {
        int databaseSizeBeforeUpdate = artworkLikeRepository.findAll().size();
        artworkLike.setId(longCount.incrementAndGet());

        // Create the ArtworkLike
        ArtworkLikeDTO artworkLikeDTO = artworkLikeMapper.toDto(artworkLike);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtworkLikeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(artworkLikeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ArtworkLike in the database
        List<ArtworkLike> artworkLikeList = artworkLikeRepository.findAll();
        assertThat(artworkLikeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateArtworkLikeWithPatch() throws Exception {
        // Initialize the database
        artworkLikeRepository.saveAndFlush(artworkLike);

        int databaseSizeBeforeUpdate = artworkLikeRepository.findAll().size();

        // Update the artworkLike using partial update
        ArtworkLike partialUpdatedArtworkLike = new ArtworkLike();
        partialUpdatedArtworkLike.setId(artworkLike.getId());

        restArtworkLikeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArtworkLike.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArtworkLike))
            )
            .andExpect(status().isOk());

        // Validate the ArtworkLike in the database
        List<ArtworkLike> artworkLikeList = artworkLikeRepository.findAll();
        assertThat(artworkLikeList).hasSize(databaseSizeBeforeUpdate);
        ArtworkLike testArtworkLike = artworkLikeList.get(artworkLikeList.size() - 1);
        assertThat(testArtworkLike.getCreateAt()).isEqualTo(DEFAULT_CREATE_AT);
    }

    @Test
    @Transactional
    void fullUpdateArtworkLikeWithPatch() throws Exception {
        // Initialize the database
        artworkLikeRepository.saveAndFlush(artworkLike);

        int databaseSizeBeforeUpdate = artworkLikeRepository.findAll().size();

        // Update the artworkLike using partial update
        ArtworkLike partialUpdatedArtworkLike = new ArtworkLike();
        partialUpdatedArtworkLike.setId(artworkLike.getId());

        partialUpdatedArtworkLike.createAt(UPDATED_CREATE_AT);

        restArtworkLikeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArtworkLike.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArtworkLike))
            )
            .andExpect(status().isOk());

        // Validate the ArtworkLike in the database
        List<ArtworkLike> artworkLikeList = artworkLikeRepository.findAll();
        assertThat(artworkLikeList).hasSize(databaseSizeBeforeUpdate);
        ArtworkLike testArtworkLike = artworkLikeList.get(artworkLikeList.size() - 1);
        assertThat(testArtworkLike.getCreateAt()).isEqualTo(UPDATED_CREATE_AT);
    }

    @Test
    @Transactional
    void patchNonExistingArtworkLike() throws Exception {
        int databaseSizeBeforeUpdate = artworkLikeRepository.findAll().size();
        artworkLike.setId(longCount.incrementAndGet());

        // Create the ArtworkLike
        ArtworkLikeDTO artworkLikeDTO = artworkLikeMapper.toDto(artworkLike);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArtworkLikeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, artworkLikeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(artworkLikeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArtworkLike in the database
        List<ArtworkLike> artworkLikeList = artworkLikeRepository.findAll();
        assertThat(artworkLikeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchArtworkLike() throws Exception {
        int databaseSizeBeforeUpdate = artworkLikeRepository.findAll().size();
        artworkLike.setId(longCount.incrementAndGet());

        // Create the ArtworkLike
        ArtworkLikeDTO artworkLikeDTO = artworkLikeMapper.toDto(artworkLike);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtworkLikeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(artworkLikeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ArtworkLike in the database
        List<ArtworkLike> artworkLikeList = artworkLikeRepository.findAll();
        assertThat(artworkLikeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamArtworkLike() throws Exception {
        int databaseSizeBeforeUpdate = artworkLikeRepository.findAll().size();
        artworkLike.setId(longCount.incrementAndGet());

        // Create the ArtworkLike
        ArtworkLikeDTO artworkLikeDTO = artworkLikeMapper.toDto(artworkLike);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtworkLikeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(artworkLikeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ArtworkLike in the database
        List<ArtworkLike> artworkLikeList = artworkLikeRepository.findAll();
        assertThat(artworkLikeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteArtworkLike() throws Exception {
        // Initialize the database
        artworkLikeRepository.saveAndFlush(artworkLike);

        int databaseSizeBeforeDelete = artworkLikeRepository.findAll().size();

        // Delete the artworkLike
        restArtworkLikeMockMvc
            .perform(delete(ENTITY_API_URL_ID, artworkLike.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ArtworkLike> artworkLikeList = artworkLikeRepository.findAll();
        assertThat(artworkLikeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
