package org.example.hsf301.repos;

import org.example.hsf301.daos.IKoiImageDAO;
import org.example.hsf301.daos.KoiImageDAO;
import org.example.hsf301.pojos.KoiImage;

import java.util.List;

/**
 * @author KoiImageRepository
 */
public class KoiImageRepository implements IKoiImageRepository{
    private final IKoiImageDAO iKoiImageDAO;
    public KoiImageRepository (String name)
    {
        iKoiImageDAO = new KoiImageDAO(name);

    }
    @Override
    public void save(KoiImage koiImage) {
        iKoiImageDAO.save(koiImage);
    }

    @Override
    public void delete(Long id) {
        iKoiImageDAO.delete(id);
    }

    @Override
    public void deleteByKois(Long koiId) {
        iKoiImageDAO.deleteByKois(koiId);
    }

    @Override
    public List<KoiImage> findByKoiId(Long koiId) {
        return List.of();
    }
}