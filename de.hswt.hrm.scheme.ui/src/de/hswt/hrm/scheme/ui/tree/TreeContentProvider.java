package de.hswt.hrm.scheme.ui.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;

import de.hswt.hrm.component.model.Category;
import de.hswt.hrm.scheme.model.RenderedComponent;

/**
 * Implements the ITreeContentProvider Interface for the SchemeTree.
 * 
 * @author Michael Sieger
 *
 */
public class TreeContentProvider implements ITreeContentProvider{
    
	/**
	 * A List of all RenderedComponents
	 */
    private Collection<RenderedComponent> comps;

    @Override
    public void dispose() {
        comps = null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        comps = (Collection<RenderedComponent>) newInput;
    }

    @Override
    public Object[] getElements(Object inputElement) {
    	Category[] cats = getCategorys();
    	SchemeTreeItem[] items = new CategoryTreeItem[cats.length];
    	for(int i = 0; i < items.length; i++){
    		items[i] = new CategoryTreeItem(cats[i], getRenderedComponents(cats[i]));
    	}
        return items;
    }

    @Override
    public Object[] getChildren(final Object parentElement) {
    	return ((SchemeTreeItem) parentElement).getChildren();
    }

    @Override
    public Object getParent(Object element) {
    	return ((SchemeTreeItem) element).getParent();
    }

    @Override
    public boolean hasChildren(Object element) {
        return ((SchemeTreeItem) element).hasChildren();
    }
    
    /**
     * @param cat The Category
     * @return All RenderedComponents with this Category
     */
    private List<RenderedComponent> getRenderedComponents(Category cat){
    	List<RenderedComponent> result = new ArrayList<RenderedComponent>();
    	for(RenderedComponent c : comps){
    		Optional<Category> cCat = c.getComponent().getCategory();
    		if(cCat.isPresent() && cCat.get().equals(cat)){
    			result.add(c);
    		}
    	}
    	return result;
    }
    
    /**
     * Returns all categorys bases on those in comps
     * 
     * @return 
     */
    private Category[] getCategorys(){
        List<Category> cats = Lists.newArrayList();
        for(RenderedComponent c : comps){
        	final Optional<Category> category = c.getComponent().getCategory();
        	if(category.isPresent()){
            	if(!cats.contains(category.get())){
            		cats.add(category.get());
            	}
        	}
        }
        Category[] r = new Category[cats.size()];
        cats.toArray(r);
        return r;
    }

}
