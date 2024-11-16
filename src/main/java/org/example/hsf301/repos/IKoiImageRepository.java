package org.example.hsf301.repos;

import org.example.hsf301.pojos.KoiImage;

import java.util.List;

/**
 * @author IKoiImageRepository
 */
public interface IKoiImageRepository {
    void save(KoiImage koiImage);

    void delete(Long id);
    void deleteByKois(Long koiId);
    List<KoiImage> findByKoiId(Long koiId);
}