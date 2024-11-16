package org.example.hsf301.daos;

import org.example.hsf301.pojos.Quotations;

import java.util.List;

/**
 * @author IQuotationDAO
 */
public interface    IQuotationDAO {
    void save(Quotations quotation);
    List<Quotations> findAll();
    Quotations findById(Long id);
    void update(Quotations quotation);
    void delete(Long id);

}
