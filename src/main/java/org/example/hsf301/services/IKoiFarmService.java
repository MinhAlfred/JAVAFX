package org.example.hsf301.services;

import org.example.hsf301.dtos.request.KoiFarmRequest;
import org.example.hsf301.pojos.KoiFarms;

import java.util.List;

/**
 * @author IKoiFarmService
 */
public interface IKoiFarmService {
    KoiFarms addKoiFarm(KoiFarmRequest koiFarm);
    List<KoiFarms> findAll();
    void delete(Long id);
    KoiFarms findById(Long id);
    KoiFarms updateKoiFarm(Long id, KoiFarmRequest koiFarm);
    List<KoiFarms> findByFarmName(String farmName);
    List<KoiFarms> findActiveFarms();
}
