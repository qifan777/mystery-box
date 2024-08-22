package io.github.qifan777.server.box.root.entity.dto;

import io.github.qifan777.server.box.root.entity.MysteryBox;
import io.github.qifan777.server.box.root.entity.MysteryBoxDraft;
import io.github.qifan777.server.box.root.entity.MysteryBoxFetcher;
import io.github.qifan777.server.product.root.entity.Product;
import io.github.qifan777.server.product.root.entity.ProductDraft;
import io.github.qifan777.server.product.root.entity.ProductFetcher;
import io.github.qifan777.server.product.root.model.KeyValue;
import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.infrastructure.generator.core.GenImageField;
import io.qifan.infrastructure.generator.core.GenKeyValueField;
import io.qifan.infrastructure.generator.core.GenNumberField;
import io.qifan.infrastructure.generator.core.GenTextAreaField;
import io.qifan.infrastructure.generator.core.GenTextField;
import io.qifan.infrastructure.generator.core.GenValueField;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.babyfish.jimmer.View;
import org.babyfish.jimmer.impl.util.DtoPropAccessor;
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
                .cover()
                .products(TargetOf_products.METADATA.getFetcher()),
            MystryBoxView::new
    );

    private static final DtoPropAccessor PRODUCTS_ACCESSOR = new DtoPropAccessor(
        true,
        new int[] { MysteryBoxDraft.Producer.SLOT_PRODUCTS },
        DtoPropAccessor.<Product, TargetOf_products>objectListGetter(TargetOf_products::new),
        DtoPropAccessor.objectListSetter(TargetOf_products::toEntity)
    );

    private String id;

    private LocalDateTime createdTime;

    private LocalDateTime editedTime;

    private String name;

    private String details;

    private String tips;

    private BigDecimal price;

    private String cover;

    private List<TargetOf_products> products;

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
        this.products = PRODUCTS_ACCESSOR.get(base);
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

    @NotNull
    public List<TargetOf_products> getProducts() {
        if (products == null) {
            throw new IllegalStateException("The property \"products\" is not specified");
        }
        return products;
    }

    public void setProducts(@NotNull List<TargetOf_products> products) {
        this.products = products;
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
            PRODUCTS_ACCESSOR.set(__draft, products != null ? products : Collections.emptyList());
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
        hash = hash * 31 + Objects.hashCode(products);
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
        if (!Objects.equals(products, other.products)) {
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
        builder.append(", products=").append(products);
        builder.append(')');
        return builder.toString();
    }

    /**
     * 商品表
     */
    @GeneratedBy
    @GenEntity
    public static class TargetOf_products implements View<Product> {
        public static final DtoMetadata<Product, TargetOf_products> METADATA = 
            new DtoMetadata<Product, TargetOf_products>(
                ProductFetcher.$
                    .createdTime()
                    .editedTime()
                    .name()
                    .price()
                    .cover()
                    .brand()
                    .description()
                    .tags()
                    .specifications()
                    .attributes(),
                TargetOf_products::new
        );

        private String id;

        private LocalDateTime createdTime;

        private LocalDateTime editedTime;

        private String name;

        private BigDecimal price;

        private String cover;

        private String brand;

        private String description;

        private List<String> tags;

        private List<KeyValue> specifications;

        private List<KeyValue> attributes;

        public TargetOf_products() {
        }

        public TargetOf_products(@NotNull Product base) {
            this.id = base.id();
            this.createdTime = base.createdTime();
            this.editedTime = base.editedTime();
            this.name = base.name();
            this.price = base.price();
            this.cover = base.cover();
            this.brand = base.brand();
            this.description = base.description();
            this.tags = base.tags();
            this.specifications = base.specifications();
            this.attributes = base.attributes();
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
         * 名称
         */
        @NotNull
        @GenTextField(
                label = "名称",
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
         * 价格
         */
        @NotNull
        @GenNumberField(
                label = "价格",
                order = 1
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
                order = 2
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

        /**
         * 品牌
         */
        @NotNull
        @GenTextField(
                label = "品牌",
                order = 3
        )
        public String getBrand() {
            if (brand == null) {
                throw new IllegalStateException("The property \"brand\" is not specified");
            }
            return brand;
        }

        public void setBrand(@NotNull String brand) {
            this.brand = brand;
        }

        /**
         * 描述
         */
        @NotNull
        @GenTextAreaField(
                label = "描述",
                order = 5
        )
        public String getDescription() {
            if (description == null) {
                throw new IllegalStateException("The property \"description\" is not specified");
            }
            return description;
        }

        public void setDescription(@NotNull String description) {
            this.description = description;
        }

        /**
         * 标签
         */
        @NotNull
        @GenValueField(
                label = "标签",
                order = 6
        )
        public List<String> getTags() {
            if (tags == null) {
                throw new IllegalStateException("The property \"tags\" is not specified");
            }
            return tags;
        }

        public void setTags(@NotNull List<String> tags) {
            this.tags = tags;
        }

        /**
         * 规格
         */
        @NotNull
        @GenKeyValueField(
                label = "规格",
                order = 7
        )
        public List<KeyValue> getSpecifications() {
            if (specifications == null) {
                throw new IllegalStateException("The property \"specifications\" is not specified");
            }
            return specifications;
        }

        public void setSpecifications(@NotNull List<KeyValue> specifications) {
            this.specifications = specifications;
        }

        /**
         * 属性
         */
        @NotNull
        @GenKeyValueField(
                label = "属性",
                order = 8
        )
        public List<KeyValue> getAttributes() {
            if (attributes == null) {
                throw new IllegalStateException("The property \"attributes\" is not specified");
            }
            return attributes;
        }

        public void setAttributes(@NotNull List<KeyValue> attributes) {
            this.attributes = attributes;
        }

        @Override
        public Product toEntity() {
            return ProductDraft.$.produce(__draft -> {
                __draft.setId(id);
                __draft.setCreatedTime(createdTime);
                __draft.setEditedTime(editedTime);
                __draft.setName(name);
                __draft.setPrice(price);
                __draft.setCover(cover);
                __draft.setBrand(brand);
                __draft.setDescription(description);
                __draft.setTags(tags);
                __draft.setSpecifications(specifications);
                __draft.setAttributes(attributes);
            });
        }

        @Override
        public int hashCode() {
            int hash = Objects.hashCode(id);
            hash = hash * 31 + Objects.hashCode(createdTime);
            hash = hash * 31 + Objects.hashCode(editedTime);
            hash = hash * 31 + Objects.hashCode(name);
            hash = hash * 31 + Objects.hashCode(price);
            hash = hash * 31 + Objects.hashCode(cover);
            hash = hash * 31 + Objects.hashCode(brand);
            hash = hash * 31 + Objects.hashCode(description);
            hash = hash * 31 + Objects.hashCode(tags);
            hash = hash * 31 + Objects.hashCode(specifications);
            hash = hash * 31 + Objects.hashCode(attributes);
            return hash;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            TargetOf_products other = (TargetOf_products) o;
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
            if (!Objects.equals(price, other.price)) {
                return false;
            }
            if (!Objects.equals(cover, other.cover)) {
                return false;
            }
            if (!Objects.equals(brand, other.brand)) {
                return false;
            }
            if (!Objects.equals(description, other.description)) {
                return false;
            }
            if (!Objects.equals(tags, other.tags)) {
                return false;
            }
            if (!Objects.equals(specifications, other.specifications)) {
                return false;
            }
            if (!Objects.equals(attributes, other.attributes)) {
                return false;
            }
            return true;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("MystryBoxView.TargetOf_products").append('(');
            builder.append("id=").append(id);
            builder.append(", createdTime=").append(createdTime);
            builder.append(", editedTime=").append(editedTime);
            builder.append(", name=").append(name);
            builder.append(", price=").append(price);
            builder.append(", cover=").append(cover);
            builder.append(", brand=").append(brand);
            builder.append(", description=").append(description);
            builder.append(", tags=").append(tags);
            builder.append(", specifications=").append(specifications);
            builder.append(", attributes=").append(attributes);
            builder.append(')');
            return builder.toString();
        }
    }
}
