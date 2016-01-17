package codemetropolis.toolchain.rendering.model.pattern;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.rendering.model.BasicBlock;

public class RandomPattern implements Pattern {
	
	private static Random random = new Random();
	private Map<Pattern, Double> subPatterns = new HashMap<Pattern, Double>();
	Pattern fallbackPattern;
	
	public void add(Pattern pattern, double probability) {
		subPatterns.put(pattern, probability);
	}

	public RandomPattern( Pattern fallbackPattern ) {
		this.fallbackPattern = fallbackPattern;
	}

	@Override
	public BasicBlock applyTo(Point position, PositionModification positionModification) {
		List<Pattern> mixedPatterns = new ArrayList<Pattern>(subPatterns.keySet());
		Collections.shuffle(mixedPatterns);
		
		for( Pattern subPattern : mixedPatterns )
		{
			if ( random.nextDouble() < subPatterns.get(subPattern) )
			{
				return subPattern.applyTo( position, positionModification );
			}
		}
		
		return fallbackPattern.applyTo( position, positionModification );
	}

}
