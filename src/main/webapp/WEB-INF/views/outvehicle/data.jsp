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
		<section class="col-lg-12">
		<section class="panel">
	    <header class="panel-heading"><s:message code="outvehicle.title.insert" />
			<span class="tools pull-right">
                <a href="javascript:;" class="fa fa-chevron-down"></a>
             </span>	    
	    </header><!-- End Header -->
   		<section class="panel-body form-insert-ov">
	        <form role="form" class="cmxform form-horizontal adminex-form" id="addOV" method="post">
	        	<section class="form-group">
	                <label class="col-lg-2 control-label"><s:message code="vehicle.code" /></label>
	                <section class="col-lg-3">
	                    <select class="s-vehicle" name="idVehicle">
	                    	<option value=""></option>
	                    	<c:forEach items="${listV }" var="rows">
	                    		<option value="${rows.idVehicle }">${rows.vehicleCode }</option>
	                    	</c:forEach>
		              	</select>
	                </section>
	                <label class="col-lg-2 control-label"><s:message code="outvehicle.demand" /></label>
	                <section class="col-lg-3">
	                    <input type="text" name="demand" 
	                    	placeholder="<s:message code="outvehicle.demand" />" 
	                    	class="form-control"/>
	                </section>
	            </section>
	            <section class="form-group">
	            	<label class="col-lg-2 control-label"><s:message code="outvehicle.date" /></label>
	                <section class="col-lg-3">
	                    <label>
	                   		<span class="btn-primary"><i class="fa fa-calendar"></i></span>
	                    	<input type="text" value="${currentDate }" 
	                    		name="date"
	                    		placeholder="<s:message code="invehicle.date" />"
	                    		class="default-date-picker form-control">
                   		</label>
	                </section>
	                <label class="col-lg-2 control-label"><s:message code="outvehicle.arrival" /></label>
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
	                    	value="<s:message code="btn.submit" />" id="btnAddOV" />
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
        <header class="panel-heading"><s:message code="outvehicle.title.data" />
            <span class="tools pull-right">
                <a href="javascript:;" class="fa fa-chevron-down"></a>
                <!-- <a href="javascript:;" class="fa fa-times"></a>  -->
             </span>
        </header><!-- End Panel-Heading -->
        <section class="panel-body">
        <section class="adv-table">
        	<section id="dynamic-table_wrapper" class="dataTables_wrapper" role="grid">
        		<section class="form-group">
        			<section class="col-lg-3">
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
					<section class="col-lg-5" style="float: right">
						<section class="dataTables_filter" id="dynamic-table_filter">
							<form method="post" id="btn-search-outvehicle">
								<label>
									<input type="text" class="form-control key-outvehicle-search" 
										aria-controls="dynamic-table">
									<button class="btn-primary btn-search"><i class="fa fa-search"></i></button>
								</label>
							</form>
						</section>
					</section>
				</section>
			</section>
	        <table class="display table table-bordered table-striped" id="dynamic-table">
		        <thead>
			        <tr>
			            <td><s:message code="outvehicle.code" /></td>
			            <td><s:message code="outvehicle.date" /></td>
						<td><s:message code="outvehicle.arrival" /></td>
						<td><s:message code="outvehicle.startload" /></td>
						<td><s:message code="outvehicle.finishload" /></td>
						<td><s:message code="outvehicle.demand" /></td>
						<td><s:message code="outvehicle.status" /></td>
						<td><s:message code="outvehicle.outdoor" /></td>
			        </tr>
		        </thead>
		        <tbody class="row_data">
		        	<c:if test="${pOutVehicle.size() > 0 }">
				        <c:forEach items="${pOutVehicle }" var="rows">
							<tr>
								<td>${rows.vehicleCode }</td>
								<td>${rows.date }</td>
								<td>${rows.arrivalTime }</td>
								<td>${rows.startLoadTime }</td>
								<td>${rows.finishLoadTime }</td>
								<td>${rows.demand }</td>
								<td>${rows.status }</td>
								<td>${rows.door.nameDoor }</td>
							</tr>
						</c:forEach>
					</c:if>
		        </tbody>
	        </table><!-- End Display-Table -->
	        <c:if test="${pOutVehicle.size() < 1 }">
	        	<span class="no-record"><s:message code="msg.norecord"/></span>
	        </c:if>
	        <c:if test="${pOutVehicle.size() > 0 }">
		        <section class="dataTables_paginate paging_bootstrap pagination">
					<ul class="page-ov">
						<li class="prev"><a>← Previous</a></li>
						<li class="page_index active" data-index="1"><a>1</a></li>
						<c:forEach begin="2" end="${numPage }" var="i">
							<li class="page_index" data-index="${i }"><a>${i }</a></li>
						</c:forEach>
						<li style="display: none" class="num-page" data-bind="${numPage }"></li>
						<li class="next"><a>Next → </a></li>
					</ul>
				</section><!-- End Pagination -->
			</c:if>
        </section><!-- End Adv-Table -->
        </section><!-- End Panel-Body-->
        </section><!-- End Panel -->
        </section><!-- End Col-Sm-12 -->
        </section><!-- End Row -->
	</section><!-- End Wraper -->