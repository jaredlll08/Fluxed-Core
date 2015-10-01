package fluxedCore.buffs;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import fluxedCore.reference.Reference;
import fluxedCore.util.CoordinatePair;
import fluxedCore.util.ResourceInformation;

public class BuffTest extends Buff {

	public BuffTest() {
		super("Test", false, new ResourceInformation(Reference.BUFF_LOCATION, new CoordinatePair(0, 0), new CoordinatePair(16, 16)));
	}

	@Override
	public void onBuffTick(World world, EntityLivingBase entity, int duration, int power) {
		if(entity instanceof EntityPlayer){
			EntityPlayer player = (EntityPlayer)entity;
			player.setHealth(1);
		}else{
			entity.motionY = 0.5;
		}
	}

}
