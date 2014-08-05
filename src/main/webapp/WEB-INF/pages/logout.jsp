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
<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <c:set var="appTitle" value="${initParam['appName']}" />
            <a class="navbar-brand brand" href="${pageContext.request.contextPath}/index">${fn:toUpperCase(appTitle)}</a>
        </div>
    </div>
</div>

<div class="container">

    <div class="start-template">
        <div class="jumbotron">
            <c:set var="appTitle" value="${initParam['appName']}" />
            <h2 style="color: ${initParam['appName']};">SSO ${fn:toUpperCase(appTitle)} App</h2>
            <p>You have been successfully logged out. </p>
            <p>Please close your browser to fully logout of all the SSO applications.</p>

        </div>
    </div>

</div>

<jsp:include page="/WEB-INF/pages/fragments/footer.jsp"/>
<script>
    (function ($) {
        $(document).ready(function () {
            $('li#nav-home').addClass('active');
        });
    })(jQuery);
</script>

</body>
</html>
