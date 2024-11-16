package org.example.hsf301.services;

import org.example.hsf301.exceptions.ResourceNotFoundException;
import org.example.hsf301.dtos.request.KoiOfFarmRequest;
import org.example.hsf301.pojos.Koi;
import org.example.hsf301.pojos.KoiFarms;
import org.example.hsf301.pojos.KoiOfFarm;
import org.example.hsf301.repos.*;

import java.util.List;

/**
 * @author KoiOfFarmService
 */
public class KoiOfFarmService implements IKoiOfFarmService{
    private final IKoiOfFarmRepository koiOfFarmRepository;
    private final IKoiRepository koiRepository;
    private final IKoiFarmsRepository iKoiFarmsRepository;
   public KoiOfFarmService (String name)
   {
       koiOfFarmRepository = new KoiOfFarmRepository(name);
       koiRepository = new KoiRepository(name);
       iKoiFarmsRepository = new KoiFarmsRepository(name);

   }

    @Override
    public KoiOfFarm addKoiOfFarm(KoiOfFarmRequest koiOfFarmRequest)throws Exception {
        KoiOfFarm koiOfFarm = koiOfFarmRepository.findByFarmIdAndKoiId(koiOfFarmRequest.getFarmId(),koiOfFarmRequest.getKoiId());
        if (koiOfFarm == null) {
            koiOfFarm = new KoiOfFarm();
        }
        else  {

            koiOfFarm.setQuantity(koiOfFarmRequest.getQuantity()+koiOfFarm.getQuantity());
             koiOfFarmRepository.update(koiOfFarm);
             return koiOfFarm;

        }
        koiOfFarm.setQuantity(koiOfFarmRequest.getQuantity());
        koiOfFarm.setAvailable(koiOfFarmRequest.isAvailable());

        Koi kois = koiRepository.findById(koiOfFarmRequest.getKoiId());
        if (kois==null)
        {
            throw new IllegalArgumentException("Not found Koi id");
        }
    if(!kois.isActive())
    {
        throw new ResourceNotFoundException("Koi inActivated");
    }
    koiOfFarm.setKoi(kois);

    KoiFarms koiFarms = iKoiFarmsRepository.findById(koiOfFarmRequest.getFarmId());
        if (koiFarms==null)
        {
            throw new IllegalArgumentException("Not found Koi id");
        }
        if(!koiFarms.isActive())
        {
            throw new ResourceNotFoundException("KoiFarm inActivated");
        }
    koiOfFarm.setKoiFarms(koiFarms);
        koiOfFarmRepository.save(koiOfFarm);

        return koiOfFarm;
    }

    @Override
    public List<KoiOfFarm> findAll() {
        return koiOfFarmRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        koiOfFarmRepository.delete(id);
    }

    @Override
    public KoiOfFarm findById(Long id) {
        return koiOfFarmRepository.findById(id);
    }

    @Override
    public KoiOfFarm updateKoiOfFarm(Long id, KoiOfFarmRequest request)throws Exception {
        KoiOfFarm koiOfFarms = koiOfFarmRepository.findById(id);
        if (koiOfFarms == null) {
            throw new ResourceNotFoundException("KoiOfFarm not found");

        }
        KoiFarms koiFarms = null;
        if (request.getFarmId() != null) {
            koiFarms = iKoiFarmsRepository.findById(request.getFarmId());
            if (koiFarms == null) {
                throw new ResourceNotFoundException("KoiFarm not found");
            }
            koiOfFarms.setKoiFarms(koiFarms);
        }

        if (request.getKoiId() != null) {
            Koi kois = koiRepository.findById(request.getKoiId());
            if (kois == null) {
                throw new ResourceNotFoundException("Koi not found");
            }
            koiOfFarms.setKoi(kois);
        }
        koiOfFarms.setAvailable(request.isAvailable());
        koiOfFarms.setQuantity(request.getQuantity());
        koiOfFarmRepository.save(koiOfFarms);
        return koiOfFarms;
    }

    @Override
    public List<KoiOfFarm> findByFarmId(Long farmId)throws Exception {
        List<KoiOfFarm> koiOfFarmList = koiOfFarmRepository.findByFarmId(farmId);
        if(koiOfFarmList == null )
        {
            throw new ResourceNotFoundException("KoiFarmList null");

        }
        return koiOfFarmList.stream().map(koiOfFarm ->
        {
            KoiOfFarm koiOfFarms = new KoiOfFarm();
            koiOfFarms.setId(koiOfFarm.getId());
            koiOfFarms.setAvailable(koiOfFarm.isAvailable());
            koiOfFarms.setQuantity(koiOfFarm.getQuantity());
            koiOfFarms.setKoi(koiOfFarm.getKoi());
            koiOfFarms.setKoiFarms(koiOfFarm.getKoiFarms());
            return koiOfFarms;
        }).toList();
    }

    @Override
    public List<KoiOfFarm> findByKoiId(Long koiId)throws Exception {
       List<KoiOfFarm> koiOfFarmList = koiOfFarmRepository.findByKoiId(koiId);
        if(koiOfFarmList == null )
        {
            throw new ResourceNotFoundException("KoiFarmList null");

        }
        return koiOfFarmList.stream().map(koiOfFarm ->
        {
            KoiOfFarm koiOfFarms = new KoiOfFarm();
            koiOfFarms.setId(koiOfFarm.getId());
            koiOfFarms.setAvailable(koiOfFarm.isAvailable());
            koiOfFarms.setQuantity(koiOfFarm.getQuantity());
            koiOfFarms.setKoi(koiOfFarm.getKoi());
            koiOfFarms.setKoiFarms(koiOfFarm.getKoiFarms());
            return koiOfFarms;
        }).toList();
    }

    @Override
    public List<KoiOfFarm> findAvailable() {
        return koiOfFarmRepository.findAvailable();
    }

    @Override
    public void updateQuantity(Long id, int quantity) {

    }

    @Override
    public void updateAvailability(Long id, boolean available) {

    }

}
