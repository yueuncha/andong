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
                                <h6 class="m-0 font-weight-bold text-primary">신고 목록</h6>
                            </div>
                            <div class="card-body">
                                <div class="table-responsive">
                                    <select id="userFilter" class="custom-select custom-select-sm form-control form-control-sm col-1"  style="display: inline-block; margin-left: 30px; float: right">
                                        <option value="">전체</option>
                                        <option value="1">회원</option>
                                        <option value="2">게스트</option>
                                    </select>
                                    <table class="table table-bordered" id="dataTable">
                                        <thead>
                                        <tr>
                                            <th>신고유형</th>
                                            <th>신고자</th>
                                            <th>신고글</th>
                                            <th>신고일자</th>
                                        </tr>
                                        </thead>
                                        <tfoot>
                                        <tr>
                                            <th>신고유형</th>
                                            <th>신고자</th>
                                            <th>신고글</th>
                                            <th>신고일자</th>
                                        </tr>
                                        </tfoot>
                                        <tbody id ="userData">
                                            <c:forEach var="item" items="${list}">
                                                <tr onclick="fnReportOne(${item.report_idx})">
                                                    <td>${item.inq_category}</td>
                                                    <td>${item.report_nickname}</td>
                                                    <td>${item.post_title}</td>
                                                    <td>${item.report_date}</td>
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