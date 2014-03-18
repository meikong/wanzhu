package com.wanzhu.jsonvo;

import com.wanzhu.entity.Label;

public class LabelVO {

	private String labelId;
	private String label;
	private Long eventCount;

	public LabelVO() {
	}
	
	public LabelVO(String labelId, String label, Long eventCount) {
		this.labelId = labelId;
		this.label = label;
		this.eventCount = eventCount;
	}
	
	public static LabelVO convertFromEntity(Label label) {
		LabelVO vo = new LabelVO();
		vo.setLabel(label.getLabel());
		vo.setLabelId(label.getLabelid());
		return vo;
	}

	public LabelVO(String labelId, String label) {
		this(labelId, label, null);
	}

	public String getLabelId() {
		return labelId;
	}

	public void setLabelId(String labelId) {
		this.labelId = labelId;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Long getEventCount() {
		return eventCount;
	}

	public void setEventCount(Long eventCount) {
		this.eventCount = eventCount;
	}

}
