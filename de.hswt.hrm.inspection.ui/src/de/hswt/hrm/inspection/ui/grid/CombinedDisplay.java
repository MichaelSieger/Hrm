package de.hswt.hrm.inspection.ui.grid;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.swt.graphics.Color;

import com.google.common.base.Preconditions;

import de.hswt.hrm.inspection.model.BiologicalRating;
import de.hswt.hrm.inspection.model.PhysicalRating;
import de.hswt.hrm.scheme.model.SchemeComponent;
import de.hswt.hrm.scheme.ui.Colorbox;
import de.hswt.hrm.scheme.ui.SchemeGridItem;

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
		Collection<SchemeGridItem> items = grid.getItems();
		for(BiologicalRating r : biologicalRatings){
			SchemeComponent component = findById(items, r.getComponent());
			int rating = r.getRating();
			Preconditions.checkArgument(rating >= 0 && rating <= colors.length);
			double m = ((double)component.getWidth())/2 + 1;
			Colorbox b = new Colorbox(component.getX(), component.getY(), m, component.getHeight(), colors[rating]);
			grid.addColorbox(b);
		}
		for(PhysicalRating r : physicalRatings){
			SchemeComponent component = findById(items, r.getComponent());
			int rating = r.getRating();
			Preconditions.checkArgument(rating >= 0 && rating <= colors.length);
			double m = ((double)r.getComponent().getWidth())/2;
			Colorbox b = new Colorbox(m + component.getX(), component.getY(), component.getWidth() - m, component.getHeight(), colors[rating]);
			grid.addColorbox(b);
		}
	}

}
