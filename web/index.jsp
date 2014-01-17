<%@ page import="Search.Searcher" %>
<%--
  Created by IntelliJ IDEA.
  User: amit
  Date: 17/01/14
  Time: 14:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <script src="static\jquery-1.10.2.min.js"></script>
        <script src="static\search.js"></script>
    </head>
<body>
    <div id="search-container">
        <div class="search-title">Search:</div>
        <span>Player: </span>
        <select class="player">
            <option>Phil Ivey</option>
            <option>Shmulikipod</option>
        </select>
        <span>Hand: </span>
        <input type="text" class="hand"></div>
        <button type="button" class="search-button">Go!</button>
    </div>
</body>
</html>