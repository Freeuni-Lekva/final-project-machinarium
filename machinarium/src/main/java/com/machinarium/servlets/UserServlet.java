package com.machinarium.servlets;

import com.machinarium.dao.GarageDAO;
import com.machinarium.dao.StatisticsDAO;
import com.machinarium.model.history.Statistics;
import com.machinarium.model.user.User;
import com.machinarium.utility.common.ConfiguredLogger;
import com.machinarium.utility.common.JSONResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.machinarium.utility.constants.ServletConstants.*;

@WebServlet(name = "UserServlet", value = "/UserServlet")
public class UserServlet extends HttpServlet {

    private final static Logger logger = ConfiguredLogger.getLogger("UserServlet");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User user = (User) request.getSession().getAttribute(ATTRIBUTE_USER);
        assert user != null;

        StatisticsDAO statisticsDAO = (StatisticsDAO) request.getServletContext().getAttribute(ATTRIBUTE_STATISTICS_DAO);
        Statistics userStatistics = statisticsDAO.getStatistics(user.getUserName());

        logger.log(Level.FINE, "Statistics for user: " + user.getUserName() + "\n" + userStatistics);

        JSONResponse wrappedResponse = new JSONResponse(response);

        JSONObject data = new JSONObject();
        data.put(PARAMETER_USER_NAME, user.getUserName());
        data.put(PARAMETER_EMAIL, user.getEmail().toString());

        data.put(PARAMETER_FIRST_PLACE_COUNT, userStatistics.getFirstCount());
        data.put(PARAMETER_SECOND_PLACE_COUNT, userStatistics.getSecondCount());
        data.put(PARAMETER_THIRD_PLACE_COUNT, userStatistics.getThirdCount());
        data.put(PARAMETER_LOSS_COUNT, userStatistics.getLoseCount());

        wrappedResponse.setBody(data);
    }
}
