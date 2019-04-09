package milkman.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import milkman.utils.PropertyChangeEvent;

@Data
public class Dirtyable {

	private boolean dirty;
	
	@JsonIgnore
	public final PropertyChangeEvent<Boolean> onDirtyChange = new PropertyChangeEvent<>();
	
	public void setDirty(boolean dirty) {
		setDirty(dirty, true);
	}
	public void setDirty(boolean dirty, boolean notification) {
		if (dirty != this.dirty) {
			if (notification)
				onDirtyChange.invoke(this.dirty, dirty);
			this.dirty = dirty;
		}
	}
	
	public void propagateDirtyStateTo(Dirtyable other) {
		onDirtyChange.add((o,n) -> other.setDirty(n));
	}
}
