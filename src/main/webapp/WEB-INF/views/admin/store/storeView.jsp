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

                <div class="row">
                    <div class="col-xl-12 col-lg-12">
                        <!-- DataTales Example -->
                        <div class="card shadow mb-4">
                            <div class="card-header py-3">
                                <h6 class="m-0 font-weight-bold text-primary">
                                    <c:choose>
                                        <c:when test="${type eq 10}">
                                            관광지
                                        </c:when>
                                        <c:when test="${type eq 11}">
                                            맛집
                                        </c:when>
                                        <c:when test="${type eq 5}">
                                            숙박
                                        </c:when>
                                        <c:otherwise>
                                            체험
                                        </c:otherwise>
                                    </c:choose> 목록 조회
                                </h6>
                            </div>
                            <div class="card-body">
                                <div class="table-responsive">
                                    <table class="table table-bordered" id="dataTable">
                                        <thead>
                                        <tr>
                                            <th>상호명</th>
                                            <th>주소</th>
                                        </tr>
                                        </thead>
                                        <tfoot>
                                        <tr>
                                            <th>상호명</th>
                                            <th>주소</th>
                                        </tr>
                                        </tfoot>
                                        <tbody>
                                        <c:forEach var="item" items="${list}">
                                            <tr onclick="location.href='/admin/store/view?str_idx='+${item.str_idx}">
                                                <td>${item.str_dt_name}</td>
                                                <td>${item.str_dt_address}</td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
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