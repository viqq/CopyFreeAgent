package com.free.agent.utils;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.junit.Assert;
import org.springframework.beans.BeanUtils;

import javax.persistence.metamodel.SingularAttribute;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by antonPC on 29.01.16.
 */
public class AssertCollectionContains<T> {
    private final static int EXPECTED_PROPERTY_VALUE = 10;
    private static AssertCollectionContains lastCall;
    private final IllegalStateException exceptionAtStart;

    private Collection<T> actualObjects;
    private String[] properties;
    private final Collection<Object[]> expectedObjects = Lists.newArrayList();

    static {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                checkAssertionStarted();
            }
        });
    }

    private String[] headerNames;

    public AssertCollectionContains() {
        checkAssertionStarted();
        exceptionAtStart = new IllegalStateException("AssertCollectionContains created but assertEquals() not started");
        lastCall = this;
    }

    public AssertCollectionContains(Collection<T> collection) {
        this();
        this.actualObjects = collection;
    }

    public static <T> AssertCollectionContains<T> with(Collection<T> collection) {
        return new AssertCollectionContains<>(ImmutableList.copyOf(collection));
    }

    public static <T> AssertCollectionContains<T> with(Iterable<T> iterable) {
        return new AssertCollectionContains<>(ImmutableList.copyOf(iterable));
    }

    public void setActualObjects(Collection<T> actualObjects) {
        this.actualObjects = actualObjects;
    }

    private static void checkAssertionStarted() {
        if (lastCall != null) {
            throw lastCall.exceptionAtStart;
        }
    }

    public AssertCollectionContains onProperties(String... properties) {
        this.properties = properties;
        this.headerNames = properties;
        return this;
    }

    public AssertCollectionContains onProperties(SingularAttribute... attributes) {
        int length = attributes.length;
        String[] properties = new String[length];
        for (int i = 0; i < length; i++) {
            properties[i] = attributes[i].getName();
        }
        this.properties = properties;
        this.headerNames = properties;
        return this;
    }


    public AssertCollectionContains values(Object... values) {
        expectedObjects.add(values);
        return this;
    }

    public void assertEquals() {
        lastCall = null;
        try {
            try {
                Assert.assertEquals(expectedObjects.size(), actualObjects.size());
            } catch (AssertionError e) {
                throwComparisonFailure("number of rows mismatches");
            }
            Collection<Object[]> notFound = Lists.newLinkedList(expectedObjects);
            for (Object o : actualObjects) {
                for (Iterator<Object[]> iterator = notFound.iterator(); iterator.hasNext(); ) {
                    Object[] objectProperties = iterator.next();

                    boolean allPropertiesMatch = true;
                    for (int i = 0; i < properties.length; i++) {
                        String propertyName = properties[i];
                        Object actualPropertyValue = getPropertyValue(o, propertyName);
                        if (!propertiesMatch(actualPropertyValue, objectProperties[i])) {
                            allPropertiesMatch = false;
                        }
                    }
                    if (allPropertiesMatch) {
                        iterator.remove();
                    }
                }
            }

            if (notFound.size() > 0) {
                throwComparisonFailure("Row cannot be found " + Joiner.on('|').useForNull("-").join(notFound.iterator().next()));
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private Object getPropertyValue(Object o, String propertyPath) throws IllegalAccessException, InvocationTargetException {
        Object currentObject = o;
        for (String propertyName : propertyPath.split("\\.")) {
            PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(currentObject.getClass(), propertyName);
            if (propertyDescriptor == null) {
                throw new RuntimeException("could not find field " + propertyPath.replace(propertyName, "[" + propertyName + "]"));
            }
            currentObject = propertyDescriptor.getReadMethod().invoke(currentObject);
            if (currentObject == null) {
                return null;
            }
        }
        return currentObject;
    }

    private void throwComparisonFailure(String message) throws IllegalAccessException, InvocationTargetException {
        String header = Joiner.on('|').join(headerNames) + "\n";
        List<String> actual = Lists.newArrayList();

        for (Object o : actualObjects) {
            String row = "";
            for (String propertyName : properties) {
                row += getPropertyValue(o, propertyName) + "|";
            }
            actual.add(row);
        }

        List<String> expected = Lists.newArrayList();

        for (Object[] objectProperties : expectedObjects) {
            String row = "";
            for (Object prop : objectProperties) {
                row += prop + "|";
            }
            expected.add(row);
        }

        String expectedFormatted = format(header + Joiner.on('\n').join(expected));
        String actualFormatted = format(header + Joiner.on('\n').join(actual));
        Assert.fail(message + "\n\n Expected : \n" + expectedFormatted + "\n Actual : \n" + actualFormatted);
    }

    private static String format(String table) {
        StringBuilder formattedTable = new StringBuilder();
        LinkedList<String> lines = Lists.newLinkedList(Splitter.on("\n").split(table));
        List<Integer> colLength = Lists.newArrayList();
        List<List<String>> lineColumns = Lists.newLinkedList();
        while (!lines.isEmpty()) {
            String line = lines.getFirst();
            List<String> cols = Arrays.asList(line.split("\\|"));
            cols = Lists.transform(cols, s -> {
                String trimed = s.trim();
                if (trimed.matches("-?[0-9]*\\.[0-9]*0+")) {
                    return trimed.replaceAll("(\\.0*|0*)$", "");
                }
                return trimed;
            });

            lineColumns.add(cols);
            if (colLength.isEmpty()) {
                for (String col : cols) {
                    colLength.add(col.length());
                }
            }

            for (int i = 0; i < cols.size(); i++) {
                String col = cols.get(i).trim();
                if (colLength.get(i) < col.length()) {
                    colLength.set(i, col.length());
                }
            }
            lines.removeFirst();
        }

        for (List<String> cols : lineColumns) {
            formattedTable.append("|");
            for (int i = 0; i < cols.size(); i++) {
                String col = cols.get(i);
                formattedTable.append(Strings.padEnd(col, colLength.get(i), ' '));
                formattedTable.append("|");
            }
            formattedTable.append("\n");
        }
        return formattedTable.toString();
    }

    private boolean propertiesMatch(Object actualPropertyValue, Object expectedPropertyValue) {
        if (actualPropertyValue == null || expectedPropertyValue == null) {
            return (expectedPropertyValue == null) && (actualPropertyValue == null);
        } else if (actualPropertyValue instanceof BigDecimal) {
            if (expectedPropertyValue instanceof BigDecimal) {
                return ((BigDecimal) actualPropertyValue).compareTo((BigDecimal) expectedPropertyValue) == 0;
            } else {
                return ((BigDecimal) actualPropertyValue).compareTo(new BigDecimal(expectedPropertyValue.toString())) == 0;
            }
        } else if (actualPropertyValue.getClass() == expectedPropertyValue.getClass()) {
            return actualPropertyValue.equals(expectedPropertyValue);
        } else if (actualPropertyValue instanceof Date && expectedPropertyValue instanceof Date) {
            return ((Date) actualPropertyValue).getTime() - ((Date) expectedPropertyValue).getTime() == 0;
        } else if (actualPropertyValue instanceof Date && expectedPropertyValue.toString().length() == EXPECTED_PROPERTY_VALUE) {
            return new SimpleDateFormat("yyyy/MM/dd").format(actualPropertyValue).equals(expectedPropertyValue.toString())
                    || new SimpleDateFormat("yyyy-MM-dd").format(actualPropertyValue).equals(expectedPropertyValue.toString());
        } else if (actualPropertyValue instanceof Date) {
            return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(actualPropertyValue).equals(expectedPropertyValue.toString())
                    || new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(actualPropertyValue).equals(expectedPropertyValue.toString());
        }
        return actualPropertyValue.toString().equals(expectedPropertyValue.toString());
    }

    public void assertNotContains() {
        lastCall = null;
        try {
            Collection<Object[]> found = Lists.newLinkedList();

            for (Object o : actualObjects) {
                for (Object[] objectProperties : expectedObjects) {
                    boolean allPropertiesMatch = true;
                    for (int i = 0; i < properties.length; i++) {
                        String propertyName = properties[i];
                        Object actualPropertyValue = getPropertyValue(o, propertyName);
                        if (!propertiesMatch(actualPropertyValue, objectProperties[i])) {
                            allPropertiesMatch = false;
                        }
                    }
                    if (allPropertiesMatch) {
                        found.add(objectProperties);
                    }
                }
            }

            if (found.size() > 0) {
                throwComparisonFailure("Row can be found " + Joiner.on('|').useForNull("-").join(found.iterator().next()));
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public AssertCollectionContains withHeader(String[] headerNames) {
        this.headerNames = headerNames;
        return this;
    }
}
