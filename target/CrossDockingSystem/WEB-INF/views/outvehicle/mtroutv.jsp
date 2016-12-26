<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

	<section class="page-heading">
	    <h3><s:message code="outvehicle.title.mtr" /></h3> 
	</section><!-- End Page-Heading -->
	<section class="wrapper">
		<div id="ac-wrapper">
			<div class="img-load">
				<center>
					<i class="fa fa-spinner fa-pulse fa-3x fa-fw"></i>
				</center>
			</div>
		</div>
		<section class="row">
		<section class="col-sm-12">
		<section class="panel">
		<section class="panel-body">
			<section class="form-group">
				<form role="form" class="cmxform form-horizontal adminex-form">
	        		<label class="col-lg-2 control-label">Vehicle Code:</label>
	        		<section class="col-lg-3">
						<input type="text" class="form-control key-outvehicle-search">
					</section>
					<label class="col-lg-2 control-label" style="margin-left: -70px">Vehicle Status:</label>
					<section class="col-lg-3">
						<select class="status-vehicle" size="1" name="dynamic-table_length">
							<option value=""></option>
							<option value="0"><s:message code="watting.assign.door.status" /></option>
							<option value="1"><s:message code="watting.unload.status" /></option>
							<option value="2"><s:message code="start.unload.status" /></option>
							<option value="3"><s:message code="finish.unload.status" /></option>
						</select> 
					</section>
					<section class="col-lg-1" style="margin-left: 30px">
						<input type="button" class="btn-primary form-control" id="btn-search-mtrov" value="Search" />
					</section>
				</form>
			</section>
		</section>
		</section>
		</section>
		</section>
		<!-- Outvehicle Data -->
		<section class="row">
		<section class="col-sm-12">
		<section class="panel">
		<header class="panel-heading"><s:message code="outvehicle.title.data"/>
			<span class="tools pull-right"> 
				<a href="javascript:;" class="fa fa-chevron-down"></a>
			</span>
		</header>
		<section class="panel-body">
		<section class="adv-table">
			<section id="dynamic-table_wrapper" class="dataTables_wrapper" role="grid">
        		<section class="form-group">
        			<section class="col-lg-5">
			        	<section id="dynamic-table_length" class="dataTables_length">
							<label>
								<select class="recordO" size="1" name="dynamic-table_length" aria-controls="dynamic-table">
									<option value="15" selected="selected">15</option>
									<option value="30">30</option>
									<option value="50">50</option>
								</select> records per page
							</label>
						</section>
					</section>
				</section>
			</section>
			<table class="display table table-bordered table-striped" id="dynamic-table">
			<thead>
				<tr>
					<td><s:message code="vehicle.code" /></td>
					<td><s:message code="vehicle.company" /></td>
					<td><s:message code="outvehicle.arrival" /></td>
					<td><s:message code="outvehicle.demand" /></td>
					<td><s:message code="outvehicle.startload" /></td>
					<td><s:message code="outvehicle.finishload" /></td>
					<td><s:message code="outvehicle.outdoor" /></td>
					<td><s:message code="outvehicle.status" /></td>
					<td></td>
				</tr>
			</thead>
				<tbody class="rowO_data">
					<c:if test="${pageV.size() > 0 }">
						<c:forEach items="${pageV }" var="rows">
							<tr>
								<td>${rows.vehicleCode }</td>
								<td>${rows.company }</td>
								<td>${rows.arrivalTime }</td>
								<td>${rows.demand }</td>
								<td>${rows.startLoadTime }</td>
								<td>${rows.finishLoadTime }</td>
								<td>${rows.door.nameDoor }</td>
								<td>
									<c:if test="${rows.status == 0}"><s:message code="watting.assign.door.status" /></c:if>
									<c:if test="${rows.status == 1}"><s:message code="watting.unload.status" /></c:if>
									<c:if test="${rows.status == 2}"><s:message code="start.unload.status" /></c:if>
									<c:if test="${rows.status == 3}"><s:message code="finish.unload.status" /></c:if>
								</td>
								<td><span class="detail-outvehicle option-vehicle" 
										data-bind="${rows.idOutVehicle }">
									<i class="fa fa-list-alt"></i>
								</span></td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table><!-- End Table -->
			<c:if test="${pageV.size() < 1 }">
	        	<span class="no-record"><s:message code="msg.norecord"/></span>
	        </c:if>
	        <c:if test="${pageV.size() > 0 }">
				<section class="dataTables_paginate paging_bootstrap pagination">
					<ul class="pageO-status">
						<li class="prev"><a>←</a></li>
						<c:forEach items="${pager.listPage }" var="item">
							<c:choose>
								<c:when test="${item==1 }">
									<li class="page_index active" data-index="${item }"><a>${item }</a></li>
								</c:when>
								<c:otherwise>
									<li class="page_index" data-index="${item }"><a>${item }</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<li style="display: none" class="num-page" data-bind="${pager.numPage }"></li>
						<li class="next"><a>→ </a></li>
					</ul>
				</section><!-- End Pagination -->
			</c:if>
			</section><!-- End Panel Body -->
			</section><!-- End Adv Table -->
			</section><!-- End Panel -->
			</section><!-- End Col-SM-6 -->
           <!-- OutVehicle Data -->
       		</section><!-- End Row -->
       		<section class="dialog-pov">
        	<section id="tabs">
        		<ul>
				    <li><a href="#fragment-1"><span>Product Vehicle</span></a></li>
				    <li><a href="#fragment-2"><span>Product Transform</span></a></li>
			  	</ul>
			  	<div id="fragment-1">
		   			<section class="row">
					<section class="col-sm-12">
					<section class="panel">
					<header class="panel-heading">
						<s:message code="invehicle.title.data" />
					</header>
					<section class="panel-body">
					<section class="adv-table">
						<table class="display table table-bordered table-striped" id="tbl-piv">
							<tr style="display: none">
								<td class="col-1"></td>
								<td class="col-2"></td>
								<td class="col-3"></td>
								<td class="col-4"></td>
								<td class="col-5"></td>
								<td class="col-6"></td>
							</tr>
							<thead>
								<tr>
									<td><s:message code="vehicle.code" /></td>
									<td><s:message code="vehicle.company" /></td>
									<td><s:message code="product.name" /></td>
									<td><s:message code="product.quantity" /></td>
									<td><s:message code="product.unit" /></td>
								</tr>
							</thead>
							<tbody class="rowPOV_data">
							</tbody>
					</table><!-- End Table -->
					</section><!-- End Panel Body -->
					</section><!-- End Adv Table -->
					</section><!-- End Panel -->
					</section><!-- End Col-SM-12 -->
		   			</section><!-- End Row -->
	   			</div>
	   			<div id="fragment-2">
       				<section class="row">
					<section class="col-sm-12">
					<section class="panel">
					<header class="panel-heading">
						<s:message code="invehicle.title.data" />
					</header>
					<section class="panel-body">
					<section class="adv-table">
						<table class="display table table-bordered table-striped" id="tbl-piv">
							<tr style="display: none">
								<td class="col-1"></td>
								<td class="col-2"></td>
								<td class="col-3"></td>
								<td class="col-4"></td>
								<td class="col-5"></td>
								<td class="col-6"></td>
							</tr>
							<thead>
								<tr>
									<td><s:message code="invehicle.code" /></td>
									<td><s:message code="outvehicle.code" /></td>
									<td><s:message code="product.name" /></td>
									<td><s:message code="product.quantity" /></td>
									<td><s:message code="product.unit" /></td>
								</tr>
							</thead>
							<tbody class="rowPT_data">
							</tbody>
					</table><!-- End Table -->
					</section><!-- End Panel Body -->
					</section><!-- End Adv Table -->
					</section><!-- End Panel -->
					</section><!-- End Col-SM-12 -->
	       			</section><!-- End Row -->
       			</div><!-- End Fragment-2 -->
   			</section><!-- End Tabs -->
   		</section><!-- End Dialog -->
	</section><!-- End Wrapper -->