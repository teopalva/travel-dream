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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((_class == null) ? 0 : _class.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		ClassPersonalizationDTO other = (ClassPersonalizationDTO) obj;
		if (_class == null) {
			if (other._class != null)
				return false;
		} else if (!_class.equals(other._class))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ClassPersonalizationDTO [_class=" + _class + "]";
	}
}
