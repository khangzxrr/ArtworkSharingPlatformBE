// package com.github.khangzxrr.web.rest;

// import static org.assertj.core.api.Assertions.assertThat;
// import static org.hamcrest.Matchers.hasItem;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// import com.github.khangzxrr.IntegrationTest;
// import com.github.khangzxrr.domain.Artwork;
// import com.github.khangzxrr.domain.enumeration.ArtworkStatus;
// import com.github.khangzxrr.repository.ArtworkRepository;
// import com.github.khangzxrr.service.dto.artworkDTOs.ArtworkDTO;
// import com.github.khangzxrr.service.mapper.ArtworkMapper;
// import jakarta.persistence.EntityManager;
// import java.util.List;
// import java.util.Random;
// import java.util.concurrent.atomic.AtomicLong;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.http.MediaType;
// import org.springframework.security.test.context.support.WithMockUser;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.transaction.annotation.Transactional;

// /**
//  * Integration tests for the {@link ArtworkResource} REST controller.
//  */
// @IntegrationTest
// @AutoConfigureMockMvc
// @WithMockUser
// class ArtworkResourceIT {

//     private static final String DEFAULT_NAME = "AAAAAAAAAA";
//     private static final String UPDATED_NAME = "BBBBBBBBBB";

//     private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
//     private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

//     private static final String DEFAULT_CREATE_AT = "AAAAAAAAAA";
//     private static final String UPDATED_CREATE_AT = "BBBBBBBBBB";

//     private static final ArtworkStatus DEFAULT_STATUS = ArtworkStatus.ENABLE;
//     private static final ArtworkStatus UPDATED_STATUS = ArtworkStatus.DISABLE;

//     private static final String ENTITY_API_URL = "/api/artworks";
//     private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

//     private static Random random = new Random();
//     private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

//     @Autowired
//     private ArtworkRepository artworkRepository;

//     @Autowired
//     private ArtworkMapper artworkMapper;

//     @Autowired
//     private EntityManager em;

//     @Autowired
//     private MockMvc restArtworkMockMvc;

//     private Artwork artwork;

//     /**
//      * Create an entity for this test.
//      *
//      * This is a static method, as tests for other entities might also need it,
//      * if they test an entity which requires the current entity.
//      */
//     public static Artwork createEntity(EntityManager em) {
//         Artwork artwork = new Artwork()
//             .name(DEFAULT_NAME)
//             .description(DEFAULT_DESCRIPTION)
//             .createAt(DEFAULT_CREATE_AT)
//             .status(DEFAULT_STATUS);
//         return artwork;
//     }

//     /**
//      * Create an updated entity for this test.
//      *
//      * This is a static method, as tests for other entities might also need it,
//      * if they test an entity which requires the current entity.
//      */
//     public static Artwork createUpdatedEntity(EntityManager em) {
//         Artwork artwork = new Artwork()
//             .name(UPDATED_NAME)
//             .description(UPDATED_DESCRIPTION)
//             .createAt(UPDATED_CREATE_AT)
//             .status(UPDATED_STATUS);
//         return artwork;
//     }

//     @BeforeEach
//     public void initTest() {
//         artwork = createEntity(em);
//     }

//     @Test
//     @Transactional
//     void createArtwork() throws Exception {
//         int databaseSizeBeforeCreate = artworkRepository.findAll().size();
//         // Create the Artwork
//         ArtworkDTO artworkDTO = artworkMapper.toDto(artwork);
//         restArtworkMockMvc
//             .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(artworkDTO)))
//             .andExpect(status().isCreated());

//         // Validate the Artwork in the database
//         List<Artwork> artworkList = artworkRepository.findAll();
//         assertThat(artworkList).hasSize(databaseSizeBeforeCreate + 1);
//         Artwork testArtwork = artworkList.get(artworkList.size() - 1);
//         assertThat(testArtwork.getName()).isEqualTo(DEFAULT_NAME);
//         assertThat(testArtwork.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
//         assertThat(testArtwork.getCreateAt()).isEqualTo(DEFAULT_CREATE_AT);
//         assertThat(testArtwork.getStatus()).isEqualTo(DEFAULT_STATUS);
//     }

//     @Test
//     @Transactional
//     void createArtworkWithExistingId() throws Exception {
//         // Create the Artwork with an existing ID
//         artwork.setId(1L);
//         ArtworkDTO artworkDTO = artworkMapper.toDto(artwork);

//         int databaseSizeBeforeCreate = artworkRepository.findAll().size();

//         // An entity with an existing ID cannot be created, so this API call must fail
//         restArtworkMockMvc
//             .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(artworkDTO)))
//             .andExpect(status().isBadRequest());

//         // Validate the Artwork in the database
//         List<Artwork> artworkList = artworkRepository.findAll();
//         assertThat(artworkList).hasSize(databaseSizeBeforeCreate);
//     }

//     @Test
//     @Transactional
//     void getAllArtworks() throws Exception {
//         // Initialize the database
//         artworkRepository.saveAndFlush(artwork);

//         // Get all the artworkList
//         restArtworkMockMvc
//             .perform(get(ENTITY_API_URL + "?sort=id,desc"))
//             .andExpect(status().isOk())
//             .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//             .andExpect(jsonPath("$.[*].id").value(hasItem(artwork.getId().intValue())))
//             .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
//             .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
//             .andExpect(jsonPath("$.[*].createAt").value(hasItem(DEFAULT_CREATE_AT)))
//             .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
//     }

//     @Test
//     @Transactional
//     void getArtwork() throws Exception {
//         // Initialize the database
//         artworkRepository.saveAndFlush(artwork);

//         // Get the artwork
//         restArtworkMockMvc
//             .perform(get(ENTITY_API_URL_ID, artwork.getId()))
//             .andExpect(status().isOk())
//             .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//             .andExpect(jsonPath("$.id").value(artwork.getId().intValue()))
//             .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
//             .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
//             .andExpect(jsonPath("$.createAt").value(DEFAULT_CREATE_AT))
//             .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
//     }

//     @Test
//     @Transactional
//     void getNonExistingArtwork() throws Exception {
//         // Get the artwork
//         restArtworkMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
//     }

//     @Test
//     @Transactional
//     void putExistingArtwork() throws Exception {
//         // Initialize the database
//         artworkRepository.saveAndFlush(artwork);

//         int databaseSizeBeforeUpdate = artworkRepository.findAll().size();

//         // Update the artwork
//         Artwork updatedArtwork = artworkRepository.findById(artwork.getId()).orElseThrow();
//         // Disconnect from session so that the updates on updatedArtwork are not directly saved in db
//         em.detach(updatedArtwork);
//         updatedArtwork.name(UPDATED_NAME).description(UPDATED_DESCRIPTION).createAt(UPDATED_CREATE_AT).status(UPDATED_STATUS);
//         ArtworkDTO artworkDTO = artworkMapper.toDto(updatedArtwork);

//         restArtworkMockMvc
//             .perform(
//                 put(ENTITY_API_URL_ID, artworkDTO.getId())
//                     .contentType(MediaType.APPLICATION_JSON)
//                     .content(TestUtil.convertObjectToJsonBytes(artworkDTO))
//             )
//             .andExpect(status().isOk());

//         // Validate the Artwork in the database
//         List<Artwork> artworkList = artworkRepository.findAll();
//         assertThat(artworkList).hasSize(databaseSizeBeforeUpdate);
//         Artwork testArtwork = artworkList.get(artworkList.size() - 1);
//         assertThat(testArtwork.getName()).isEqualTo(UPDATED_NAME);
//         assertThat(testArtwork.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
//         assertThat(testArtwork.getCreateAt()).isEqualTo(UPDATED_CREATE_AT);
//         assertThat(testArtwork.getStatus()).isEqualTo(UPDATED_STATUS);
//     }

//     @Test
//     @Transactional
//     void putNonExistingArtwork() throws Exception {
//         int databaseSizeBeforeUpdate = artworkRepository.findAll().size();
//         artwork.setId(longCount.incrementAndGet());

//         // Create the Artwork
//         ArtworkDTO artworkDTO = artworkMapper.toDto(artwork);

//         // If the entity doesn't have an ID, it will throw BadRequestAlertException
//         restArtworkMockMvc
//             .perform(
//                 put(ENTITY_API_URL_ID, artworkDTO.getId())
//                     .contentType(MediaType.APPLICATION_JSON)
//                     .content(TestUtil.convertObjectToJsonBytes(artworkDTO))
//             )
//             .andExpect(status().isBadRequest());

//         // Validate the Artwork in the database
//         List<Artwork> artworkList = artworkRepository.findAll();
//         assertThat(artworkList).hasSize(databaseSizeBeforeUpdate);
//     }

//     @Test
//     @Transactional
//     void putWithIdMismatchArtwork() throws Exception {
//         int databaseSizeBeforeUpdate = artworkRepository.findAll().size();
//         artwork.setId(longCount.incrementAndGet());

//         // Create the Artwork
//         ArtworkDTO artworkDTO = artworkMapper.toDto(artwork);

//         // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//         restArtworkMockMvc
//             .perform(
//                 put(ENTITY_API_URL_ID, longCount.incrementAndGet())
//                     .contentType(MediaType.APPLICATION_JSON)
//                     .content(TestUtil.convertObjectToJsonBytes(artworkDTO))
//             )
//             .andExpect(status().isBadRequest());

//         // Validate the Artwork in the database
//         List<Artwork> artworkList = artworkRepository.findAll();
//         assertThat(artworkList).hasSize(databaseSizeBeforeUpdate);
//     }

//     @Test
//     @Transactional
//     void putWithMissingIdPathParamArtwork() throws Exception {
//         int databaseSizeBeforeUpdate = artworkRepository.findAll().size();
//         artwork.setId(longCount.incrementAndGet());

//         // Create the Artwork
//         ArtworkDTO artworkDTO = artworkMapper.toDto(artwork);

//         // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//         restArtworkMockMvc
//             .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(artworkDTO)))
//             .andExpect(status().isMethodNotAllowed());

//         // Validate the Artwork in the database
//         List<Artwork> artworkList = artworkRepository.findAll();
//         assertThat(artworkList).hasSize(databaseSizeBeforeUpdate);
//     }

//     @Test
//     @Transactional
//     void partialUpdateArtworkWithPatch() throws Exception {
//         // Initialize the database
//         artworkRepository.saveAndFlush(artwork);

//         int databaseSizeBeforeUpdate = artworkRepository.findAll().size();

//         // Update the artwork using partial update
//         Artwork partialUpdatedArtwork = new Artwork();
//         partialUpdatedArtwork.setId(artwork.getId());

//         partialUpdatedArtwork.description(UPDATED_DESCRIPTION).createAt(UPDATED_CREATE_AT);

//         restArtworkMockMvc
//             .perform(
//                 patch(ENTITY_API_URL_ID, partialUpdatedArtwork.getId())
//                     .contentType("application/merge-patch+json")
//                     .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArtwork))
//             )
//             .andExpect(status().isOk());

//         // Validate the Artwork in the database
//         List<Artwork> artworkList = artworkRepository.findAll();
//         assertThat(artworkList).hasSize(databaseSizeBeforeUpdate);
//         Artwork testArtwork = artworkList.get(artworkList.size() - 1);
//         assertThat(testArtwork.getName()).isEqualTo(DEFAULT_NAME);
//         assertThat(testArtwork.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
//         assertThat(testArtwork.getCreateAt()).isEqualTo(UPDATED_CREATE_AT);
//         assertThat(testArtwork.getStatus()).isEqualTo(DEFAULT_STATUS);
//     }

//     @Test
//     @Transactional
//     void fullUpdateArtworkWithPatch() throws Exception {
//         // Initialize the database
//         artworkRepository.saveAndFlush(artwork);

//         int databaseSizeBeforeUpdate = artworkRepository.findAll().size();

//         // Update the artwork using partial update
//         Artwork partialUpdatedArtwork = new Artwork();
//         partialUpdatedArtwork.setId(artwork.getId());

//         partialUpdatedArtwork.name(UPDATED_NAME).description(UPDATED_DESCRIPTION).createAt(UPDATED_CREATE_AT).status(UPDATED_STATUS);

//         restArtworkMockMvc
//             .perform(
//                 patch(ENTITY_API_URL_ID, partialUpdatedArtwork.getId())
//                     .contentType("application/merge-patch+json")
//                     .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArtwork))
//             )
//             .andExpect(status().isOk());

//         // Validate the Artwork in the database
//         List<Artwork> artworkList = artworkRepository.findAll();
//         assertThat(artworkList).hasSize(databaseSizeBeforeUpdate);
//         Artwork testArtwork = artworkList.get(artworkList.size() - 1);
//         assertThat(testArtwork.getName()).isEqualTo(UPDATED_NAME);
//         assertThat(testArtwork.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
//         assertThat(testArtwork.getCreateAt()).isEqualTo(UPDATED_CREATE_AT);
//         assertThat(testArtwork.getStatus()).isEqualTo(UPDATED_STATUS);
//     }

//     @Test
//     @Transactional
//     void patchNonExistingArtwork() throws Exception {
//         int databaseSizeBeforeUpdate = artworkRepository.findAll().size();
//         artwork.setId(longCount.incrementAndGet());

//         // Create the Artwork
//         ArtworkDTO artworkDTO = artworkMapper.toDto(artwork);

//         // If the entity doesn't have an ID, it will throw BadRequestAlertException
//         restArtworkMockMvc
//             .perform(
//                 patch(ENTITY_API_URL_ID, artworkDTO.getId())
//                     .contentType("application/merge-patch+json")
//                     .content(TestUtil.convertObjectToJsonBytes(artworkDTO))
//             )
//             .andExpect(status().isBadRequest());

//         // Validate the Artwork in the database
//         List<Artwork> artworkList = artworkRepository.findAll();
//         assertThat(artworkList).hasSize(databaseSizeBeforeUpdate);
//     }

//     @Test
//     @Transactional
//     void patchWithIdMismatchArtwork() throws Exception {
//         int databaseSizeBeforeUpdate = artworkRepository.findAll().size();
//         artwork.setId(longCount.incrementAndGet());

//         // Create the Artwork
//         ArtworkDTO artworkDTO = artworkMapper.toDto(artwork);

//         // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//         restArtworkMockMvc
//             .perform(
//                 patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
//                     .contentType("application/merge-patch+json")
//                     .content(TestUtil.convertObjectToJsonBytes(artworkDTO))
//             )
//             .andExpect(status().isBadRequest());

//         // Validate the Artwork in the database
//         List<Artwork> artworkList = artworkRepository.findAll();
//         assertThat(artworkList).hasSize(databaseSizeBeforeUpdate);
//     }

//     @Test
//     @Transactional
//     void patchWithMissingIdPathParamArtwork() throws Exception {
//         int databaseSizeBeforeUpdate = artworkRepository.findAll().size();
//         artwork.setId(longCount.incrementAndGet());

//         // Create the Artwork
//         ArtworkDTO artworkDTO = artworkMapper.toDto(artwork);

//         // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//         restArtworkMockMvc
//             .perform(
//                 patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(artworkDTO))
//             )
//             .andExpect(status().isMethodNotAllowed());

//         // Validate the Artwork in the database
//         List<Artwork> artworkList = artworkRepository.findAll();
//         assertThat(artworkList).hasSize(databaseSizeBeforeUpdate);
//     }

//     @Test
//     @Transactional
//     void deleteArtwork() throws Exception {
//         // Initialize the database
//         artworkRepository.saveAndFlush(artwork);

//         int databaseSizeBeforeDelete = artworkRepository.findAll().size();

//         // Delete the artwork
//         restArtworkMockMvc
//             .perform(delete(ENTITY_API_URL_ID, artwork.getId()).accept(MediaType.APPLICATION_JSON))
//             .andExpect(status().isNoContent());

//         // Validate the database contains one less item
//         List<Artwork> artworkList = artworkRepository.findAll();
//         assertThat(artworkList).hasSize(databaseSizeBeforeDelete - 1);
//     }
// }
