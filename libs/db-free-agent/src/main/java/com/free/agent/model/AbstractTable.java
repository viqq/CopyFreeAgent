package com.free.agent.model;

import java.io.Serializable;

/**
 * Created by antonPC on 11.07.15.
 */
public abstract class AbstractTable<PK> implements Serializable, Cloneable {

    protected abstract PK getId();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractTable<?> that = (AbstractTable<?>) o;
        return !(getId() != null ? !getId().equals(that.getId()) : that.getId() != null);
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : super.hashCode();
    }
}
