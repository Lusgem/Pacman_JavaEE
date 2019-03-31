<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="menu">
	<p>
		<a href="<c:url value="/home"/>">Accueil</a>
	</p>
	<p>
		<a href="<c:url value="/compte/utilisateur"/>">Mon Compte</a>
	</p>
	<c:choose>
		<c:when test="${!empty sessionScope.sessionUtilisateur}">
			<p>
				<a href="<c:url value="/deconnexion"/>">Deconnexion</a>
			</p>
		</c:when>
		<c:otherwise>
			<p>
				<a href="<c:url value="/connexion"/>">Connexion</a>
			</p>
			<p>
				<a href="<c:url value="/inscription"/>">Inscription</a>
			</p>
		</c:otherwise>
	</c:choose>
</div>