<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<script type="text/javascript">
    var SERVLET_CONTEXT = '${pageContext.request.contextPath}';
</script>

<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar">hello</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <c:set var="appName" value="${initParam['appName']}" />
            <a class="navbar-brand brand" style="color: ${appName};" href="${pageContext.request.contextPath}/index">SSO ${fn:toUpperCase(appName)}</a>
        </div>
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <c:set var="appName1" value="${initParam['appName1']}" />
                <li id="nav-docs"><a href="/${appName1}" target="_blank">${fn:toUpperCase(appName1)}</a></li>
                <li id="nav-users"><a href="${pageContext.request.contextPath}/documentation">Documentation</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown" id="nav-alerts">
                    <a class="dropdown-toggle" data-toggle="dropdown"
                       href="${pageContext.request.contextPath}/alerts.htm">Alerts
                        <span id="alertCount" class="badge">${fn:length(alerts)}</span><b class="caret"></b></a>
                    <ul id="alertMenuGroup" class="dropdown-menu list-group alert-dropdown scrollable-menu">
                        <c:choose>
                            <c:when test="${empty alerts}">
                                <li class="list-group-item">
                                    <div class="alert alert-info alert-dropdown-div">You have no alerts</div>
                                </li>
                            </c:when>
                            <c:when test="${not empty alerts}">
                                <c:forEach var="alert" items="${alerts}">
                                    <c:choose>
                                        <c:when test="${alert.priority == 1}">
                                            <c:set var="alertClass" value="alert-success"/>
                                        </c:when>
                                        <c:when test="${alert.priority == 2}">
                                            <c:set var="alertClass" value="alert-info"/>
                                        </c:when>
                                        <c:when test="${alert.priority == 3}">
                                            <c:set var="alertClass" value="alert-warning"/>
                                        </c:when>
                                        <c:when test="${alert.priority == 4}">
                                            <c:set var="alertClass" value="alert-danger"/>
                                        </c:when>
                                    </c:choose>
                                    <li class="alert list-group-item alert-dismissable" data-id="${alert.id}">
                                        <div class="alert-dropdown-div ${alertClass}">
                                            <button type="button" class="close" style="margin: 20px;" data-dismiss="alert" aria-hidden="true">&times;</button>
                                            <p class="list-group-item-text alert-date">Created: <strong><fmt:formatDate value="${alert.createdDate.time}" type="date" dateStyle="short" timeStyle="short" /></strong></p><br/>
                                            <p class="list-group-item-text">From: <strong>${alert.createdBy}</strong></p><br/>
                                            <p class="list-group-item-text">${alert.message}</p>
                                            <br/>
                                        </div>
                                    </li>
                                </c:forEach>
                            </c:when>
                        </c:choose>

                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">${userId} <b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li><a href="${pageContext.request.contextPath}/logout">Log Out</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</div>
