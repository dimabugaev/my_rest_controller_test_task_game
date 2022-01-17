package com.game.repository;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class PlayerDaoImpl implements PlayerDao {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public List<Player> getPlayersList(Map<String, String> allRequestParam, boolean setPageParam) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Player> playerCriteriaQuery = criteriaBuilder.createQuery(Player.class);
        Root<Player> playerRoot = playerCriteriaQuery.from(Player.class);
        //playerCriteriaQuery.select(playerRoot);

        List<Predicate> predicates = new ArrayList<>();

        String paramName = "name";
        if (allRequestParam.containsKey(paramName)) {
            predicates.add(criteriaBuilder.like(playerRoot.get(paramName),
                    "%" + allRequestParam.get(paramName) + "%"));
        }
        paramName = "title";
        if (allRequestParam.containsKey(paramName)) {
            predicates.add(criteriaBuilder.like(playerRoot.get(paramName),
                    "%" + allRequestParam.get(paramName) + "%"));
        }

        paramName = "race";
        if (allRequestParam.containsKey(paramName)) {
            predicates.add(criteriaBuilder.equal(playerRoot.get(paramName),
                    Race.valueOf(allRequestParam.get(paramName))));
        }

        paramName = "profession";
        if (allRequestParam.containsKey(paramName)) {
            predicates.add(criteriaBuilder.equal(playerRoot.get(paramName),
                    Profession.valueOf(allRequestParam.get(paramName))));
        }

        paramName = "after";
        if (allRequestParam.containsKey(paramName)) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(playerRoot.get("birthday"),
                    new Date(Long.parseLong(allRequestParam.get(paramName)))));
        }

        paramName = "before";
        if (allRequestParam.containsKey(paramName)) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(playerRoot.get("birthday"),
                    new Date(Long.parseLong(allRequestParam.get(paramName)))));
        }

        paramName = "banned";
        if (allRequestParam.containsKey(paramName)) {
            predicates.add(criteriaBuilder.equal(playerRoot.get(paramName),
                    Boolean.parseBoolean(allRequestParam.get(paramName))));
        }

        paramName = "minExperience";
        if (allRequestParam.containsKey(paramName)) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(playerRoot.get("experience"),
                    Integer.parseInt(allRequestParam.get(paramName))));
        }

        paramName = "maxExperience";
        if (allRequestParam.containsKey(paramName)) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(playerRoot.get("experience"),
                    Integer.parseInt(allRequestParam.get(paramName))));
        }

        paramName = "minLevel";
        if (allRequestParam.containsKey(paramName)) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(playerRoot.get("level"),
                    Integer.parseInt(allRequestParam.get(paramName))));
        }

        paramName = "maxLevel";
        if (allRequestParam.containsKey(paramName)) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(playerRoot.get("level"),
                    Integer.parseInt(allRequestParam.get(paramName))));
        }

        if (predicates.size()>0){
            Predicate[] predicatesArray = new Predicate[predicates.size()];
            predicates.toArray(predicatesArray);
            playerCriteriaQuery.where(criteriaBuilder.and(predicatesArray));
        }


        paramName = "order";
        if (allRequestParam.containsKey(paramName)) {
            playerCriteriaQuery.orderBy(criteriaBuilder.asc(playerRoot
                    .get(PlayerOrder.valueOf(allRequestParam.get(paramName)).getFieldName())));
        }else {
            playerCriteriaQuery.orderBy(criteriaBuilder.asc(playerRoot.get(PlayerOrder.ID.getFieldName())));
        }


        playerCriteriaQuery.select(playerRoot);

        Query resultQuery = entityManager.createQuery(playerCriteriaQuery);

        if (setPageParam) {
            resultQuery.setFirstResult(0);

            paramName = "pageSize";
            int pageSize = 3;
            if (allRequestParam.containsKey(paramName)) {
                pageSize = Integer.parseInt(allRequestParam.get(paramName));
            }
            resultQuery.setMaxResults(pageSize);

            paramName = "pageNumber";
            int pageNumber = 0;
            if (allRequestParam.containsKey(paramName)) {
                pageNumber = Integer.parseInt(allRequestParam.get(paramName));
            }

            resultQuery.setFirstResult(pageNumber * pageSize);
        }

        return resultQuery.getResultList();
    }

    @Override
    public Integer getPlayersCount(Map<String, String> allRequestParam) {
        return getPlayersList(allRequestParam, false).size();
    }


    @Override
    public Player createPlayer(Player player) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Session session = entityManager.unwrap(Session.class);
        session.save(player);
        return player;
    }


    @Override
    public Player getPlayer(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Session session = entityManager.unwrap(Session.class);
        return session.get(Player.class, id);
    }

    @Override
    public Player updatePlayer(Player player) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Player persistInstance = entityManager.merge(player);
        entityManager.persist(persistInstance);
        entityManager.flush();
        entityManager.getTransaction().commit();

        return player;
    }

    @Override
    public void deletePlayer(Player player) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Player persistInstance = entityManager.merge(player);
        entityManager.remove(persistInstance);
        entityManager.flush();
        entityManager.getTransaction().commit();

    }
}
