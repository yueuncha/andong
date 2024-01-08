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
                                <h6 class="m-0 font-weight-bold text-primary">1:1 문의</h6>
                            </div>
                            <div class="card-body">
                                <div class="table-responsive">
                                    <table class="table table-bordered" id="dataTable" data-list="${list}">
                                        <thead>
                                        <tr>
                                            <th>답변여부</th>
                                            <th>카테고리</th>
                                            <th>제목</th>
                                            <th>작성자</th>
                                            <th>작성일자</th>
                                        </tr>
                                        </thead>
                                        <tfoot>
                                        <tr>
                                            <th>답변여부</th>
                                            <th>카테고리</th>
                                            <th>제목</th>
                                            <th>작성자</th>
                                            <th>작성일자</th>
                                        </tr>
                                        </tfoot>
                                        <tbody id="inquiryData">
                                        <c:forEach var="item" items="${list}">
                                            <tr onclick="fnInquiryOne(${item.iq_idx})">
                                                <c:choose>
                                                    <c:when test="${item.iq_status eq 'A'}">
                                                        <td class="text-primary">${item.iq_status_text}</td>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <td>${item.iq_status_text}</td>
                                                    </c:otherwise>
                                                </c:choose>
                                                <td>${item.inq_category}</td>
                                                <td>${item.iq_title}</td>
                                                <td>${item.mb_nickname}</td>
                                                <td>${item.iq_regdate}</td>
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