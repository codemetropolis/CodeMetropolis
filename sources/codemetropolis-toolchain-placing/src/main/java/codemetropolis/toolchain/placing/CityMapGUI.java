package codemetropolis.toolchain.placing;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.ScrollPane;
import java.awt.event.WindowEvent;

import codemetropolis.toolchain.commons.cmxml.Buildable;
import codemetropolis.toolchain.commons.cmxml.BuildableTree;

public class CityMapGUI extends Frame {
	
	private static final long serialVersionUID = 1L;
	
	private static final int SCALE = 3;
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	
	public CityMapGUI(BuildableTree buildables) {

		super("Map");
		setSize(WIDTH, HEIGHT);
		setLayout(new BorderLayout()); 

		ScrollPane scroller = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
		scroller.add(new CityMapCanvas(buildables));
		scroller.setSize(WIDTH, HEIGHT);

		add("Center", scroller);
		
		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				System.exit(0);
			}
		});
	}
	
	public class CityMapCanvas extends Canvas {
		
		private static final long serialVersionUID = 1L;

		Dimension preferredSize;
		
		private BuildableTree buildables;
		
		public CityMapCanvas(BuildableTree buildables) {
			super();
			this.buildables = buildables;
			
			int mapWidth = 0;
			int mapHeight = 0;
			
			for(Buildable b : buildables.getBuildables()) {
				if( mapWidth < (b.getPositionX() + b.getSizeX()) * SCALE) 
					mapWidth = (b.getPositionX() + b.getSizeX()) * SCALE;
				if( mapHeight < (b.getPositionZ() + b.getSizeZ()) * SCALE) 
					mapHeight = (b.getPositionZ() + b.getSizeZ()) * SCALE;	
			}
			
			preferredSize = new Dimension(mapWidth + 10, mapHeight + 10);
			
		}

		@Override
	    public Dimension getPreferredSize() {
	        return preferredSize;
	    }
		
	    @Override
	    public void paint(Graphics g) {
	    	
	    	Graphics2D g2d = (Graphics2D) g;
	    	
	    	for(Buildable b : buildables.getBuildables()) {
	        	switch(b.getType()) {
	        		case GROUND :
	        			g2d.setColor(new Color(240, 180, 100)); //orange
	        			break;
	        		case GARDEN :
	        			g2d.setColor(new Color(40, 80, 140)); //blue
	        			break;
	        		case FLOOR :
	        			g2d.setColor(new Color(170, 200, 30)); //green
	        			break;
	        		case CELLAR :
	        			g2d.setColor(new Color(230, 50, 40)); //red
	        			break;
	        		case TUNNEL :
	        			g2d.setColor(new Color(122, 122, 122)); //grey
	        			break;
	        		default:
	        			break;
	        	}
	        	g2d.drawRect(b.getPositionX() * SCALE, b.getPositionZ() * SCALE, b.getSizeX() * SCALE, b.getSizeZ() * SCALE);
	        }
	    	
	    }
	      
	}

}

