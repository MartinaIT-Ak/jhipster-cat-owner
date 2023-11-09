package fr.it_akademy.jhipster.cat_owner.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class VeterinaryTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Veterinary getVeterinarySample1() {
        return new Veterinary().id(1L).title("title1").firstName("firstName1").lastName("lastName1").address("address1").phoneNumber(1L);
    }

    public static Veterinary getVeterinarySample2() {
        return new Veterinary().id(2L).title("title2").firstName("firstName2").lastName("lastName2").address("address2").phoneNumber(2L);
    }

    public static Veterinary getVeterinaryRandomSampleGenerator() {
        return new Veterinary()
            .id(longCount.incrementAndGet())
            .title(UUID.randomUUID().toString())
            .firstName(UUID.randomUUID().toString())
            .lastName(UUID.randomUUID().toString())
            .address(UUID.randomUUID().toString())
            .phoneNumber(longCount.incrementAndGet());
    }
}
