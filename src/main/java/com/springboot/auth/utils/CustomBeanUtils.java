package com.springboot.auth.utils;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Collection;
// 이 class 는 객체간의 속성 복사를 자동화하여 코드의 반복 작업을 줄이고
// 객체 간 데이터 전달을 보다 간편하게 만드는 데 사용
@Component
public class CustomBeanUtils<T> { //제네릭으로 관리되고 있다
//source에서 destination 객체로 복사하는 역할
    //소스가 복사해올 객체 -> 원본데이터가 저장된 객체
    //데이터를 복사해 갈 객체 ->복사된 데이터를 저장할 객체
    public T copyNonNullProperties(T source, T destination) {
        if (source == null || destination == null || source.getClass() != destination.getClass()) {
            return null;   //null 값인지 확인하고 두개가 같은 클래스인지 확인해서 아니면  null 값 반환
        }

        final BeanWrapper src = new BeanWrapperImpl(source);
        final BeanWrapper dest = new BeanWrapperImpl(destination);
    //BeanWrapper는 스프링에서 제공하는 인터페이스
        //객체 속성에 접근가능하고 설정할 수 있다

        //for 문 돌리기
        //source 객체의 모든필드에 대해 반복
        for (final Field property : source.getClass().getDeclaredFields()) {
          //속성값 가져온다
            Object sourceProperty = src.getPropertyValue(property.getName());
            if (sourceProperty != null && !(sourceProperty instanceof Collection<?>)) {
              //sourceProperty 가 null 아 아니고 Collection 타입이 아닌 경우에만 destination 객체 생성
                dest.setPropertyValue(property.getName(), sourceProperty);
            }
        }

        return destination;
    }
}