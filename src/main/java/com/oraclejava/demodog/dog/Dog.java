package com.oraclejava.demodog.dog;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "dogs")
public class Dog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** 개 품종 */
	@Column(nullable = false, length = 100)
	private String kind;

	private Integer price;

	/** 이미지 경로 또는 URL */
	@Column(length = 500)
	private String image;

	/** 원산지 */
	@Column(length = 100)
	private String country;

	private Double height;

	private Double weight;

	/** 개 설명 */
	@Column(length = 4000)
	private String content;

	/** 조회수 */
	@Column(nullable = false)
	private int readcount;

	protected Dog() {
	}

	public Dog(String kind, Integer price, String image, String country, Double height, Double weight,
			String content) {
		update(kind, price, image, country, height, weight, content);
	}

	public void update(String kind, Integer price, String image, String country, Double height, Double weight,
			String content) {
		this.kind = kind;
		this.price = price;
		this.image = image;
		this.country = country;
		this.height = height;
		this.weight = weight;
		this.content = content;
	}

	public void increaseReadcount() {
		this.readcount++;
	}

	public Long getId() {
		return id;
	}

	public String getKind() {
		return kind;
	}

	public Integer getPrice() {
		return price;
	}

	public String getImage() {
		return image;
	}

	public String getCountry() {
		return country;
	}

	public Double getHeight() {
		return height;
	}

	public Double getWeight() {
		return weight;
	}

	public String getContent() {
		return content;
	}

	public int getReadcount() {
		return readcount;
	}

}
