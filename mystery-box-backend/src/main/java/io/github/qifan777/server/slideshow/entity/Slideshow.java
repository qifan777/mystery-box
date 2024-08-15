package io.github.qifan777.server.slideshow.entity;

import io.github.qifan777.server.dict.model.DictConstants;
import io.github.qifan777.server.infrastructure.jimmer.BaseEntity;
import io.qifan.infrastructure.generator.core.*;
import jakarta.validation.constraints.Null;
import org.babyfish.jimmer.sql.Entity;


/**
 * 轮播图
 */
@GenEntity
@Entity
public interface Slideshow extends BaseEntity {

    /**
     * 公告图片
     */
    @GenImageField(label = "公告图片", order = 0)
    String picture();

    /**
     * 公告内容
     */
    @GenTextField(label = "公告内容", order = 1)
    String content();

    /**
     * 跳转目标id
     */
    @GenTextField(label = "跳转目标id", order = 2)
    @Null
    String navigatorId();

    /**
     * 跳转类型
     */
    @GenDictField(label = "跳转类型", order = 3, dictEnName = DictConstants.NAVIGATOR_TYPE)
    @Null
    DictConstants.NavigatorType navigatorType();

    /**
     * 排序号
     */
    @GenNumberField(label = "排序号", order = 4)
    int sort();

    /**
     * 是否显示
     */
    @GenNumberField(label = "是否显示", order = 5)
    boolean valid();
}

