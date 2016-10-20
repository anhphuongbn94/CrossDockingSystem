<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>


	<section class="page-heading">
	    <h3><s:message code="outvehicle.title" /></h3>
	    <!-- <ul class="breadcrumb">
	        <li><a href="#">Dashboard</a></li>
	        <li><a href="#">Data Tables</a></li>
	        <li class="active"> Dynamic Table </li>
	    </ul> -->
	</section><!-- End Page-Heading -->
	<section class="wrapper">
        <section class="row">
        <section class="col-sm-12">
        <section class="panel">
        <header class="panel-heading"><s:message code="outvehicle.title.data" />
            <span class="tools pull-right">
                <a href="javascript:;" class="fa fa-chevron-down"></a>
                <!-- <a href="javascript:;" class="fa fa-times"></a>  -->
             </span>
        </header><!-- End Panel-Heading -->
        <section class="panel-body">
        <section class="adv-table">
	        <table class="display table table-bordered table-striped" id="dynamic-table">
		        <thead>
			        <tr>
			        	<th><s:message code="outvehicle.code" /></th>
			            <th><s:message code="outvehicle.date" /></th>
						<th><s:message code="outvehicle.arrival" /></th>
						<th><s:message code="outvehicle.outdoor" /></th>
						<th><s:message code="outvehicle.cds" /></th>
						<th><s:message code="btn.startload" /></th>
						<th><s:message code="btn.finishload" /></th>
			        </tr>
		        </thead>
		        <tbody class="row_data">
			        <c:forEach items="${pOutVehicle }" var="row">
						<tr>
							<td>${row.vehicleCode }</td>
							<td>${row.date }</td>
							<td>${row.arrivalTime }</td>
							<td>${row.door.nameDoor }</td>
							<td>${row.cDS.nameCrossDockingSystem }</td>
							<c:choose>
								<c:when test="${empty row.startLoadTime }">
									<td><button class="btnStartLoad btn-primary" data-bind="${row.idOutVehicle }">
										<s:message code="btn.startload" />
									</button></td>
									<td><button class="btnFinishLoad btn-primary" data-bind="${row.idOutVehicle }" disabled="disabled">
										<s:message code="btn.finishload" />
									</button></td>
								</c:when>
								<c:otherwise>
									<td>${row.startLoadTime }</td>
									<td><button class="btnFinishLoad btn-primary" data-bind="${row.idOutVehicle }">
										<s:message code="btn.finishload" />
									</button></td>
								</c:otherwise>
							</c:choose>
						</tr>
					</c:forEach>
		        </tbody>
	        </table><!-- End Display-Table -->
	        <section class="dataTables_paginate paging_bootstrap pagination">
				<ul class="pageO_load">
					<li class="prev"><a href="#">← Previous</a></li>
					<c:forEach begin="1" end="${numPage }" var="i">
						<li class="page_index" data-index="${i }"><a>${i }</a></li>
					</c:forEach>
					<li class="next"><a href="#">Next → </a></li>
				</ul>
			</section><!-- End Pagination -->
        </section><!-- End Adv-Table -->
        </section><!-- End Panel-Body-->
        </section><!-- End Panel -->
        </section><!-- End Col-Sm-12 -->
        </section><!-- End Row -->
	</section><!-- End Wraper -->