package com.springboot.response;

import lombok.Getter;

@Getter
public enum pageDirection {
    PAGE_CREATED_DATE_DESC,
    PAGE_CREATE_DATE_ASC,
    PAGE_LIKES_DESC,
    PAGE_LIKES_ASC,
    PAGE_VIEWS_DESC,
    PAGE_VIEWS_ASC;

}
