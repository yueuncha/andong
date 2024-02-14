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
                                <h6 class="m-0 font-weight-bold text-primary">
                                    <c:choose>
                                        <c:when test="${category != null}">
                                            하위
                                        </c:when>
                                        <c:otherwise>
                                            상위
                                        </c:otherwise>
                                    </c:choose>
                                    카테고리 조회</h6>
                            </div>
                            <div class="card-body">
                                <div class="table-responsive" style="height: 50%">
                                    <table class="table table-bordered" id="dataTable">
                                        <thead>
                                        <tr>
                                            <c:choose>
                                                <c:when test="${category != null}">
                                                    <th>상위카테고리명</th>
                                                </c:when>
                                                <c:otherwise></c:otherwise>
                                            </c:choose>
                                            <th>카테고리명</th>
                                            <th>카테고리명(영어)</th>
                                            <th>카테고리명(중국)</th>
                                            <th>카테고리명(일본)</th>
                                            <th>카테고리명(스페인)</th>
                                            <th>카테고리명(독일)</th>
                                            <th>등록일자</th>
                                            <th>수정일자</th>
                                            <th>사용여부</th>
                                        </tr>
                                        </thead>
                                        <tfoot>
                                        <tr>
                                            <c:choose>
                                                <c:when test="${category != null}">
                                                    <th>상위카테고리명</th>
                                                </c:when>
                                                <c:otherwise></c:otherwise>
                                            </c:choose>
                                            <th>카테고리명</th>
                                            <th>카테고리명(영어)</th>
                                            <th>카테고리명(중국)</th>
                                            <th>카테고리명(일본)</th>
                                            <th>카테고리명(스페인)</th>
                                            <th>카테고리명(독일)</th>
                                            <th>등록일자</th>
                                            <th>수정일자</th>
                                            <th>사용여부</th>
                                        </tr>
                                        </tfoot>
                                        <tbody>
                                        <c:forEach var="item" items="${list}">
                                            <tr onclick="fnLoadCategory(${item.ct_idx})">
                                                <c:choose>
                                                    <c:when test="${category != null}">
                                                        <td>${item.ct_parent_nm}</td>
                                                    </c:when>
                                                    <c:otherwise></c:otherwise>
                                                </c:choose>
                                                <td>${item.ct_ko_nm}</td>
                                                <td>${item.ct_eu_nm}</td>
                                                <td>${item.ct_zh_nm}</td>
                                                <td>${item.ct_ja_nm}</td>
                                                <td>${item.ct_es_nm}</td>
                                                <td>${item.ct_de_nm}</td>
                                                <td>${item.ct_reg_dt}</td>
                                                <td>${item.ct_update_dt}</td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${item.ct_use eq 'Y'}">사용</c:when>
                                                        <c:otherwise>미사용</c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                        </c:forEach>
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
                                <div style=" width : 100%">
                                    <c:choose>
                                        <c:when test="${category != null}">
                                            <div class="form-group mb-3 p-3" style="width: auto; display: inline-block;">
                                                <label for="exampleFormControlSelect2">상위 카테고리명</label>
                                                <select class="form-control" id="exampleFormControlSelect2" name="ct_parent" disabled>
                                                </select>
                                            </div>
                                        </c:when>
                                        <c:otherwise></c:otherwise>
                                    </c:choose>

                                    <div class="form-group mb-3 p-3" style="width: auto; display: inline-block; ">
                                        <label>카테고리명</label>
                                        <input class="form-control mb-2" type="text" name="ct_ko_nm" disabled>
                                    </div>
                                    <div class="form-group mb-3 p-3" style="width: auto; display: inline-block;">
                                        <label>카테고리명(영문)</label>
                                        <input class="form-control mb-2" type="text" name="ct_eu_nm" disabled>
                                    </div>
                                    <div class="form-group mb-3 p-3" style="width: auto; display: inline-block;">
                                        <label>카테고리명(중국)</label>
                                        <input class="form-control mb-2" type="text" name="ct_zh_nm" disabled>
                                    </div>
                                    <div class="form-group mb-3 p-3" style="width: auto; display: inline-block;">
                                        <label>카테고리명(일본)</label>
                                        <input class="form-control mb-2" type="text" name="ct_ja_nm" disabled>
                                    </div>
                                    <div class="form-group mb-3 p-3" style="width: auto; display: inline-block;">
                                        <label>카테고리명(스페인)</label>
                                        <input class="form-control mb-2" type="text" name="ct_es_nm" disabled>
                                    </div>
                                    <div class="form-group mb-3 p-3" style="width: auto; display: inline-block;">
                                        <label>카테고리명(독일)</label>
                                        <input class="form-control mb-2" type="text" name="ct_de_nm" disabled>
                                    </div>
                                    <div class="form-group" style="width: auto; display: inline-block;">
                                        <label for="exampleFormControlSelect1">사용여부</label>
                                        <select class="form-control" id="exampleFormControlSelect1" name="ct_use" disabled>
                                            <option value="Y">사용</option>
                                            <option value="N">미사용</option>
                                        </select>
                                    </div>
                                </div>
                                <div style="float : right">
                                    <a class="btn btn-primary btn-icon-split" onclick="fnCreate()">
                                        <span class="text">신규</span>
                                    </a>
                                    <a class="btn btn-dark btn-icon-split">
                                        <span class="text">저장</span>
                                    </a>
                                    <a class="btn btn-circle btn-icon-split" style="width: auto;" onclick="fnClear('ct_use')">
                                        <span class="text">초기화</span>
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