package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.AmountOfGoods;
import com.mycompany.myapp.repository.AmountOfGoodsRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.AmountOfGoods}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AmountOfGoodsResource {

    private final Logger log = LoggerFactory.getLogger(AmountOfGoodsResource.class);

    private static final String ENTITY_NAME = "amountOfGoods";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AmountOfGoodsRepository amountOfGoodsRepository;

    public AmountOfGoodsResource(AmountOfGoodsRepository amountOfGoodsRepository) {
        this.amountOfGoodsRepository = amountOfGoodsRepository;
    }

    /**
     * {@code POST  /amount-of-goods} : Create a new amountOfGoods.
     *
     * @param amountOfGoods the amountOfGoods to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new amountOfGoods, or with status {@code 400 (Bad Request)} if the amountOfGoods has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/amount-of-goods")
    public ResponseEntity<AmountOfGoods> createAmountOfGoods(@RequestBody AmountOfGoods amountOfGoods) throws URISyntaxException {
        log.debug("REST request to save AmountOfGoods : {}", amountOfGoods);
        if (amountOfGoods.getId() != null) {
            throw new BadRequestAlertException("A new amountOfGoods cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AmountOfGoods result = amountOfGoodsRepository.save(amountOfGoods);
        return ResponseEntity.created(new URI("/api/amount-of-goods/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /amount-of-goods} : Updates an existing amountOfGoods.
     *
     * @param amountOfGoods the amountOfGoods to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated amountOfGoods,
     * or with status {@code 400 (Bad Request)} if the amountOfGoods is not valid,
     * or with status {@code 500 (Internal Server Error)} if the amountOfGoods couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/amount-of-goods")
    public ResponseEntity<AmountOfGoods> updateAmountOfGoods(@RequestBody AmountOfGoods amountOfGoods) throws URISyntaxException {
        log.debug("REST request to update AmountOfGoods : {}", amountOfGoods);
        if (amountOfGoods.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AmountOfGoods result = amountOfGoodsRepository.save(amountOfGoods);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, amountOfGoods.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /amount-of-goods} : get all the amountOfGoods.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of amountOfGoods in body.
     */
    @GetMapping("/amount-of-goods")
    public ResponseEntity<List<AmountOfGoods>> getAllAmountOfGoods(Pageable pageable) {
        log.debug("REST request to get a page of AmountOfGoods");
        Page<AmountOfGoods> page = amountOfGoodsRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /amount-of-goods/:id} : get the "id" amountOfGoods.
     *
     * @param id the id of the amountOfGoods to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the amountOfGoods, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/amount-of-goods/{id}")
    public ResponseEntity<AmountOfGoods> getAmountOfGoods(@PathVariable Long id) {
        log.debug("REST request to get AmountOfGoods : {}", id);
        Optional<AmountOfGoods> amountOfGoods = amountOfGoodsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(amountOfGoods);
    }

    /**
     * {@code DELETE  /amount-of-goods/:id} : delete the "id" amountOfGoods.
     *
     * @param id the id of the amountOfGoods to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/amount-of-goods/{id}")
    public ResponseEntity<Void> deleteAmountOfGoods(@PathVariable Long id) {
        log.debug("REST request to delete AmountOfGoods : {}", id);
        amountOfGoodsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
