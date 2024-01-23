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
                                <h6 class="m-0 font-weight-bold text-primary">카테고리 조회</h6>
                            </div>
                            <div class="card-body">
                                <div class="table-responsive" style="height: 50%">
                                    <table class="table table-bordered" id="dataTable">
                                        <thead>
                                        <tr>
                                            <th>상위카테고리명</th>
                                            <th>카테고리명</th>
                                            <th>카테고리명(영어)</th>
                                            <th>카테고리명(중국)</th>
                                            <th>카테고리명(일본)</th>
                                            <th>카테고리명(스페인)</th>
                                            <th>카테고리명(독일어)</th>
                                            <th>등록일시</th>
                                            <th>수정일시</th>
                                            <th>사용여부</th>
                                        </tr>
                                        </thead>
                                        <tfoot>
                                        <tr>
                                            <th>상위카테고리명</th>
                                            <th>카테고리명</th>
                                            <th>카테고리명(영어)</th>
                                            <th>카테고리명(중국)</th>
                                            <th>카테고리명(일본)</th>
                                            <th>카테고리명(스페인)</th>
                                            <th>카테고리명(독일어)</th>
                                            <th>등록일시</th>
                                            <th>수정일시</th>
                                            <th>사용여부</th>
                                        </tr>
                                        </tfoot>
                                        <tbody>
<%--                                            <tr onclick="">--%>
<%--                                                <td>${item.ct_parent_nm}</td>--%>
<%--                                                <td>${item.ct_ko_nm}</td>--%>
<%--                                                <td>${item.ct_eu_nm}</td>--%>
<%--                                                <td>${item.ct_zh_nm}</td>--%>
<%--                                                <td>${item.ct_ja_nm}</td>--%>
<%--                                                <td>${item.ct_es_nm}</td>--%>
<%--                                                <td>${item.ct_de_nm}</td>--%>
<%--                                                <td>${item.ct_reg_dt}</td>--%>
<%--                                                <td>${item.ct_update_dt}</td>--%>
<%--                                                <td>1</td>--%>
<%--                                            </tr>--%>
                                        </tbody>
                                    </table>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
                <hr>
                <div class="row">
                    <div class="col-xl-12 col-lg-12">
                        <!-- DataTales Example -->
                        <div class="card shadow mb-4">
                            <div class="card-header py-3">
                                <h6 class="m-0 font-weight-bold text-primary">카테고리 변경</h6>
                            </div>
                            <div class="card-body">

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