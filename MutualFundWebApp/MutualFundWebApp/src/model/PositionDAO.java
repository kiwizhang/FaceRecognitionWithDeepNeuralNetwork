package model;

import java.util.*;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import databean.PositionBean;

public class PositionDAO extends GenericDAO<PositionBean> {
    public PositionDAO(String tableName, ConnectionPool pool) throws DAOException {
        super(PositionBean.class, tableName, pool);
    }
    
    public boolean existName( int fundId ) throws RollbackException{
        PositionBean[] fbs = match(MatchArg.equals("fundId", fundId));
        if (fbs.length != 0) return true;
        return false;
    }

    //--------1/21/2015----update for sell funciton---------
    public PositionBean getPendingShares(int fundId, int customerId) throws RollbackException{
        PositionBean[] position = null;
        position = match(
                MatchArg.and(
                    MatchArg.equals("fundId", fundId),
                    MatchArg.equals("customerId",  customerId)));
        //position = match(MatchArg.equals("fundId", fundId));
        if (position.length != 0) return position[0];
        return null;
    }

    //-----------------
    public void addShare(int cid, int fid, long add)
        throws RollbackException{
        PositionBean[] pbs = match();
        boolean done = false;
        System.out.println("In add share,");
        System.out.printf("cid, fid, add = %d, %d, %d", cid, fid, add);
        for(int i = 0; i < pbs.length; i ++){
            if( pbs[i].getFundId() == fid && pbs[i].getCustomerId() == cid ){
                done = true;
                try {
                    Transaction.begin();

                    long tmp = pbs[i].getShares() + add;
                    if( tmp > 0 ){
                        pbs[i].setShares(tmp);
                        update(pbs[i]);
                    }
                    else{
                        delete(pbs[i].getFundId(), pbs[i].getCustomerId() );
                    }
                    Transaction.commit();
                } finally {
                    if (Transaction.isActive()) Transaction.rollback();
                }
            }
        }
        if( !done ){
            System.out.printf("Create new! cid, fid, add = %d, %d, %d", cid, fid, add);
            PositionBean pb = new PositionBean();
            pb.setCustomerId(cid);
            pb.setFundId(fid);
            pb.setShares(add);
            create(pb);
        }

    }

    public void setPendingShare(int customerId,int fundId) throws RollbackException{
        PositionBean[] position = null;
        position = match(
                MatchArg.and(
                    MatchArg.equals("fundId", fundId),
                    MatchArg.equals("customerId",  customerId)));
        if(position == null) return;

        Transaction.begin();
        long tmp = position[0].getShares();
        if( tmp > 0 ){
            position[0].setPendingShares(tmp);
            update(position[0]);
        }
        else{
            delete(position[0].getFundId(), position[0].getCustomerId() );
        }
        Transaction.commit();
    }

    public ArrayList<Integer> getSharesByCustomerId(int customerId) throws RollbackException {
        PositionBean[] a = match(MatchArg.equals("customerId", customerId));
        ArrayList<Integer> ids = new ArrayList<Integer>();
        for(int i = 0; i < a.length; i ++){
            ids.add(a[i].getFundId());
        }
        return ids;
    }


}
