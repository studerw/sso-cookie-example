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
            <a class="navbar-brand brand"
               href="${pageContext.request.contextPath}/index">Single Sign On</a>
        </div>
    </div>
</div>

<div class="container">
    <div class="start-template" style="width: 500px; margin: 10px auto;">
        <c:if test="${error == true}">
            <div class="errorblock">
                <p>Your login attempt was not successful.</p>

                <p> Cause: ${errorMsg}</p>
            </div>
        </c:if>
        <c:if test="${not empty msg}">
            <div class="flash">
                <p><strong>${msg}</strong></p>
            </div>
        </c:if>

        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">SSO Login</h3>
            </div>
            <div class="panel-body">
                <form style="margin: 20px;" class="form-horizontal" role="form" action="login" method="post">
                    <div class="form-group">
                        <label for="j_username" class="col-sm-2 control-label">User</label>

                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="j_username" name="j_username" required
                                   placeholder="User Name" autofocus>
                        </div>
                    </div>
                    <%----%>
                    <%--<div class="form-group">--%>
                        <%--<label for="simulateNewUser" class="col-sm-2 control-label">Simulate Unknown SSO User</label>--%>

                        <%--<div class="col-sm-10">--%>
                            <%--<input type="checkbox" class="form-control" id="simulateNewUser" name="simulateNewUser"/>--%>
                        <%--</div>--%>
                    <%--</div>--%>

                    <button class="btn btn-primary btn-default pull-right" name="submit" type="submit">Sign in</button>
                </form>
            </div>

        </div>
        <p>Use any user name - this would be replaced by the environment security provider in the real world.</p>
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
