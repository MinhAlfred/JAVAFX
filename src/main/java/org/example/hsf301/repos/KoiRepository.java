
package org.example.hsf301.repos;

import org.example.hsf301.daos.IKoiDAO;
import org.example.hsf301.daos.KoiDAO;
import org.example.hsf301.pojos.Koi;

import java.util.List;

/**
 * @author KoiRepository
 */
public class KoiRepository implements IKoiRepository {
    private final IKoiDAO koiDAO;
    public KoiRepository (String name)
    {
        koiDAO = new KoiDAO(name);
    }

    @Override
    public void save(Koi koi) {
        koiDAO.save(koi);
    }

    @Override
    public List<Koi> findAll() {
        return koiDAO.findAll();
    }

    @Override
    public void delete(Long id) {
        koiDAO.delete(id);
    }

    @Override
    public Koi findById(Long id) {
        return koiDAO.findById(id);
    }

    @Override
    public void update(Koi koi) {
        koiDAO.update(koi);
    }

    @Override
    public List<Koi> findByColor(String color) {
        return koiDAO.findByColor(color);
    }

    @Override
    public List<Koi> findByKoiName(String koiName) {
        return koiDAO.findByKoiName(koiName);
    }

    @Override
    public List<Koi> findAllActive() {
        return koiDAO.findAllActive();
    }
}
