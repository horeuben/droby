package com.example.reube.droby.Database;

import java.util.Date;

/**
 * Created by reube on 26/6/2017.
 */

public class Frequency {
    private int clothes_id;
    private Date date_worn;

    public Frequency() {
    }

    public Frequency(int clothes_id, Date date_worn) {
        this.clothes_id = clothes_id;
        this.date_worn = date_worn;
    }

    public int getClothes_id() {
        return clothes_id;
    }

    public void setClothes_id(int clothes_id) {
        this.clothes_id = clothes_id;
    }

    public Date getDate_worn() {
        return date_worn;
    }

    public void setDate_worn(Date date_worn) {
        this.date_worn = date_worn;
    }
}
