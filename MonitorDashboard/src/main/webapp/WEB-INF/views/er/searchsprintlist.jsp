<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<div class="col-sm-12">
	<div class="form-group">
		<label class="col-sm-3 control-label">Sprint Range</label>
		<div class="col-sm-7" id="sprint">
			<select class="form-control sprintselect" id="sprint_start" style="width: 200px;">
				<c:forEach items="${sprintList}" var="sprint">
					<option value="${sprint}">${sprint}</option>
				</c:forEach>
			</select>
			<p style="padding: 3px 12px;">-</p>
			<select class="form-control sprintselect" id="sprint_end" style="width: 200px;">
				<c:forEach items="${sprintList}" var="sprint">
					<option value="${sprint}">${sprint}</option>
				</c:forEach>
			</select>
		</div>
	</div>
</div>