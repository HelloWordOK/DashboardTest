package com.nokia.charts.dto.qa;

public class QAResultDataDto {

	private int created;
	private int closed;
	private int backlog;
	private float sla;
	private float slaP1;
	private float slaP2;
	private float slaP3;
	private float slaP4;
	private int resolved;
	private int pending;
	private int workInProgress;
	private int ownerAssinged;

	public float getSlaP1() {
		return slaP1;
	}

	public void setSlaP1(float slaP1) {
		this.slaP1 = slaP1;
	}

	public float getSlaP2() {
		return slaP2;
	}

	public void setSlaP2(float slaP2) {
		this.slaP2 = slaP2;
	}

	public float getSlaP3() {
		return slaP3;
	}

	public void setSlaP3(float slaP3) {
		this.slaP3 = slaP3;
	}

	public float getSlaP4() {
		return slaP4;
	}

	public void setSlaP4(float slaP4) {
		this.slaP4 = slaP4;
	}

	public int getCreated() {
		return created;
	}

	public void setCreated(int created) {
		this.created = created;
	}

	public int getClosed() {
		return closed;
	}

	public void setClosed(int closed) {
		this.closed = closed;
	}

	public int getBacklog() {
		return backlog;
	}

	public void setBacklog(int backlog) {
		this.backlog = backlog;
	}

	public float getSla() {
		return sla;
	}

	public void setSla(float sla) {
		this.sla = sla;
	}

	public int getResolved() {
		return resolved;
	}

	public void setResolved(int resolved) {
		this.resolved = resolved;
	}

	public int getPending() {
		return pending;
	}

	public void setPending(int pending) {
		this.pending = pending;
	}

	public int getWorkInProgress() {
		return workInProgress;
	}

	public void setWorkInProgress(int workInProgress) {
		this.workInProgress = workInProgress;
	}

	public int getOwnerAssinged() {
		return ownerAssinged;
	}

	public void setOwnerAssinged(int ownerAssinged) {
		this.ownerAssinged = ownerAssinged;
	}
}
