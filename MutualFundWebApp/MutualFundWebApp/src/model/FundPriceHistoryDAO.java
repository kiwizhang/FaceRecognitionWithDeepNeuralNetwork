package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;

import databean.PriceHistoryBean;

public class FundPriceHistoryDAO extends GenericDAO<PriceHistoryBean> {
    public FundPriceHistoryDAO(String tableName, ConnectionPool pool) throws DAOException {
        super(PriceHistoryBean.class, tableName, pool);
    }
    public PriceHistoryBean[] read(int fid) throws RollbackException{
        PriceHistoryBean[] fph = match(MatchArg.equals("fundId", fid));
        List<PriceHistoryBean> list = new ArrayList<PriceHistoryBean>(Arrays.asList(fph));
        
        Collections.sort(list, new Comparator<PriceHistoryBean>(){
            public int compare(PriceHistoryBean a, PriceHistoryBean b){
                java.util.Date da = a.getPriceDate();
                java.util.Date db = b.getPriceDate();
                if(da == null) return -1;
                if(db == null) return 1;
                return da.after(db) ? -1:1;
            }
        } );
        fph = list.toArray(fph);
        return fph;
    }
    
    public long getPriceByFundId(int fid) throws RollbackException{
        PriceHistoryBean[] fphs = match(MatchArg.equals("fundId", fid));
        if(fphs.length > 0){
            java.util.Date latest = fphs[0].getPriceDate();
            long price = fphs[0].getPrice();
            for(int j = 1; j < fphs.length; j ++){
                java.util.Date cur = fphs[j].getPriceDate();
                if( cur.after(latest) ){
                    price = fphs[j].getPrice();
                    latest = cur;  
                }
            }
            return price;
        }
        return 0;
    }
    
    public long getPriceByFundIdAndDate(int fid, Date d ) throws RollbackException{
        PriceHistoryBean[] fphs = match(MatchArg.and(
                                                MatchArg.equals("fundId", fid),
                                                MatchArg.equals("priceDate", d)) );
        if(fphs.length > 0){
            return fphs[0].getPrice();
        }
        return 0;
    }

    public void transitionDay(Date cur, int[] ids, double[] amount) throws RollbackException{
        for(int i = 0; i < ids.length; i ++){
            System.out.printf("In DAO, i=%d, %d, %f\n", i, ids[i], amount[i]);
            PriceHistoryBean fpb = new PriceHistoryBean();
            fpb.setFundId(ids[i]);
            fpb.setPrice((long)(amount[i]*100));
            fpb.setPriceDate(cur);
            create(fpb);
        }
    }

    public java.util.Date getAllLastDate() throws RollbackException {
        PriceHistoryBean[] fphs = match();

        if(fphs.length > 0){
            java.util.Date latest = null;
            for(int j = 0; j < fphs.length; j ++){
                java.util.Date cur = fphs[j].getPriceDate();
                if ( cur == null) 
                	continue;
                if ( latest == null || cur.after(latest) )
                    latest = cur;  
            }
            return latest;
        }
        return null;
    }
}
