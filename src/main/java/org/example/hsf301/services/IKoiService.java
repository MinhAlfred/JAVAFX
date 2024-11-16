package org.example.hsf301.services;

import org.example.hsf301.dtos.request.KoiRequest;
import org.example.hsf301.pojos.Koi;

import java.util.List;

/**
 * @author IKoiService
 */
public interface IKoiService {
    // Save a new Koi record
    Koi save(KoiRequest koi);

    // Find all Koi records
    List<Koi> findAll();

    // Delete a Koi record by ID
    void delete(Long id);

    // Find a Koi record by ID
    Koi findByKoiId(Long id);

    // Update an existing Koi record
    Koi updateKoi(Long id,KoiRequest koi);

    // Find Koi records by color
    List<Koi> findByColor(String color);

    // Find Koi records by koiName
    List<Koi> findByKoiName(String koiName);

    List<Koi> findAllActive();
    // Additional methods can be defined as needed
}
