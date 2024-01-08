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
                                <h6 class="m-0 font-weight-bold text-primary">스탬프</h6>
                            </div>
                            <div class="card-body">
                                <div class="table-responsive">
                                    <table class="table table-bordered" id="dataTable" style="table-layout:fixed">
                                        <thead>
                                        <tr>
                                            <th>스탬프명</th>
                                            <th>사용여부</th>
                                            <th>경품수령가능</th>
                                            <th>설명</th>
                                        </tr>
                                        </thead>
                                        <tfoot>
                                        <tr>
                                            <th>스탬프명</th>
                                            <th>사용여부</th>
                                            <th>경품수령여부</th>
                                            <th>설명</th>
                                        </tr>
                                        </tfoot>
                                        <tbody>
                                        <c:forEach var="item" items="${list}">
                                            <tr class="userData">
                                                <td>${item.stp_nm}</td>
                                                <td>${item.stp_use}</td>
                                                <td><c:choose>
                                                    <c:when test="${item.stp_give eq 'Y'}">가능</c:when>
                                                    <c:otherwise>불가능</c:otherwise>
                                                </c:choose></td>
                                                <td style="overflow:hidden;white-space:nowrap;text-overflow:ellipsis;">${item.stp_contents}</td>
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