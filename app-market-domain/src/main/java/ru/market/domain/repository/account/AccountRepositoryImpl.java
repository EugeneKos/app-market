package ru.market.domain.repository.account;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import ru.market.domain.data.BankAccount;
import ru.market.domain.data.Person;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import java.util.HashSet;
import java.util.Set;

public class AccountRepositoryImpl<Entity extends BankAccount> extends SimpleJpaRepository<Entity, Long>
        implements AccountRepository<Entity> {

    private Class<Entity> domainClass;

    private EntityManager entityManager;

    public AccountRepositoryImpl(JpaEntityInformation<Entity, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.domainClass = entityInformation.getJavaType();
        this.entityManager = entityManager;
    }

    @Override
    public Set<Entity> findAllByPerson(Person person) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Entity> criteriaQuery = criteriaBuilder.createQuery(domainClass);
        Root<Entity> root = criteriaQuery.from(domainClass);

        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get(AccountFields.PERSON), person));

        return new HashSet<>(entityManager.createQuery(criteriaQuery).getResultList());
    }

    @Override
    public Set<Long> findAllAccountIdByPersonId(Long personId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Long> queryLong = criteriaBuilder.createQuery(Long.class);
        Root<Entity> root = queryLong.from(domainClass);

        queryLong.select(root.get(AccountFields.ID)).where(
                criteriaBuilder.equal(root.get(AccountFields.PERSON).get(AccountFields.ID), personId)
        );

        return new HashSet<>(entityManager.createQuery(queryLong).getResultList());
    }

    @Deprecated
    @Override
    public void deleteByPersonId(Long personId) {
        // Оставил для примера удаления через criteria API
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaDelete<Entity> criteriaDelete = criteriaBuilder.createCriteriaDelete(domainClass);
        Root<Entity> root = criteriaDelete.from(domainClass);

        criteriaDelete.where(criteriaBuilder.equal(root.get(AccountFields.PERSON).get(AccountFields.ID), personId));

        entityManager.createQuery(criteriaDelete).executeUpdate();
    }
}
