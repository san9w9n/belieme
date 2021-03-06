package com.example.beliemeserver;

import com.example.beliemeserver.data.entity.StuffEntity;
import com.example.beliemeserver.data.repository.HistoryRepository;
import com.example.beliemeserver.data.repository.ItemRepository;
import com.example.beliemeserver.data.repository.StuffRepository;
import com.example.beliemeserver.data.repository.UserRepository;
import com.example.beliemeserver.data.repository.custom.RefreshRepositoryImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Iterator;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = RefreshRepositoryImpl.class)
public class BeliemeServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(BeliemeServerApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(UserRepository userRepository, StuffRepository stuffRepository, ItemRepository itemRepository, HistoryRepository historyRepository) throws Exception {
		return (String[] args) -> {
			int maxId = 0;
			Iterator<StuffEntity> iterator = stuffRepository.findAll().iterator();
			while(iterator.hasNext()) {
				StuffEntity tmp = iterator.next();
				if (tmp.getId() > maxId) {
					maxId = tmp.getId();
				}
			}
			System.out.println("#######run###### maxId : " + maxId);
			StuffEntity.setCounter(maxId+1);
		};
	}
}
