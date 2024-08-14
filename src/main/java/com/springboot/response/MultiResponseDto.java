package com.springboot.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@AllArgsConstructor
public class MultiResponseDto<T> {
    private List<T> data;
    private PageInfo pageInfo;



    public MultiResponseDto(List<T> data , Page page ) {
        this.data = data;   //페이지가 0 부터 시작하기 때문에 +1 해야지 1페이지 부터 시작인 것
        this.pageInfo = new PageInfo( page.getNumber()+ 1,
                page.getSize() , page.getTotalElements() , page.getTotalPages());
    }

}
