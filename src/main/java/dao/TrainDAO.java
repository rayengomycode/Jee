package dao;

import model.Train;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class TrainDAO extends GenericDAO<Train> {
    
    public TrainDAO() {
        super(Train.class);
    }
    
    public Train findByNumero(String numero) {
        Session session = sessionFactory.openSession();
        Query<Train> query = session.createQuery("from Train where numero = :numero", Train.class);
        query.setParameter("numero", numero);
        Train train = query.uniqueResult();
        session.close();
        return train;
    }
}
