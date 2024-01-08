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
                                <h6 class="m-0 font-weight-bold text-primary">안동이야기 > 작성</h6>
                            </div>
                            <div class="card-body">
                                <div class="form-group mb-3 font-weight-bold">
                                    <form id="storyWriteForm" >
                                        <div class="form-group mb-3" >
                                            <label for="mainTitle">제목</label>
                                            <input class="form-control mb-2" type="text" id="mainTitle" name="tour_title">
                                        </div>
                                        <div class="form-group mb-3" >
                                            <label for="subTitle">부제목</label>
                                            <input class="form-control mb-2" type="text" id="subTitle" name="tour_subtitle">
                                        </div>
                                        <div class="form-group mb-3" >
                                            <label for="storyTag">스토리 태그</label>
                                            <input class="form-control mb-2" type="text" id="storyTag" name="tour_category">
                                        </div>
                                        <input type="hidden" name="tour_contents" value="">
                                        <%@include file="summerNote.jsp"%>
                                        <hr>
                                        <div style="float: right;">
                                            <a id="andongBtn" class="btn btn-primary btn-icon-split">
                                                <span class="text">저장</span>
                                            </a>
                                        </div>
                                    </form>
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