package com.clearpay.coin;

import com.clearpay.coin.model.User;
import com.clearpay.coin.model.Wallet;
import com.clearpay.coin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Provisioning test data");
		userRepository.deleteAll();

		User user1 = new User(null, "Felipe VI", List.of(new Wallet(null, 150), new Wallet(null, 25)));
		User user2 = new User(null, " Juan Carlos", List.of(new Wallet(null, 20000), new Wallet(null, 3434)));
		userRepository.saveAll(List.of(user1, user2));
	}
}
