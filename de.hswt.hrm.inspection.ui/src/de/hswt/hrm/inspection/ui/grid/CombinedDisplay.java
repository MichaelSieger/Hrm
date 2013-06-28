package de.hswt.hrm.inspection.ui.grid;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.swt.graphics.Color;

import com.google.common.base.Preconditions;

import de.hswt.hrm.inspection.model.BiologicalRating;
import de.hswt.hrm.inspection.model.PhysicalRating;
import de.hswt.hrm.scheme.model.SchemeComponent;
import de.hswt.hrm.scheme.ui.Colorbox;

public class CombinedDisplay extends RatingDisplay{

	private Collection<BiologicalRating> biologicalRatings = new ArrayList<>();
	private Collection<PhysicalRating> physicalRatings = new ArrayList<>();
	
	public CombinedDisplay(InspectionSchemeGrid grid, SamplingPoints samplingPoints) {
		super(grid, samplingPoints);
	}
	
	public void updateBiological(Collection<BiologicalRating> biologicalRatings){
		this.biologicalRatings = biologicalRatings;
		update();
	}
	
	public void updatePhysical(Collection<PhysicalRating> physicalRatings){
		this.physicalRatings = physicalRatings;
		update();
	}
	
	private void update(){
		InspectionSchemeGrid grid = getSchemeGrid();
		grid.clearColors();
		Color[] colors = getColors();
		for(BiologicalRating r : biologicalRatings){
			int rating = r.getRating();
			Preconditions.checkArgument(rating >= 0 && rating <= colors.length);
			SchemeComponent c = r.getComponent();
			double m = ((double)r.getComponent().getWidth())/2 + 1;
			Colorbox b = new Colorbox(c.getX(), c.getY(), m, c.getHeight(), colors[rating]);
			grid.addColorbox(b);
		}
		for(PhysicalRating r : physicalRatings){
			int rating = r.getRating();
			Preconditions.checkArgument(rating >= 0 && rating <= colors.length);
			SchemeComponent c = r.getComponent();
			double m = ((double)r.getComponent().getWidth())/2;
			Colorbox b = new Colorbox(m + c.getX(), c.getY(), c.getWidth() - m, c.getHeight(), colors[rating]);
			grid.addColorbox(b);
		}
	}

}
