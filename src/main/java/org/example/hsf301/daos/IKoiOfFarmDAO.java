package org.example.hsf301.daos;

import org.example.hsf301.pojos.KoiOfFarm;

import java.util.List;

/**
 * @author IKoiOfFarmDAO
 */
public interface IKoiOfFarmDAO {
    void save(KoiOfFarm koiOfFarm);
    List<KoiOfFarm> findAll();
    void delete(Long id);
    KoiOfFarm findById(Long id);
    void update(KoiOfFarm koiOfFarm);
    List<KoiOfFarm> findByFarmId(Long farmId);
    List<KoiOfFarm> findByKoiId(Long koiId);
    List<KoiOfFarm> findAvailable();
    KoiOfFarm findByFarmIdAndKoiId(Long farmId, Long koiId);
}