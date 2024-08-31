package io.github.qifan777.server.address.entity;

import io.github.qifan777.server.infrastructure.jimmer.BaseEntity;
import io.qifan.infrastructure.generator.core.GenEntity;
import jakarta.validation.constraints.NotBlank;
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
     * 详细地址
     */
    @NotBlank(message = "详细地址不能为空")
    String details();

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    String phoneNumber();

    /**
     * 真实姓名
     */
    @NotBlank(message = "真实姓名不能为空")
    String realName();

    /**
     * 门牌号
     */
    @NotBlank(message = "门牌号不能为空")
    String houseNumber();

    /**
     * 是否置顶
     */
    boolean top();
}

