package codemetropolis.toolchain.rendering.exceptions;

import codemetropolis.toolchain.commons.cmxml.Buildable.Type;
import codemetropolis.toolchain.rendering.model.building.Building;

public class BuildingTypeMismatchException extends Exception {

	private static final long serialVersionUID = 7171725393210241725L;

	public BuildingTypeMismatchException() {
		super();
	}

	public BuildingTypeMismatchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public BuildingTypeMismatchException(String message, Throwable cause) {
		super(message, cause);
	}

	public BuildingTypeMismatchException(String message) {
		super(message);
	}

	public BuildingTypeMismatchException(Throwable cause) {
		super(cause);
	}
	
	public BuildingTypeMismatchException(Type buildableType, Class<? extends Building> buildingType) {
		this(String.format("Buildable of type '%s' cannot be converted to '%s'", buildableType.name(), buildingType.getName()));
	}
	
}
