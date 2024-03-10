// package com.github.khangzxrr.web.rest;

// import static org.assertj.core.api.Assertions.assertThat;
// import static org.hamcrest.Matchers.hasItem;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// import com.github.khangzxrr.IntegrationTest;
// import com.github.khangzxrr.domain.ArtworkComment;
// import com.github.khangzxrr.repository.ArtworkCommentRepository;
// import com.github.khangzxrr.service.dto.ArtworkCommentDTO;
// import com.github.khangzxrr.service.mapper.ArtworkCommentMapper;
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
//  * Integration tests for the {@link ArtworkCommentResource} REST controller.
//  */
// @IntegrationTest
// @AutoConfigureMockMvc
// @WithMockUser
// class ArtworkCommentResourceIT {

//     private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
//     private static final String UPDATED_CONTENT = "BBBBBBBBBB";

//     private static final LocalDate DEFAULT_CREATE_AT = LocalDate.ofEpochDay(0L);
//     private static final LocalDate UPDATED_CREATE_AT = LocalDate.now(ZoneId.systemDefault());

//     private static final String ENTITY_API_URL = "/api/artwork-comments";
//     private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

//     private static Random random = new Random();
//     private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

//     @Autowired
//     private ArtworkCommentRepository artworkCommentRepository;

//     @Autowired
//     private ArtworkCommentMapper artworkCommentMapper;

//     @Autowired
//     private EntityManager em;

//     @Autowired
//     private MockMvc restArtworkCommentMockMvc;

//     private ArtworkComment artworkComment;

//     /**
//      * Create an entity for this test.
//      *
//      * This is a static method, as tests for other entities might also need it,
//      * if they test an entity which requires the current entity.
//      */
//     public static ArtworkComment createEntity(EntityManager em) {
//         ArtworkComment artworkComment = new ArtworkComment().content(DEFAULT_CONTENT).createAt(DEFAULT_CREATE_AT);
//         return artworkComment;
//     }

//     /**
//      * Create an updated entity for this test.
//      *
//      * This is a static method, as tests for other entities might also need it,
//      * if they test an entity which requires the current entity.
//      */
//     public static ArtworkComment createUpdatedEntity(EntityManager em) {
//         ArtworkComment artworkComment = new ArtworkComment().content(UPDATED_CONTENT).createAt(UPDATED_CREATE_AT);
//         return artworkComment;
//     }

//     @BeforeEach
//     public void initTest() {
//         artworkComment = createEntity(em);
//     }

//     @Test
//     @Transactional
//     void createArtworkComment() throws Exception {
//         int databaseSizeBeforeCreate = artworkCommentRepository.findAll().size();
//         // Create the ArtworkComment
//         ArtworkCommentDTO artworkCommentDTO = artworkCommentMapper.toDto(artworkComment);
//         restArtworkCommentMockMvc
//             .perform(
//                 post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(artworkCommentDTO))
//             )
//             .andExpect(status().isCreated());

//         // Validate the ArtworkComment in the database
//         List<ArtworkComment> artworkCommentList = artworkCommentRepository.findAll();
//         assertThat(artworkCommentList).hasSize(databaseSizeBeforeCreate + 1);
//         ArtworkComment testArtworkComment = artworkCommentList.get(artworkCommentList.size() - 1);
//         assertThat(testArtworkComment.getContent()).isEqualTo(DEFAULT_CONTENT);
//         assertThat(testArtworkComment.getCreateAt()).isEqualTo(DEFAULT_CREATE_AT);
//     }

//     @Test
//     @Transactional
//     void createArtworkCommentWithExistingId() throws Exception {
//         // Create the ArtworkComment with an existing ID
//         artworkComment.setId(1L);
//         ArtworkCommentDTO artworkCommentDTO = artworkCommentMapper.toDto(artworkComment);

//         int databaseSizeBeforeCreate = artworkCommentRepository.findAll().size();

//         // An entity with an existing ID cannot be created, so this API call must fail
//         restArtworkCommentMockMvc
//             .perform(
//                 post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(artworkCommentDTO))
//             )
//             .andExpect(status().isBadRequest());

//         // Validate the ArtworkComment in the database
//         List<ArtworkComment> artworkCommentList = artworkCommentRepository.findAll();
//         assertThat(artworkCommentList).hasSize(databaseSizeBeforeCreate);
//     }

//     @Test
//     @Transactional
//     void getAllArtworkComments() throws Exception {
//         // Initialize the database
//         artworkCommentRepository.saveAndFlush(artworkComment);

//         // Get all the artworkCommentList
//         restArtworkCommentMockMvc
//             .perform(get(ENTITY_API_URL + "?sort=id,desc"))
//             .andExpect(status().isOk())
//             .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//             .andExpect(jsonPath("$.[*].id").value(hasItem(artworkComment.getId().intValue())))
//             .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
//             .andExpect(jsonPath("$.[*].createAt").value(hasItem(DEFAULT_CREATE_AT.toString())));
//     }

//     @Test
//     @Transactional
//     void getArtworkComment() throws Exception {
//         // Initialize the database
//         artworkCommentRepository.saveAndFlush(artworkComment);

//         // Get the artworkComment
//         restArtworkCommentMockMvc
//             .perform(get(ENTITY_API_URL_ID, artworkComment.getId()))
//             .andExpect(status().isOk())
//             .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//             .andExpect(jsonPath("$.id").value(artworkComment.getId().intValue()))
//             .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT))
//             .andExpect(jsonPath("$.createAt").value(DEFAULT_CREATE_AT.toString()));
//     }

//     @Test
//     @Transactional
//     void getNonExistingArtworkComment() throws Exception {
//         // Get the artworkComment
//         restArtworkCommentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
//     }

//     @Test
//     @Transactional
//     void putExistingArtworkComment() throws Exception {
//         // Initialize the database
//         artworkCommentRepository.saveAndFlush(artworkComment);

//         int databaseSizeBeforeUpdate = artworkCommentRepository.findAll().size();

//         // Update the artworkComment
//         ArtworkComment updatedArtworkComment = artworkCommentRepository.findById(artworkComment.getId()).orElseThrow();
//         // Disconnect from session so that the updates on updatedArtworkComment are not directly saved in db
//         em.detach(updatedArtworkComment);
//         updatedArtworkComment.content(UPDATED_CONTENT).createAt(UPDATED_CREATE_AT);
//         ArtworkCommentDTO artworkCommentDTO = artworkCommentMapper.toDto(updatedArtworkComment);

//         restArtworkCommentMockMvc
//             .perform(
//                 put(ENTITY_API_URL_ID, artworkCommentDTO.getId())
//                     .contentType(MediaType.APPLICATION_JSON)
//                     .content(TestUtil.convertObjectToJsonBytes(artworkCommentDTO))
//             )
//             .andExpect(status().isOk());

//         // Validate the ArtworkComment in the database
//         List<ArtworkComment> artworkCommentList = artworkCommentRepository.findAll();
//         assertThat(artworkCommentList).hasSize(databaseSizeBeforeUpdate);
//         ArtworkComment testArtworkComment = artworkCommentList.get(artworkCommentList.size() - 1);
//         assertThat(testArtworkComment.getContent()).isEqualTo(UPDATED_CONTENT);
//         assertThat(testArtworkComment.getCreateAt()).isEqualTo(UPDATED_CREATE_AT);
//     }

//     @Test
//     @Transactional
//     void putNonExistingArtworkComment() throws Exception {
//         int databaseSizeBeforeUpdate = artworkCommentRepository.findAll().size();
//         artworkComment.setId(longCount.incrementAndGet());

//         // Create the ArtworkComment
//         ArtworkCommentDTO artworkCommentDTO = artworkCommentMapper.toDto(artworkComment);

//         // If the entity doesn't have an ID, it will throw BadRequestAlertException
//         restArtworkCommentMockMvc
//             .perform(
//                 put(ENTITY_API_URL_ID, artworkCommentDTO.getId())
//                     .contentType(MediaType.APPLICATION_JSON)
//                     .content(TestUtil.convertObjectToJsonBytes(artworkCommentDTO))
//             )
//             .andExpect(status().isBadRequest());

//         // Validate the ArtworkComment in the database
//         List<ArtworkComment> artworkCommentList = artworkCommentRepository.findAll();
//         assertThat(artworkCommentList).hasSize(databaseSizeBeforeUpdate);
//     }

//     @Test
//     @Transactional
//     void putWithIdMismatchArtworkComment() throws Exception {
//         int databaseSizeBeforeUpdate = artworkCommentRepository.findAll().size();
//         artworkComment.setId(longCount.incrementAndGet());

//         // Create the ArtworkComment
//         ArtworkCommentDTO artworkCommentDTO = artworkCommentMapper.toDto(artworkComment);

//         // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//         restArtworkCommentMockMvc
//             .perform(
//                 put(ENTITY_API_URL_ID, longCount.incrementAndGet())
//                     .contentType(MediaType.APPLICATION_JSON)
//                     .content(TestUtil.convertObjectToJsonBytes(artworkCommentDTO))
//             )
//             .andExpect(status().isBadRequest());

//         // Validate the ArtworkComment in the database
//         List<ArtworkComment> artworkCommentList = artworkCommentRepository.findAll();
//         assertThat(artworkCommentList).hasSize(databaseSizeBeforeUpdate);
//     }

//     @Test
//     @Transactional
//     void putWithMissingIdPathParamArtworkComment() throws Exception {
//         int databaseSizeBeforeUpdate = artworkCommentRepository.findAll().size();
//         artworkComment.setId(longCount.incrementAndGet());

//         // Create the ArtworkComment
//         ArtworkCommentDTO artworkCommentDTO = artworkCommentMapper.toDto(artworkComment);

//         // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//         restArtworkCommentMockMvc
//             .perform(
//                 put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(artworkCommentDTO))
//             )
//             .andExpect(status().isMethodNotAllowed());

//         // Validate the ArtworkComment in the database
//         List<ArtworkComment> artworkCommentList = artworkCommentRepository.findAll();
//         assertThat(artworkCommentList).hasSize(databaseSizeBeforeUpdate);
//     }

//     @Test
//     @Transactional
//     void partialUpdateArtworkCommentWithPatch() throws Exception {
//         // Initialize the database
//         artworkCommentRepository.saveAndFlush(artworkComment);

//         int databaseSizeBeforeUpdate = artworkCommentRepository.findAll().size();

//         // Update the artworkComment using partial update
//         ArtworkComment partialUpdatedArtworkComment = new ArtworkComment();
//         partialUpdatedArtworkComment.setId(artworkComment.getId());

//         partialUpdatedArtworkComment.createAt(UPDATED_CREATE_AT);

//         restArtworkCommentMockMvc
//             .perform(
//                 patch(ENTITY_API_URL_ID, partialUpdatedArtworkComment.getId())
//                     .contentType("application/merge-patch+json")
//                     .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArtworkComment))
//             )
//             .andExpect(status().isOk());

//         // Validate the ArtworkComment in the database
//         List<ArtworkComment> artworkCommentList = artworkCommentRepository.findAll();
//         assertThat(artworkCommentList).hasSize(databaseSizeBeforeUpdate);
//         ArtworkComment testArtworkComment = artworkCommentList.get(artworkCommentList.size() - 1);
//         assertThat(testArtworkComment.getContent()).isEqualTo(DEFAULT_CONTENT);
//         assertThat(testArtworkComment.getCreateAt()).isEqualTo(UPDATED_CREATE_AT);
//     }

//     @Test
//     @Transactional
//     void fullUpdateArtworkCommentWithPatch() throws Exception {
//         // Initialize the database
//         artworkCommentRepository.saveAndFlush(artworkComment);

//         int databaseSizeBeforeUpdate = artworkCommentRepository.findAll().size();

//         // Update the artworkComment using partial update
//         ArtworkComment partialUpdatedArtworkComment = new ArtworkComment();
//         partialUpdatedArtworkComment.setId(artworkComment.getId());

//         partialUpdatedArtworkComment.content(UPDATED_CONTENT).createAt(UPDATED_CREATE_AT);

//         restArtworkCommentMockMvc
//             .perform(
//                 patch(ENTITY_API_URL_ID, partialUpdatedArtworkComment.getId())
//                     .contentType("application/merge-patch+json")
//                     .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArtworkComment))
//             )
//             .andExpect(status().isOk());

//         // Validate the ArtworkComment in the database
//         List<ArtworkComment> artworkCommentList = artworkCommentRepository.findAll();
//         assertThat(artworkCommentList).hasSize(databaseSizeBeforeUpdate);
//         ArtworkComment testArtworkComment = artworkCommentList.get(artworkCommentList.size() - 1);
//         assertThat(testArtworkComment.getContent()).isEqualTo(UPDATED_CONTENT);
//         assertThat(testArtworkComment.getCreateAt()).isEqualTo(UPDATED_CREATE_AT);
//     }

//     @Test
//     @Transactional
//     void patchNonExistingArtworkComment() throws Exception {
//         int databaseSizeBeforeUpdate = artworkCommentRepository.findAll().size();
//         artworkComment.setId(longCount.incrementAndGet());

//         // Create the ArtworkComment
//         ArtworkCommentDTO artworkCommentDTO = artworkCommentMapper.toDto(artworkComment);

//         // If the entity doesn't have an ID, it will throw BadRequestAlertException
//         restArtworkCommentMockMvc
//             .perform(
//                 patch(ENTITY_API_URL_ID, artworkCommentDTO.getId())
//                     .contentType("application/merge-patch+json")
//                     .content(TestUtil.convertObjectToJsonBytes(artworkCommentDTO))
//             )
//             .andExpect(status().isBadRequest());

//         // Validate the ArtworkComment in the database
//         List<ArtworkComment> artworkCommentList = artworkCommentRepository.findAll();
//         assertThat(artworkCommentList).hasSize(databaseSizeBeforeUpdate);
//     }

//     @Test
//     @Transactional
//     void patchWithIdMismatchArtworkComment() throws Exception {
//         int databaseSizeBeforeUpdate = artworkCommentRepository.findAll().size();
//         artworkComment.setId(longCount.incrementAndGet());

//         // Create the ArtworkComment
//         ArtworkCommentDTO artworkCommentDTO = artworkCommentMapper.toDto(artworkComment);

//         // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//         restArtworkCommentMockMvc
//             .perform(
//                 patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
//                     .contentType("application/merge-patch+json")
//                     .content(TestUtil.convertObjectToJsonBytes(artworkCommentDTO))
//             )
//             .andExpect(status().isBadRequest());

//         // Validate the ArtworkComment in the database
//         List<ArtworkComment> artworkCommentList = artworkCommentRepository.findAll();
//         assertThat(artworkCommentList).hasSize(databaseSizeBeforeUpdate);
//     }

//     @Test
//     @Transactional
//     void patchWithMissingIdPathParamArtworkComment() throws Exception {
//         int databaseSizeBeforeUpdate = artworkCommentRepository.findAll().size();
//         artworkComment.setId(longCount.incrementAndGet());

//         // Create the ArtworkComment
//         ArtworkCommentDTO artworkCommentDTO = artworkCommentMapper.toDto(artworkComment);

//         // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//         restArtworkCommentMockMvc
//             .perform(
//                 patch(ENTITY_API_URL)
//                     .contentType("application/merge-patch+json")
//                     .content(TestUtil.convertObjectToJsonBytes(artworkCommentDTO))
//             )
//             .andExpect(status().isMethodNotAllowed());

//         // Validate the ArtworkComment in the database
//         List<ArtworkComment> artworkCommentList = artworkCommentRepository.findAll();
//         assertThat(artworkCommentList).hasSize(databaseSizeBeforeUpdate);
//     }

//     @Test
//     @Transactional
//     void deleteArtworkComment() throws Exception {
//         // Initialize the database
//         artworkCommentRepository.saveAndFlush(artworkComment);

//         int databaseSizeBeforeDelete = artworkCommentRepository.findAll().size();

//         // Delete the artworkComment
//         restArtworkCommentMockMvc
//             .perform(delete(ENTITY_API_URL_ID, artworkComment.getId()).accept(MediaType.APPLICATION_JSON))
//             .andExpect(status().isNoContent());

//         // Validate the database contains one less item
//         List<ArtworkComment> artworkCommentList = artworkCommentRepository.findAll();
//         assertThat(artworkCommentList).hasSize(databaseSizeBeforeDelete - 1);
//     }
// }
