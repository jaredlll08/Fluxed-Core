package fluxedCore.util;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

/**
 * Created by Jared on 10/22/2015.
 */
public class EntityUtils {
	public static MovingObjectPosition getTargetBlock(World world, Entity entity, boolean par3) {
		float var4 = 1.0F;
		float pitch = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * var4;

		float yaw = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * var4;

		double posX = entity.prevPosX + (entity.posX - entity.prevPosX) * var4;

		double posY = entity.prevPosY + (entity.posY - entity.prevPosY) * var4 + 1.62D - entity.getYOffset();

		double posZ = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * var4;

		Vec3 var13 = new Vec3(posX, posY, posZ);
		float var14 = MathHelper.cos(-yaw * 0.01745329F - 3.141593F);
		float var15 = MathHelper.sin(-yaw * 0.01745329F - 3.141593F);
		float var16 = -MathHelper.cos(-pitch * 0.01745329F);
		float var17 = MathHelper.sin(-pitch * 0.01745329F);
		float var18 = var15 * var16;
		float var20 = var14 * var16;
		double var21 = 10.0D;
		Vec3 var23 = var13.addVector(var18 * var21, var17 * var21, var20 * var21);

		return world.rayTraceBlocks(var13, var23, par3, !par3, false);
	}

	public static MovingObjectPosition getTargetBlock(World world, double x, double y, double z, float yaw, float pitch, boolean par3, double range) {
		Vec3 vec = new Vec3(x, y, z);
		float var14 = MathHelper.cos(-yaw * 0.01745329F - 3.141593F);
		float var15 = MathHelper.sin(-yaw * 0.01745329F - 3.141593F);
		float var16 = -MathHelper.cos(-pitch * 0.01745329F);
		float var17 = MathHelper.sin(-pitch * 0.01745329F);
		float var18 = var15 * var16;
		float var20 = var14 * var16;
		Vec3 var23 = vec.addVector(var18 * range, var17 * range, var20 * range);

		return world.rayTraceBlocks(vec, var23, par3, !par3, false);
	}

	@SuppressWarnings("rawtypes")
	public static Entity getPointedEntity(World world, EntityLivingBase entityplayer, double range, double collideRadius, boolean nonCollide) {
		Entity pointedEntity = null;
		double d = range;
		Vec3 vec3d = new Vec3(entityplayer.posX, entityplayer.posY + entityplayer.getEyeHeight(), entityplayer.posZ);
		Vec3 vec3d1 = entityplayer.getLookVec();
		Vec3 vec3d2 = vec3d.addVector(vec3d1.xCoord * d, vec3d1.yCoord * d, vec3d1.zCoord * d);
		double f1 = collideRadius;
		List list = world.getEntitiesWithinAABBExcludingEntity(entityplayer, entityplayer.getCollisionBoundingBox().addCoord(vec3d1.xCoord * d, vec3d1.yCoord * d, vec3d1.zCoord * d).expand(f1, f1, f1));

		double d2 = 0.0D;
		for (int i = 0; i < list.size(); i++) {
			Entity entity = (Entity) list.get(i);
			MovingObjectPosition mop = world.rayTraceBlocks(new Vec3(entityplayer.posX, entityplayer.posY + entityplayer.getEyeHeight(), entityplayer.posZ),new Vec3(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ), false);
			if (((entity.canBeCollidedWith()) || (nonCollide)) && mop == null) {
				float f2 = Math.max(0.8F, entity.getCollisionBorderSize());
				AxisAlignedBB axisalignedbb = entity.getCollisionBoundingBox().expand(f2, f2, f2);
				MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3d, vec3d2);
				if (axisalignedbb.isVecInside(vec3d)) {
					if ((0.0D < d2) || (d2 == 0.0D)) {
						pointedEntity = entity;
						d2 = 0.0D;
					}

				} else if (movingobjectposition != null) {
					double d3 = vec3d.distanceTo(movingobjectposition.hitVec);
					if ((d3 < d2) || (d2 == 0.0D)) {
						pointedEntity = entity;
						d2 = d3;
					}
				}
			}
		}
		return pointedEntity;
	}
}
