package fr.it_akademy.jhipster.cat_owner.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CatTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Cat getCatSample1() {
        return new Cat().id(1L).name("name1").race("race1").age(1L).healtStatus("healtStatus1");
    }

    public static Cat getCatSample2() {
        return new Cat().id(2L).name("name2").race("race2").age(2L).healtStatus("healtStatus2");
    }

    public static Cat getCatRandomSampleGenerator() {
        return new Cat()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .race(UUID.randomUUID().toString())
            .age(longCount.incrementAndGet())
            .healtStatus(UUID.randomUUID().toString());
    }
}
