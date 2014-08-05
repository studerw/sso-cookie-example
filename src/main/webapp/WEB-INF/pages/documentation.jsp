<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <jsp:include page="fragments/head.jsp"/>
    <title>SSO App ${initParam['appName']}</title>
</head>

<body>
<jsp:include page="fragments/navbar-top.jsp"/>
<div class="container">

    <div class="start-template">
        <c:if test="${not empty alerts}">
            <div class="alert alert-warning">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                <p>You currently have ${fn:length(alerts)} alert(s) not yet acknowledged.</p>
            </div>
        </c:if>
        <div class="jumbotron">
            <c:set var="appTitle" value="${initParam['appName']}"/>
            <h2 style="color: ${initParam['appName']};">Single Sign On Documentation</h2>

            <p>TODO...</p>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/pages/fragments/footer.jsp"/>
<script>
    (function ($) {
        $(document).ready(function () {
            $('li#nav-home').addClass('active');
            $(document).ajaxError(function (event, jqxhr, settings, thrownError) {
                if (jqxhr.status === 419){
                    bootbox.alert("You've been logged out of the SSO session from another application. Please close the browser and login again.", function () {
                    });
                }
            });
        });
    })(jQuery);
</script>

</body>
</html>
