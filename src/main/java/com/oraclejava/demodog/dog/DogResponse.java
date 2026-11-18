package com.oraclejava.demodog.dog;

public record DogResponse(
		Long id,
		String kind,
		Integer price,
		String image,
		String country,
		Double height,
		Double weight,
		String content,
		int readcount) {

	public static DogResponse from(Dog dog) {
		return new DogResponse(dog.getId(), dog.getKind(), dog.getPrice(), dog.getImage(), dog.getCountry(),
				dog.getHeight(), dog.getWeight(), dog.getContent(), dog.getReadcount());
	}

}
