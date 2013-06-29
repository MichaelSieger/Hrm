package de.hswt.hrm.inspection.model;

import com.google.common.base.Optional;

import de.hswt.hrm.scheme.model.SchemeComponent;

public interface Rating {

	SchemeComponent getComponent();
	Optional<SamplingPointType> getSamplingPointType();
	
}
