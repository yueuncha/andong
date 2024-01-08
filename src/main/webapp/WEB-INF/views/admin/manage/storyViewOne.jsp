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
                                    <hr>
                                    <div style=" width: 100%; height: 500px">
                                        <div style="width: 40% ;display: inline-block; padding: 40px 10px 30px 20px;">
                                            <p class="text-primary">제목</p>
                                            <p>${list.tour_title}</p>
                                            <p class="text-primary">부제목</p>
                                            <p>${list.tour_subtitle}</p>
                                            <p class="text-primary">작성일자</p>
                                            <p>${list.tour_reg_dt}</p>
                                            <p class="text-primary">조회수</p>
                                            <p>${list.tour_view_cnt}</p>
                                        </div>
                                        <div style="text-align: center;display: inline-block; width: 60%;float: right">
                                            <img src="${url}/image/story/${list.tour_image}" style="padding: 20px 10px 0px 20px;"
                                                 width="750" height="450">
                                        </div>

                                    </div>
                                    <hr>

                                    <hr>
                                    <p class="text-primary">내용</p>
                                    <div style="padding: 3px 12px; text-align: center;" id="storyContents">
                                        ${list.tour_contents}
                                    </div>
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