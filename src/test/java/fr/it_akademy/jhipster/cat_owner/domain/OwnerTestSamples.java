package fr.it_akademy.jhipster.cat_owner.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class OwnerTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Owner getOwnerSample1() {
        return new Owner().id(1L).firstName("firstName1").lastName("lastName1").address("address1").phoneNumber(1L);
    }

    public static Owner getOwnerSample2() {
        return new Owner().id(2L).firstName("firstName2").lastName("lastName2").address("address2").phoneNumber(2L);
    }

    public static Owner getOwnerRandomSampleGenerator() {
        return new Owner()
            .id(longCount.incrementAndGet())
            .firstName(UUID.randomUUID().toString())
            .lastName(UUID.randomUUID().toString())
            .address(UUID.randomUUID().toString())
            .phoneNumber(longCount.incrementAndGet());
    }
}
