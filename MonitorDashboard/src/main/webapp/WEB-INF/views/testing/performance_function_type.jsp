<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<div class="col-sm-12">
	<div class="form-group">
	<c:if test="${ctype == '1'}">
		<label class="col-sm-3 control-label">Function Type</label>
	</c:if>
	<c:if test="${ctype == '2'}">
		<label class="col-sm-3 control-label">Version</label>
	</c:if>
		<div class="col-sm-7">
			<select class="form-control searchselect" id="functionType">
				<c:forEach items="${functionTypeList}" var="type">
					<option value="${type}">${type}</option>
				</c:forEach>
			</select>
		</div>
	</div>
</div>