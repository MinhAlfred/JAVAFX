package org.example.hsf301.repos;

import org.example.hsf301.pojos.Quotations;

import java.util.List;

/**
 * @author IQuotationRepository
 */
public interface IQuotationRepository {
    void save(Quotations quotation);
    List<Quotations> findAll();
    Quotations findById(Long id);
    void update(Quotations quotation);
    void delete(Long id);

}
