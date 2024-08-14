package com.springboot.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SingleResponseDto <T> {
    private T data;
}

//<T> 는 아무거나 들어올 수 있으니까
