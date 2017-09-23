<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<div class="col-sm-12">
	<div class="form-group">
		<label class="col-sm-3 control-label">Monitor</label>
		<div class="col-sm-7">
			<select class="form-control searchselect" id="monitor">
				<c:forEach items="${serverMonitorList}" var="monitor">
					<c:if test="${monitor.description=='check_url' }">
						<option value="${monitor.id}" selected="selected">${monitor.monitorName}</option>
					</c:if>
					<c:if test="${monitor.description!='check_url' }">
						<option value="${monitor.id}">${monitor.monitorName}</option>
					</c:if>
				</c:forEach>
			</select>
		</div>
	</div>
</div>