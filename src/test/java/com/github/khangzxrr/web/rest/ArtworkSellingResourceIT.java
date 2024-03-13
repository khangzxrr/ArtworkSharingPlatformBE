// package com.github.khangzxrr.web.rest;

// import static org.assertj.core.api.Assertions.assertThat;
// import static org.hamcrest.Matchers.hasItem;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// import com.github.khangzxrr.IntegrationTest;
// import com.github.khangzxrr.domain.ArtworkSelling;
// import com.github.khangzxrr.domain.enumeration.ArtworkSellingStatus;
// import com.github.khangzxrr.domain.enumeration.ArtworkSellingType;
// import com.github.khangzxrr.repository.ArtworkSellingRepository;
// import com.github.khangzxrr.service.dto.ArtworkSellingDTO;
// import com.github.khangzxrr.service.mapper.ArtworkSellingMapper;
// import jakarta.persistence.EntityManager;
// import java.time.LocalDate;
// import java.time.ZoneId;
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
//  * Integration tests for the {@link ArtworkSellingResource} REST controller.
//  */
// @IntegrationTest
// @AutoConfigureMockMvc
// @WithMockUser
// class ArtworkSellingResourceIT {

//     private static final LocalDate DEFAULT_CREATE_AT = LocalDate.ofEpochDay(0L);
//     private static final LocalDate UPDATED_CREATE_AT = LocalDate.now(ZoneId.systemDefault());

//     private static final ArtworkSellingType DEFAULT_TYPE = ArtworkSellingType.DIRECT;
//     private static final ArtworkSellingType UPDATED_TYPE = ArtworkSellingType.AUCTION;

//     private static final ArtworkSellingStatus DEFAULT_STATUS = ArtworkSellingStatus.ON_GOING;
//     private static final ArtworkSellingStatus UPDATED_STATUS = ArtworkSellingStatus.FINISHED;

//     private static final Long DEFAULT_EXPECTED_SELLING_PRICE = 1L;
//     private static final Long UPDATED_EXPECTED_SELLING_PRICE = 2L;

//     private static final String ENTITY_API_URL = "/api/artwork-sellings";
//     private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

//     private static Random random = new Random();
//     private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

//     @Autowired
//     private ArtworkSellingRepository artworkSellingRepository;

//     @Autowired
//     private ArtworkSellingMapper artworkSellingMapper;

//     @Autowired
//     private EntityManager em;

//     @Autowired
//     private MockMvc restArtworkSellingMockMvc;

//     private ArtworkSelling artworkSelling;

//     /**
//      * Create an entity for this test.
//      *
//      * This is a static method, as tests for other entities might also need it,
//      * if they test an entity which requires the current entity.
//      */
//     public static ArtworkSelling createEntity(EntityManager em) {
//         ArtworkSelling artworkSelling = new ArtworkSelling()
//             .createAt(DEFAULT_CREATE_AT)
//             .type(DEFAULT_TYPE)
//             .status(DEFAULT_STATUS)
//             .expectedSellingPrice(DEFAULT_EXPECTED_SELLING_PRICE);
//         return artworkSelling;
//     }

//     /**
//      * Create an updated entity for this test.
//      *
//      * This is a static method, as tests for other entities might also need it,
//      * if they test an entity which requires the current entity.
//      */
//     public static ArtworkSelling createUpdatedEntity(EntityManager em) {
//         ArtworkSelling artworkSelling = new ArtworkSelling()
//             .createAt(UPDATED_CREATE_AT)
//             .type(UPDATED_TYPE)
//             .status(UPDATED_STATUS)
//             .expectedSellingPrice(UPDATED_EXPECTED_SELLING_PRICE);
//         return artworkSelling;
//     }

//     @BeforeEach
//     public void initTest() {
//         artworkSelling = createEntity(em);
//     }

//     @Test
//     @Transactional
//     void createArtworkSelling() throws Exception {
//         int databaseSizeBeforeCreate = artworkSellingRepository.findAll().size();
//         // Create the ArtworkSelling
//         ArtworkSellingDTO artworkSellingDTO = artworkSellingMapper.toDto(artworkSelling);
//         restArtworkSellingMockMvc
//             .perform(
//                 post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(artworkSellingDTO))
//             )
//             .andExpect(status().isCreated());

//         // Validate the ArtworkSelling in the database
//         List<ArtworkSelling> artworkSellingList = artworkSellingRepository.findAll();
//         assertThat(artworkSellingList).hasSize(databaseSizeBeforeCreate + 1);
//         ArtworkSelling testArtworkSelling = artworkSellingList.get(artworkSellingList.size() - 1);
//         assertThat(testArtworkSelling.getCreateAt()).isEqualTo(DEFAULT_CREATE_AT);
//         assertThat(testArtworkSelling.getType()).isEqualTo(DEFAULT_TYPE);
//         assertThat(testArtworkSelling.getStatus()).isEqualTo(DEFAULT_STATUS);
//         assertThat(testArtworkSelling.getExpectedSellingPrice()).isEqualTo(DEFAULT_EXPECTED_SELLING_PRICE);
//     }

//     @Test
//     @Transactional
//     void createArtworkSellingWithExistingId() throws Exception {
//         // Create the ArtworkSelling with an existing ID
//         artworkSelling.setId(1L);
//         ArtworkSellingDTO artworkSellingDTO = artworkSellingMapper.toDto(artworkSelling);

//         int databaseSizeBeforeCreate = artworkSellingRepository.findAll().size();

//         // An entity with an existing ID cannot be created, so this API call must fail
//         restArtworkSellingMockMvc
//             .perform(
//                 post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(artworkSellingDTO))
//             )
//             .andExpect(status().isBadRequest());

//         // Validate the ArtworkSelling in the database
//         List<ArtworkSelling> artworkSellingList = artworkSellingRepository.findAll();
//         assertThat(artworkSellingList).hasSize(databaseSizeBeforeCreate);
//     }

//     @Test
//     @Transactional
//     void getAllArtworkSellings() throws Exception {
//         // Initialize the database
//         artworkSellingRepository.saveAndFlush(artworkSelling);

//         // Get all the artworkSellingList
//         restArtworkSellingMockMvc
//             .perform(get(ENTITY_API_URL + "?sort=id,desc"))
//             .andExpect(status().isOk())
//             .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//             .andExpect(jsonPath("$.[*].id").value(hasItem(artworkSelling.getId().intValue())))
//             .andExpect(jsonPath("$.[*].createAt").value(hasItem(DEFAULT_CREATE_AT.toString())))
//             .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
//             .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
//             .andExpect(jsonPath("$.[*].expectedSellingPrice").value(hasItem(DEFAULT_EXPECTED_SELLING_PRICE.intValue())));
//     }

//     @Test
//     @Transactional
//     void getArtworkSelling() throws Exception {
//         // Initialize the database
//         artworkSellingRepository.saveAndFlush(artworkSelling);

//         // Get the artworkSelling
//         restArtworkSellingMockMvc
//             .perform(get(ENTITY_API_URL_ID, artworkSelling.getId()))
//             .andExpect(status().isOk())
//             .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//             .andExpect(jsonPath("$.id").value(artworkSelling.getId().intValue()))
//             .andExpect(jsonPath("$.createAt").value(DEFAULT_CREATE_AT.toString()))
//             .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
//             .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
//             .andExpect(jsonPath("$.expectedSellingPrice").value(DEFAULT_EXPECTED_SELLING_PRICE.intValue()));
//     }

//     @Test
//     @Transactional
//     void getNonExistingArtworkSelling() throws Exception {
//         // Get the artworkSelling
//         restArtworkSellingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
//     }

//     @Test
//     @Transactional
//     void putExistingArtworkSelling() throws Exception {
//         // Initialize the database
//         artworkSellingRepository.saveAndFlush(artworkSelling);

//         int databaseSizeBeforeUpdate = artworkSellingRepository.findAll().size();

//         // Update the artworkSelling
//         ArtworkSelling updatedArtworkSelling = artworkSellingRepository.findById(artworkSelling.getId()).orElseThrow();
//         // Disconnect from session so that the updates on updatedArtworkSelling are not directly saved in db
//         em.detach(updatedArtworkSelling);
//         updatedArtworkSelling
//             .createAt(UPDATED_CREATE_AT)
//             .type(UPDATED_TYPE)
//             .status(UPDATED_STATUS)
//             .expectedSellingPrice(UPDATED_EXPECTED_SELLING_PRICE);
//         ArtworkSellingDTO artworkSellingDTO = artworkSellingMapper.toDto(updatedArtworkSelling);

//         restArtworkSellingMockMvc
//             .perform(
//                 put(ENTITY_API_URL_ID, artworkSellingDTO.getId())
//                     .contentType(MediaType.APPLICATION_JSON)
//                     .content(TestUtil.convertObjectToJsonBytes(artworkSellingDTO))
//             )
//             .andExpect(status().isOk());

//         // Validate the ArtworkSelling in the database
//         List<ArtworkSelling> artworkSellingList = artworkSellingRepository.findAll();
//         assertThat(artworkSellingList).hasSize(databaseSizeBeforeUpdate);
//         ArtworkSelling testArtworkSelling = artworkSellingList.get(artworkSellingList.size() - 1);
//         assertThat(testArtworkSelling.getCreateAt()).isEqualTo(UPDATED_CREATE_AT);
//         assertThat(testArtworkSelling.getType()).isEqualTo(UPDATED_TYPE);
//         assertThat(testArtworkSelling.getStatus()).isEqualTo(UPDATED_STATUS);
//         assertThat(testArtworkSelling.getExpectedSellingPrice()).isEqualTo(UPDATED_EXPECTED_SELLING_PRICE);
//     }

//     @Test
//     @Transactional
//     void putNonExistingArtworkSelling() throws Exception {
//         int databaseSizeBeforeUpdate = artworkSellingRepository.findAll().size();
//         artworkSelling.setId(longCount.incrementAndGet());

//         // Create the ArtworkSelling
//         ArtworkSellingDTO artworkSellingDTO = artworkSellingMapper.toDto(artworkSelling);

//         // If the entity doesn't have an ID, it will throw BadRequestAlertException
//         restArtworkSellingMockMvc
//             .perform(
//                 put(ENTITY_API_URL_ID, artworkSellingDTO.getId())
//                     .contentType(MediaType.APPLICATION_JSON)
//                     .content(TestUtil.convertObjectToJsonBytes(artworkSellingDTO))
//             )
//             .andExpect(status().isBadRequest());

//         // Validate the ArtworkSelling in the database
//         List<ArtworkSelling> artworkSellingList = artworkSellingRepository.findAll();
//         assertThat(artworkSellingList).hasSize(databaseSizeBeforeUpdate);
//     }

//     @Test
//     @Transactional
//     void putWithIdMismatchArtworkSelling() throws Exception {
//         int databaseSizeBeforeUpdate = artworkSellingRepository.findAll().size();
//         artworkSelling.setId(longCount.incrementAndGet());

//         // Create the ArtworkSelling
//         ArtworkSellingDTO artworkSellingDTO = artworkSellingMapper.toDto(artworkSelling);

//         // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//         restArtworkSellingMockMvc
//             .perform(
//                 put(ENTITY_API_URL_ID, longCount.incrementAndGet())
//                     .contentType(MediaType.APPLICATION_JSON)
//                     .content(TestUtil.convertObjectToJsonBytes(artworkSellingDTO))
//             )
//             .andExpect(status().isBadRequest());

//         // Validate the ArtworkSelling in the database
//         List<ArtworkSelling> artworkSellingList = artworkSellingRepository.findAll();
//         assertThat(artworkSellingList).hasSize(databaseSizeBeforeUpdate);
//     }

//     @Test
//     @Transactional
//     void putWithMissingIdPathParamArtworkSelling() throws Exception {
//         int databaseSizeBeforeUpdate = artworkSellingRepository.findAll().size();
//         artworkSelling.setId(longCount.incrementAndGet());

//         // Create the ArtworkSelling
//         ArtworkSellingDTO artworkSellingDTO = artworkSellingMapper.toDto(artworkSelling);

//         // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//         restArtworkSellingMockMvc
//             .perform(
//                 put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(artworkSellingDTO))
//             )
//             .andExpect(status().isMethodNotAllowed());

//         // Validate the ArtworkSelling in the database
//         List<ArtworkSelling> artworkSellingList = artworkSellingRepository.findAll();
//         assertThat(artworkSellingList).hasSize(databaseSizeBeforeUpdate);
//     }

//     @Test
//     @Transactional
//     void partialUpdateArtworkSellingWithPatch() throws Exception {
//         // Initialize the database
//         artworkSellingRepository.saveAndFlush(artworkSelling);

//         int databaseSizeBeforeUpdate = artworkSellingRepository.findAll().size();

//         // Update the artworkSelling using partial update
//         ArtworkSelling partialUpdatedArtworkSelling = new ArtworkSelling();
//         partialUpdatedArtworkSelling.setId(artworkSelling.getId());

//         partialUpdatedArtworkSelling.createAt(UPDATED_CREATE_AT).type(UPDATED_TYPE).status(UPDATED_STATUS);

//         restArtworkSellingMockMvc
//             .perform(
//                 patch(ENTITY_API_URL_ID, partialUpdatedArtworkSelling.getId())
//                     .contentType("application/merge-patch+json")
//                     .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArtworkSelling))
//             )
//             .andExpect(status().isOk());

//         // Validate the ArtworkSelling in the database
//         List<ArtworkSelling> artworkSellingList = artworkSellingRepository.findAll();
//         assertThat(artworkSellingList).hasSize(databaseSizeBeforeUpdate);
//         ArtworkSelling testArtworkSelling = artworkSellingList.get(artworkSellingList.size() - 1);
//         assertThat(testArtworkSelling.getCreateAt()).isEqualTo(UPDATED_CREATE_AT);
//         assertThat(testArtworkSelling.getType()).isEqualTo(UPDATED_TYPE);
//         assertThat(testArtworkSelling.getStatus()).isEqualTo(UPDATED_STATUS);
//         assertThat(testArtworkSelling.getExpectedSellingPrice()).isEqualTo(DEFAULT_EXPECTED_SELLING_PRICE);
//     }

//     @Test
//     @Transactional
//     void fullUpdateArtworkSellingWithPatch() throws Exception {
//         // Initialize the database
//         artworkSellingRepository.saveAndFlush(artworkSelling);

//         int databaseSizeBeforeUpdate = artworkSellingRepository.findAll().size();

//         // Update the artworkSelling using partial update
//         ArtworkSelling partialUpdatedArtworkSelling = new ArtworkSelling();
//         partialUpdatedArtworkSelling.setId(artworkSelling.getId());

//         partialUpdatedArtworkSelling
//             .createAt(UPDATED_CREATE_AT)
//             .type(UPDATED_TYPE)
//             .status(UPDATED_STATUS)
//             .expectedSellingPrice(UPDATED_EXPECTED_SELLING_PRICE);

//         restArtworkSellingMockMvc
//             .perform(
//                 patch(ENTITY_API_URL_ID, partialUpdatedArtworkSelling.getId())
//                     .contentType("application/merge-patch+json")
//                     .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArtworkSelling))
//             )
//             .andExpect(status().isOk());

//         // Validate the ArtworkSelling in the database
//         List<ArtworkSelling> artworkSellingList = artworkSellingRepository.findAll();
//         assertThat(artworkSellingList).hasSize(databaseSizeBeforeUpdate);
//         ArtworkSelling testArtworkSelling = artworkSellingList.get(artworkSellingList.size() - 1);
//         assertThat(testArtworkSelling.getCreateAt()).isEqualTo(UPDATED_CREATE_AT);
//         assertThat(testArtworkSelling.getType()).isEqualTo(UPDATED_TYPE);
//         assertThat(testArtworkSelling.getStatus()).isEqualTo(UPDATED_STATUS);
//         assertThat(testArtworkSelling.getExpectedSellingPrice()).isEqualTo(UPDATED_EXPECTED_SELLING_PRICE);
//     }

//     @Test
//     @Transactional
//     void patchNonExistingArtworkSelling() throws Exception {
//         int databaseSizeBeforeUpdate = artworkSellingRepository.findAll().size();
//         artworkSelling.setId(longCount.incrementAndGet());

//         // Create the ArtworkSelling
//         ArtworkSellingDTO artworkSellingDTO = artworkSellingMapper.toDto(artworkSelling);

//         // If the entity doesn't have an ID, it will throw BadRequestAlertException
//         restArtworkSellingMockMvc
//             .perform(
//                 patch(ENTITY_API_URL_ID, artworkSellingDTO.getId())
//                     .contentType("application/merge-patch+json")
//                     .content(TestUtil.convertObjectToJsonBytes(artworkSellingDTO))
//             )
//             .andExpect(status().isBadRequest());

//         // Validate the ArtworkSelling in the database
//         List<ArtworkSelling> artworkSellingList = artworkSellingRepository.findAll();
//         assertThat(artworkSellingList).hasSize(databaseSizeBeforeUpdate);
//     }

//     @Test
//     @Transactional
//     void patchWithIdMismatchArtworkSelling() throws Exception {
//         int databaseSizeBeforeUpdate = artworkSellingRepository.findAll().size();
//         artworkSelling.setId(longCount.incrementAndGet());

//         // Create the ArtworkSelling
//         ArtworkSellingDTO artworkSellingDTO = artworkSellingMapper.toDto(artworkSelling);

//         // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//         restArtworkSellingMockMvc
//             .perform(
//                 patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
//                     .contentType("application/merge-patch+json")
//                     .content(TestUtil.convertObjectToJsonBytes(artworkSellingDTO))
//             )
//             .andExpect(status().isBadRequest());

//         // Validate the ArtworkSelling in the database
//         List<ArtworkSelling> artworkSellingList = artworkSellingRepository.findAll();
//         assertThat(artworkSellingList).hasSize(databaseSizeBeforeUpdate);
//     }

//     @Test
//     @Transactional
//     void patchWithMissingIdPathParamArtworkSelling() throws Exception {
//         int databaseSizeBeforeUpdate = artworkSellingRepository.findAll().size();
//         artworkSelling.setId(longCount.incrementAndGet());

//         // Create the ArtworkSelling
//         ArtworkSellingDTO artworkSellingDTO = artworkSellingMapper.toDto(artworkSelling);

//         // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//         restArtworkSellingMockMvc
//             .perform(
//                 patch(ENTITY_API_URL)
//                     .contentType("application/merge-patch+json")
//                     .content(TestUtil.convertObjectToJsonBytes(artworkSellingDTO))
//             )
//             .andExpect(status().isMethodNotAllowed());

//         // Validate the ArtworkSelling in the database
//         List<ArtworkSelling> artworkSellingList = artworkSellingRepository.findAll();
//         assertThat(artworkSellingList).hasSize(databaseSizeBeforeUpdate);
//     }

//     @Test
//     @Transactional
//     void deleteArtworkSelling() throws Exception {
//         // Initialize the database
//         artworkSellingRepository.saveAndFlush(artworkSelling);

//         int databaseSizeBeforeDelete = artworkSellingRepository.findAll().size();

//         // Delete the artworkSelling
//         restArtworkSellingMockMvc
//             .perform(delete(ENTITY_API_URL_ID, artworkSelling.getId()).accept(MediaType.APPLICATION_JSON))
//             .andExpect(status().isNoContent());

//         // Validate the database contains one less item
//         List<ArtworkSelling> artworkSellingList = artworkSellingRepository.findAll();
//         assertThat(artworkSellingList).hasSize(databaseSizeBeforeDelete - 1);
//     }
// }
