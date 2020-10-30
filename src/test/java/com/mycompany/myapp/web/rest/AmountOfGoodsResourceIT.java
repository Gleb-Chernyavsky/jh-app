package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhApp;
import com.mycompany.myapp.domain.AmountOfGoods;
import com.mycompany.myapp.repository.AmountOfGoodsRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AmountOfGoodsResource} REST controller.
 */
@SpringBootTest(classes = JhApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AmountOfGoodsResourceIT {

    private static final Integer DEFAULT_AMOUNT = 1;
    private static final Integer UPDATED_AMOUNT = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private AmountOfGoodsRepository amountOfGoodsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAmountOfGoodsMockMvc;

    private AmountOfGoods amountOfGoods;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AmountOfGoods createEntity(EntityManager em) {
        AmountOfGoods amountOfGoods = new AmountOfGoods()
            .amount(DEFAULT_AMOUNT)
            .name(DEFAULT_NAME);
        return amountOfGoods;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AmountOfGoods createUpdatedEntity(EntityManager em) {
        AmountOfGoods amountOfGoods = new AmountOfGoods()
            .amount(UPDATED_AMOUNT)
            .name(UPDATED_NAME);
        return amountOfGoods;
    }

    @BeforeEach
    public void initTest() {
        amountOfGoods = createEntity(em);
    }

    @Test
    @Transactional
    public void createAmountOfGoods() throws Exception {
        int databaseSizeBeforeCreate = amountOfGoodsRepository.findAll().size();
        // Create the AmountOfGoods
        restAmountOfGoodsMockMvc.perform(post("/api/amount-of-goods")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(amountOfGoods)))
            .andExpect(status().isCreated());

        // Validate the AmountOfGoods in the database
        List<AmountOfGoods> amountOfGoodsList = amountOfGoodsRepository.findAll();
        assertThat(amountOfGoodsList).hasSize(databaseSizeBeforeCreate + 1);
        AmountOfGoods testAmountOfGoods = amountOfGoodsList.get(amountOfGoodsList.size() - 1);
        assertThat(testAmountOfGoods.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testAmountOfGoods.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createAmountOfGoodsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = amountOfGoodsRepository.findAll().size();

        // Create the AmountOfGoods with an existing ID
        amountOfGoods.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAmountOfGoodsMockMvc.perform(post("/api/amount-of-goods")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(amountOfGoods)))
            .andExpect(status().isBadRequest());

        // Validate the AmountOfGoods in the database
        List<AmountOfGoods> amountOfGoodsList = amountOfGoodsRepository.findAll();
        assertThat(amountOfGoodsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAmountOfGoods() throws Exception {
        // Initialize the database
        amountOfGoodsRepository.saveAndFlush(amountOfGoods);

        // Get all the amountOfGoodsList
        restAmountOfGoodsMockMvc.perform(get("/api/amount-of-goods?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(amountOfGoods.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getAmountOfGoods() throws Exception {
        // Initialize the database
        amountOfGoodsRepository.saveAndFlush(amountOfGoods);

        // Get the amountOfGoods
        restAmountOfGoodsMockMvc.perform(get("/api/amount-of-goods/{id}", amountOfGoods.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(amountOfGoods.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingAmountOfGoods() throws Exception {
        // Get the amountOfGoods
        restAmountOfGoodsMockMvc.perform(get("/api/amount-of-goods/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAmountOfGoods() throws Exception {
        // Initialize the database
        amountOfGoodsRepository.saveAndFlush(amountOfGoods);

        int databaseSizeBeforeUpdate = amountOfGoodsRepository.findAll().size();

        // Update the amountOfGoods
        AmountOfGoods updatedAmountOfGoods = amountOfGoodsRepository.findById(amountOfGoods.getId()).get();
        // Disconnect from session so that the updates on updatedAmountOfGoods are not directly saved in db
        em.detach(updatedAmountOfGoods);
        updatedAmountOfGoods
            .amount(UPDATED_AMOUNT)
            .name(UPDATED_NAME);

        restAmountOfGoodsMockMvc.perform(put("/api/amount-of-goods")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAmountOfGoods)))
            .andExpect(status().isOk());

        // Validate the AmountOfGoods in the database
        List<AmountOfGoods> amountOfGoodsList = amountOfGoodsRepository.findAll();
        assertThat(amountOfGoodsList).hasSize(databaseSizeBeforeUpdate);
        AmountOfGoods testAmountOfGoods = amountOfGoodsList.get(amountOfGoodsList.size() - 1);
        assertThat(testAmountOfGoods.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testAmountOfGoods.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingAmountOfGoods() throws Exception {
        int databaseSizeBeforeUpdate = amountOfGoodsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAmountOfGoodsMockMvc.perform(put("/api/amount-of-goods")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(amountOfGoods)))
            .andExpect(status().isBadRequest());

        // Validate the AmountOfGoods in the database
        List<AmountOfGoods> amountOfGoodsList = amountOfGoodsRepository.findAll();
        assertThat(amountOfGoodsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAmountOfGoods() throws Exception {
        // Initialize the database
        amountOfGoodsRepository.saveAndFlush(amountOfGoods);

        int databaseSizeBeforeDelete = amountOfGoodsRepository.findAll().size();

        // Delete the amountOfGoods
        restAmountOfGoodsMockMvc.perform(delete("/api/amount-of-goods/{id}", amountOfGoods.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AmountOfGoods> amountOfGoodsList = amountOfGoodsRepository.findAll();
        assertThat(amountOfGoodsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
