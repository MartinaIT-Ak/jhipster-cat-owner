package fr.it_akademy.jhipster.cat_owner.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class DogTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Dog getDogSample1() {
        return new Dog().id(1L).name("name1").race("race1").age(1L).healthStatus("healthStatus1");
    }

    public static Dog getDogSample2() {
        return new Dog().id(2L).name("name2").race("race2").age(2L).healthStatus("healthStatus2");
    }

    public static Dog getDogRandomSampleGenerator() {
        return new Dog()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .race(UUID.randomUUID().toString())
            .age(longCount.incrementAndGet())
            .healthStatus(UUID.randomUUID().toString());
    }
}
