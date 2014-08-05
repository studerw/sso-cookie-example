<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div id="footer">
    <div class="container">
        <c:set var="appTitle" value="${initParam['appName']}" />
        <p class="text-muted text-center">SSO ${fn:toUpperCase(appTitle)} 2014</p>
    </div>
</div>
<script src="${pageContext.request.contextPath}/resources/js/console.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery-1.10.1.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery.blockUI.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootbox.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/app/app.js"></script>
