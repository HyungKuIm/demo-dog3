package com.oraclejava.demodog.dog;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

/** 등록/수정 요청 본문. id와 readcount는 서버에서 관리하므로 받지 않는다. */
public record DogRequest(
		@NotBlank @Size(max = 100) String kind,
		@PositiveOrZero Integer price,
		@Size(max = 500) String image,
		@Size(max = 100) String country,
		@PositiveOrZero Double height,
		@PositiveOrZero Double weight,
		@Size(max = 4000) String content) {

}
