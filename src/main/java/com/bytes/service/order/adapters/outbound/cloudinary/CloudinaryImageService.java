package com.bytes.service.order.adapters.outbound.cloudinary;

import com.bytes.service.order.domain.outbound.ImageServicePort;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryImageService implements ImageServicePort {
    @Autowired
    private Cloudinary cloudinary;
    @Override
    public String uploadImage(byte[] imagePath) {
        try {
            Map uploadResult = cloudinary.uploader().upload(imagePath, ObjectUtils.emptyMap());
            return uploadResult.get("url").toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
