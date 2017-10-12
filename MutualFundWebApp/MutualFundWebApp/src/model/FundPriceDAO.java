
package model;
import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericViewDAO;
import org.genericdao.RollbackException;

import databean.FundPriceBean;

public class FundPriceDAO extends GenericViewDAO<FundPriceBean> {
    public FundPriceDAO(ConnectionPool pool) throws DAOException {
        super(FundPriceBean.class, pool);
    }

    public FundPriceBean[] getLatestFundPrice() throws RollbackException {	
        String query = "SELECT fund.fundId, name, symbol, max(priceDate) as priceDate, price " +
            "FROM fund, fundpricehistory " +
            "WHERE fund.fundId = fundpricehistory.fundId " +
            "AND fundpricehistory.priceDate = (SELECT max(priceDate) as priceDate " +
            "From fundpricehistory " +
            "WHERE fund.fundId = fundpricehistory.fundId)" +
            "GROUP BY fund.fundId";

        return executeQuery(query);
    }

}

