package org.example.hsf301.services;

import org.example.hsf301.dtos.request.KoiFarmRequest;
import org.example.hsf301.pojos.KoiFarms;
import org.example.hsf301.repos.IKoiFarmsRepository;
import org.example.hsf301.repos.KoiFarmsRepository;

import java.util.List;

/**
 * @author KoiFarmService
 */
public class KoiFarmService implements IKoiFarmService{
   private final IKoiFarmsRepository iKoiFarmsRepository;

   public  KoiFarmService (String name)
    {
        iKoiFarmsRepository =  new KoiFarmsRepository(name);
    }



    @Override
    public KoiFarms addKoiFarm(KoiFarmRequest farmRequest) {
        KoiFarms koiFarm = new KoiFarms();
            if (farmRequest == null) {
                throw new IllegalArgumentException("KoiFarmRequest cannot be null");
            }
            koiFarm.setFarmName(farmRequest.getFarmName());
            koiFarm.setFarmPhoneNumber(farmRequest.getFarmPhoneNumber());
            koiFarm.setFarmEmail(farmRequest.getFarmEmail());
            koiFarm.setDescription(farmRequest.getDescription());
            koiFarm.setFarmAddress(farmRequest.getFarmAddress());
            koiFarm.setWebsite(farmRequest.getWebsite());
            koiFarm.setActive(true);
            koiFarm.setImage(farmRequest.getImages());
//            List<KoiFarmImage> koiFarmImages = new ArrayList<>();
//            for (String url : farmRequest.getImages()) {
//                KoiFarmImage koiFarmImage = new KoiFarmImage();
//                koiFarmImage.setImageUrl(url);
//                koiFarmImage.setKoiFarms(koiFarm);
//                koiFarmImages.add(koiFarmImage);
//            }
      //  koiFarm.setKoiFarmImages(koiFarmImages);
            iKoiFarmsRepository.save(koiFarm);
        return koiFarm;
    }

    @Override
    public List<KoiFarms> findAll() {
        return iKoiFarmsRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        iKoiFarmsRepository.delete(id);
    }

    @Override
    public KoiFarms findById(Long id) {
        return iKoiFarmsRepository.findById(id);
    }

    @Override
    public KoiFarms updateKoiFarm(Long id, KoiFarmRequest farmRequest) {
            KoiFarms koiFarm = iKoiFarmsRepository.findById(id);
            if (koiFarm == null)
            {
                throw new IllegalArgumentException("KoiFarm null");
            }
        koiFarm.setFarmName(farmRequest.getFarmName());
        koiFarm.setFarmPhoneNumber(farmRequest.getFarmPhoneNumber());
        koiFarm.setFarmEmail(farmRequest.getFarmEmail());
        koiFarm.setDescription(farmRequest.getDescription());
        koiFarm.setFarmAddress(farmRequest.getFarmAddress());
        koiFarm.setWebsite(farmRequest.getWebsite());
        koiFarm.setImage(farmRequest.getImages());
        iKoiFarmsRepository.update(koiFarm);
        return koiFarm;
    }

    @Override
    public List<KoiFarms> findByFarmName(String farmName) {
        return iKoiFarmsRepository.findByFarmName(farmName);
    }

    @Override
    public List<KoiFarms> findActiveFarms() {
        return iKoiFarmsRepository.findActiveFarms();
    }
}
