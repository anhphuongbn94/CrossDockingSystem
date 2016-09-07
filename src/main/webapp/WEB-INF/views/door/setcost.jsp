<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>

	<section class="page-heading">
	    <h3><s:message code="cost.title" /></h3>
	</section><!-- End Page-Heading -->
	<section class="wrapper">
		<section class="row">
		<section class="col-lg-11">
		<section class="panel">
	    <header class="panel-heading"><s:message code="cost.title.data" />
			<span class="tools pull-right">
                <a href="javascript:;" class="fa fa-chevron-down"></a>
           	</span>	    
	    </header><!-- End Header -->
        <section class="panel-body">
        <section class="adv-table">
	        <table class="table table-hover dataTable" id="dynamic-table">
		        <thead>
			        <tr>
			            <th><s:message code="indoor.label" /></th>
			            <th><s:message code="outdoor.label" /></th>
						<th><s:message code="cost.door" /></th>
						<th>Option</th>
						<!-- <th></th> -->
			        </tr>
		        </thead>
		        <tbody class="rowCost_data">
			        <c:forEach items="${pageCost }" var="rows">
						<tr>
							<td style="display: none"><span class="id-cost">${rows.idCost }</span></td>
							<td><span>${rows.inDoor.nameDoor }</span></td>
							<td><span>${rows.outDoor.nameDoor }</span></td>
							<c:choose>
								<c:when test="${rows.cost==0 }">
									<td><input type="text" class="form-control small"/></td>
									<td><a href="" class="save-cost">Save</a></td>
								</c:when>
								<c:otherwise>
									<td><span class="cost">${rows.cost }</span></td>
									<td><a href="" class="edit-cost">Edit</a></td>
								</c:otherwise>
							</c:choose>
						</tr>
					</c:forEach>
					<!-- <tr>
						<td></td><td></td><td></td>
						<td><a href="" class="save-all-cost">Save all</a></td>
					</tr> -->
		        </tbody>
	        </table><!-- End Display-Table -->
        </section><!-- End Adv-Table -->
        <section class="dataTables_paginate paging_bootstrap pagination">
			<ul class="pageCost">
				<c:forEach begin="1" end="${numPage }" var="i">
					<li class="page_index" data-index="${i }"><a>${i }</a></li>
				</c:forEach>
			</ul>
		</section><!-- End Pagination -->
        </section><!-- End Panel-Body-->
		</section><!-- End Panel -->
        </section><!-- End Col -->
        </section><!-- End Row -->
	</section><!-- End Wraper -->
