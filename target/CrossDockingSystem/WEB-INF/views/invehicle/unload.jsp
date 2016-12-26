<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>



	<section class="page-heading">
	    <h3><s:message code="invehicle.title" /></h3>
	</section><!-- End Page-Heading -->
	<section class="wrapper">
        <section class="row">
        <section class="col-sm-12">
        <section class="panel">
        <header class="panel-heading"><s:message code="vehicle.unload.title" />
            <span class="tools pull-right">
                <a href="javascript:;" class="fa fa-chevron-down"></a>
             </span>
        </header><!-- End Panel-Heading -->
        <section class="panel-body">
        <section class="adv-table">
        	<section id="dynamic-table_wrapper" class="dataTables_wrapper" role="grid">
        		<section class="form-group">
        			<section class="col-lg-3">
			        	<section id="dynamic-table_length" class="dataTables_length">
							<label>
								<select class="recordI-unload" size="1" name="dynamic-table_length" aria-controls="dynamic-table">
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
	        	<tr class="tr-width">
	        		<td style="width: 12%"></td>
	        		<td style="width: 11%"></td>
	        		<td style="width: 11%"></td>
	        		<td style="width: 11%"></td>
	        		<td style="width: 11%"></td>
	        		<td style="width: "></td>
	        		<td style="width: "></td>
	        		<td style="width: 3%"></td>
	        		<td style="width: 3%"></td>
	        	</tr>
		        <thead>
			        <tr>
			        	<td><s:message code="invehicle.code" /></td>
			        	<td><s:message code="vehicle.company" /></td>
			            <td><s:message code="invehicle.date" /></td>
						<td><s:message code="invehicle.arrival" /></td>
						<td><s:message code="invehicle.indoor" /></td>
						<td><s:message code="label.startunload" /></td>
						<td><s:message code="label.finishunload" /></td>
						<td colspan="2"><s:message code="label.option" /></td>
			        </tr>
		        </thead>
		        <tbody class="row_data">
			        <c:forEach items="${pInVehicle }" var="row">
						<tr>
							<td>${row.vehicleCode }</td>
							<td>${row.company }</td>
							<td>${row.date }</td>
							<td>${row.arrivalTime }</td>
							<td>${row.door.nameDoor }</td>
							<c:choose>
								<c:when test="${row.status==1 }">
									<td class="td-start-time">
					                    <input type="text" class="timepicker-24 start-time" />
				                    	<i class="fa fa-clock-o"></i>
									</td>
									<td class="td-end-time">
					                    <input type="text" class="timepicker-24 end-time" />
				                    	<i class="fa fa-clock-o"></i>
									</td>
								</c:when>
								<c:otherwise>
									<td class="td-start-time label-time">${row.startUnloadTime }</td>
									<td class="td-end-time label-time">${row.finishUnloadTime }</td>
								</c:otherwise>
							</c:choose>
							<td><c:if test="${row.status==1 }">
									<span class="save-unload option-vehicle" data-bind="${row.idInVehicle }">
										<i class="fa fa-floppy-o"></i>
									</span>
								</c:if>
								<c:if test="${row.status==2 }">
									<span class="edit-unload option-vehicle" 
											data-bind="${row.idInVehicle }">
										<i class="fa fa-pencil-square-o"></i>
									</span>
								</c:if>
							</td>
							<td class="td-finish-unload">
								<c:if test="${row.status > 1 and row.status < 3}">
									<span class="finish-unload option-vehicle" 
											data-bind="${row.idInVehicle }">
										<i class="fa fa-check"></i>
									</span>
								</c:if>
							</td>
						</tr>
					</c:forEach>
		        </tbody>
	        </table><!-- End Display-Table -->
	        <c:if test="${empty pInVehicle }">
	        	<s:message code="msg.norecord" />
	        </c:if>
	        <c:if test="${!empty pInVehicle }">
		        <section class="dataTables_paginate paging_bootstrap pagination">
					<ul class="pageI_unload">
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
        </section><!-- End Adv-Table -->
        </section><!-- End Panel-Body-->
        </section><!-- End Panel -->
        </section><!-- End Col-Sm-12 -->
        </section><!-- End Row -->
	</section><!-- End Wraper -->
