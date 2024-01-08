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
                                <h6 class="m-0 font-weight-bold text-primary">알림 발송 이력</h6>
                            </div>
                            <div class="card-body">
                                <div class="form-group">
                                    <label for="exampleFormControlSelect1">알림 대상자</label>
                                    <select class="form-control" id="exampleFormControlSelect1">
                                        <option>전체</option>
                                        <option>10대</option>
                                        <option>20대</option>
                                        <option>30대</option>
                                        <option>40대</option>
                                        <option>50대</option>
                                    </select>
                                </div>
                                <div class="form-group" >
                                    <label for="exampleFormControlTextarea1">내용</label>
                                    <textarea class="form-control" id="exampleFormControlTextarea1" rows="10"></textarea>
                                </div>
                                <div style="float: right;">
                                    <a class="btn btn-primary btn-icon-split">
                                        <span class="text">발송</span>
                                    </a>
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