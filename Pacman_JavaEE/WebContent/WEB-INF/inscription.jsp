<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Inscription</title>
<link type="text/css" rel="stylesheet"
	href="<c:url value="/inc/form.css"/>" />
<link type="text/css" rel="stylesheet"
	href="<c:url value="/inc/style.css"/>" />
</head>
<body>
	<c:import url="/inc/menu.jsp"></c:import>
	<form method="post" action="inscription">
		<fieldset>
			<legend>Inscription</legend>
			<p>Vous pouvez vous inscrire via ce formulaire.</p>

			<label for="email">Adresse email <span class="requis">*</span></label>
			<input type="email" id="email" name="email"
				value="<c:out value="${utilisateur.email}"/>" size="20"
				maxlength="60" /> <span class="erreur">${form.erreurs['email']}</span>
			<br /> <label for="pseudo">Pseudo <span class="requis">*</span></label>
			<input type="text" id="nom" name="pseudo"
				value="<c:out value="${utilisateur.pseudo}"/>" size="20"
				maxlength="20" /> <span class="erreur">${form.erreurs['pseudo']}</span>
			<br /> <label for="motdepasse">Mot de passe <span
				class="requis">*</span></label> <input type="password" id="motdepasse"
				name="motdepasse" value="" size="20" maxlength="20" /> <span
				class="erreur">${form.erreurs['motdepasse']}</span> <br /> <label
				for="confirmation">Confirmation du mot de passe <span
				class="requis">*</span></label> <input type="password" id="confirmation"
				name="confirmation" value="" size="20" maxlength="20" /> <span
				class="erreur">${form.erreurs['confirmation']}</span> <br /> <input
				type="submit" value="Inscription" class="sansLabel" /> <br />

			<p class="${empty form.erreurs ? 'succes' : 'erreur'}">${form.resultat}
			<br />
				<a href="<c:url value="/home"/>">Retourner à l'accueil</a>
			</p>
		</fieldset>
	</form>
</body>
</html>