package com.s2c.demos.conceptos.ejb;

import com.s2c.demos.conceptos.data.ConceptoPrecio;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * EJB Facade class implementation for ConceptoPrecio Persistence features
 * @author demo
 */
@Stateless
public class ConceptoPrecioFacade extends AbstractFacade<ConceptoPrecio> {
    @PersistenceContext(unitName = "s2c-demo-webPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ConceptoPrecioFacade() {
        super(ConceptoPrecio.class);
    }

    public boolean alreadyExistsConcepto(String concepto) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root root = cq.from(ConceptoPrecio.class);
        cq.select(root).where(cb.equal(root.get("concepto"), concepto));
        List resultList = em.createQuery(cq).getResultList();
        return (resultList != null && resultList.isEmpty());
    }
    
}
