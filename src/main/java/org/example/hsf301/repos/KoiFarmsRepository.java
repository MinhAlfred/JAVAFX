package org.example.hsf301.repos;

import org.example.hsf301.daos.IKoiFarmsDAO;
import org.example.hsf301.daos.KoiFarmsDAO;
import org.example.hsf301.pojos.KoiFarms;

import java.util.List;

/**
 * @author KoiFarmsRepository
 */
public class KoiFarmsRepository implements IKoiFarmsRepository{
    private final IKoiFarmsDAO iKoiFarmsDAO;

    public  KoiFarmsRepository (String name)
    {
        iKoiFarmsDAO = new KoiFarmsDAO(name);
    }
    @Override
    public void save(KoiFarms koiFarms) {
        iKoiFarmsDAO.save(koiFarms);
    }

    @Override
    public List<KoiFarms> findAll() {
        return iKoiFarmsDAO.findAll();
    }

    @Override
    public void delete(Long id) {
        iKoiFarmsDAO.delete(id);
    }

    @Override
    public KoiFarms findById(Long id) {
        return iKoiFarmsDAO.findById(id);
    }

    @Override
    public void update(KoiFarms koiFarms) {
        iKoiFarmsDAO.update(koiFarms);
    }

    @Override
    public List<KoiFarms> findByFarmName(String farmName) {
        return iKoiFarmsDAO.findByFarmName(farmName);
    }

    @Override
    public List<KoiFarms> findActiveFarms() {
        return iKoiFarmsDAO.findActiveFarms();
    }
}