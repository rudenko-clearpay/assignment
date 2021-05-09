package com.clearpay.coin.configuration;

import com.clearpay.coin.model.User;
import com.clearpay.coin.model.Wallet;
import com.clearpay.coin.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
@Profile("test")
@Slf4j
public class TestDataLoader implements ApplicationListener<ApplicationReadyEvent> {
    public static final int MIN_WALLETS = 1;
    public static final int MAX_WALLTS = 3;

    public static final int MIN_BALANCE = 0;
    public static final int MAX_BALANCE = 5000;

    private final UserRepository userRepository;

    public TestDataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        log.warn("Provisioning test data");
        userRepository.deleteAll();
        List<User> users = generateTestUsers();
        userRepository.saveAll(users);
    }

    private List<User> generateTestUsers() {
        List<String> names = List.of("Jim Halpert", "Dwight Schrute", "Michael Scott", "Pam Beesly", "Ryan Howard", "Andy Bernard", "Robert California", "Jan Levinson", "Stanley Hudson", "Kevin Malone", "Meredith Palmer", "Angela Martin", "Oscar Martinez", "Phyllis Vance", "Toby Flenderson", "Kelly Kapoor", "Creed Bratton", "Darryl Philbin", "Erin Hannon", "Pete Miller", "Holly Flax");

        return names.stream().map(name -> {
            List<Wallet> wallets = new LinkedList<>();
            int walletsAmount = getRandomInRange(MIN_WALLETS, MAX_WALLTS);
            for (int i = 0; i < walletsAmount; i++) {
                int balance = getRandomInRange(MIN_BALANCE, MAX_BALANCE);
                wallets.add(new Wallet(null, String.valueOf(balance)));
            }
            return new User(null, name, wallets);
        }).toList();
    }

    private int getRandomInRange(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}
