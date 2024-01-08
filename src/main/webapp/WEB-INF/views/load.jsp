<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="ko">
<%@include file="/resources/common/header.jsp"%>
<body id="page-top">
<!-- Page Wrapper -->
<div id="wrapper">
    <!-- sidebar -->
    <%@include file="/resources/common/sidebar.jsp"%>
    <div id="content-wrapper" class="d-flex flex-column">
        <!-- Main Content -->
        <div id="content">
            <%@include file="/resources/common/navbar.jsp"%>
            <!-- Begin Page Content -->
            <div class="container-fluid">
                <!-- 404 Error Text -->
                <div class="text-center">
                    <div class="error mx-auto"></div>
                    <p class="lead text-gray-800 mb-5">LOAD</p>
                    <input type="hidden" value="${url}" id="loadUrl" />
                    <script>
                        document.addEventListener("DOMContentLoaded", function(){
                            location.href = document.getElementById("loadUrl").value
                        });
                    </script>
                    <!-- <p class="text-gray-500 mb-0">It looks like you found a glitch in the matrix...</p> -->
                    <a href="/">&larr; 뒤로가기</a>
                </div>

            </div>
            <!-- /.container-fluid -->
        </div>
        <!-- main end -->
    </div>
    <!-- content-wrapper end-->
</div>
<!-- wrapper end -->
<%@include file="/resources/common/script.jsp"%>
</body>
</html>