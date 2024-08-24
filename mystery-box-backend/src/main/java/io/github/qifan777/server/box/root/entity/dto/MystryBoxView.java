package io.github.qifan777.server.box.root.entity.dto;

import io.github.qifan777.server.box.root.entity.MysteryBox;
import io.github.qifan777.server.box.root.entity.MysteryBoxDraft;
import io.github.qifan777.server.box.root.entity.MysteryBoxFetcher;
import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.infrastructure.generator.core.GenImageField;
import io.qifan.infrastructure.generator.core.GenNumberField;
import io.qifan.infrastructure.generator.core.GenTextAreaField;
import io.qifan.infrastructure.generator.core.GenTextField;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import org.babyfish.jimmer.View;
import org.babyfish.jimmer.internal.GeneratedBy;
import org.babyfish.jimmer.sql.fetcher.DtoMetadata;
import org.jetbrains.annotations.NotNull;

/**
 * 盲盒
 */
@GeneratedBy(
        file = "<mystery-box-backend>/src/main/dto/box/MysteryBox.dto"
)
@GenEntity
public class MystryBoxView implements View<MysteryBox> {
    public static final DtoMetadata<MysteryBox, MystryBoxView> METADATA = 
        new DtoMetadata<MysteryBox, MystryBoxView>(
            MysteryBoxFetcher.$
                .createdTime()
                .editedTime()
                .name()
                .details()
                .tips()
                .price()
                .cover(),
            MystryBoxView::new
    );

    private String id;

    private LocalDateTime createdTime;

    private LocalDateTime editedTime;

    private String name;

    private String details;

    private String tips;

    private BigDecimal price;

    private String cover;

    public MystryBoxView() {
    }

    public MystryBoxView(@NotNull MysteryBox base) {
        this.id = base.id();
        this.createdTime = base.createdTime();
        this.editedTime = base.editedTime();
        this.name = base.name();
        this.details = base.details();
        this.tips = base.tips();
        this.price = base.price();
        this.cover = base.cover();
    }

    @NotNull
    public String getId() {
        if (id == null) {
            throw new IllegalStateException("The property \"id\" is not specified");
        }
        return id;
    }

    public void setId(@NotNull String id) {
        this.id = id;
    }

    @NotNull
    public LocalDateTime getCreatedTime() {
        if (createdTime == null) {
            throw new IllegalStateException("The property \"createdTime\" is not specified");
        }
        return createdTime;
    }

    public void setCreatedTime(@NotNull LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    @NotNull
    public LocalDateTime getEditedTime() {
        if (editedTime == null) {
            throw new IllegalStateException("The property \"editedTime\" is not specified");
        }
        return editedTime;
    }

    public void setEditedTime(@NotNull LocalDateTime editedTime) {
        this.editedTime = editedTime;
    }

    /**
     * 盲盒名字
     */
    @NotNull
    @GenTextField(
            label = "盲盒名字",
            order = 0
    )
    public String getName() {
        if (name == null) {
            throw new IllegalStateException("The property \"name\" is not specified");
        }
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    /**
     * 盲盒详情
     */
    @NotNull
    @GenTextAreaField(
            label = "盲盒详情",
            order = 1
    )
    public String getDetails() {
        if (details == null) {
            throw new IllegalStateException("The property \"details\" is not specified");
        }
        return details;
    }

    public void setDetails(@NotNull String details) {
        this.details = details;
    }

    /**
     * 购买提示
     */
    @NotNull
    @GenTextField(
            label = "购买提示",
            order = 2
    )
    public String getTips() {
        if (tips == null) {
            throw new IllegalStateException("The property \"tips\" is not specified");
        }
        return tips;
    }

    public void setTips(@NotNull String tips) {
        this.tips = tips;
    }

    /**
     * 价格
     */
    @NotNull
    @GenNumberField(
            label = "价格",
            order = 3
    )
    public BigDecimal getPrice() {
        if (price == null) {
            throw new IllegalStateException("The property \"price\" is not specified");
        }
        return price;
    }

    public void setPrice(@NotNull BigDecimal price) {
        this.price = price;
    }

    /**
     * 封面
     */
    @NotNull
    @GenImageField(
            label = "封面",
            order = 4
    )
    public String getCover() {
        if (cover == null) {
            throw new IllegalStateException("The property \"cover\" is not specified");
        }
        return cover;
    }

    public void setCover(@NotNull String cover) {
        this.cover = cover;
    }

    @Override
    public MysteryBox toEntity() {
        return MysteryBoxDraft.$.produce(__draft -> {
            __draft.setId(id);
            __draft.setCreatedTime(createdTime);
            __draft.setEditedTime(editedTime);
            __draft.setName(name);
            __draft.setDetails(details);
            __draft.setTips(tips);
            __draft.setPrice(price);
            __draft.setCover(cover);
        });
    }

    @Override
    public int hashCode() {
        int hash = Objects.hashCode(id);
        hash = hash * 31 + Objects.hashCode(createdTime);
        hash = hash * 31 + Objects.hashCode(editedTime);
        hash = hash * 31 + Objects.hashCode(name);
        hash = hash * 31 + Objects.hashCode(details);
        hash = hash * 31 + Objects.hashCode(tips);
        hash = hash * 31 + Objects.hashCode(price);
        hash = hash * 31 + Objects.hashCode(cover);
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        MystryBoxView other = (MystryBoxView) o;
        if (!Objects.equals(id, other.id)) {
            return false;
        }
        if (!Objects.equals(createdTime, other.createdTime)) {
            return false;
        }
        if (!Objects.equals(editedTime, other.editedTime)) {
            return false;
        }
        if (!Objects.equals(name, other.name)) {
            return false;
        }
        if (!Objects.equals(details, other.details)) {
            return false;
        }
        if (!Objects.equals(tips, other.tips)) {
            return false;
        }
        if (!Objects.equals(price, other.price)) {
            return false;
        }
        if (!Objects.equals(cover, other.cover)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("MystryBoxView").append('(');
        builder.append("id=").append(id);
        builder.append(", createdTime=").append(createdTime);
        builder.append(", editedTime=").append(editedTime);
        builder.append(", name=").append(name);
        builder.append(", details=").append(details);
        builder.append(", tips=").append(tips);
        builder.append(", price=").append(price);
        builder.append(", cover=").append(cover);
        builder.append(')');
        return builder.toString();
    }
}
