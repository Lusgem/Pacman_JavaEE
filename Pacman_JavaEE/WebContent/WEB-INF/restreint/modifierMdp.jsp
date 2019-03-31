<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Modification De Mot De Passe</title>
        <link type="text/css" rel="stylesheet" href="<c:url value="/inc/form.css"/>" />
    </head>
    <body>
    	<c:import url="/inc/menu.jsp" ></c:import>
        <form method="post" action="<c:url value="/modifier" />">
            <fieldset>
                
                <label for="ancienmdp">Ancien mot de passe <span class="requis">*</span></label>
                <input type="password" id="ancienmdp" name="ancienmdp" value="" size="20" maxlength="20" />
                <span class="erreur">${form.erreurs['ancienmdp']}</span>
                <br />
                <label for="nouveaumdp">Nouveau mot de passe <span class="requis">*</span></label>
                <input type="password" id="nouveaumdp" name="nouveaumdp" value="" size="20" maxlength="20" />
                <span class="erreur">${form.erreurs['nouveaumdp']}</span>
                <br />
                <label for="nouveaumdpconf">Confirmation du mot de passe <span class="requis">*</span></label>
                <input type="password" id="nouveaumdpconf" name="nouveaumdpconf" value="" size="20" maxlength="20" />
                <span class="erreur">${form.erreurs['nouveaumdpconf']}</span>
                <br />
                
                <input type="submit" value="Modifier" class="sansLabel" />
                <br />
                
                <p class="${empty form.erreurs ? 'succes' : 'erreur'}">${form.resultat}</p>
               
            </fieldset>
        </form>
    </body>
</html>