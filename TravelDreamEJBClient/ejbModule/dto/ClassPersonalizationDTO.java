package dto;

import entity.ClassPersonalization;

public class ClassPersonalizationDTO extends PersonalizationDTO {
	private String _class;
	
	public ClassPersonalizationDTO(ClassPersonalization classPersonalization) {
		this._class = classPersonalization.getClass_();
	}

	public ClassPersonalizationDTO(String _class) {
		super();
		this._class = _class;
	}

	public String get_class() {
		return _class;
	}

	public void set_class(String _class) {
		this._class = _class;
	}

}
