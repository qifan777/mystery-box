package io.github.qifan777.server.feedback.entity;

import io.github.qifan777.server.infrastructure.jimmer.BaseEntity;
import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.infrastructure.generator.core.GenTextAreaField;
import jakarta.validation.constraints.Null;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.Serialized;

import java.util.List;

/**
 * 反馈表
 */
@GenEntity
@Entity
public interface Feedback extends BaseEntity {
    /**
     * 文本内容
     */
    @GenTextAreaField(label = "文本内容", order = 1)
    String content();

    /**
     * 图片列表
     */
    @Null
    @Serialized
    List<String> pictures();
}

