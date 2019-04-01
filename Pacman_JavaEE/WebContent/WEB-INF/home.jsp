<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Accueil</title>
        <link type="text/css" rel="stylesheet" href="<c:url value="/inc/style.css"></c:url>" />
    </head>
    <body onload="sortTable()">
        <c:import url="/inc/menu.jsp" ></c:import>
        <script src="<c:url value="/inc/script.js"></c:url>" ></script>
        <div id="corps">
        <c:choose>
            <%-- Si aucune Score n'existe en session, affichage d'un message par défaut. --%>
            <c:when test="${ empty sessionScope.scores }">
                <p class="erreur">Aucun score enregistré.</p>
            </c:when>
            <%-- Sinon, affichage du tableau. --%>
            <c:otherwise>
            
            <h1>Classement Général</h1>
            <table id="myTable">
                <tr>
                    <th>Rang</th>
                    <th>Pseudo</th>
                    <th>Points</th>
                    <th class="action">Action</th>                    
                </tr>
                <%-- Parcours de la Map des Scores en session, et utilisation de l'objet varStatus. --%>
                <c:forEach items="${ sessionScope.scores }" var="mapScores" varStatus="boucle">
                <%-- Simple test de parité sur l'index de parcours, pour alterner la couleur de fond de chaque ligne du tableau. --%>
                <tr class="${boucle.index % 2 == 0 ? 'pair' : 'impair'}">
                    <%-- Affichage des propriétés du bean Score, qui est stocké en tant que valeur de l'entrée courante de la map --%>
                    <td><c:out value="${mapScores.value.rang }"/></td>
                    <td><c:out value="${ mapScores.value.pseudo }"/></td>
                    <td><c:out value="${ mapScores.value.score }"/></td>
                   
                    <%-- Lien vers la servlet de suppression, avec passage de la date de la Score - c'est-à-dire la clé de la Map - en paramètre grâce à la balise <c:param></c:param>. --%>
                    <td class="action">
                        <a href="<c:url value="/profilPublic"><c:param name="idScore" value="${ mapScores.key }" ></c:param></c:url>">
                            Voir
                        </a>
                    </td>
                </tr>
                </c:forEach>
            </table>
            </c:otherwise>
        </c:choose>
        </div>
        
    </body>
</html>