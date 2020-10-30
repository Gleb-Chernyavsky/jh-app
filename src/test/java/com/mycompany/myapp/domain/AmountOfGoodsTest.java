package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class AmountOfGoodsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AmountOfGoods.class);
        AmountOfGoods amountOfGoods1 = new AmountOfGoods();
        amountOfGoods1.setId(1L);
        AmountOfGoods amountOfGoods2 = new AmountOfGoods();
        amountOfGoods2.setId(amountOfGoods1.getId());
        assertThat(amountOfGoods1).isEqualTo(amountOfGoods2);
        amountOfGoods2.setId(2L);
        assertThat(amountOfGoods1).isNotEqualTo(amountOfGoods2);
        amountOfGoods1.setId(null);
        assertThat(amountOfGoods1).isNotEqualTo(amountOfGoods2);
    }
}
