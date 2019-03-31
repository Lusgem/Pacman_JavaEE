<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Mon Compte</title>
<link type="text/css" rel="stylesheet"
	href="<c:url value="/inc/style.css"/>" />
</head>
<body onload="sortTable()">
	<c:import url="/inc/menu.jsp"></c:import>
	<div id="corps">

		<c:if test="${!empty sessionScope.sessionUtilisateur}">
			<%-- Si l'utilisateur existe en session, alors on affiche son adresse email. --%>
			<p>Bienvenue ${sessionScope.sessionUtilisateur.pseudo} !</p>
			<p>Votre adresse mail est :
				${sessionScope.sessionUtilisateur.email} !</p>
			<p>Vous êtes inscris depuis le :
				${sessionScope.sessionUtilisateur.dateInscription}</p>
			<a href="<c:url value="/compte/modifier"/>">Modifier mon mot de passe</a>

		</c:if>

		<c:choose>
			<%-- Si aucune Score n'existe en session, affichage d'un message par défaut. --%>
			<c:when test="${ empty requestScope.scores }">
				<p class="erreur">Aucun score enregistré.</p>
			</c:when>
			<%-- Sinon, affichage du tableau. --%>
			<c:otherwise>
				<h1>Vos scores</h1>
				<table id="myTable">
					<tr>
						<th>Rang</th>
						<th>Pseudo</th>
						<th>Points</th>
					</tr>
					<%-- Parcours de la Map des Scores en session, et utilisation de l'objet varStatus. --%>
					<c:forEach items="${ requestScope.scores }" var="mapScores"
						varStatus="boucle">
						<%-- Simple test de parité sur l'index de parcours, pour alterner la couleur de fond de chaque ligne du tableau. --%>
						<tr class="${boucle.index % 2 == 0 ? 'pair' : 'impair'}">
							<%-- Affichage des propriétés du bean Score, qui est stocké en tant que valeur de l'entrée courante de la map --%>
							<td><c:out value="${mapScores.value.rang }" /></td>
							<td><c:out value="${ mapScores.value.pseudo }" /></td>
							<td><c:out value="${ mapScores.value.score }" /></td>

						</tr>
					</c:forEach>
				</table>
			</c:otherwise>
		</c:choose>
	</div>

	<script>
        function sortTable() {
        	  var table, rows, switching, i, x, y, shouldSwitch;
        	  table = document.getElementById("myTable");
        	  switching = true;
        	  /* Make a loop that will continue until
        	  no switching has been done: */
        	  while (switching) {
        	    // Start by saying: no switching is done:
        	    switching = false;
        	    rows = table.rows;
        	    /* Loop through all table rows (except the
        	    first, which contains table headers): */
        	    for (i = 1; i < (rows.length - 1); i++) {
        	      // Start by saying there should be no switching:
        	      shouldSwitch = false;
        	      /* Get the two elements you want to compare,
        	      one from current row and one from the next: */
        	      x = rows[i].getElementsByTagName("TD")[0];
        	      y = rows[i + 1].getElementsByTagName("TD")[0];
        	      // Check if the two rows should switch place:
        	      
        	      if (Number(x.innerHTML) > Number(y.innerHTML)) {
        	        // If so, mark as a switch and break the loop:
        	        shouldSwitch = true;
        	        break;
        	      }
        	    }
        	    if (shouldSwitch) {
        	      /* If a switch has been marked, make the switch
        	      and mark that a switch has been done: */
        	      rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
        	      switching = true;
        	    }
        	  }
        	}
        </script>
</body>
</html>