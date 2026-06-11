package com.freelance.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.freelance.bean.Bid;
import com.freelance.util.DBUtil;

public class BidDAO {
    private Bid mapBid(ResultSet rs) throws SQLException {
        Bid bid = new Bid();
        bid.setBidID(rs.getInt("BID_ID"));
        bid.setProjectID(rs.getInt("PROJECT_ID"));
        bid.setFreelancerUserID(rs.getString("FREELANCER_USER_ID"));
        bid.setBidAmount(rs.getBigDecimal("BID_AMOUNT"));
        bid.setDeliveryDays(rs.getInt("DELIVERY_DAYS"));
        bid.setCoverLetter(rs.getString("COVER_LETTER"));
        bid.setBidDate(rs.getDate("BID_DATE"));
        bid.setBidStatus(rs.getString("BID_STATUS"));
        return bid;
    }

    public int generateBidID() throws ClassNotFoundException, SQLException {
        int bidID = 710001;
        Connection con = DBUtil.getDBConnection();
        String sql = "SELECT NVL(MAX(BID_ID),710000)+1 FROM BID_TBL";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            bidID = rs.getInt(1);
        }
        rs.close();
        ps.close();
        con.close();
        return bidID;
    }

    public int generateBidID(Connection con) throws SQLException {
        int bidID = 710001;
        String sql = "SELECT NVL(MAX(BID_ID),710000)+1 FROM BID_TBL";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            bidID = rs.getInt(1);
        }
        rs.close();
        ps.close();
        return bidID;
    }

    public boolean insertBid(Bid bid) throws ClassNotFoundException, SQLException {
        boolean result = false;
        Connection con = DBUtil.getDBConnection();
        String sql = "INSERT INTO BID_TBL(BID_ID,PROJECT_ID,FREELANCER_USER_ID,BID_AMOUNT,DELIVERY_DAYS,COVER_LETTER,BID_DATE,BID_STATUS) VALUES(?,?,?,?,?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, bid.getBidID());
        ps.setInt(2, bid.getProjectID());
        ps.setString(3, bid.getFreelancerUserID());
        ps.setBigDecimal(4, bid.getBidAmount());
        ps.setInt(5, bid.getDeliveryDays());
        ps.setString(6, bid.getCoverLetter());
        ps.setDate(7, bid.getBidDate());
        ps.setString(8, bid.getBidStatus());
        int rows = ps.executeUpdate();
        if (rows > 0) {
            result = true;
        }
        ps.close();
        con.close();
        return result;
    }

    public boolean insertBid(Connection con, Bid bid) throws SQLException {
        boolean result = false;
        String sql = "INSERT INTO BID_TBL(BID_ID,PROJECT_ID,FREELANCER_USER_ID,BID_AMOUNT,DELIVERY_DAYS,COVER_LETTER,BID_DATE,BID_STATUS) VALUES(?,?,?,?,?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, bid.getBidID());
        ps.setInt(2, bid.getProjectID());
        ps.setString(3, bid.getFreelancerUserID());
        ps.setBigDecimal(4, bid.getBidAmount());
        ps.setInt(5, bid.getDeliveryDays());
        ps.setString(6, bid.getCoverLetter());
        ps.setDate(7, bid.getBidDate());
        ps.setString(8, bid.getBidStatus());
        int rows = ps.executeUpdate();
        if (rows > 0) {
            result = true;
        }
        ps.close();
        return result;
    }

    public Bid findBid(int bidID) throws ClassNotFoundException, SQLException {
        Bid bid = null;
        Connection con = DBUtil.getDBConnection();
        String sql = "SELECT * FROM BID_TBL WHERE BID_ID=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, bidID);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            bid = mapBid(rs);
        }
        rs.close();
        ps.close();
        con.close();
        return bid;
    }

    public List<Bid> findBidsByProject(int projectID) throws ClassNotFoundException, SQLException {
        List<Bid> bids = new ArrayList<Bid>();
        Connection con = DBUtil.getDBConnection();
        String sql = "SELECT * FROM BID_TBL WHERE PROJECT_ID=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, projectID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            bids.add(mapBid(rs));
        }
        rs.close();
        ps.close();
        con.close();
        return bids;
    }

    public List<Bid> findBidsByFreelancer(String freelancerUserID) throws ClassNotFoundException, SQLException {
        List<Bid> bids = new ArrayList<Bid>();
        Connection con = DBUtil.getDBConnection();
        String sql = "SELECT * FROM BID_TBL WHERE FREELANCER_USER_ID=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, freelancerUserID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            bids.add(mapBid(rs));
        }
        rs.close();
        ps.close();
        con.close();
        return bids;
    }

    public Bid findActiveBidForProjectAndFreelancer(int projectID, String freelancerUserID) throws ClassNotFoundException, SQLException {
        Bid bid = null;
        Connection con = DBUtil.getDBConnection();
        String sql = "SELECT * FROM BID_TBL WHERE PROJECT_ID=? AND FREELANCER_USER_ID=? AND BID_STATUS='PENDING'";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, projectID);
        ps.setString(2, freelancerUserID);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            bid = mapBid(rs);
        }
        rs.close();
        ps.close();
        con.close();
        return bid;
    }

    public boolean updateBidStatus(int bidID, String status) throws ClassNotFoundException, SQLException {
        boolean result = false;
        Connection con = DBUtil.getDBConnection();
        String sql = "UPDATE BID_TBL SET BID_STATUS=? WHERE BID_ID=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, status);
        ps.setInt(2, bidID);
        int rows = ps.executeUpdate();
        if (rows > 0) {
            result = true;
        }
        ps.close();
        con.close();
        return result;
    }

    public boolean updateBidStatus(Connection con, int bidID, String status) throws SQLException {
        boolean result = false;
        String sql = "UPDATE BID_TBL SET BID_STATUS=? WHERE BID_ID=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, status);
        ps.setInt(2, bidID);
        int rows = ps.executeUpdate();
        if (rows > 0) {
            result = true;
        }
        ps.close();
        return result;
    }

    public boolean bulkRejectOtherBids(int projectID, int acceptedBidID) throws ClassNotFoundException, SQLException {
        boolean result = false;
        Connection con = DBUtil.getDBConnection();
        String sql = "UPDATE BID_TBL SET BID_STATUS='REJECTED' WHERE PROJECT_ID=? AND BID_ID<>? AND BID_STATUS='PENDING'";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, projectID);
        ps.setInt(2, acceptedBidID);
        int rows = ps.executeUpdate();
        if (rows >= 0) {
            result = true;
        }
        ps.close();
        con.close();
        return result;
    }

    public boolean bulkRejectOtherBids(Connection con, int projectID, int acceptedBidID) throws SQLException {
        boolean result = false;
        String sql = "UPDATE BID_TBL SET BID_STATUS='REJECTED' WHERE PROJECT_ID=? AND BID_ID<>? AND BID_STATUS='PENDING'";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, projectID);
        ps.setInt(2, acceptedBidID);
        int rows = ps.executeUpdate();
        if (rows >= 0) {
            result = true;
        }
        ps.close();
        return result;
    }

    public List<Bid> findActiveBidsForFreelancer(String freelancerUserID) throws ClassNotFoundException, SQLException {
        List<Bid> bids = new ArrayList<Bid>();
        Connection con = DBUtil.getDBConnection();
        String sql = "SELECT B.* FROM BID_TBL B JOIN PROJECT_TBL P ON B.PROJECT_ID=P.PROJECT_ID WHERE B.FREELANCER_USER_ID=? AND P.STATUS IN('OPEN','AWARDED') AND B.BID_STATUS NOT IN('REJECTED','WITHDRAWN')";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, freelancerUserID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            bids.add(mapBid(rs));
        }
        rs.close();
        ps.close();
        con.close();
        return bids;
    }
}
