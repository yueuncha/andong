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
    <div id="content-wrapper" class="d-flex flex-column">
        <!-- Main Content -->
        <div id="content">

            <div class="card-body" style="margin: 546px">
                <div class="d-sm-flex align-items-center justify-content-between mb-3">
                    <h1 class="h3 mb-0 text-gray-800">안동 스마스 관광 서비스 관리</h1>
                </div>
                <div class="form-group mb-3">
                    <label for="member_id">ID</label>
                    <input class="form-control mb-2" type="text" id="member_id">
                </div>
                <div class="form-group mb-3">
                    <label for="member_pw">PASSWORD</label>
                    <input class="form-control mb-2" type="password" id="member_pw">
                </div>
            </div>
            <!-- content-fruid -->
        </div>
        <!-- main end -->
    </div>
    <!-- content-wrapper end-->
</div>
<!-- wrapper end -->
<%@include file="/resources/common/script.jsp"%>
</body>
</html>