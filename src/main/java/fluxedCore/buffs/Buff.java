package fluxedCore.buffs;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import fluxedCore.util.ResourceInformation;

public abstract class Buff {

	private final String unlocalizedName;
	private final boolean isDebuff;
	private final ResourceInformation resourceInformation;

	public Buff(String unlocalizedName, boolean isDebuff, ResourceInformation resourceInformation) {
		this.unlocalizedName = unlocalizedName;
		this.isDebuff = isDebuff;
		this.resourceInformation = resourceInformation;
	}

	public boolean canUpdate() {
		return true;
	}

	public void onBuffTick(World world, EntityLivingBase entity, int duration, int power){
		
	}

	public String getUnlocalizedName() {
		return unlocalizedName;
	}
	
	public String getLocalizedName(){
		return StatCollector.translateToLocal(unlocalizedName);
	}

	public boolean isDebuff() {
		return isDebuff;
	}

	public ResourceInformation getResourceInformation() {
		return resourceInformation;
	}

	@Override
	public boolean equals(Object newBuff) {
		return unlocalizedName.equals(((Buff) newBuff).getUnlocalizedName());
	}

	@Override
	public String toString() {
		return "Buff [getUnlocalizedName()=" + getUnlocalizedName() + ", isDebuff()=" + isDebuff() + "]";
	}

}
