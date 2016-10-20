<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>


	<section class="page-heading">
	    <h3><s:message code="invehicle.title" /></h3>
	</section><!-- End Page-Heading -->
	<section class="wrapper">
		<section class="row">
		<section class="col-lg-12">
		<section class="panel">
	    <header class="panel-heading"><s:message code="invehicle.title.insert" />
			<span class="tools pull-right">
                <a href="javascript:;" class="fa fa-chevron-down"></a>
           	</span>	    
	    </header><!-- End Header -->
   		<section class="panel-body form-insert-iv">
	        <form role="form" class="cmxform form-horizontal adminex-form" id="addIV" method="post">
	        	<section class="form-group">
	                <label class="col-lg-2 control-label"><s:message code="vehicle.code" /></label>
	                <section class="col-lg-3">
	                    <select class="s-vehicle" name="idVehicle">
	                    	<option value="">Please select vehicle</option>
	                    	<c:forEach items="${listV }" var="rows">
	                    		<option value="${rows.idVehicle }">${rows.vehicleCode }</option>
	                    	</c:forEach>
		              	</select>
	                </section>
	                <label class="col-lg-2 control-label"><s:message code="invehicle.volumn" /></label>
	                <section class="col-lg-3">
	                    <input type="text" name="volumn" 
	                    	placeholder=<s:message code="invehicle.volumn" /> 
	                    	class="form-control fix-form-length"/>
	                </section>
	            </section>
	            <section class="form-group">
	                <label class="col-lg-2 control-label"><s:message code="invehicle.date" /></label>
	                <section class="col-lg-3">
	                	<label>
	                   		<span class="btn-primary"><i class="fa fa-calendar"></i></span>
	                    	<input type="text" value="${currentDate }" 
	                    		name="date"
	                    		placeholder="<s:message code="invehicle.date" />"
	                    		class="default-date-picker form-control">
                   		</label>
	                </section>
	                <label class="col-lg-2 control-label"><s:message code="invehicle.arrival" /></label>
	                <section class="col-lg-3">
	                	<label>
	                    	<span class="btn-primary"><i class="fa fa-clock-o"></i></span>
		                    <input type="text" name="arrivalTime"
		                    	placeholder="<s:message code="invehicle.arrival" />" 
		                    	class="timepicker-24 form-control" 
	                    	/>
                    	</label>
	                </section>
	                <section class="col-lg-1">
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
        	<section id="dynamic-table_wrapper" class="dataTables_wrapper" role="grid">
        		<%-- <section class="row"> --%>
        			<section class="col-lg-3">
			        	<section id="dynamic-table_length" class="dataTables_length">
							<label>
								<select class="recordI" size="1" name="dynamic-table_length" aria-controls="dynamic-table">
									<option value="10" selected="selected">10</option>
									<option value="25">25</option>
									<option value="50">50</option>
									<option value="100">100</option>
								</select> records per page
							</label>
						</section>
					<%-- </section> --%>
					<%-- <section class="col-lg-3">
						<section class="dataTables_filter" id="dynamic-table_filter">
							<label>
								Search: <input type="text" class="form-control" 
											aria-controls="dynamic-table">
							</label>
						</section>
					</section>
				</section> --%>
			</section>
			
	        <table class="display table table-bordered table-striped" id="dynamic-table">
		        <thead>
			        <tr>
			            <th><s:message code="invehicle.code" /></th>
			            <th><s:message code="invehicle.date" /></th>
						<th><s:message code="invehicle.arrival" /></th>
						<th><s:message code="invehicle.startunload" /></th>
						<th><s:message code="invehicle.finishunload" /></th>
						<th><s:message code="invehicle.volumn" /></th>
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
							<td>${rows.volumn }</td>
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
