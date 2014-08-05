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
            <h2 style="color: ${initParam['appName']};">Single Sign On ${fn:toUpperCase(appTitle)} App</h2>

            <p>IP address: ${pageContext.request.remoteAddr}</p>
            <%--<p>The application uses the--%>
            <%--<a href="https://git.lab76.org/chrome-developers/chrome-common-services/wikis/alert-api">Chrome Alert--%>
            <%--API</a>--%>
            <%--</p>--%>

            <p><a class="btn btn-lg btn-success" id="ajaxPing" role="button">Ajax Ping</a></p>
        </div>

        <div class="panel panel-default">
            <div class="panel-heading">Add Session Attribute</div>
            <div class="panel-body">
                <form class="form-horizontal" role="form" action="index" method="post">
                    <div class="form-group form-group-sm">
                        <label class="col-sm-2 control-label" for="sessionName">name</label>

                        <div class="col-sm-10">
                            <input class="form-control" type="text" name="sessionName" id="sessionName"
                                   placeholder="session name" required autofocus="true">
                        </div>
                    </div>
                    <div class="form-group form-group-sm">
                        <label class="col-sm-2 control-label" for="sessionVal">value</label>

                        <div class="col-sm-10">
                            <input class="form-control" type="text" name="sessionVal" id="sessionVal"
                                   placeholder="Session value" required>
                        </div>
                    </div>
                    <button class="btn btn-primary btn-default pull-right" name="submit" type="submit">Add Attribute</button>
                </form>
            </div>
        </div>

        <div class="alert alert-info">
            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
            <h3>Session Variables: ${fn:length(sessionScope)}</h3>
            <hr/>
            <div>
                <c:forEach items="${sessionScope}" var="item">
                    <p>
                    <h4>${item.key}</h4>
                    Value: ${item.value}<br/>
                    </p>
                    <hr/>
                </c:forEach>
            </div>
        </div>

        <div class="alert alert-info">
            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
            <h3>Cookies: ${numCookies}</h3>
            <hr/>
            <div>
                <c:forEach var="cook" items="${myCookies}">
                    <p>
                    <h4>${cook.name}</h4>
                    Value: ${cook.value}<br/>
                    </p>
                    <hr/>
                </c:forEach>
            </div>
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
            $('#ajaxPing').on("click", function (event) {
                $.ajax({
                    type: 'GET',
                    dataType: 'json',
                    url: SERVLET_CONTEXT + '/ajax',
                    headers: {
                        Accept: "application/json"
                    },
                    success: function (data) {
                        bootbox.alert(data.message, function () {
                        });
                    },
                    error: function (error) {
                        bootbox.alert("Error", function () {
                        });
                    }
                });
            });
        });
    })(jQuery);
</script>

</body>
</html>
