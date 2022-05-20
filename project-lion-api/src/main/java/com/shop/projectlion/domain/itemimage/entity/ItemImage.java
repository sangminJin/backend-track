package com.shop.projectlion.domain.itemimage.entity;

import com.shop.projectlion.domain.common.BaseEntity;
import com.shop.projectlion.domain.item.entity.Item;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ItemImage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_image_id")
    private Long id;

    @Column(name = "image_name", nullable = false)
    private String imageName;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "is_rep_image", nullable = false)
    private Boolean isRepImage;

    @Column(name = "original_image_name", nullable = false)
    private String originalImageName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Builder
    public ItemImage(String imageName, String imageUrl, Boolean isRepImage, String originalImageName, Item item) {
        this.imageName = imageName;
        this.imageUrl = imageUrl;
        this.isRepImage = isRepImage;
        this.originalImageName = originalImageName;
        this.item = item;
    }

    public void modifyItemImage(String originalFileName, String imageName, String imageUrl) {
        this.originalImageName = originalFileName;
        this.imageName = imageName;
        this.imageUrl = imageUrl;
    }

    public void initImageInfo() {
        this.originalImageName = "";
        this.imageName = "";
        this.imageUrl = "";
    }

    // 연관관계 편의 메소드
    public void updateItem(Item item) {
        this.item = item;
        item.getItemImages().add(this);
    }
}
