package com.mraof.minestuck.block;

import com.mraof.minestuck.block.BlockAspectLog.BlockType;
import com.mraof.minestuck.item.MinestuckItems;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockLog.EnumAxis;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockAspectLog3 extends BlockLog
{
	public static final PropertyEnum<BlockType> VARIANT = PropertyEnum.create("variant", BlockType.class);
	
	public BlockAspectLog3()
	{
		super();
		setCreativeTab(MinestuckItems.tabMinestuck);
		setDefaultState(blockState.getBaseState().withProperty(VARIANT, BlockType.ASPECT_TIME).withProperty(LOG_AXIS, BlockLog.EnumAxis.Y));
		setUnlocalizedName("logAspect");
	}
	
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {VARIANT, LOG_AXIS});
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		IBlockState iblockstate = this.getDefaultState().withProperty(VARIANT, BlockType.values()[meta&3]);
		iblockstate = iblockstate.withProperty(LOG_AXIS, BlockLog.EnumAxis.values()[(meta>>2)&3]);
		
		return iblockstate;
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		int i = state.getValue(VARIANT).ordinal();
		
		i |= state.getValue(LOG_AXIS).ordinal()<<2;
		
		return i;
	}
	
	@Override
	public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face)
	{
		return 5;
	}
	
	@Override
	public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face)
	{
		return 5;
	}
	
	@Override
	public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		BlockType type = state.getValue(VARIANT);
		if(state.getValue(LOG_AXIS).equals(EnumAxis.Y))
			return type.topColor;
		else return type.sideColor;
	}
	
	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items)
	{
		for(BlockType type : BlockType.values())
			items.add(new ItemStack(this, 1, type.ordinal()));
	}
	
	@Override
	protected ItemStack getSilkTouchDrop(IBlockState state)
	{
		return new ItemStack(Item.getItemFromBlock(this), 1, state.getValue(VARIANT).ordinal());
	}
	
	@Override
	public int damageDropped(IBlockState state)
	{
		return state.getValue(VARIANT).ordinal();
	}
	
	public enum BlockType implements IStringSerializable
	{
		ASPECT_RAGE("aspect_rage", "aspectRage", MapColor.WOOD, MapColor.WOOD),
		ASPECT_SPACE("aspect_space", "aspectSpace", MapColor.WOOD, MapColor.WOOD),
		ASPECT_TIME("aspect_time", "aspectTime", MapColor.WOOD, MapColor.WOOD),
		ASPECT_VOID("aspect_void", "aspectVoid", MapColor.WOOD, MapColor.WOOD);
		
		private final String name;
		private final String unlocalizedName;
		private final MapColor topColor, sideColor;
		
		BlockType(String name, String unlocalizedName, MapColor topColor, MapColor sideColor)
		{
			this.name = name;
			this.unlocalizedName = unlocalizedName;
			this.topColor = topColor;
			this.sideColor = sideColor;
		}
		
		@Override
		public String getName()
		{
			return name;
		}
		
		public String getUnlocalizedName()
		{
			return unlocalizedName;
		}
	}
}