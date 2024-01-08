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
                                <h6 class="m-0 font-weight-bold text-primary">1:1 문의 > 자세히 보기</h6>
                            </div>
                            <div class="card-body">
                                <div class="form-group mb-3 font-weight-bold">
                                    <p class="text-primary">${list.iq_status_text}</p>
                                    <hr>
                                    <p>${list.iq_title}</p>
                                    <hr>
                                    <p>작성자 : ${list.mb_nickname}</p>
                                    <p>작성일자 : ${list.iq_regdate}</p>
                                    <hr>
                                    <p>${list.iq_content}</p>
                                    <hr>
                                </div>
                                <form id="inquiryForm" action="/admin/inquiry/write" method="post">
                                    <div class="form-group" >
                                        <label for="exampleFormControlTextarea1">답변하기</label>
                                        <input type="hidden" value="${list.iq_idx}" name="iq_idx"/>
                                        <textarea class="form-control" name="inquiryAnswer" id="exampleFormControlTextarea1" 
                                                  rows="10" >${list.answer_content}</textarea>

                                    </div>
                                    <div style="float: right;">
                                        <a id="inquiryBtn" class="btn btn-primary btn-icon-split" onclick="fnInquiryWrite()">
                                            <span class="text">저장</span>
                                        </a>
                                    </div>
                                </form>
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