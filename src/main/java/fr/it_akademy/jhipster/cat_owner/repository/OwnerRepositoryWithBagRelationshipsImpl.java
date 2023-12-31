package fr.it_akademy.jhipster.cat_owner.repository;

import fr.it_akademy.jhipster.cat_owner.domain.Owner;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class OwnerRepositoryWithBagRelationshipsImpl implements OwnerRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Owner> fetchBagRelationships(Optional<Owner> owner) {
        return owner.map(this::fetchVeterinaries);
    }

    @Override
    public Page<Owner> fetchBagRelationships(Page<Owner> owners) {
        return new PageImpl<>(fetchBagRelationships(owners.getContent()), owners.getPageable(), owners.getTotalElements());
    }

    @Override
    public List<Owner> fetchBagRelationships(List<Owner> owners) {
        return Optional.of(owners).map(this::fetchVeterinaries).orElse(Collections.emptyList());
    }

    Owner fetchVeterinaries(Owner result) {
        return entityManager
            .createQuery("select owner from Owner owner left join fetch owner.veterinaries where owner.id = :id", Owner.class)
            .setParameter("id", result.getId())
            .getSingleResult();
    }

    List<Owner> fetchVeterinaries(List<Owner> owners) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, owners.size()).forEach(index -> order.put(owners.get(index).getId(), index));
        List<Owner> result = entityManager
            .createQuery("select owner from Owner owner left join fetch owner.veterinaries where owner in :owners", Owner.class)
            .setParameter("owners", owners)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
