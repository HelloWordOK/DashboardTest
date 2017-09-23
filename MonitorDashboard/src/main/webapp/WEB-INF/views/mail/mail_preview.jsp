<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
	<div class="modal-dialog" role="document" style="width:1000px">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">&times;</button>
				<h4 class="modal-title" id="majorModalLabel">Mail Preview:</h4>
			</div>
			<div class="modal-body">
				<div class="container-fluid">

					<div class="col-sm-12">
						<label class="control-label">Subject:</label>
					</div>

					<div class="col-sm-12">
						<label class="control-label" id="subject">${psubject}</label>
					</div>

					<div class="col-sm-12">
							<label class="control-label">Content:</label>
					</div>

					<div class="col-sm-12" id="content">${pcontent}</div>
				</div>
		</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal" id="btn_close">Close</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
