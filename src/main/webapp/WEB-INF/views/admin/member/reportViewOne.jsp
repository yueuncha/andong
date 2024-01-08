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
                                <h6 class="m-0 font-weight-bold text-primary">신고 > 상세보기</h6>
                            </div>
                            <div class="card-body">
                                <div class="form-group mb-3 font-weight-bold">
                                    <p class="text-primary">신고유형</p>
                                    <p>${list.inq_category}</p>
                                    <p class="text-primary">신고일자</p>
                                    <p>${list.report_date}</p>
                                    <p class="text-primary">신고자</p>
                                    <p>${list.report_nickname}</p>
                                    <p class="text-primary">신고내용</p>
                                    <p>${list.report_content}</p>
                                    <hr>
                                    <p class="text-primary">신고 피드</p>
                                    <p>${list.post_nickname}</p>
                                    <p>${list.post_title}</p>
                                    <p>${list.post_content}</p>
                                    <hr>
                                </div>
                                    <div style="float: right;">
                                        <a class="btn btn-primary btn-icon-split" onclick="history.back()">
                                            <span class="text">확인</span>
                                        </a>
                                    </div>
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