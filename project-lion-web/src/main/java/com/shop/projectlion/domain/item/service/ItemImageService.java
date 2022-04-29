package com.shop.projectlion.domain.item.service;

import com.shop.projectlion.domain.item.entity.ItemImage;
import com.shop.projectlion.domain.item.repository.ItemImageRepository;
import com.shop.projectlion.infra.FileService;
import com.shop.projectlion.infra.UploadFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ItemImageService {

    private final ItemImageRepository itemImageRepository;
    private final FileService fileService;

    // ItemImageRepository : DB에 이미지정보 저장
    // FileService : File System에 이미지 저장
    @Transactional
    public void saveItemImg(ItemImage itemImage, MultipartFile multipartFile) throws Exception{
        UploadFile uploadFile = fileService.storeFile(multipartFile);
        if(uploadFile != null) itemImage.updateItemImgInfo(uploadFile);
        itemImageRepository.save(itemImage);
    }
}
