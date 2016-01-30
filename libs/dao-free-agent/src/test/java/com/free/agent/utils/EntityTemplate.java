package com.free.agent.utils;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.BeanUtils;

import javax.persistence.metamodel.SingularAttribute;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by antonPC on 29.01.16.
 */
public class EntityTemplate<T extends Serializable> {
    private final T template;
    private final List<Object[]> expectedObjects = Lists.newLinkedList();
    private String[] properties = new String[0];

    public EntityTemplate(T template) {
        this.template = template;
    }

    public EntityTemplate<T> onProperties(String... properties) {
        this.properties = properties;
        return this;
    }

    public EntityTemplate<T> onProperties(SingularAttribute... attributes) {
        int length = attributes.length;
        String[] properties = new String[length];
        for (int i = 0; i < length; i++) {
            properties[i] = attributes[i].getName();
        }
        this.properties = properties;
        return this;
    }

    public EntityTemplate<T> values(Object... values) {
        expectedObjects.add(values);
        return this;
    }

    public List<T> create() {
        try {
            List<T> list = Lists.newArrayListWithExpectedSize(properties.length);
            for (Object[] expectedObject : expectedObjects) {
                T cloned = SerializationUtils.clone(template);
                for (int i = 0; i < properties.length; i++) {
                    String[] propertyPath = properties[i].split("\\.");
                    Object currentObject = cloned;
                    for (int j = 0; j < propertyPath.length - 1; j++) {
                        String propertyName = propertyPath[j];
                        PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(currentObject.getClass(), propertyName);
                        if (propertyDescriptor == null) {
                            throw new RuntimeException("could not find field " + properties[i].replace(propertyName, "[" + propertyName + "]"));
                        }
                        currentObject = propertyDescriptor.getReadMethod().invoke(currentObject);
                    }

                    String propertyName = propertyPath[propertyPath.length - 1];
                    PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(currentObject.getClass(), propertyName);
                    if (propertyDescriptor == null) {
                        throw new RuntimeException("could not find field " + properties[i].replace(propertyName, "[" + propertyName + "]"));
                    }
                    try {
                        propertyDescriptor.getWriteMethod()
                                .invoke(currentObject, transformExpectedObjectValue(expectedObject[i], propertyDescriptor.getPropertyType()));
                    } catch (IllegalArgumentException e) {
                        throw new RuntimeException("could not set " + properties[i] + " with value " + expectedObject[i] + " of type "
                                + expectedObject[i].getClass().getCanonicalName() + " expected " + propertyDescriptor.getWriteMethod()
                                .getParameterTypes()[0].getCanonicalName());
                    }

                }
                list.add(cloned);
            }
            expectedObjects.clear();
            return list;
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private Object transformExpectedObjectValue(Object expectedObjectValue, Class<?> type) {
        if (expectedObjectValue != null && type.isAssignableFrom(BigDecimal.class)) {
            return expectedObjectValue instanceof BigDecimal ? expectedObjectValue : new BigDecimal(expectedObjectValue.toString());
        }
        return expectedObjectValue;
    }

    public T createOne(Object... values) {
        return values(values).create().get(0);
    }
}
