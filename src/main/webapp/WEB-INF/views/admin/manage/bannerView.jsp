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
                                <h6 class="m-0 font-weight-bold text-primary">배너 업데이트</h6>
                            </div>
                            <div class="card-body">
                                <div class="table-responsive">
                                    <table class="table table-bordered" id="dataTable">
                                        <thead>
                                        <tr>
                                            <th>배너명</th>
                                            <th>배너 이미지</th>
                                            <th>배너 URL</th>
                                            <th></th>
                                        </tr>
                                        </thead>
                                        <tfoot>
                                        <tr>
                                            <th>배너명</th>
                                            <th>배너 이미지</th>
                                            <th>배너 URL</th>
                                            <th></th>
                                        </tr>
                                        </tfoot>
                                        <tbody>
                                        <c:forEach var="item" items="${list}">
                                            <tr>
                                                <td name="bn_title">${item.bn_title}</td>
                                                <td name="bn_img">${item.bn_img}</td>
                                                <td name="bn_url">${item.bn_url}</td>
                                                <td style="text-align: center">
                                                    <a class="btn btn-primary btn-icon-split" data-toggle="modal" data-target="#bannerView"
                                                        data-image="${item.bn_img}" data-url="${item.bn_url}" onclick="fnBannerView(this)">
                                                        <span class="text">확인</span>
                                                    </a>
                                                    <a class="btn btn-dark btn-icon-split" onclick="fnBannerUpdate(this, ${item.bn_idx})"><span class="text">수정</span></a>
                                                    <a class="btn btn-danger btn-icon-split" onclick="location.href='/admin/banner/delete?bn_idx=${item.bn_idx}'"><span class="text">삭제</span></a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                                <div style="float: right; margin-top: 10px">
                                    <a class="btn btn-primary btn-icon-split" onclick="location.href='/admin/banner/add'">
                                        <span class="text">최신 업데이트</span>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- main end -->
        <!-- Logout Modal-->
        <div class="modal fade" id="bannerView" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
             aria-hidden="true">
            <div class="modal-dialog" role="document" style="max-width : 1000px">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">미리보기</h5>
                        <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">×</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <img id="bannerImage" src="" alt=""/>
                        <h5 style="padding : 0.7rem">배너 URL</h5>
                        <span id="bannerUrl" style="padding : 1.0rem"></span>
                    </div>
                    <div class="modal-footer">
                        <button class="btn btn-primary" type="button" data-dismiss="modal">확인</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- content-wrapper end-->
</div>
<!-- wrapper end -->
<%@include file="/resources/common/script.jsp"%>
</body>
</html>