package com.freelance.service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import com.freelance.bean.Bid;
import com.freelance.bean.Project;
import com.freelance.bean.User;
import com.freelance.dao.BidDAO;
import com.freelance.dao.ProjectDAO;
import com.freelance.dao.UserDAO;
import com.freelance.util.ActiveEngagementsExistException;
import com.freelance.util.DBUtil;
import com.freelance.util.ProjectAwardingException;
import com.freelance.util.ValidationException;

public class FreelanceService {

    private UserDAO userDAO = new UserDAO();
    private ProjectDAO projectDAO = new ProjectDAO();
    private BidDAO bidDAO = new BidDAO();

    private boolean isEmpty(String value) {
        return value == null || value.trim().equals("");
    }

    public User viewUserDetails(String userID) throws ValidationException, ClassNotFoundException, SQLException {
        if (isEmpty(userID)) {
            throw new ValidationException("User ID is required");
        }
        return userDAO.findUser(userID);
    }

    public List<User> viewAllUsers() throws ClassNotFoundException, SQLException {
        return userDAO.viewAllUsers();
    }

    public boolean registerNewUser(User user) throws ValidationException, ClassNotFoundException, SQLException {
        if (user == null) {
            throw new ValidationException("User details are required");
        }

        if (isEmpty(user.getUserID()) || isEmpty(user.getFullName()) || isEmpty(user.getEmail()) || isEmpty(user.getRole())) {
            throw new ValidationException("User ID, name, email and role are mandatory");
        }

        String role = user.getRole().toUpperCase();

        if (!role.equals("CLIENT") && !role.equals("FREELANCER")) {
            throw new ValidationException("Role must be CLIENT or FREELANCER");
        }

        User existingUser = userDAO.findUser(user.getUserID());

        if (existingUser != null) {
            throw new ValidationException("User already exists");
        }

        user.setRole(role);
        user.setStatus("ACTIVE");

        return userDAO.insertUser(user);
    }

    public boolean postNewProject(Project project) throws ValidationException, ClassNotFoundException, SQLException {
        if (project == null) {
            throw new ValidationException("Project details are required");
        }

        if (isEmpty(project.getClientUserID()) || isEmpty(project.getProjectTitle()) || isEmpty(project.getProjectDescription())) {
            throw new ValidationException("Client ID, title and description are mandatory");
        }

        if (project.getBudgetMin() == null || project.getBudgetMax() == null) {
            throw new ValidationException("Budget details are required");
        }

        if (project.getBudgetMin().compareTo(BigDecimal.ZERO) <= 0 || project.getBudgetMax().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("Budget must be greater than zero");
        }

        if (project.getBudgetMax().compareTo(project.getBudgetMin()) < 0) {
            throw new ValidationException("Maximum budget cannot be less than minimum budget");
        }

        User client = userDAO.findUser(project.getClientUserID());

        if (client == null) {
            return false;
        }

        if (!"CLIENT".equalsIgnoreCase(client.getRole()) || !"ACTIVE".equalsIgnoreCase(client.getStatus())) {
            return false;
        }

        project.setProjectID(projectDAO.generateProjectID());
        project.setPostedDate(new Date(System.currentTimeMillis()));
        project.setStatus("OPEN");
        project.setAwardedBidID(null);

        return projectDAO.insertProject(project);
    }

    public boolean placeBid(int projectID, String freelancerUserID, BigDecimal bidAmount, int deliveryDays, String coverLetter) throws ValidationException, ClassNotFoundException, SQLException {
        if (projectID <= 0) {
            throw new ValidationException("Invalid project ID");
        }

        if (isEmpty(freelancerUserID)) {
            throw new ValidationException("Freelancer ID is required");
        }

        if (bidAmount == null || bidAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("Bid amount must be greater than zero");
        }

        if (deliveryDays <= 0) {
            throw new ValidationException("Delivery days must be greater than zero");
        }

        Project project = projectDAO.findProject(projectID);

        if (project == null || !"OPEN".equalsIgnoreCase(project.getStatus())) {
            return false;
        }

        User freelancer = userDAO.findUser(freelancerUserID);

        if (freelancer == null) {
            return false;
        }

        if (!"FREELANCER".equalsIgnoreCase(freelancer.getRole()) || !"ACTIVE".equalsIgnoreCase(freelancer.getStatus())) {
            return false;
        }

        Bid oldBid = bidDAO.findActiveBidForProjectAndFreelancer(projectID, freelancerUserID);

        if (oldBid != null) {
            throw new ValidationException("Freelancer has already placed a bid for this project");
        }

        Connection con = null;

        try {
            con = DBUtil.getDBConnection();
            con.setAutoCommit(false);

            Bid bid = new Bid();
            bid.setBidID(bidDAO.generateBidID(con));
            bid.setProjectID(projectID);
            bid.setFreelancerUserID(freelancerUserID);
            bid.setBidAmount(bidAmount);
            bid.setDeliveryDays(deliveryDays);
            bid.setCoverLetter(coverLetter);
            bid.setBidDate(new Date(System.currentTimeMillis()));
            bid.setBidStatus("PENDING");

            boolean result = bidDAO.insertBid(con, bid);

            if (result) {
                con.commit();
            } else {
                con.rollback();
            }

            return result;
        } catch (SQLException e) {
            if (con != null) {
                con.rollback();
            }
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }

    public boolean awardProject(int projectID, int bidID) throws ValidationException, ProjectAwardingException, ClassNotFoundException, SQLException {
        if (projectID <= 0 || bidID <= 0) {
            throw new ValidationException("Invalid project ID or bid ID");
        }

        Project project = projectDAO.findProject(projectID);

        if (project == null || !"OPEN".equalsIgnoreCase(project.getStatus())) {
            throw new ProjectAwardingException("Project is not open for awarding");
        }

        Bid bid = bidDAO.findBid(bidID);

        if (bid == null) {
            throw new ProjectAwardingException("Bid not found");
        }

        if (bid.getProjectID() != projectID) {
            throw new ProjectAwardingException("Bid does not belong to this project");
        }

        if (!"PENDING".equalsIgnoreCase(bid.getBidStatus())) {
            throw new ProjectAwardingException("Only pending bid can be accepted");
        }

        Connection con = null;

        try {
            con = DBUtil.getDBConnection();
            con.setAutoCommit(false);

            boolean accepted = bidDAO.updateBidStatus(con, bidID, "ACCEPTED");
            boolean rejected = bidDAO.bulkRejectOtherBids(con, projectID, bidID);
            boolean awarded = projectDAO.updateAwardedBid(con, projectID, bidID, "AWARDED");

            if (accepted && rejected && awarded) {
                con.commit();
                return true;
            } else {
                con.rollback();
                return false;
            }
        } catch (SQLException e) {
            if (con != null) {
                con.rollback();
            }
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }

    public boolean markProjectCompleted(int projectID) throws ValidationException, ClassNotFoundException, SQLException {
        if (projectID <= 0) {
            throw new ValidationException("Invalid project ID");
        }

        Project project = projectDAO.findProject(projectID);

        if (project == null || !"AWARDED".equalsIgnoreCase(project.getStatus())) {
            return false;
        }

        Connection con = null;

        try {
            con = DBUtil.getDBConnection();
            con.setAutoCommit(false);

            boolean result = projectDAO.updateProjectStatus(con, projectID, "COMPLETED");

            if (result) {
                con.commit();
            } else {
                con.rollback();
            }

            return result;
        } catch (SQLException e) {
            if (con != null) {
                con.rollback();
            }
            throw e;
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }

    public List<Bid> listBidsByProject(int projectID) throws ValidationException, ClassNotFoundException, SQLException {
        if (projectID <= 0) {
            throw new ValidationException("Invalid project ID");
        }
        return bidDAO.findBidsByProject(projectID);
    }

    public List<Bid> listBidsByFreelancer(String freelancerUserID) throws ValidationException, ClassNotFoundException, SQLException {
        if (isEmpty(freelancerUserID)) {
            throw new ValidationException("Freelancer ID is required");
        }
        return bidDAO.findBidsByFreelancer(freelancerUserID);
    }

    public boolean removeUser(String userID) throws ValidationException, ActiveEngagementsExistException, ClassNotFoundException, SQLException {
        if (isEmpty(userID)) {
            throw new ValidationException("User ID is required");
        }

        User user = userDAO.findUser(userID);

        if (user == null) {
            return false;
        }

        if ("CLIENT".equalsIgnoreCase(user.getRole())) {
            List<Project> projects = projectDAO.findActiveProjectsByClient(userID);

            if (projects.size() > 0) {
                throw new ActiveEngagementsExistException("Client has active projects");
            }
        } else if ("FREELANCER".equalsIgnoreCase(user.getRole())) {
            List<Bid> bids = bidDAO.findActiveBidsForFreelancer(userID);

            if (bids.size() > 0) {
                throw new ActiveEngagementsExistException("Freelancer has active bids");
            }
        }

        return userDAO.deleteUser(userID);
    }
}