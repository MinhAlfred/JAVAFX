package org.example.hsf301.daos;

import org.example.hsf301.pojos.KoiImage;

import java.util.List;

/**
 * @author IKoiImageDAO
 */
public interface IKoiImageDAO {
    void save(KoiImage koiImage);

    void delete(Long id);
    void deleteByKois(Long koiId);
    List<KoiImage> findByKoiId(Long koiId);
}