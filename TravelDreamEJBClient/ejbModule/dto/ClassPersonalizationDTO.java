package dto;

import entity.ClassPersonalization;

public class ClassPersonalizationDTO extends PersonalizationDTO {
	private String _class;
	
	public ClassPersonalizationDTO(ClassPersonalization classPersonalization) {
		this._class = classPersonalization.getClass_();
		this.id = classPersonalization.getId();
	}

	public ClassPersonalizationDTO(String _class) {
		super();
		this._class = _class;
		this.id=-1;
	}

	public String get_class() {
		return _class;
	}

	public void set_class(String _class) {
		this._class = _class;
	}

}
