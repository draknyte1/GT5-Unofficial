package gregtech.loaders.oreprocessing;

import gregtech.api.GregTech_API;
import gregtech.api.enums.ConfigCategories;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.SubTag;
import gregtech.api.util.GT_Log;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class ProcessingBlock implements gregtech.api.interfaces.IOreRecipeRegistrator {

	private boolean isGem = false;

	public ProcessingBlock() {
		OrePrefixes.block.add(this);
	}

	public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
		GT_Values.RA.addCutterRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.plate, aMaterial, 9L), null, (int) Math.max(aMaterial.getMass() * 10L, 1L), 30);

		System.err.println("[GREGTECH 5 DEBUG] - Working with Material: "+aMaterial.name());

		ItemStack tStack1 = GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial, 1L);
		ItemStack tStack2 = GT_OreDictUnificator.get(OrePrefixes.gem, aMaterial, 1L);
		ItemStack tStack3 = GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial, 1L);

		GT_ModHandler.removeRecipe(new ItemStack[]{GT_Utility.copyAmount(1L, new Object[]{aStack})});

		if (tStack1 != null) {
			System.err.println("[GREGTECH 5 DEBUG] - Removing recipes for Ingot of Material: "+aMaterial.name());
			GT_ModHandler.removeRecipe(new ItemStack[]{tStack1, tStack1, tStack1, tStack1, tStack1, tStack1, tStack1, tStack1, tStack1});
		}
		if (tStack2 != null) {
			System.err.println("[GREGTECH 5 DEBUG] - Removing recipes for Gem of Material: "+aMaterial.name());
			GT_ModHandler.removeRecipe(new ItemStack[]{tStack2, tStack2, tStack2, tStack2, tStack2, tStack2, tStack2, tStack2, tStack2});
		}
		if (tStack3 != null) {
			System.err.println("[GREGTECH 5 DEBUG] - Removing recipes for Dust of Material: "+aMaterial.name());
			GT_ModHandler.removeRecipe(new ItemStack[]{tStack3, tStack3, tStack3, tStack3, tStack3, tStack3, tStack3, tStack3, tStack3});
		}
		if (aMaterial.mStandardMoltenFluid != null) {
			GT_Values.RA.addFluidSolidifierRecipe(ItemList.Shape_Mold_Block.get(0L, new Object[0]), aMaterial.getMolten(1296L), GT_OreDictUnificator.get(OrePrefixes.block, aMaterial, 1L), 288, 8);
		}
		if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.storageblockcrafting, OrePrefixes.block.get(aMaterial).toString(), false)) {
			if ((tStack1 == null) && (tStack2 == null) && (tStack3 != null)){
				System.err.println("[GREGTECH 5 DEBUG] Is tStack1 null?  "+tStack1.toString()+" | Is tStack2 null? "+tStack2.toString()+" | Is tStack3 valid? "+tStack3.toString());
				GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.block, aMaterial, 1L), new Object[]{"XXX", "XXX", "XXX", Character.valueOf('X'), OrePrefixes.dust.get(aMaterial)});
			}
			if (tStack2 != null){ 
				System.err.println("[GREGTECH 5 DEBUG] Is tStack1 null?  "+tStack1.toString()+" | Is tStack2 valid? "+tStack2.toString()+" | Is tStack3 null? "+tStack3.toString());
				GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.block, aMaterial, 1L), new Object[]{"XXX", "XXX", "XXX", Character.valueOf('X'), OrePrefixes.gem.get(aMaterial)});
			}
			if (tStack1 != null) {
				System.err.println("[GREGTECH 5 DEBUG] Is tStack1 valid?  "+tStack1.toString()+" | Is tStack2 null? "+tStack2.toString()+" | Is tStack3 null? "+tStack3.toString());
				GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.block, aMaterial, 1L), new Object[]{"XXX", "XXX", "XXX", Character.valueOf('X'), OrePrefixes.ingot.get(aMaterial)});
			}
		}

		if (aMaterial.contains(SubTag.CRYSTAL) && !aMaterial.contains(SubTag.METAL)){
			System.err.println("[GREGTECH 5 DEBUG] THIS IS A GEM MATERIAL, APPARENTLY.");
			isGem = true;
		}
		else {
			System.err.println("[GREGTECH 5 DEBUG] THIS IS NOT A GEM MATERIAL, APPARENTLY.");
			isGem = false;
		}
		//Ingots
		if (tStack1 != null) {
			System.err.println("[GREGTECH 5 DEBUG] Output for tStack1.stackSize = 9");
			tStack1.stackSize = 9;
		}
		//Gems
		if (tStack2 != null && !isGem) {
			System.err.println("[GREGTECH 5 DEBUG] Output for tStack2.stackSize = 9");
			tStack2.stackSize = 9;
		}
		else if (tStack2 != null && isGem) {
			System.err.println("[GREGTECH 5 DEBUG] Output for tStack2.stackSize = 0");
			tStack2.stackSize = 0;
		}
		//Dusts
		if (tStack3 != null && !isGem) {
			System.err.println("[GREGTECH 5 DEBUG] Output for tStack3.stackSize = 9");
			tStack3.stackSize = 9;
		}
		else if (tStack3 != null && isGem) {
			System.err.println("[GREGTECH 5 DEBUG] Output for tStack3.stackSize = 0");
			tStack3.stackSize = 0;
		}

		//Gems in FORGE HAMMER
		if (tStack2 != null && !isGem) {
			System.err.println("[GREGTECH 5 DEBUG] Output for tStack2.stackSize = 9 | FORGE HAMMER RECIPE [metalBlock->9x ingots]");
			tStack2.stackSize = 9;
			GT_Values.RA.addForgeHammerRecipe(aStack, tStack2, 100, 24);
		}
		else if (tStack2 != null && isGem) {
			System.err.println("[GREGTECH 5 DEBUG] Output for tStack2.stackSize = 9 | FORGE HAMMER RECIPE [gemBlock->9x gem]");
			tStack2.stackSize = 9;
			GT_Values.RA.addForgeHammerRecipe(aStack, tStack2, 100, 24);
			tStack2.stackSize = 0;
		}
		

		if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.storageblockdecrafting, OrePrefixes.block.get(aMaterial).toString(), tStack2 != null)) {
			if (tStack3 != null)
				GT_ModHandler.addShapelessCraftingRecipe(tStack3, new Object[]{OrePrefixes.block.get(aMaterial)});
			if (tStack2 != null)
				GT_ModHandler.addShapelessCraftingRecipe(tStack2, new Object[]{OrePrefixes.block.get(aMaterial)});
			if (tStack1 != null) {
				GT_ModHandler.addShapelessCraftingRecipe(tStack1, new Object[]{OrePrefixes.block.get(aMaterial)});
			}
		}
		switch (aMaterial) {
		case Mercury:
			System.err.println("'blockQuickSilver'?, In which Ice Desert can you actually place this as a solid Block?");
			break;
		case Iron:
		case WroughtIron:
			GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), ItemList.Shape_Extruder_Rod.get(0L, new Object[0]), ItemList.IC2_ShaftIron.get(1L, new Object[0]), 640, 120);
			GT_Values.RA.addAssemblerRecipe(ItemList.IC2_Compressed_Coal_Ball.get(8L, new Object[0]), GT_Utility.copyAmount(1L, new Object[]{aStack}), ItemList.IC2_Compressed_Coal_Chunk.get(1L, new Object[0]), 400, 4);
			break;
		case Steel:
			GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), ItemList.Shape_Extruder_Rod.get(0L, new Object[0]), ItemList.IC2_ShaftSteel.get(1L, new Object[0]), 1280, 120);
			GT_Values.RA.addAssemblerRecipe(ItemList.IC2_Compressed_Coal_Ball.get(8L, new Object[0]), GT_Utility.copyAmount(1L, new Object[]{aStack}), ItemList.IC2_Compressed_Coal_Chunk.get(1L, new Object[0]), 400, 4);
		}
	}
}
