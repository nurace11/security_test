package com.nuracell.bs.entity.customgen;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentityGenerator;

import java.io.Serializable;

public class MyGenerator extends IdentityGenerator {
    @Override
    public Object generate(SharedSessionContractImplementor s, Object obj) {
        System.out.println("[MY_GENERATOR] obj: " + obj);
        if (obj == null) {
            throw new HibernateException(new NullPointerException());
        }
        final Serializable id = (Serializable) s.getEntityPersister(null, obj).getClassMetadata().getIdentifier(obj, s);
        System.out.println("[MY_GENERATOR] id: " + id);
        return id == null ? super.generate(s, obj) : id;
    }
}
