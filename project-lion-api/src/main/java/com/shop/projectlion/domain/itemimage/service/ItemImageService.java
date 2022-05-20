package com.shop.projectlion.domain.itemimage.service;

import com.shop.projectlion.domain.item.entity.Item;
import com.shop.projectlion.domain.itemimage.entity.ItemImage;
import com.shop.projectlion.domain.itemimage.repository.ItemImageRepository;
import com.shop.projectlion.infra.file.FileService;
import com.shop.projectlion.infra.file.UploadFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ItemImageService {

    private final ItemImageRepository itemImageRepository;
    private final FileService fileService;
    private final String IMAGE_URL_PREFIX = "/images/";

    public List<ItemImage> findByItemOrderByIdAsc(Item item) {
        return itemImageRepository.findByItemOrderByIdAsc(item);
    }

    @Transactional
    public void registerItemImages(Item item, List<MultipartFile> itemImageFiles) throws IOException {
        for(int i=0; i<itemImageFiles.size(); i++) {
            boolean isRepImage = i == 0;
            registerItemImg(item, itemImageFiles.get(i), isRepImage);
        }
    }

    @Transactional
    public void registerItemImg(Item item, MultipartFile itemImageFile, boolean isRepImage) throws IOException {
        UploadFile uploadFile = fileService.storeFile(itemImageFile);
        String storeFileName = uploadFile != null ? uploadFile.getStoreFileName() : "";
        String originalFilename = uploadFile != null ? uploadFile.getOriginalFileName() : "";
        String imageUrl = uploadFile != null ? IMAGE_URL_PREFIX + storeFileName : "";

        ItemImage itemImage = ItemImage.builder()
                .imageName(storeFileName)
                .imageUrl(imageUrl)
                .originalImageName(originalFilename)
                .isRepImage(isRepImage)
                .item(item)
                .build();

        itemImageRepository.save(itemImage);
    }

    public void updateItemImage(ItemImage itemImage, MultipartFile itemImageFile) throws IOException {
        // 기존에 사진 파일 있었으면 삭제 처리
        if(StringUtils.hasText(itemImage.getImageName())) fileService.deleteFile(itemImage.getImageUrl());

        // 새로운 이미지 파일 등록
        UploadFile uploadFile = fileService.storeFile(itemImageFile);
        String originalFileName = uploadFile.getOriginalFileName();
        String storeFileName = uploadFile.getStoreFileName();
        String imageUrl = IMAGE_URL_PREFIX + storeFileName;

        itemImage.modifyItemImage(originalFileName, storeFileName, imageUrl);
    }

    public void deleteItemImage(ItemImage itemImage) {
        String fileUploadPath = fileService.getFullFileUploadPath(itemImage.getImageName());
        fileService.deleteFile(fileUploadPath);
        itemImage.initImageInfo();
    }
}
