package io.hmheng.grading.utils;

import java.beans.FeatureDescriptor;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Stream;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.BeanCreationException;

/**
 * Created by nandipatim on 8/27/15.
 */
public class BeanPropertyUtils {
  private static final Logger logger = LoggerFactory.getLogger(BeanPropertyUtils.class);

    /**
     * @param source
     * @param destination
     * @return
     */
    public static Object copyProperties(Object source , Class<?> destination){
        try{
            Object destinationObject = destination.newInstance();
            PropertyUtils.copyProperties(destinationObject, source);
            return destinationObject;
        }catch(Exception ex) {
            throw new BeanCreationException("Error while copying from : "+source.getClass()+" to :"+destination.getClass(),ex);
        }

    }

    // then use Spring BeanUtils to copy and ignore null
    public static void copyPropertiesWithOnlyPopulated(Object src, Object target) {


        BeanUtils.copyProperties(src, target,getNullPropertyNames(src));
    }

    public static   Object  getObjectFromList(Collection<?> coll, Object O){
        Iterator iter = coll.iterator();
        while(iter.hasNext()) {
            Object out = iter.next();
            if(out.equals(O))
                return out;
        }
        return null;
    }

  public static String[] getNullPropertyNames(Object source) {
    final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
    return Stream.of(wrappedSource.getPropertyDescriptors())
        .map(FeatureDescriptor::getName)
        .filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null)
        .toArray(String[]::new);
  }
}
