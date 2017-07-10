package io.hmheng.grading.utils;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by srikanthk on 5/1/17.
 */
public class ListUtils {


    public static <T> Collection<T> findAll(Collection<T> coll, Object object){

        LinkedList<T> list = new LinkedList<T>();
        for(T obj: coll)
            if(obj.equals(object))
                list.add(obj);

        return list ;
    }


}
