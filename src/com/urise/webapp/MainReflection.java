package com.urise.webapp;

import com.urise.webapp.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {

    public static void main(String[] args) throws IllegalAccessException {
        Resume r = new Resume();
        Field field = r.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        System.out.println(field.getName());
        System.out.println(field.get(r));
        field.set(r,"new_uuid");
        System.out.println(r);
        Method[] methods = r.getClass().getDeclaredMethods();
        for (Method method: methods){
            System.out.println(method.getName());
            /*Parameter[] parametrs = method.getParameters();
            for(Parameter parameter:parametrs){
                System.out.println(parameter.getName());
                System.out.println(parameter.getType().getName());
            }*/
        }
        try {
            System.out.println(methods[2].invoke(r, (Object[]) null));
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
