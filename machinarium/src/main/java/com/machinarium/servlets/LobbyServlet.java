package com.machinarium.servlets;

import com.machinarium.dao.GameDAO;
import com.machinarium.utility.common.ConfiguredLogger;
import com.machinarium.utility.common.ID;
import com.machinarium.utility.common.JSONResponse;
import com.machinarium.utility.common.SessionManager;
import com.machinarium.utility.constants.ServletConstants;
import org.json.simple.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@WebServlet(name = "LobbyServlet", value = "/LobbyServlet")
public class LobbyServlet extends HttpServlet {

    private final static Logger logger = ConfiguredLogger.getLogger("LobbyServlet");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String userName = SessionManager.getLoginUser(request).getUserName();
        ServletContext contextListener = request.getServletContext();

        GameDAO gameDAO = (GameDAO) contextListener.getAttribute(ServletConstants.ATTRIBUTE_GAME_DAO);

        JSONResponse wrappedResponse = new JSONResponse(response);

        ID activeGameID = gameDAO.getActiveGame(userName);

        if(activeGameID == null) {

            wrappedResponse.setError(response.SC_NOT_FOUND, "The user is not participating in a game.");
            return;
        }

        String hostName = gameDAO.getGameHost(activeGameID);
        Function<String, String> getRole = user -> user.equals(hostName) ? ServletConstants.VALUE_HOST : ServletConstants.VALUE_GUEST;

        JSONObject data = new JSONObject();
        data.put(ServletConstants.PARAMETER_STATUS, gameDAO.getGameStage(activeGameID));

        data.put(ServletConstants.PARAMETER_USERS, gameDAO.getGameUsers(activeGameID).stream()
                .map(user ->
                        Map.of(ServletConstants.PARAMETER_USER_NAME, user,
                               ServletConstants.PARAMETER_ROLE, getRole.apply(user))
                ).collect(Collectors.toList()));

        data.put(ServletConstants.PARAMETER_ROLE, getRole.apply(userName));

        wrappedResponse.setResponse(response.SC_OK, data);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        String userName = SessionManager.getLoginUser(request).getUserName();
        ServletContext contextListener = request.getServletContext();

        GameDAO gameDAO = (GameDAO) contextListener.getAttribute(ServletConstants.ATTRIBUTE_GAME_DAO);

        logger.log(Level.INFO, "User(" + userName + ") Requests to join a lobby.");

        // TODO: Change Lobby logic.

        ID activeGameID = gameDAO.getActiveGame(userName);

        if(activeGameID != null) {

            JSONResponse wrappedResponse = new JSONResponse(response);
            wrappedResponse.setError(response.SC_CONFLICT, "The user is already in a game.");
            return;
        }

        synchronized(LobbyServlet.class) {

            List<ID> activeGames = gameDAO.getAllActiveGames();
            if(activeGames.size() == 0) gameDAO.addGame(userName);
            else gameDAO.addUser(activeGames.get(0), userName);
        }

        response.setStatus(response.SC_OK);
    }
}
