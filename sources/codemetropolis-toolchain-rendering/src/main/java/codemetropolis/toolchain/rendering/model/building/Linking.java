package codemetropolis.toolchain.rendering.model.building;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.rendering.exceptions.RenderingException;
import codemetropolis.toolchain.rendering.model.BasicBlock;
import codemetropolis.toolchain.rendering.model.pattern.RepeationPattern;
import codemetropolis.toolchain.rendering.model.primitive.SolidBox;
import codemetropolis.toolchain.rendering.util.Orientation;

public abstract class Linking extends Building {
	public static final int MIN_SIZE = 2;
	public static final String LINKING_ATTRIBUTE_TARGET = "target";
	public static final String LINKING_ATTRIBUTE_STANDALONE = "standalone";
	public static final String LINKING_ATTRIBUTE_ORIENTATION = "orientation";
	
	protected boolean standalone;
	protected String orientation;
	protected int level;
	protected int height;
	protected int width;
	
	public Linking(Buildable innerBuildable) throws RenderingException {
		
		this.innerBuildable = innerBuildable;

		
		if (this.innerBuildable.hasAttribute(LINKING_ATTRIBUTE_STANDALONE)) {
			this.standalone = Boolean.parseBoolean(this.innerBuildable.getAttributeValue(LINKING_ATTRIBUTE_STANDALONE));
		} else {
			throw new RenderingException(LINKING_ATTRIBUTE_STANDALONE + " attribute not present in Linking.");
		}
		
		if (this.innerBuildable.hasAttribute(LINKING_ATTRIBUTE_ORIENTATION)) {
			this.orientation = this.innerBuildable.getAttributeValue(LINKING_ATTRIBUTE_ORIENTATION);
		} else {
			throw new RenderingException(LINKING_ATTRIBUTE_ORIENTATION + " attribute not present in Linking.");
		}
		
		size = new Point(
				adjustSize(innerBuildable.getSizeX(), MIN_SIZE),
				adjustSize(innerBuildable.getSizeY(), MIN_SIZE),
				adjustSize(innerBuildable.getSizeZ(), MIN_SIZE)
				);
		
		position = new Point(
				innerBuildable.getPositionX(),
				innerBuildable.getPositionY(),
				innerBuildable.getPositionZ()
				);
		
		center = new Point(
				(int)(size.getX() * 0.5),
				(int)(size.getY() * 0.5),
				(int)(size.getZ() * 0.5)
				);
	}
	
	protected void prepareLinking(BasicBlock[][][] material) {
		
		primitives.add(
				new SolidBox(
					new Point(position.getX(), level, position.getZ()),
					new Point(size.getX(), this.height, size.getZ()),
					new RepeationPattern( material),
					new RepeationPattern( material ),
					Orientation.NearX ) );
		
	}
	
	public void prepareStairs() {
		BasicBlock _air = new BasicBlock( (short) 0 );
		BasicBlock _str = new BasicBlock( (short) 1 );
		BasicBlock _cre = new BasicBlock( (short) 85 );
		
		BasicBlock[][][] steps =
				new BasicBlock[][][]
				{
					{
						{ _str, _air, _air },
						{ _air, _cre, _air },
						{ _air, _air, _air }
					},
					{
						{ _air, _str, _air },
						{ _air, _cre, _air },
						{ _air, _air, _air }
					},
					{
						{ _air, _air, _str },
						{ _air, _cre, _air },
						{ _air, _air, _air }
					},
					{
						{ _air, _air, _air },
						{ _air, _cre, _str },
						{ _air, _air, _air }
					},
					{
						{ _air, _air, _air },
						{ _air, _cre, _air },
						{ _air, _air, _str }
					},
					{
						{ _air, _air, _air },
						{ _air, _cre, _air },
						{ _air, _str, _air }
					},
					{
						{ _air, _air, _air },
						{ _air, _cre, _air },
						{ _str, _air, _air }
					},
					{
						{ _air, _air, _air },
						{ _str, _cre, _air },
						{ _air, _air, _air }
					}
				};
		
		if (!this.innerBuildable.getParent().hasStairs()) {
			this.innerBuildable.getParent().setHasStairs(true);		
		
			primitives.add(
				new SolidBox(
					calculateStepPosition(false),
					new Point( 3, calculateHeight(this.innerBuildable.getParent()) + this.height, 3 ),
					new RepeationPattern( steps ),
					new RepeationPattern( steps ),
					Orientation.NearY ) );
		}
		
		if (standalone) {
			
			String id = this.innerBuildable.getAttributeValue(LINKING_ATTRIBUTE_TARGET);
			
			if (id == null) { return; }
			
			Buildable root = this.innerBuildable.getParent();
			while(!root.isRoot()) {
				root = root.getParent();
			}
			
			Buildable target = getTarget(root, id);
			if (target == null || target.hasStairs()) { return; }
			target.setHasStairs(true);
			
			primitives.add(
					new SolidBox(
						calculateStepPosition(true),
						new Point( 3, calculateHeight(target) + this.height, 3 ),
						new RepeationPattern( steps ),
						new RepeationPattern( steps ),
						Orientation.NearY ) );
		}
		
	}
	

	public Buildable getTarget(Buildable buildable, String id) {
		Buildable b = null;
		
		if (id.equals(buildable.getId())) {
			return buildable;
		} else if (buildable.getNumberOfChildren() == 0) {
			return null;
		} else {
			for(Buildable child : buildable.getChildren()) {
				b = getTarget(child, id);
				
				if (b != null) { break; }
			}
		}
		return b;
	} 

	public abstract int calculateHeight(Buildable buildable);
	
	public abstract Point calculateStepPosition(boolean isTarget);
	
	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
}