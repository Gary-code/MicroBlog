/**
 * Authored by Gary on 2020/08/28.
 **/

package com.gary.microblog.util;

import com.gary.microblog.entity.Blog;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.context.annotation.PropertySource;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

public class MyBeanUtils {
    /**
     * 获取所有属性值为空的数组名字
     * @param source
     * @return
     */
    public static String[] getNullPropertyNames(Object source){
        BeanWrapper beanWrapper=new BeanWrapperImpl(source);
        PropertyDescriptor [] pds=beanWrapper.getPropertyDescriptors();
        List<String> nullPropertyName=new ArrayList<>();
        for(PropertyDescriptor pd:pds){
            String propertyName=pd.getName();
            if(beanWrapper.getPropertyValue(propertyName)==null){
                nullPropertyName.add(propertyName);
            }
        }
        return nullPropertyName.toArray(new String[nullPropertyName.size()]);
    }



}
