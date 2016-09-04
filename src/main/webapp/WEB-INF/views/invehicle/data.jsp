<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>


	<section class="page-heading">
	    <h3><s:message code="invehicle.title" /></h3>
	</section><!-- End Page-Heading -->
	<section class="wrapper">
		<section class="row">
		<section class="col-lg-10">
		<section class="panel">
	    <header class="panel-heading"><s:message code="invehicle.title.insert" />
			<span class="tools pull-right">
                <a href="javascript:;" class="fa fa-chevron-down"></a>
           	</span>	    
	    </header><!-- End Header -->
   		<section class="panel-body">
	        <form role="form" class="form-horizontal adminex-form" id="addIV" method="post">
	        	<section class="form-group">
	                <label class="col-lg-2 control-label"><s:message code="invehicle.code" /></label>
	                <section class="col-lg-7">
	                    <!-- <input type="text" name="codeVehicle" placeholder="Code Vehicle" class="form-control"/> -->
	                    <select class="s-vehicle" name="idVehicle">
	                    	<c:forEach items="${listV }" var="rows">
	                    		<option value="${rows.idVehicle }">${rows.vehicleCode }</option>
	                    	</c:forEach>
		              	</select>
	                </section>
	            </section>
	            <section class="form-group">
	                <label class="col-lg-2 control-label"><s:message code="invehicle.date" /></label>
	                <section class="col-lg-7">
	                    <input type="text" name="date" placeholder="Date" class="form-control form-control-inline input-medium default-date-picker"/>
	                </section>
	            </section>
	            <section class="form-group">
	                <label class="col-lg-2 control-label"><s:message code="invehicle.arrival" /></label>
	                <section class="col-lg-7">
	                    <input type="text" name="arrivalTime" placeholder="ArrivalTime" class="form-control timepicker-24"/>
	                </section>
	            </section>	            	           
	            <section class="form-group">
	                <section class="col-lg-offset-2 col-lg-10">
	                    <input class="btnDefault btn-primary" type="submit" 
	                    	value="<s:message code="btn.submit" />" id="btnAddIV" />
	                </section>
	            </section>
	        </form><!-- End Form -->
    	</section><!-- End Panel-Body -->
		</section><!-- End Panel -->
        </section><!-- End Col -->
        </section><!-- End Row -->
        <section class="row">
        <section class="col-sm-12">
        <section class="panel">
        <header class="panel-heading"><s:message code="invehicle.title.data" />
            <span class="tools pull-right">
                <a href="javascript:;" class="fa fa-chevron-down"></a>
             </span>
        </header><!-- End Panel-Heading -->
        <section class="panel-body">
        <section class="adv-table">
        	<div id="dynamic-table_wrapper" class="dataTables_wrapper" role="grid">
	        	<div id="dynamic-table_length" class="dataTables_length">
					<label>
						<select class="recordI" size="1" name="dynamic-table_length" aria-controls="dynamic-table">
							<option value="10" selected="selected">10</option>
							<option value="25">25</option>
							<option value="50">50</option>
							<option value="100">100</option>
						</select> records per page
					</label>
				</div>
			</div>
			<!-- <div class="dataTables_filter" id="dynamic-table_filter">
				<label>
					Search: <input type="text" class="form-control" 
								aria-controls="dynamic-table">
				</label>
			</div> -->
	        <table class="table table-hover" id="dynamic-table">
		        <thead>
			        <tr>
			            <th><s:message code="invehicle.code" /></th>
			            <th><s:message code="invehicle.date" /></th>
						<th><s:message code="invehicle.arrival" /></th>
						<th><s:message code="invehicle.startunload" /></th>
						<th><s:message code="invehicle.finishunload" /></th>
						<th><s:message code="invehicle.status" /></th>
						<th><s:message code="invehicle.indoor" /></th>
						<%-- <th><s:message code="invehicle.cds" /></th> --%>
						<!-- <th></th> -->
			        </tr>
		        </thead>
		        <tbody class="row_data">
			        <c:forEach items="${pInVehicle }" var="rows">
						<tr>
							<td>${rows.vehicleCode }</td>
							<td>${rows.date }</td>
							<td>${rows.arrivalTime }</td>
							<td>${rows.startUnloadTime }</td>
							<td>${rows.finishUnloadTime }</td>
							<td>${rows.status }</td>
							<td>${rows.door.nameDoor }</td>
							<%-- <td>${rows.cDS.nameCrossDockingSystem }</td> --%>
							<%-- <td><a class="delIV" data-bind="${row.idVehicle }">Del</a></td> --%>
						</tr>
					</c:forEach>
		        </tbody>
	        </table><!-- End Display-Table -->
	        <section class="dataTables_paginate paging_bootstrap pagination">
				<ul class="pageI">
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
