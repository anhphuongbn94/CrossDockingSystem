<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

		<section class="page-heading">
		    <h3><s:message code="rpvehicle.title" /></h3>
		</section><!-- End Page-Heading -->
		<section class="wrapper">
		<section id="dynamic-table_wrapper" class="dataTables_wrapper" role="grid">
			<section class="infor-search">
				<div id="dynamic-table_length" class="dataTables_length" 
					style="margin-top: 2px">
					<label><s:message code="invehicle.code" />:
						<input type="text" name="vehicleCode" size="30" />
					</label>
				</div>
	        	<div id="dynamic-table_length" class="dataTables_length" style="clear: both">
					<label><s:message code="invehicle.date" />:
	                    <input type="text" value="${startDate }" size="20" readonly="readonly"
	                    		class="default-date-picker date-picker" name="strDate">
	                   	<span class="btn-primary"><i class="fa fa-calendar"></i></span>
					</label>
					   to
				</div>
				<div id="dynamic-table_length" class="dataTables_length" 
	        		style="float: left; margin-left: 10px;">
					<label>
	                    <input type="text" value="${endDate }" size="20" readonly="readonly"
	                    		class="default-date-picker date-picker" name="endDate">
	                   	<span class="btn-primary"><i class="fa fa-calendar"></i></span>
					</label>
				</div>
				<div id="dynamic-table_length" class="dataTables_length" 
					style="float: left; margin-left: 100px;">
					<input class="btnDefault btn-primary" type="button" 
		                    	value="Search" id="btnSearch" />
				</div>
			</section>
		</section>
		<section class="row" style="clear: both">
		<!-- Start InVehicle -->
		<section class="col-sm-6">
		<section class="panel">
			<header class="panel-heading">Data InVehicle
				<span class="tools pull-right"> 
				<a href="javascript:;" class="fa fa-chevron-down"></a>
				</span>
			</header>
			<section class="panel-body">
				<table class="table table-hover">
			    <thead>
				     <tr>
			        	<th><s:message code="invehicle.code" /></th>
						<th><s:message code="invehicle.date" /></th>
						<th><s:message code="invehicle.volumn" /></th>
						<th></th>
					 </tr>
				</thead>
				<tbody class="rowI_data">
					<c:forEach items="${pageIV }" var="rows">
                  		<tr>
							<td>${rows.vehicleCode }</td>
							<td>${rows.date }</td>
							<td>${rows.volumn }</td>
							<td><a href="#">Detail</a></td>
						</tr>
                  	</c:forEach>
			    </tbody>
				</table><!-- End Table -->
				<section class="dataTables_paginate paging_bootstrap pagination">
					<ul class="pageI-rp">
						<c:forEach begin="1" end="${numPageI }" var="j">
							<li class="page_index" data-index="${j }"><a>${j }</a></li>
						</c:forEach>
					</ul>
				</section><!-- End Pagination -->
			</section><!-- End Panel Body -->
		</section><!-- End Panel -->
		</section><!-- End Col -->
		<!-- End OutVehicle -->
		<!-- Start OutVehicle -->
		<section class="col-sm-6">
		<section class="panel">
			<header class="panel-heading">Data OutVehicle
				<span class="tools pull-right"> 
				<a href="javascript:;" class="fa fa-chevron-down"></a>
				</span>
			</header>
			<section class="panel-body">
				<table class="table table-hover">
			    <thead>
				     <tr>
			        	<th><s:message code="outvehicle.code" /></th>
						<th><s:message code="outvehicle.date" /></th>
						<th><s:message code="outvehicle.demand" /></th>
						<th>Detail</th>
					 </tr>
				</thead>
				<tbody class="rowO_data">
					<c:forEach items="${pageOV }" var="rows">
                  		<tr>
							<td>${rows.vehicleCode }</td>
							<td>${rows.date }</td>
							<td>${rows.demand }</td>
							<td><a href="#">Detail</a></td>
						</tr>
                  	</c:forEach>
			    </tbody>
				</table><!-- End Table -->
				<section class="dataTables_paginate paging_bootstrap pagination">
					<ul class="pageO-rp">
						<c:forEach begin="1" end="${numPageO }" var="j">
							<li class="page_index" data-index="${j }"><a>${j }</a></li>
						</c:forEach>
					</ul>
				</section><!-- End Pagination -->
			</section><!-- End Panel Body -->
		</section><!-- End Panel -->
		</section><!-- End Col -->
		<!-- End OutVehicle -->
        </section><!-- End Row -->
        </section><!-- End Wrapper -->