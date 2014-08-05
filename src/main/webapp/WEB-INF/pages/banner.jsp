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
            <h2 style="color: ${initParam['appName']};">SSO Terms</h2>
            <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent vel eros consectetur, pellentesque ipsum non, iaculis eros. Cras porta laoreet euismod. Donec convallis arcu elit; at egestas mi tempus non. Donec consequat egestas laoreet. Fusce eu molestie quam. Suspendisse dolor ante, molestie a vehicula faucibus; tristique at lacus. Sed placerat augue mauris, vitae tempus nulla tincidunt in. Ut viverra dui ac lorem tempus ultrices! Praesent scelerisque velit a lobortis lobortis. Donec id ligula ipsum. Pellentesque pretium justo erat, at hendrerit ipsum rhoncus nec? Aenean volutpat erat non enim aliquet, at ornare elit viverra. Cras suscipit sapien nec mattis viverra.</p>
            <p>Nulla neque mi, hendrerit vitae ultricies nec; feugiat dapibus dui. Praesent laoreet urna a purus aliquam, elementum pharetra justo tincidunt? Donec commodo elit sem, quis tempor elit accumsan vitae? Pellentesque ac lacus est? Integer convallis eu ligula ac posuere. Aenean vestibulum malesuada varius. Ut consectetur consequat sapien nec cursus. Donec mattis tortor nibh, id scelerisque velit commodo eu. Proin iaculis metus in mauris tristique, vitae porttitor tortor tincidunt. Nunc sed tincidunt justo. Ut euismod feugiat euismod. Maecenas suscipit ultricies lacus, vitae tempor massa ultrices ultrices? Donec ut rhoncus tellus! Sed sed libero a arcu sodales fringilla. Aenean bibendum mollis condimentum!</p>

            <form name="terms" action="${pageContext.request.contextPath}/banner" method="post" role="form">
                <div class="checkbox">
                    <label>
                        <input type="checkbox" name="accept" id="accept">Accept
                    </label>
                </div>
                <button type="submit" class="btn btn-primary btn-large">Submit</button>
            </form>

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
