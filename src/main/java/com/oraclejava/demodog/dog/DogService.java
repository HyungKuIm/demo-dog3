package com.oraclejava.demodog.dog;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class DogService {

	private final DogRepository dogRepository;

	public DogService(DogRepository dogRepository) {
		this.dogRepository = dogRepository;
	}

	public List<DogResponse> findAll() {
		return dogRepository.findAll(Sort.by("id")).stream().map(DogResponse::from).toList();
	}

	/** 단건 조회 시 조회수를 1 증가시킨다. */
	@Transactional
	public DogResponse read(Long id) {
		Dog dog = getDog(id);
		dog.increaseReadcount();
		return DogResponse.from(dog);
	}

	@Transactional
	public DogResponse create(DogRequest req) {
		Dog dog = new Dog(req.kind(), req.price(), req.image(), req.country(), req.height(), req.weight(),
				req.content());
		return DogResponse.from(dogRepository.save(dog));
	}

	@Transactional
	public DogResponse update(Long id, DogRequest req) {
		Dog dog = getDog(id);
		dog.update(req.kind(), req.price(), req.image(), req.country(), req.height(), req.weight(), req.content());
		return DogResponse.from(dog);
	}

	@Transactional
	public void delete(Long id) {
		dogRepository.delete(getDog(id));
	}

	private Dog getDog(Long id) {
		return dogRepository.findById(id).orElseThrow(() -> new DogNotFoundException(id));
	}

}
