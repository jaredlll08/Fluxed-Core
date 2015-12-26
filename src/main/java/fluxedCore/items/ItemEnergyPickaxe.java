package fluxedCore.items;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cofh.api.energy.IEnergyContainerItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fluxedCore.reference.Reference;

public class ItemEnergyPickaxe extends ItemPickaxe implements IEnergyContainerItem {

	private int maxCapacity = 10000;

	public ItemEnergyPickaxe(ItemTool pick) {
		super(pick.func_150913_i());
		name = pick.getUnlocalizedName();
		toolMaterial = pick;
		maxCapacity = 10000;
		setMaxDamage(maxCapacity);
	}

	@Override
	public boolean canHarvestBlock(Block block, ItemStack itemStack) {
		return toolMaterial.canHarvestBlock(block, new ItemStack(toolMaterial)) && getEnergyStored(itemStack) >= 250;
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity player, int slot, boolean p_77663_5_) {
		if (stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
		}
		stack.setItemDamage(1);
	}

	@Override
	public int getDisplayDamage(ItemStack stack) {
		return getDamageFromEnergy(stack, maxCapacity);
	}

	@Override
	public int getDamage(ItemStack stack) {
		return getDamageFromEnergy(stack, maxCapacity);
	}

	@Override
	public boolean onBlockDestroyed(ItemStack itemstack, World world, Block block, int x, int y, int z, EntityLivingBase ent) {
		if (getEnergyStored(itemstack) >= 125 && toolMaterial.onBlockDestroyed(new ItemStack(toolMaterial), world, block, x, y, z, ent)) {
			extractEnergy(itemstack, 125, false);
		}
		return true;
	}

	@Override
	public boolean onBlockStartBreak(ItemStack itemstack, int x, int y, int z, EntityPlayer player) {
		if (getEnergyStored(itemstack) >= 125 && toolMaterial.onBlockStartBreak(new ItemStack(toolMaterial), x, y, z, player)) {
			extractEnergy(itemstack, 125, false);
		}
		return false;
	}

	public float func_150893_a(ItemStack par1ItemStack, Block par2Block) {
		if (getEnergyStored(par1ItemStack) < 125) {
			return 1;
		} else {
			return toolMaterial.func_150893_a(new ItemStack(toolMaterial), par2Block);
		}
	}

	private IIcon overlay;
	private IIcon underlay;
	private String name;
	private ItemTool toolMaterial;

	@Override
	public void registerIcons(IIconRegister icon) {
		overlay = icon.registerIcon(Reference.MOD_ID + ":energyPickaxeOverlay");
		underlay = toolMaterial.getIconFromDamage(0);
	}

	@Override
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	public int getRenderPasses(int metadata) {
		return 2;
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamageForRenderPass(int par1, int par2) {
		return par2 == 0 ? this.underlay : this.overlay;
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return "Fluxed " + StatCollector.translateToLocal(name + ".name");
	}

	@Override
	public int receiveEnergy(ItemStack container, int maxReceive, boolean simulate) {
		if (container.stackTagCompound == null) {
			container.stackTagCompound = new NBTTagCompound();
		}
		int energy = container.stackTagCompound.getInteger("energy");
		int energyReceived = Math.min(maxCapacity - energy, Math.min(100, maxReceive));
		if (!simulate) {
			energy += energyReceived;
			container.stackTagCompound.setInteger("energy", energy);
		}
		return energyReceived;
	}

	@Override
	public int extractEnergy(ItemStack container, int maxExtract, boolean simulate) {
		if (container == null || container.getTagCompound() == null)
			return 0;
		int available = container.stackTagCompound.getInteger("energy");
		int removed;
		if (maxExtract < available) {
			if (!simulate)
				container.stackTagCompound.setInteger("energy", available - maxExtract);
			removed = maxExtract;
		} else {
			if (!simulate) {
				container.stackTagCompound.setInteger("energy", 0);
			}
			removed = available;
		}
		if (!simulate)
			container.setItemDamage(getDamageFromEnergy(container, container.getMaxDamage()));
		return removed;
	}

	private int getDamageFromEnergy(ItemStack stack, int max) {
		return ((int) (Math.abs(((float) getEnergyStored(stack) / maxCapacity) - 1) * max) + 1);
	}

	@Override
	public int getEnergyStored(ItemStack container) {
		if (container == null || container.stackTagCompound == null || !container.stackTagCompound.hasKey("energy"))
			return 0;
		return container.stackTagCompound.getInteger("energy");
	}

	@Override
	public int getMaxEnergyStored(ItemStack container) {
		return 10000;
	}

	@Override
	public float getDigSpeed(ItemStack stack, Block block, int meta) {
		if (getEnergyStored(stack) < 125) {
			return 0.1f;
		}
		return toolMaterial.getDigSpeed(stack, block, meta) + (toolMaterial.getDigSpeed(stack, block, meta) / 2);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_) {
		return toolMaterial.onItemRightClick(p_77659_1_, p_77659_2_, p_77659_3_);
	}

	@Override
	public boolean onItemUse(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
		return toolMaterial.onItemUse(p_77648_1_, p_77648_2_, p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_8_, p_77648_9_, p_77648_10_);
	}
}
