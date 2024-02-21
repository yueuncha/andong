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
                                <h6 class="m-0 font-weight-bold text-primary">관광지/업체  > 상세보기</h6>
                            </div>
                            <div class="card-body" id="storeView">
                                <div class="form-group mb-3 font-weight-bold" >
                                    <c:forEach var="item" items="${list}" varStatus="status" >
                                        <div style="display: inline-block" onclick="fnLangChange(${status.index},'${item.str_dt_type}', 0)">
                                            <input type="hidden" value="${status.index}" name="list_idx"/>
                                            <input type="hidden" value="${item.str_dt_type}"  name="store_type"/>
                                            <div class="text-primary shadow " style="padding :0 20px; ">${item.str_dt_type}</div>
                                        </div>
                                    </c:forEach>
                                </div>
                                <c:forEach var="item" items="${list}" varStatus="status">
                                    <div id="storeItem" class="form-group mb-3 font-weight-bold store_${status.index} store" style="display: none; text-align: center" >
                                        <hr>
                                        <hr>
                                        <p class="text-primary">명칭</p>
                                        <p>${item.str_dt_name}</p>
                                        <p class="text-primary">이미지</p>
                                        <img src="${item.str_image}">
                                        <p class="text-primary">주소</p>
                                        <p>${item.str_dt_address}</p>
                                        <p class="text-primary">기본 정보</p>
                                        <p>${item.str_dt_desc}</p>
                                        <hr>
                                        <hr>
                                    </div>
                                </c:forEach>
                                <div style="float: right;">
                                    <a class="btn btn-dark btn-icon-split" onclick="fnStoreUpdate()">
                                        <span class="text">수정</span>
                                    </a>
                                    <a class="btn btn-primary btn-icon-split" onclick="history.back()">
                                        <span class="text">확인</span>
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