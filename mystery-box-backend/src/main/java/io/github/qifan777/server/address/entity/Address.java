package io.github.qifan777.server.address.entity;

import io.github.qifan777.server.infrastructure.jimmer.BaseEntity;
import io.qifan.infrastructure.generator.core.GenEntity;
import org.babyfish.jimmer.sql.Entity;

/**
 * 地址
 */
@GenEntity
@Entity
public interface Address extends BaseEntity {

    /**
     * 维度
     */
    double latitude();

    /**
     * 经度
     */
    double longitude();

    /**
     * 详细地址
     */
    String details();

    /**
     * 省
     */
    String province();

    /**
     * 市
     */
    String city();

    /**
     * 区
     */
    String district();

    /**
     * 手机号
     */
    String phoneNumber();

    /**
     * 真实姓名
     */
    String realName();

    /**
     * 门牌号
     */
    String houseNumber();

    /**
     * 是否置顶
     */
    boolean top();
}

