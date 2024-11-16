package org.example.hsf301.repos;

import org.example.hsf301.daos.IQuotationDAO;
import org.example.hsf301.daos.QuotationDAO;
import org.example.hsf301.pojos.Quotations;

import java.util.List;

/**
 * @author QuotationRepository
 */
public class QuotationRepository implements  IQuotationRepository{
    private final IQuotationDAO quotationDAO;

    public QuotationRepository (String name)
    {
        quotationDAO = new QuotationDAO(name);
    }

    @Override
    public void save(Quotations quotation) {
     quotationDAO.save(quotation);
    }

    @Override
    public List<Quotations> findAll() {
        return quotationDAO.findAll();
    }

    @Override
    public Quotations findById(Long id) {
        return quotationDAO.findById(id);
    }

    @Override
    public void update(Quotations quotation) {
            quotationDAO.update(quotation);
    }

    @Override
    public void delete(Long id) {
            quotationDAO.delete(id);
    }
}
