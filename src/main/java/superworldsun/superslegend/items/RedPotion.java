package superworldsun.superslegend.items;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class RedPotion extends Item
{

	public RedPotion(Properties properties)
	{
		super(properties);
	}
	
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand)
	 {
		ItemStack stack = player.getHeldItem(hand);
		  
		 if(!world.isRemote && !player.isCreative() && player.shouldHeal())
	     {
			 
			 BlockPos currentPos = player.getPosition();
			 world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.ENTITY_WITCH_DRINK, SoundCategory.PLAYERS, 1f, 1f);
			 
			 	player.addPotionEffect(new EffectInstance(Effect.get(10), 60, 4, false, false));
				stack.shrink(1);
				player.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
				//player.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40);
	     }
		 else if (!world.isRemote && player.isCreative())
		 {
			 BlockPos currentPos = player.getPosition();
			 world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.ENTITY_WITCH_DRINK, SoundCategory.PLAYERS, 1f, 1f);
			 
			 player.addPotionEffect(new EffectInstance(Effect.get(10), 60, 4, true, true));
		 }
	 
		 return new ActionResult<>(ActionResultType.PASS, player.getHeldItem(hand)); 
	 }
	
	
	@Override
	public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.addInformation(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.RED + "The Medicine of Life"));
	}  
	
}
