package com.machinarium.servlets;

import com.machinarium.dao.GameDAO;
import com.machinarium.dao.ItemDAO;
import com.machinarium.model.Item.Item;
import com.machinarium.model.user.User;
import com.machinarium.utility.common.ID;
import com.machinarium.utility.common.JSONResponse;
import com.machinarium.utility.common.SessionManager;
import com.machinarium.utility.constants.ServletConstants;
import org.json.simple.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(name = "GameServlet", value = "/GameServlet")
public class GameServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ServletContext contextListener = request.getServletContext();
        GameDAO gameDAO = (GameDAO) contextListener.getAttribute(ServletConstants.ATTRIBUTE_GAME_DAO);
        ItemDAO itemDAO = (ItemDAO) contextListener.getAttribute(ServletConstants.ATTRIBUTE_ITEM_DAO);

        JSONResponse wrappedResponse = new JSONResponse(response);

        ID gameID = SessionManager.getActiveGameID(request);

        JSONObject data = new JSONObject();
        data.put(ServletConstants.PARAMETER_USERS, gameDAO.getGameUsers(gameID).stream()
                .map(userName -> Map.of(ServletConstants.PARAMETER_USER_NAME, userName)).collect(Collectors.toList()));
        data.put(ServletConstants.PARAMETER_ITEMS, itemDAO.getAllItems().stream()
                .map(Item::toJSONMap).collect(Collectors.toList()));

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
        }

        gameDAO.updateGameStage(activeGameID, GameDAO.ACTIVE);

        response.setStatus(response.SC_OK);
    }
}
