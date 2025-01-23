package PAF.day5_workshop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import PAF.day5_workshop.Repository.RedisRepository;

@Component
public class CommandLineAppRunner implements CommandLineRunner {
    @Autowired
    private RedisRepository redisRepository;

    @Override
    public void run(String... args) throws Exception {
        String appName = args[0];
        redisRepository.addAppName(appName);
    }
}
