package org.example.hsf301.services;

import org.example.hsf301.dtos.request.KoiRequest;
import org.example.hsf301.pojos.Koi;
import org.example.hsf301.repos.IKoiRepository;
import org.example.hsf301.repos.KoiRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author KoiService
 */
public class KoiService  implements  IKoiService{
    private final IKoiRepository koiRepository;
    public KoiService (String name)
    {
        koiRepository = new KoiRepository(name);
        //iKoiImageRepository=new KoiImageRepository(name);
    }
    @Override
    public Koi save(KoiRequest koiRequest) {
        Koi koi = new Koi();
        koi.setKoiName(koiRequest.getKoiName());
        koi.setColor(koiRequest.getColor());
        koi.setDescription(koiRequest.getDescription());
        koi.setOrigin(koiRequest.getOrigin());


        koi.setActive(true);
        koi.setKoiImage(koiRequest.getImage());

        koiRepository.save(koi);
        return koi;

    }

    @Override
    public List<Koi> findAll() {
        return koiRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        koiRepository.delete(id);
    }
    @Transactional
    @Override
    public Koi findByKoiId(Long id) {
        return koiRepository.findById(id);
    }

    @Override
    public Koi updateKoi(Long id,KoiRequest koiRequest) {
        Koi koi = koiRepository.findById(id);
        if (koiRequest==null)
        {
            throw new IllegalArgumentException("KoiRequest cannot be null");
        }

        koi.setKoiName(koiRequest.getKoiName());
        koi.setColor(koiRequest.getColor());
        koi.setDescription(koiRequest.getDescription());
        koi.setOrigin(koiRequest.getOrigin());


        koi.setActive(true);
        koi.setKoiImage(koiRequest.getImage());
        koiRepository.update(koi);
        return koi;
    }

    @Override
    public List<Koi> findByColor(String color) {
        return koiRepository.findByColor(color);
    }

    @Override
    public List<Koi> findByKoiName(String koiName) {
        return koiRepository.findByKoiName(koiName);
    }

    @Override
    public List<Koi> findAllActive() {
        return koiRepository.findAllActive();
    }

}