package fluxedCore.util;

import net.minecraft.util.ResourceLocation;

public class ResourceInformation {
	
	private final ResourceLocation location;
	private final CoordinatePair uvPair;
	private final CoordinatePair sizePair;
	/**
	 * @param location
	 * @param uvPair
	 * @param xyPair
	 */
	public ResourceInformation(ResourceLocation location, CoordinatePair uvPair, CoordinatePair sizePair) {
		this.location = location;
		this.uvPair = uvPair;
		this.sizePair = sizePair;
	}
	public ResourceLocation getLocation() {
		return location;
	}
	public CoordinatePair getUvPair() {
		return uvPair;
	}
	public CoordinatePair getSizePair() {
		return sizePair;
	}

	
	
}
