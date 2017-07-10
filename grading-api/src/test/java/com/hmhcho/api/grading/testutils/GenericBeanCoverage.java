package com.hmhcho.api.grading.testutils;

import org.mockito.Mockito;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by srikanthk on 4/28/17.
 */
public class GenericBeanCoverage {


    public static void doBeanCodeCoverage(Class<?> c) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException {
        Object obj = c.newInstance();
        Method[] methods = c.getDeclaredMethods();

        for (Method method : methods) {
            System.out.println("Method : " + method.getName());
            Class<?>[] paramTypes = method.getParameterTypes();

            if (method.getName().startsWith("set")) {
                Object[] parameterInstances = buildParameterIObjectArray(paramTypes);
                method.invoke(obj, parameterInstances);
            }
            if (method.getName().startsWith("get")) {
                Object val = method.invoke(obj);
                System.out.println(method.getName() + "\t" + val);
            }
        }
    }

    public static Object[] buildParameterIObjectArray(Class<?>[] paramTypes) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        List<Object> objInstances = new ArrayList<Object>();
        for (Class<?> c : paramTypes) {

            Object obj = primativeValue(c);
            if (obj == null) {
                obj = Mockito.mock(Class.forName(c.getName()));
            }
            objInstances.add(obj);
        }

        return objInstances.toArray(new Object[]{});

    }

    private static Object primativeValue(Class<?> c) {
        if (c == boolean.class) {
            return true;
        } else if (c == byte.class) {
            return (byte) 0;
        } else if (c == char.class) {
            return 'c';
        } else if (c == int.class) {
            return -1;
        } else if (c == double.class) {
            return -2;
        } else if (c == double.class) {
            return -2;
        } else if (c == float.class) {
            return -3;
        } else if (c == long.class) {
            return -4;
        } else if (c == short.class) {
            return -5;
        } else if (c == Boolean.class) {
            return new Boolean(true);
        } else if (c == Byte.class) {
            return new Byte((byte) 0);
        } else if (c == Integer.class) {
            return new Integer(1);
        } else if (c == Double.class) {
            return new Double(2);
        } else if (c == Float.class) {
            return new Float(3);
        } else if (c == Long.class) {
            return new Long(3);
        } else if (c == Short.class) {
            return new Short("4");
        } else if (c == String.class) {
            return new String("s");
        } else if (c == LocalDateTime.class) {
            return LocalDateTime.now();
        } else if (c == UUID.class) {
            return UUID.randomUUID();
        } else if ("class [I".equals(c.toString())) {
            return new int[] {5};
        } else if ("class [L".equals(c.toString())) {
            return new long[] {6L};
        } else if ("class [D".equals(c.toString())) {
            return new double[] {7D};
        } else if ("class [F".equals(c.toString())) {
            return new float[] {8F};
        } else {
            return null;
        }
    }

}
