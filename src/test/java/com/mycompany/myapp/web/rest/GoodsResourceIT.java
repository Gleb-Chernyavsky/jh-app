package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhApp;
import com.mycompany.myapp.domain.Goods;
import com.mycompany.myapp.repository.GoodsRepository;

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
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link GoodsResource} REST controller.
 */
@SpringBootTest(classes = JhApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class GoodsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final byte[] DEFAULT_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_CONTENT_TYPE = "image/png";

    @Autowired
    private GoodsRepository goodsRepository;

    @Mock
    private GoodsRepository goodsRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGoodsMockMvc;

    private Goods goods;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Goods createEntity(EntityManager em) {
        Goods goods = new Goods()
            .name(DEFAULT_NAME)
            .price(DEFAULT_PRICE)
            .photo(DEFAULT_PHOTO)
            .photoContentType(DEFAULT_PHOTO_CONTENT_TYPE);
        return goods;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Goods createUpdatedEntity(EntityManager em) {
        Goods goods = new Goods()
            .name(UPDATED_NAME)
            .price(UPDATED_PRICE)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE);
        return goods;
    }

    @BeforeEach
    public void initTest() {
        goods = createEntity(em);
    }

    @Test
    @Transactional
    public void createGoods() throws Exception {
        int databaseSizeBeforeCreate = goodsRepository.findAll().size();
        // Create the Goods
        restGoodsMockMvc.perform(post("/api/goods")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(goods)))
            .andExpect(status().isCreated());

        // Validate the Goods in the database
        List<Goods> goodsList = goodsRepository.findAll();
        assertThat(goodsList).hasSize(databaseSizeBeforeCreate + 1);
        Goods testGoods = goodsList.get(goodsList.size() - 1);
        assertThat(testGoods.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGoods.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testGoods.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testGoods.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createGoodsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = goodsRepository.findAll().size();

        // Create the Goods with an existing ID
        goods.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGoodsMockMvc.perform(post("/api/goods")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(goods)))
            .andExpect(status().isBadRequest());

        // Validate the Goods in the database
        List<Goods> goodsList = goodsRepository.findAll();
        assertThat(goodsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = goodsRepository.findAll().size();
        // set the field null
        goods.setName(null);

        // Create the Goods, which fails.


        restGoodsMockMvc.perform(post("/api/goods")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(goods)))
            .andExpect(status().isBadRequest());

        List<Goods> goodsList = goodsRepository.findAll();
        assertThat(goodsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = goodsRepository.findAll().size();
        // set the field null
        goods.setPrice(null);

        // Create the Goods, which fails.


        restGoodsMockMvc.perform(post("/api/goods")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(goods)))
            .andExpect(status().isBadRequest());

        List<Goods> goodsList = goodsRepository.findAll();
        assertThat(goodsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGoods() throws Exception {
        // Initialize the database
        goodsRepository.saveAndFlush(goods);

        // Get all the goodsList
        restGoodsMockMvc.perform(get("/api/goods?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(goods.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllGoodsWithEagerRelationshipsIsEnabled() throws Exception {
        when(goodsRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restGoodsMockMvc.perform(get("/api/goods?eagerload=true"))
            .andExpect(status().isOk());

        verify(goodsRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllGoodsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(goodsRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restGoodsMockMvc.perform(get("/api/goods?eagerload=true"))
            .andExpect(status().isOk());

        verify(goodsRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getGoods() throws Exception {
        // Initialize the database
        goodsRepository.saveAndFlush(goods);

        // Get the goods
        restGoodsMockMvc.perform(get("/api/goods/{id}", goods.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(goods.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.photoContentType").value(DEFAULT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo").value(Base64Utils.encodeToString(DEFAULT_PHOTO)));
    }
    @Test
    @Transactional
    public void getNonExistingGoods() throws Exception {
        // Get the goods
        restGoodsMockMvc.perform(get("/api/goods/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGoods() throws Exception {
        // Initialize the database
        goodsRepository.saveAndFlush(goods);

        int databaseSizeBeforeUpdate = goodsRepository.findAll().size();

        // Update the goods
        Goods updatedGoods = goodsRepository.findById(goods.getId()).get();
        // Disconnect from session so that the updates on updatedGoods are not directly saved in db
        em.detach(updatedGoods);
        updatedGoods
            .name(UPDATED_NAME)
            .price(UPDATED_PRICE)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE);

        restGoodsMockMvc.perform(put("/api/goods")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedGoods)))
            .andExpect(status().isOk());

        // Validate the Goods in the database
        List<Goods> goodsList = goodsRepository.findAll();
        assertThat(goodsList).hasSize(databaseSizeBeforeUpdate);
        Goods testGoods = goodsList.get(goodsList.size() - 1);
        assertThat(testGoods.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGoods.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testGoods.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testGoods.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingGoods() throws Exception {
        int databaseSizeBeforeUpdate = goodsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGoodsMockMvc.perform(put("/api/goods")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(goods)))
            .andExpect(status().isBadRequest());

        // Validate the Goods in the database
        List<Goods> goodsList = goodsRepository.findAll();
        assertThat(goodsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGoods() throws Exception {
        // Initialize the database
        goodsRepository.saveAndFlush(goods);

        int databaseSizeBeforeDelete = goodsRepository.findAll().size();

        // Delete the goods
        restGoodsMockMvc.perform(delete("/api/goods/{id}", goods.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Goods> goodsList = goodsRepository.findAll();
        assertThat(goodsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
