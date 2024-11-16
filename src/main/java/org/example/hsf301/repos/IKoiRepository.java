
package org.example.hsf301.repos;
import org.example.hsf301.pojos.Koi;

import java.util.List;
/**
 * @author IKoiRepository
 */
public interface IKoiRepository {
    // Save a new Koi record
    void save(Koi koi);

    // Find all Koi records
    List<Koi> findAll();

    // Delete a Koi record by ID
    void delete(Long id);

    // Find a Koi record by ID
    Koi findById(Long id);

    // Update an existing Koi record
    void update(Koi koi);

    // Find Koi records by color
    List<Koi> findByColor(String color);

    // Find Koi records by koiName
    List<Koi> findByKoiName(String koiName);

    List<Koi> findAllActive();
    // Additional methods can be defined as needed
}
