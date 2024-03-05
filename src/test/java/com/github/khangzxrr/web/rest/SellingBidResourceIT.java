// package com.github.khangzxrr.web.rest;

// import static org.assertj.core.api.Assertions.assertThat;
// import static org.hamcrest.Matchers.hasItem;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// import com.github.khangzxrr.IntegrationTest;
// import com.github.khangzxrr.domain.SellingBid;
// import com.github.khangzxrr.domain.WalletTransaction;
// import com.github.khangzxrr.domain.enumeration.SellingBidStatus;
// import com.github.khangzxrr.repository.SellingBidRepository;
// import com.github.khangzxrr.service.dto.SellingBidDTO;
// import com.github.khangzxrr.service.mapper.SellingBidMapper;
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
//  * Integration tests for the {@link SellingBidResource} REST controller.
//  */
// @IntegrationTest
// @AutoConfigureMockMvc
// @WithMockUser
// class SellingBidResourceIT {

//     private static final Long DEFAULT_BID_PRICE = 1L;
//     private static final Long UPDATED_BID_PRICE = 2L;

//     private static final LocalDate DEFAULT_CREATE_AT = LocalDate.ofEpochDay(0L);
//     private static final LocalDate UPDATED_CREATE_AT = LocalDate.now(ZoneId.systemDefault());

//     private static final SellingBidStatus DEFAULT_STATUS = SellingBidStatus.BIDED;
//     private static final SellingBidStatus UPDATED_STATUS = SellingBidStatus.SOLD;

//     private static final String ENTITY_API_URL = "/api/selling-bids";
//     private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

//     private static Random random = new Random();
//     private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

//     @Autowired
//     private SellingBidRepository sellingBidRepository;

//     @Autowired
//     private SellingBidMapper sellingBidMapper;

//     @Autowired
//     private EntityManager em;

//     @Autowired
//     private MockMvc restSellingBidMockMvc;

//     private SellingBid sellingBid;

//     /**
//      * Create an entity for this test.
//      *
//      * This is a static method, as tests for other entities might also need it,
//      * if they test an entity which requires the current entity.
//      */
//     public static SellingBid createEntity(EntityManager em) {
//         SellingBid sellingBid = new SellingBid().bidPrice(DEFAULT_BID_PRICE).createAt(DEFAULT_CREATE_AT).status(DEFAULT_STATUS);
//         // Add required entity
//         WalletTransaction walletTransaction;
//         if (TestUtil.findAll(em, WalletTransaction.class).isEmpty()) {
//             walletTransaction = WalletTransactionResourceIT.createEntity(em);
//             em.persist(walletTransaction);
//             em.flush();
//         } else {
//             walletTransaction = TestUtil.findAll(em, WalletTransaction.class).get(0);
//         }
//         sellingBid.setTransaction(walletTransaction);
//         return sellingBid;
//     }

//     /**
//      * Create an updated entity for this test.
//      *
//      * This is a static method, as tests for other entities might also need it,
//      * if they test an entity which requires the current entity.
//      */
//     public static SellingBid createUpdatedEntity(EntityManager em) {
//         SellingBid sellingBid = new SellingBid().bidPrice(UPDATED_BID_PRICE).createAt(UPDATED_CREATE_AT).status(UPDATED_STATUS);
//         // Add required entity
//         WalletTransaction walletTransaction;
//         if (TestUtil.findAll(em, WalletTransaction.class).isEmpty()) {
//             walletTransaction = WalletTransactionResourceIT.createUpdatedEntity(em);
//             em.persist(walletTransaction);
//             em.flush();
//         } else {
//             walletTransaction = TestUtil.findAll(em, WalletTransaction.class).get(0);
//         }
//         sellingBid.setTransaction(walletTransaction);
//         return sellingBid;
//     }

//     @BeforeEach
//     public void initTest() {
//         sellingBid = createEntity(em);
//     }

//     @Test
//     @Transactional
//     void createSellingBid() throws Exception {
//         int databaseSizeBeforeCreate = sellingBidRepository.findAll().size();
//         // Create the SellingBid
//         SellingBidDTO sellingBidDTO = sellingBidMapper.toDto(sellingBid);
//         restSellingBidMockMvc
//             .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sellingBidDTO)))
//             .andExpect(status().isCreated());

//         // Validate the SellingBid in the database
//         List<SellingBid> sellingBidList = sellingBidRepository.findAll();
//         assertThat(sellingBidList).hasSize(databaseSizeBeforeCreate + 1);
//         SellingBid testSellingBid = sellingBidList.get(sellingBidList.size() - 1);
//         assertThat(testSellingBid.getBidPrice()).isEqualTo(DEFAULT_BID_PRICE);
//         assertThat(testSellingBid.getCreateAt()).isEqualTo(DEFAULT_CREATE_AT);
//         assertThat(testSellingBid.getStatus()).isEqualTo(DEFAULT_STATUS);
//     }

//     @Test
//     @Transactional
//     void createSellingBidWithExistingId() throws Exception {
//         // Create the SellingBid with an existing ID
//         sellingBid.setId(1L);
//         SellingBidDTO sellingBidDTO = sellingBidMapper.toDto(sellingBid);

//         int databaseSizeBeforeCreate = sellingBidRepository.findAll().size();

//         // An entity with an existing ID cannot be created, so this API call must fail
//         restSellingBidMockMvc
//             .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sellingBidDTO)))
//             .andExpect(status().isBadRequest());

//         // Validate the SellingBid in the database
//         List<SellingBid> sellingBidList = sellingBidRepository.findAll();
//         assertThat(sellingBidList).hasSize(databaseSizeBeforeCreate);
//     }

//     @Test
//     @Transactional
//     void getAllSellingBids() throws Exception {
//         // Initialize the database
//         sellingBidRepository.saveAndFlush(sellingBid);

//         // Get all the sellingBidList
//         restSellingBidMockMvc
//             .perform(get(ENTITY_API_URL + "?sort=id,desc"))
//             .andExpect(status().isOk())
//             .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//             .andExpect(jsonPath("$.[*].id").value(hasItem(sellingBid.getId().intValue())))
//             .andExpect(jsonPath("$.[*].bidPrice").value(hasItem(DEFAULT_BID_PRICE.intValue())))
//             .andExpect(jsonPath("$.[*].createAt").value(hasItem(DEFAULT_CREATE_AT.toString())))
//             .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
//     }

//     @Test
//     @Transactional
//     void getSellingBid() throws Exception {
//         // Initialize the database
//         sellingBidRepository.saveAndFlush(sellingBid);

//         // Get the sellingBid
//         restSellingBidMockMvc
//             .perform(get(ENTITY_API_URL_ID, sellingBid.getId()))
//             .andExpect(status().isOk())
//             .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//             .andExpect(jsonPath("$.id").value(sellingBid.getId().intValue()))
//             .andExpect(jsonPath("$.bidPrice").value(DEFAULT_BID_PRICE.intValue()))
//             .andExpect(jsonPath("$.createAt").value(DEFAULT_CREATE_AT.toString()))
//             .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
//     }

//     @Test
//     @Transactional
//     void getNonExistingSellingBid() throws Exception {
//         // Get the sellingBid
//         restSellingBidMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
//     }

//     @Test
//     @Transactional
//     void putExistingSellingBid() throws Exception {
//         // Initialize the database
//         sellingBidRepository.saveAndFlush(sellingBid);

//         int databaseSizeBeforeUpdate = sellingBidRepository.findAll().size();

//         // Update the sellingBid
//         SellingBid updatedSellingBid = sellingBidRepository.findById(sellingBid.getId()).orElseThrow();
//         // Disconnect from session so that the updates on updatedSellingBid are not directly saved in db
//         em.detach(updatedSellingBid);
//         updatedSellingBid.bidPrice(UPDATED_BID_PRICE).createAt(UPDATED_CREATE_AT).status(UPDATED_STATUS);
//         SellingBidDTO sellingBidDTO = sellingBidMapper.toDto(updatedSellingBid);

//         restSellingBidMockMvc
//             .perform(
//                 put(ENTITY_API_URL_ID, sellingBidDTO.getId())
//                     .contentType(MediaType.APPLICATION_JSON)
//                     .content(TestUtil.convertObjectToJsonBytes(sellingBidDTO))
//             )
//             .andExpect(status().isOk());

//         // Validate the SellingBid in the database
//         List<SellingBid> sellingBidList = sellingBidRepository.findAll();
//         assertThat(sellingBidList).hasSize(databaseSizeBeforeUpdate);
//         SellingBid testSellingBid = sellingBidList.get(sellingBidList.size() - 1);
//         assertThat(testSellingBid.getBidPrice()).isEqualTo(UPDATED_BID_PRICE);
//         assertThat(testSellingBid.getCreateAt()).isEqualTo(UPDATED_CREATE_AT);
//         assertThat(testSellingBid.getStatus()).isEqualTo(UPDATED_STATUS);
//     }

//     @Test
//     @Transactional
//     void putNonExistingSellingBid() throws Exception {
//         int databaseSizeBeforeUpdate = sellingBidRepository.findAll().size();
//         sellingBid.setId(longCount.incrementAndGet());

//         // Create the SellingBid
//         SellingBidDTO sellingBidDTO = sellingBidMapper.toDto(sellingBid);

//         // If the entity doesn't have an ID, it will throw BadRequestAlertException
//         restSellingBidMockMvc
//             .perform(
//                 put(ENTITY_API_URL_ID, sellingBidDTO.getId())
//                     .contentType(MediaType.APPLICATION_JSON)
//                     .content(TestUtil.convertObjectToJsonBytes(sellingBidDTO))
//             )
//             .andExpect(status().isBadRequest());

//         // Validate the SellingBid in the database
//         List<SellingBid> sellingBidList = sellingBidRepository.findAll();
//         assertThat(sellingBidList).hasSize(databaseSizeBeforeUpdate);
//     }

//     @Test
//     @Transactional
//     void putWithIdMismatchSellingBid() throws Exception {
//         int databaseSizeBeforeUpdate = sellingBidRepository.findAll().size();
//         sellingBid.setId(longCount.incrementAndGet());

//         // Create the SellingBid
//         SellingBidDTO sellingBidDTO = sellingBidMapper.toDto(sellingBid);

//         // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//         restSellingBidMockMvc
//             .perform(
//                 put(ENTITY_API_URL_ID, longCount.incrementAndGet())
//                     .contentType(MediaType.APPLICATION_JSON)
//                     .content(TestUtil.convertObjectToJsonBytes(sellingBidDTO))
//             )
//             .andExpect(status().isBadRequest());

//         // Validate the SellingBid in the database
//         List<SellingBid> sellingBidList = sellingBidRepository.findAll();
//         assertThat(sellingBidList).hasSize(databaseSizeBeforeUpdate);
//     }

//     @Test
//     @Transactional
//     void putWithMissingIdPathParamSellingBid() throws Exception {
//         int databaseSizeBeforeUpdate = sellingBidRepository.findAll().size();
//         sellingBid.setId(longCount.incrementAndGet());

//         // Create the SellingBid
//         SellingBidDTO sellingBidDTO = sellingBidMapper.toDto(sellingBid);

//         // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//         restSellingBidMockMvc
//             .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sellingBidDTO)))
//             .andExpect(status().isMethodNotAllowed());

//         // Validate the SellingBid in the database
//         List<SellingBid> sellingBidList = sellingBidRepository.findAll();
//         assertThat(sellingBidList).hasSize(databaseSizeBeforeUpdate);
//     }

//     @Test
//     @Transactional
//     void partialUpdateSellingBidWithPatch() throws Exception {
//         // Initialize the database
//         sellingBidRepository.saveAndFlush(sellingBid);

//         int databaseSizeBeforeUpdate = sellingBidRepository.findAll().size();

//         // Update the sellingBid using partial update
//         SellingBid partialUpdatedSellingBid = new SellingBid();
//         partialUpdatedSellingBid.setId(sellingBid.getId());

//         partialUpdatedSellingBid.createAt(UPDATED_CREATE_AT).status(UPDATED_STATUS);

//         restSellingBidMockMvc
//             .perform(
//                 patch(ENTITY_API_URL_ID, partialUpdatedSellingBid.getId())
//                     .contentType("application/merge-patch+json")
//                     .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSellingBid))
//             )
//             .andExpect(status().isOk());

//         // Validate the SellingBid in the database
//         List<SellingBid> sellingBidList = sellingBidRepository.findAll();
//         assertThat(sellingBidList).hasSize(databaseSizeBeforeUpdate);
//         SellingBid testSellingBid = sellingBidList.get(sellingBidList.size() - 1);
//         assertThat(testSellingBid.getBidPrice()).isEqualTo(DEFAULT_BID_PRICE);
//         assertThat(testSellingBid.getCreateAt()).isEqualTo(UPDATED_CREATE_AT);
//         assertThat(testSellingBid.getStatus()).isEqualTo(UPDATED_STATUS);
//     }

//     @Test
//     @Transactional
//     void fullUpdateSellingBidWithPatch() throws Exception {
//         // Initialize the database
//         sellingBidRepository.saveAndFlush(sellingBid);

//         int databaseSizeBeforeUpdate = sellingBidRepository.findAll().size();

//         // Update the sellingBid using partial update
//         SellingBid partialUpdatedSellingBid = new SellingBid();
//         partialUpdatedSellingBid.setId(sellingBid.getId());

//         partialUpdatedSellingBid.bidPrice(UPDATED_BID_PRICE).createAt(UPDATED_CREATE_AT).status(UPDATED_STATUS);

//         restSellingBidMockMvc
//             .perform(
//                 patch(ENTITY_API_URL_ID, partialUpdatedSellingBid.getId())
//                     .contentType("application/merge-patch+json")
//                     .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSellingBid))
//             )
//             .andExpect(status().isOk());

//         // Validate the SellingBid in the database
//         List<SellingBid> sellingBidList = sellingBidRepository.findAll();
//         assertThat(sellingBidList).hasSize(databaseSizeBeforeUpdate);
//         SellingBid testSellingBid = sellingBidList.get(sellingBidList.size() - 1);
//         assertThat(testSellingBid.getBidPrice()).isEqualTo(UPDATED_BID_PRICE);
//         assertThat(testSellingBid.getCreateAt()).isEqualTo(UPDATED_CREATE_AT);
//         assertThat(testSellingBid.getStatus()).isEqualTo(UPDATED_STATUS);
//     }

//     @Test
//     @Transactional
//     void patchNonExistingSellingBid() throws Exception {
//         int databaseSizeBeforeUpdate = sellingBidRepository.findAll().size();
//         sellingBid.setId(longCount.incrementAndGet());

//         // Create the SellingBid
//         SellingBidDTO sellingBidDTO = sellingBidMapper.toDto(sellingBid);

//         // If the entity doesn't have an ID, it will throw BadRequestAlertException
//         restSellingBidMockMvc
//             .perform(
//                 patch(ENTITY_API_URL_ID, sellingBidDTO.getId())
//                     .contentType("application/merge-patch+json")
//                     .content(TestUtil.convertObjectToJsonBytes(sellingBidDTO))
//             )
//             .andExpect(status().isBadRequest());

//         // Validate the SellingBid in the database
//         List<SellingBid> sellingBidList = sellingBidRepository.findAll();
//         assertThat(sellingBidList).hasSize(databaseSizeBeforeUpdate);
//     }

//     @Test
//     @Transactional
//     void patchWithIdMismatchSellingBid() throws Exception {
//         int databaseSizeBeforeUpdate = sellingBidRepository.findAll().size();
//         sellingBid.setId(longCount.incrementAndGet());

//         // Create the SellingBid
//         SellingBidDTO sellingBidDTO = sellingBidMapper.toDto(sellingBid);

//         // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//         restSellingBidMockMvc
//             .perform(
//                 patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
//                     .contentType("application/merge-patch+json")
//                     .content(TestUtil.convertObjectToJsonBytes(sellingBidDTO))
//             )
//             .andExpect(status().isBadRequest());

//         // Validate the SellingBid in the database
//         List<SellingBid> sellingBidList = sellingBidRepository.findAll();
//         assertThat(sellingBidList).hasSize(databaseSizeBeforeUpdate);
//     }

//     @Test
//     @Transactional
//     void patchWithMissingIdPathParamSellingBid() throws Exception {
//         int databaseSizeBeforeUpdate = sellingBidRepository.findAll().size();
//         sellingBid.setId(longCount.incrementAndGet());

//         // Create the SellingBid
//         SellingBidDTO sellingBidDTO = sellingBidMapper.toDto(sellingBid);

//         // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//         restSellingBidMockMvc
//             .perform(
//                 patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(sellingBidDTO))
//             )
//             .andExpect(status().isMethodNotAllowed());

//         // Validate the SellingBid in the database
//         List<SellingBid> sellingBidList = sellingBidRepository.findAll();
//         assertThat(sellingBidList).hasSize(databaseSizeBeforeUpdate);
//     }

//     @Test
//     @Transactional
//     void deleteSellingBid() throws Exception {
//         // Initialize the database
//         sellingBidRepository.saveAndFlush(sellingBid);

//         int databaseSizeBeforeDelete = sellingBidRepository.findAll().size();

//         // Delete the sellingBid
//         restSellingBidMockMvc
//             .perform(delete(ENTITY_API_URL_ID, sellingBid.getId()).accept(MediaType.APPLICATION_JSON))
//             .andExpect(status().isNoContent());

//         // Validate the database contains one less item
//         List<SellingBid> sellingBidList = sellingBidRepository.findAll();
//         assertThat(sellingBidList).hasSize(databaseSizeBeforeDelete - 1);
//     }
// }
