package org.mat.sample.bg;

public class Task {

	private String taskId;
	private String summary;

	public Task(String taskId, String summary) {
		this.taskId = taskId;
		this.summary = summary;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

}
