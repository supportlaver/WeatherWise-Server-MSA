package com.idle.couponservice.domain;

import com.idle.commonservice.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

import static jakarta.persistence.GenerationType.*;

@Entity
@Getter
public class Coupon extends BaseEntity {
    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "coupon_id")
    private Long id;

    @Embedded
    private CouponDateInfo couponDateInfo;

    @Embedded
    private CouponDiscountInfo couponDiscountInfo;

    @Column(name = "coupon_name")
    private String name;

    @Column(name = "quantity")
    private int quantity;

    public boolean checkQuantity() {
        return this.quantity - 1 > 0;
    }
}
