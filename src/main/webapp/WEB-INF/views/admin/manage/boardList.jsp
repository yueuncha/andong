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
                                <h6 class="m-0 font-weight-bold text-primary">여행 피드</h6>
                            </div>
                            <div class="card-body">
                                <div class="table-responsive">
                                    <table class="table table-bordered" id="dataTable">
                                        <thead>
                                        <tr>
                                            <th>작성자</th>
                                            <th>제목</th>
                                            <th>내용</th>
                                            <th>작성일시</th>
                                            <th>수정일시</th>
                                        </tr>
                                        </thead>
                                        <tfoot>
                                        <tr>
                                            <th>작성자</th>
                                            <th>제목</th>
                                            <th>내용</th>
                                            <th>작성일시</th>
                                            <th>수정일시</th>
                                        </tr>
                                        </tfoot>
                                        <tbody>
                                        <c:forEach var="item" items="${list}">
                                            <tr>
                                                <td>${item.mb_nickname}</td>
                                                <td>${item.post_title}</td>
                                                <td style="overflow:hidden;white-space:nowrap;text-overflow:ellipsis;">${item.post_content}</td>
                                                <td>${item.post_reg_date}</td>
                                                <td>${item.post_update}</td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
<%--                                <div style="float: right;">--%>
<%--                                    <a class="btn btn-primary btn-icon-split" onclick="location.href='/admin/story/write'">--%>
<%--                                        <span class="text">작성</span>--%>
<%--                                    </a>--%>
<%--                                </div>--%>
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