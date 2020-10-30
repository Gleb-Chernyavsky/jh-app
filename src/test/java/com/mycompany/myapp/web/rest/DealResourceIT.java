package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhApp;
import com.mycompany.myapp.domain.Deal;
import com.mycompany.myapp.repository.DealRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DealResource} REST controller.
 */
@SpringBootTest(classes = JhApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class DealResourceIT {

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private DealRepository dealRepository;

    @Mock
    private DealRepository dealRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDealMockMvc;

    private Deal deal;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Deal createEntity(EntityManager em) {
        Deal deal = new Deal()
            .date(DEFAULT_DATE);
        return deal;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Deal createUpdatedEntity(EntityManager em) {
        Deal deal = new Deal()
            .date(UPDATED_DATE);
        return deal;
    }

    @BeforeEach
    public void initTest() {
        deal = createEntity(em);
    }

    @Test
    @Transactional
    public void createDeal() throws Exception {
        int databaseSizeBeforeCreate = dealRepository.findAll().size();
        // Create the Deal
        restDealMockMvc.perform(post("/api/deals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deal)))
            .andExpect(status().isCreated());

        // Validate the Deal in the database
        List<Deal> dealList = dealRepository.findAll();
        assertThat(dealList).hasSize(databaseSizeBeforeCreate + 1);
        Deal testDeal = dealList.get(dealList.size() - 1);
        assertThat(testDeal.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createDealWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dealRepository.findAll().size();

        // Create the Deal with an existing ID
        deal.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDealMockMvc.perform(post("/api/deals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deal)))
            .andExpect(status().isBadRequest());

        // Validate the Deal in the database
        List<Deal> dealList = dealRepository.findAll();
        assertThat(dealList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = dealRepository.findAll().size();
        // set the field null
        deal.setDate(null);

        // Create the Deal, which fails.


        restDealMockMvc.perform(post("/api/deals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deal)))
            .andExpect(status().isBadRequest());

        List<Deal> dealList = dealRepository.findAll();
        assertThat(dealList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDeals() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get all the dealList
        restDealMockMvc.perform(get("/api/deals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deal.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllDealsWithEagerRelationshipsIsEnabled() throws Exception {
        when(dealRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDealMockMvc.perform(get("/api/deals?eagerload=true"))
            .andExpect(status().isOk());

        verify(dealRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllDealsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(dealRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDealMockMvc.perform(get("/api/deals?eagerload=true"))
            .andExpect(status().isOk());

        verify(dealRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getDeal() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        // Get the deal
        restDealMockMvc.perform(get("/api/deals/{id}", deal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(deal.getId().intValue()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)));
    }
    @Test
    @Transactional
    public void getNonExistingDeal() throws Exception {
        // Get the deal
        restDealMockMvc.perform(get("/api/deals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDeal() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        int databaseSizeBeforeUpdate = dealRepository.findAll().size();

        // Update the deal
        Deal updatedDeal = dealRepository.findById(deal.getId()).get();
        // Disconnect from session so that the updates on updatedDeal are not directly saved in db
        em.detach(updatedDeal);
        updatedDeal
            .date(UPDATED_DATE);

        restDealMockMvc.perform(put("/api/deals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDeal)))
            .andExpect(status().isOk());

        // Validate the Deal in the database
        List<Deal> dealList = dealRepository.findAll();
        assertThat(dealList).hasSize(databaseSizeBeforeUpdate);
        Deal testDeal = dealList.get(dealList.size() - 1);
        assertThat(testDeal.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingDeal() throws Exception {
        int databaseSizeBeforeUpdate = dealRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDealMockMvc.perform(put("/api/deals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deal)))
            .andExpect(status().isBadRequest());

        // Validate the Deal in the database
        List<Deal> dealList = dealRepository.findAll();
        assertThat(dealList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDeal() throws Exception {
        // Initialize the database
        dealRepository.saveAndFlush(deal);

        int databaseSizeBeforeDelete = dealRepository.findAll().size();

        // Delete the deal
        restDealMockMvc.perform(delete("/api/deals/{id}", deal.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Deal> dealList = dealRepository.findAll();
        assertThat(dealList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
