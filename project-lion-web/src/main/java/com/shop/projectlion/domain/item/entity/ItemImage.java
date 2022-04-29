package com.shop.projectlion.domain.item.entity;

import com.shop.projectlion.domain.base.BaseEntity;
import com.shop.projectlion.infra.UploadFile;
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

    @Column(name = "image_name")
    private String imageName;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "is_rep_image")
    private Boolean isRepImage;

    @Column(name = "original_image_name")
    private String originalImageName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    public void updateRepImage(Boolean repImage) {
        isRepImage = repImage;
    }

    public void updateItemImgInfo(UploadFile uploadFile) {
        imageName = uploadFile.getStoreFileName();
        imageUrl = uploadFile.getFileUploadUrl();
    }

    @Builder
    public ItemImage(Boolean isRepImage, String originalImageName, Item item) {
        this.isRepImage = isRepImage;
        this.originalImageName = originalImageName;
        this.item = item;
    }
}
