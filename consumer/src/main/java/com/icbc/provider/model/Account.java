package com.icbc.provider.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Kaiya Xiong
 * @date 2019-08-14
 */
public class Account implements Serializable {
    private String cardId; //卡号
    private BigDecimal moneyRemain; //余额

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public BigDecimal getMoneyRemain() {
        return moneyRemain;
    }

    public void setMoneyRemain(BigDecimal moneyRemain) {
        this.moneyRemain = moneyRemain;
    }

    public Account(String cardId, BigDecimal moneyRemain) {
        this.cardId = cardId;
        this.moneyRemain = moneyRemain;
    }
}
