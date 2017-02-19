<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<section class="page-heading">
    <h3><s:message code="assign.title" /></h3>
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
		<section style="float: left; padding-top: 10px">
			User assign cost: <span class="total-cost">${totalCost }</span>  
			| AI assign cost: <span class="total-cost-ai">${totalCostAI }</span>
		</section>
		<section style="float: right; padding-top: 10px" class="div-btn-assign-ai">
			<c:if test="${checkExistsAI }">
				<button class="btnDefault btn-primary" id="btnAssAI">AI Assign</button>
			</c:if>
			<c:if test="${!checkExistsAI }">
				<button class="btnDefault btn-primary" id="btnUseAssAI">Use AI Assign</button>
			</c:if>
		</section>
	</section>
	</section>
	</section>
	</section>		
	<!--------------------------------------------------------------------------------------------------->
       <!-- InVehicle -->
       <section class="row">
       <section class="col-sm-12">
       <section class="panel">
       <header class="panel-heading"><s:message code="invehicle.title.assign" />
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
		            <td><s:message code="vehicle.code" /></td>
					<td><s:message code="invehicle.volumn" /></td>
					<td><s:message code="invehicle.arrival" /></td>
					<c:forEach items="${listInDoor }" var="rowID" varStatus="n">
						<td style="text-align: center;">
							${rowID.nameDoor }
							<span class="uIDoor-${n.index }" style="display: none">${rowID.capuse }</span>
							<div style="margin-top: -5px">(<span class="cIDoor-${n.index}" data-bind="${rowID.capacity-rowID.capuse }">${rowID.capacity }</span>)</div>
						</td>
					</c:forEach>
					<td style="display: none"><span class="sizeIDoor">${listInDoor.size() }</span></td>
		        </tr>
	        </thead>
	        <tbody class="rowI_data">
				<tr style="display: none"><td><span class="size-in-vehicle" data-bind="${pInVehicle.size() }"></span></td></tr>
				<c:set var="n" value="0" />
		        <c:forEach items="${pInVehicle }" var="rowIV" varStatus="n">
					<tr class="gIDoor">
						<td class="code-iv">${rowIV.vehicleCode }</td>
						<td class="volumn-iv">
							<span class="volumn" data-bind="${rowIV.volumn }">${rowIV.volumn }</span>
							<span class="volumn-${n.index }" data-bind=${rowIV.volumn }></span>
						</td>
						<td class="arrival-iv">${rowIV.arrivalTime }</td>
						<c:forEach items="${listInDoor }" var="rowID" varStatus="m">
							<td style="text-align: center;">
								<label>
									<c:choose>
										<c:when test="${rowIV.door.idDoor == rowID.idDoor}">
											<input type="radio" 
												id="iDoor"
												name="groupI-${n.index }" 
												class="iDoor-${m.index }" 
												data-idInVehicle="${rowIV.idInVehicle }" 
												data-index="${m.index }" 
												data-idDoor="${rowID.idDoor }"
												checked="checked" 
												data-flag="choose"
											/>
										</c:when> 
										<c:otherwise>
											<input type="radio" 
												name="groupI-${n.index }" 
												class="iDoor-${m.index }" 
												data-idInVehicle="${rowIV.idInVehicle }" 
												data-index="${m.index }" 
												data-idDoor="${rowID.idDoor }"
											/>
										</c:otherwise>
									</c:choose>
									<c:set var="cssDoorAI" value="" />
									<c:if test="${rowIV.idDoorAI == rowID.idDoor }">
										<c:set var="cssDoorAI" value="door-assign-ai" />
									</c:if>
									<span class="remain ${cssDoorAI }">${rowID.capacity-rowID.capuse }</span>
								</label>
							</td>
						</c:forEach>
					</tr>
				</c:forEach>
	        </tbody>
        </table><!-- End Display-Table -->
        <c:if test="${pInVehicle.size() < 1 }">
        	<s:message code="msg.norecord" />
        </c:if>
       </section><!-- End Adv-Table -->
       <c:if test="${pInVehicle.size() > 0 }">
	       <section class="dataTables_paginate paging_bootstrap pagination">
				<ul class="page-iv-assign">
					<li class="prev"><a>←</a></li>
					<c:forEach items="${pagerI.listPage }" var="item">
						<c:choose>
							<c:when test="${item==1 }">
								<li class="page_index active" data-index="${item }"><a>${item }</a></li>
							</c:when>
							<c:otherwise>
								<li class="page_index" data-index="${item }"><a>${item }</a></li>
							</c:otherwise>
						</c:choose>
					</c:forEach>
					<li style="display: none" class="num-pageI" data-bind="${pagerI.numPage }"></li>
					<li class="next"><a>→ </a></li>
				</ul>
			</section><!-- End Pagination -->
		</c:if>
       </section><!-- End Panel-Body-->
       </section><!-- End Panel -->
       </section><!-- End Col-Sm-12 -->
       </section><!-- End Row --> 
       <!--------------------------------------------------------------------------------------------------->
       <!-- OutVehicle -->
       <section class="row">
       <section class="col-sm-12">
       <section class="panel">
       <header class="panel-heading"><s:message code="outvehicle.title.assign" />
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
		            <td><s:message code="vehicle.code" /></td>
					<td><s:message code="outvehicle.demand" /></td>
					<td><s:message code="outvehicle.arrival" /></td>
					<c:forEach items="${listOutDoor }" var="rowOD" varStatus="n">
						<td style="text-align: center;">${rowOD.nameDoor }
							<span class="uODoor-${n.index }" style="display: none">${rowOD.capuse }</span>
							<div style="margin-top: -5px">(<span class="cODoor-${n.index}">${rowOD.capacity }</span>)</div>
						</td>
					</c:forEach>
					<td style="display: none"><span class="sizeODoor">${listOutDoor.size() }</span></td>
		        </tr>
	        </thead>
	        <tbody class="rowO_data">
				<tr style="display: none"><td><span class="size-out-vehicle" data-bind="${pOutVehicle.size() }"></span></td></tr>
		        <c:forEach items="${pOutVehicle }" var="rowOV" varStatus="n">
					<tr class="gODoor">
						<td>${rowOV.vehicleCode }</td>
						<td><span class="demand" data-bind="${rowOV.demand }">${rowOV.demand }</span>
							<span class="demand-${n.index }" data-bind="${rowOV.demand }"></span>
						</td>
						<td>${rowOV.arrivalTime }</td>
						<c:forEach items="${listOutDoor }" var="rowOD" varStatus="m">
							<td style="text-align: center;">
								<label>
									<c:choose>
										<c:when test="${rowOV.door.idDoor == rowOD.idDoor}">
											<input type="radio" id="oDoor"
												name="groupO-${n.index }" 
												class="oDoor-${m.index }" 
												data-idOutVehicle="${rowOV.idOutVehicle }" 
												data-index="${m.index }" 
												data-idDoor="${rowOD.idDoor }"
												checked="checked" data-flag="choose"/>
										</c:when> 
										<c:otherwise>
											<input type="radio" 
												name="groupO-${n.index }" 
												class="oDoor-${m.index }" 
												data-idOutVehicle="${rowOV.idOutVehicle }" 
												data-index="${m.index }" 
												data-idDoor="${rowOD.idDoor }"/>
										</c:otherwise>
									</c:choose>
									<c:set var="cssDoorAI" value="" />
									<c:if test="${rowOV.idDoorAI == rowOD.idDoor }">
										<c:set var="cssDoorAI" value="door-assign-ai" />
									</c:if>
									<span class="remain ${cssDoorAI }">${rowOD.capacity-rowOD.capuse }</span>
								</label>
							</td>
						</c:forEach>
					</tr>
				</c:forEach>
	        </tbody>
        </table><!-- End Display-Table -->
        <c:if test="${pInVehicle.size() < 1 }">
        	<s:message code="msg.norecord" />
        </c:if>
        <c:if test="${pInVehicle.size() > 0 }">
	        <section class="dataTables_paginate paging_bootstrap pagination">
				<ul class="page-ov-assign">
					<li class="prev"><a>←</a></li>
					<c:forEach items="${pagerO.listPage }" var="item">
						<c:choose>
							<c:when test="${item==1 }">
								<li class="page_index active" data-index="${item }"><a>${item }</a></li>
							</c:when>
							<c:otherwise>
								<li class="page_index" data-index="${item }"><a>${item }</a></li>
							</c:otherwise>
						</c:choose>
					</c:forEach>
					<li style="display: none" class="num-pageO" data-bind="${pagerO.numPage }"></li>
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