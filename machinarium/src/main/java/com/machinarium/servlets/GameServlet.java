package com.machinarium.servlets;

import com.machinarium.dao.GameDAO;
import com.machinarium.dao.GarageDAO;
import com.machinarium.dao.ItemDAO;
import com.machinarium.model.Item.Item;
import com.machinarium.model.user.User;
import com.machinarium.utility.common.*;
import com.machinarium.utility.constants.ServletConstants;
import org.json.simple.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@WebServlet(name = "GameServlet", value = "/GameServlet")
public class GameServlet extends HttpServlet {

    private final static Logger logger = ConfiguredLogger.getLogger("GarageServlet");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        ServletContext contextListener = request.getServletContext();
        GameDAO gameDAO = (GameDAO) contextListener.getAttribute(ServletConstants.ATTRIBUTE_GAME_DAO);
        ItemDAO itemDAO = (ItemDAO) contextListener.getAttribute(ServletConstants.ATTRIBUTE_ITEM_DAO);

        JSONResponse wrappedResponse = new JSONResponse(response);

        ID gameID = SessionManager.getActiveGameID(request);

        logger.log(Level.INFO, "Game information requested by user(" + SessionManager.getLoginUser(request).getUserName() + ").");

        JSONObject data = new JSONObject();
        data.put(ServletConstants.PARAMETER_USERS, gameDAO.getGameUsers(gameID).stream()
                .map(userName -> Map.of(ServletConstants.PARAMETER_USER_NAME, userName)).collect(Collectors.toList()));
        data.put(ServletConstants.PARAMETER_ITEMS, itemDAO.getAllItems().stream()
                .map(Item::toJSONMap).collect(Collectors.toList()));
        data.put(ServletConstants.PARAMETER_STATUS, gameDAO.getGameStage(gameID).toString());

        wrappedResponse.setResponse(response.SC_OK, data);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        String userName = SessionManager.getLoginUser(request).getUserName();

        ServletContext contextListener = request.getServletContext();
        GameDAO gameDAO = (GameDAO) contextListener.getAttribute(ServletConstants.ATTRIBUTE_GAME_DAO);

        JSONResponse wrappedResponse = new JSONResponse(response);

        ID activeGameID = gameDAO.getActiveGame(userName);

        if(!userName.equals(gameDAO.getGameHost(activeGameID))) {
            wrappedResponse.setError(response.SC_PRECONDITION_FAILED, "This user is not the host for the current game.");
            return;
        } else if (gameDAO.getGameStage(activeGameID).equals(GameDAO.GameStage.ACTIVE)) {
            wrappedResponse.setError(response.SC_CONFLICT, "This game has already been started.");
            return;
        }

        logger.log(Level.INFO, "Received request to start the game with ID: " + activeGameID);

        if(!gameDAO.updateGameStage(activeGameID, GameDAO.GameStage.ACTIVE)) {
            wrappedResponse.setError(response.SC_CONFLICT, "Couldn't update the game stage to: " + GameDAO.GameStage.ACTIVE);
        } else {

            ItemDistributor.distributeStartingItems(gameDAO.getGameUsers(activeGameID), request);
            response.setStatus(response.SC_OK);
        }
    }
}
