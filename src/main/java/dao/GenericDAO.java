package dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;

public abstract class GenericDAO<T> {
    protected SessionFactory sessionFactory;
    protected Class<T> entityClass;

    public GenericDAO(Class<T> entityClass) {
        this.sessionFactory = HibernateUtil.getSessionFactory();
        this.entityClass = entityClass;
    }

    public T findById(Long id) {
        Session session = sessionFactory.openSession();
        T entity = session.get(entityClass, id);
        session.close();
        return entity;
    }

    public List<T> findAll() {
        Session session = sessionFactory.openSession();
        List<T> entities = session.createQuery("from " + entityClass.getSimpleName(), entityClass).getResultList();
        session.close();
        return entities;
    }

    public boolean create(T entity) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        boolean success = false;

        try {
            tx = session.beginTransaction();
            System.out.println("=== CREATING ENTITY: " + entity.getClass().getSimpleName() + " ===");
            session.persist(entity);
            tx.commit();
            success = true;
            System.out.println("=== ENTITY CREATED SUCCESSFULLY ===");
        } catch (Exception e) {
            System.out.println("=== ERROR CREATING ENTITY ===");
            System.out.println("Entity: " + entity.getClass().getSimpleName());
            System.out.println("Error: " + e.getMessage());
            if (tx != null) {
                tx.rollback();
                System.out.println("Transaction rolled back");
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        return success;
    }

    public boolean update(T entity) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        boolean success = false;

        try {
            tx = session.beginTransaction();
            session.merge(entity);
            tx.commit();
            success = true;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        return success;
    }

    public boolean delete(T entity) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        boolean success = false;

        try {
            tx = session.beginTransaction();
            session.remove(entity);
            tx.commit();
            success = true;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        return success;
    }

    public boolean deleteById(Long id) {
        T entity = findById(id);
        if (entity != null) {
            return delete(entity);
        }
        return false;
    }
}
